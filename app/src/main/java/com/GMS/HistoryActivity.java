package com.GMS;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.GeneralAdapters.RecyclerViewAdapterHistory;
import com.GMS.GeneralClasses.HistoryItem;
import com.GMS.GeneralClasses.SingleItemClickListener;
import com.GMS.databinding.ActivityHistoryBinding;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import static android.graphics.BlendMode.DARKEN;

public class HistoryActivity extends AppCompatActivity {

    private ActivityHistoryBinding mBinding;
    RecyclerViewAdapterHistory adapter;
    SingleItemClickListener mSingleItemClickListener;
    ArrayList<HistoryItem> mHistoryItems = new ArrayList<>();
    private final static int PERMISSION_EXTERNAL_STORAGE=105;
    Bitmap bitmap , scaleBitmap;
  private final static int PAGE_WIDTH=1200;
    private final static int PAGE_HEIGHT=2010;
    PdfDocument pdfDocument = new PdfDocument();
    Paint myPaint = new Paint();
    Paint titlePaint = new Paint();
    PdfDocument.PageInfo myPageInfo ;
    Canvas canvas;
    private final static int TOP_START_TEXT_DRAW=500;
    private final static int POINT_TABLE_TOP_START=450;
    private final static int POINT_TABLE_BOTTOM_END=530;
    private int topStartTextDraw=TOP_START_TEXT_DRAW;
    private int pointTableTopStart=POINT_TABLE_TOP_START;
    private int pointTableBottomEnd=POINT_TABLE_BOTTOM_END;
    private int pointXStartTextDraw=40;
    private int locationXLine=180;
    private int stationXLine=480;
    private int priceXLine=780;
    private int totalXLine=960;
    private int QtyTextStartXPoint=40;
    private int locationTextStartXPoint=200;
    private int stationTextStartXPoint=500;
    private int priceTextStartPoint=800;
    private int totalTextStartXPoint=980;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        setSupportActionBar(mBinding.toolBarHistory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        mHistoryItems.add(new HistoryItem("Al-kornish", "12/10/2020", "Abubaker khalid", "General", 3650, 252));
        mHistoryItems.add(new HistoryItem("Al-kornish", "12/10/2020", "Abubaker khalid", "General", 3650, 252));
        mHistoryItems.add(new HistoryItem("Al-kornish", "12/10/2020", "Abubaker khalid", "General", 3650, 252));
        mHistoryItems.add(new HistoryItem("Al-kornish", "15/10/2020", "Abubaker khalid", "General", 3650, 252));
        mHistoryItems.add(new HistoryItem("Al-kornish", "14/10/2020", "Abubaker khalid", "General", 3650, 252));

        mSingleItemClickListener = new SingleItemClickListener() {
            @Override
            public void onClick(String location, String date) {

                Toast.makeText(getBaseContext(), "go to the details activity", Toast.LENGTH_SHORT).show();
                // here you write the code which take us to the details activity
            }
        };
        initRV();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_history, menu);
        MenuItem searchItem = menu.findItem(R.id.search_ic);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);

                return false;
            }

        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.full_report:
                createPdf();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initRV() {
        adapter = new RecyclerViewAdapterHistory(getBaseContext(), mHistoryItems, mSingleItemClickListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        mBinding.rvHistory.setLayoutManager(layoutManager);
        mBinding.rvHistory.setAdapter(adapter);
        mBinding.rvHistory.setHasFixedSize(true);
    }

    private void createPdf()
    {
      if(ActivityCompat.checkSelfPermission(getApplicationContext() , Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
      {
          ActivityCompat.requestPermissions((Activity) mBinding.getRoot().getContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_EXTERNAL_STORAGE);
        return;
      }

        myPageInfo = new PdfDocument.PageInfo.Builder(PAGE_WIDTH,PAGE_HEIGHT,1).create();

       // bitmap = BitmapFactory.decodeResource(getResources() , R.drawable.gas_cylinder);
        PdfDocument.Page myPage = pdfDocument.startPage(myPageInfo);

        myPaint.setStrokeWidth(1200);
        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT ,Typeface.BOLD));
        titlePaint.setTextSize(70);

        canvas = myPage.getCanvas();
     //   canvas.drawBitmap(bitmap ,0 ,0 ,myPaint);
        canvas.drawText("Geeky Boy" ,PAGE_WIDTH/2 ,270 ,titlePaint);
      //  canvas.drawBitmap(bitmap , 10 , 10 , myPaint);

        // draw the header of table
        drawCell();
         // draw the title of the head of table
        fillCell(0 ,"Qty" ,"Location" , "Station" ,"Price" , "Total");

        // fill the data in the each raw
        for(int i = 0 ; i<mHistoryItems.size()  ; i++)
        {
            pointTableTopStart+=80;
            pointTableBottomEnd+=80;
            drawCell();

            fillCell(1 ,String.valueOf(mHistoryItems.get(i).getQty())
                    ,mHistoryItems.get(i).getLocation()
                    , mHistoryItems.get(i).getStation()
                    ,String.valueOf(mHistoryItems.get(i).getPrice())
                    , String.valueOf(mHistoryItems.get(i).getPrice()*mHistoryItems.get(i).getQty()));

        }



        // draw the separator line between the title in the head of table
         canvas.drawLine(locationXLine,POINT_TABLE_TOP_START,locationXLine,pointTableBottomEnd ,myPaint);
         canvas.drawLine(stationXLine, POINT_TABLE_TOP_START, stationXLine,pointTableBottomEnd,myPaint);
         canvas.drawLine(priceXLine,POINT_TABLE_TOP_START,priceXLine,pointTableBottomEnd,myPaint);
         canvas.drawLine(totalXLine,POINT_TABLE_TOP_START,totalXLine,pointTableBottomEnd,myPaint);


        pdfDocument.finishPage(myPage);

        File file = new File(Environment.getExternalStorageDirectory() , "/012.pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            pdfDocument.close();
            Toast.makeText(getBaseContext() , "pdf has saved" ,Toast.LENGTH_SHORT).show();

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case PERMISSION_EXTERNAL_STORAGE:
                if(permissions.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(getBaseContext() ,"granted external storage" , Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getBaseContext() ,"dismiss external storage" , Toast.LENGTH_SHORT).show();

                }
        }
    }

    private void drawCell()
    {

        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeWidth(2);
        myPaint.setColor(Color.BLACK);


        canvas.drawRect(20,pointTableTopStart,1180,pointTableBottomEnd,myPaint);

        titlePaint.setTextSize(35f);
        titlePaint.setTextAlign(Paint.Align.LEFT);
        titlePaint.setStyle(Paint.Style.FILL);
        titlePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF ,Typeface.BOLD));

        myPaint.setTextSize(30f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setStyle(Paint.Style.FILL);

    }


    private void fillCell(int typeCell , String Qty ,String location , String station , String price , String Total)
    {

        if(typeCell==0) {
            canvas.drawText(Qty, QtyTextStartXPoint, topStartTextDraw, titlePaint);
            canvas.drawText( location, locationTextStartXPoint, topStartTextDraw, titlePaint);
            canvas.drawText(station, stationTextStartXPoint, topStartTextDraw, titlePaint);
            canvas.drawText(price, priceTextStartPoint, topStartTextDraw, titlePaint);
            canvas.drawText(Total, totalTextStartXPoint, topStartTextDraw, titlePaint);
        }
        else
        {
            canvas.drawText(Qty, QtyTextStartXPoint, topStartTextDraw, myPaint);
            canvas.drawText( location, locationTextStartXPoint, topStartTextDraw, myPaint);
            canvas.drawText(station, stationTextStartXPoint, topStartTextDraw, myPaint);
            canvas.drawText(price, priceTextStartPoint, topStartTextDraw, myPaint);
            canvas.drawText(Total, totalTextStartXPoint, topStartTextDraw, myPaint);

        }
        topStartTextDraw+=80;
    }
}