package com.GMS;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.GMS.databinding.ActivityQrscannerBinding;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.Result;

import org.jetbrains.annotations.NotNull;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScannerActivity extends AppCompatActivity /*implements Detector.Processor */ implements ZXingScannerView.ResultHandler {

    ActivityQrscannerBinding mBinding;
    BarcodeDetector mBarcodeDetector;
    CameraSource mCameraSource;
    ZXingScannerView mZXingScannerView;
    final static int CAMERA_PERMISSION = 1024;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mZXingScannerView = new ZXingScannerView(getLayoutInflater().getContext());
        mBinding = ActivityQrscannerBinding.inflate(getLayoutInflater());
        mZXingScannerView = new ZXingScannerView(mBinding.getRoot().getContext());
        setContentView(mZXingScannerView);
        /*
        mBarcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
        mBarcodeDetector.setProcessor(this);
        mCameraSource = new CameraSource.Builder(getApplicationContext() , mBarcodeDetector).setRequestedPreviewSize(1024 , 768).setAutoFocusEnabled(true).build();
        final Activity activity = this;
         */
        /*
        mBinding.surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                try {

                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(activity , new String[]{Manifest.permission.CAMERA} , CAMERA_PERMISSION);
                    Toast.makeText(mBinding.getRoot().getContext() , "if" , Toast.LENGTH_SHORT).show();

                    return;
                }
                mCameraSource.start(mBinding.surfaceView.getHolder());

                }
                catch (Exception ex)
                {
                    Log.e("camera start problem" , ex.getMessage());
                }

            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                mCameraSource.stop();
            }
        });

         */
    }

    /*
        @Override
        public void release() {

        }

     */
/*
    @Override
    public void receiveDetections(Detector.Detections detections) {
        SparseArray<Barcode> mBarcodes = detections.getDetectedItems();
        StringBuilder mStringBuilder = new StringBuilder();
        if(mBarcodes.size() != 0)
        {

            for(int i=0 ; i<mBarcodes.size()  ; i++)
            {
                mStringBuilder.append(mBarcodes.valueAt(i).rawValue).append("\n");
            }

        }
       mBinding.tvValueOfScanner.post(new Runnable() {
           @Override
           public void run() {
               if(mStringBuilder.length()>0) {
                   /
                   mBinding.tvValueOfScanner.setText(mStringBuilder.toString());
                   mCameraSource.stop();
                   mBinding.surfaceView.setVisibility(View.INVISIBLE);
               }

           }
       });



    }


 */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSION:
                if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    finish();
                }
        }
    }


    @Override
    public void handleResult(Result result) {

        Toast.makeText(getApplicationContext(), result.getText().toString(), Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mZXingScannerView.stopCamera();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();


    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mBinding.getRoot().getContext(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
            Toast.makeText(mBinding.getRoot().getContext(), "if", Toast.LENGTH_SHORT).show();
            return;
        }
        startQRScanner();


    }

    private void startQRScanner() {
        mZXingScannerView.setResultHandler(this);
        mZXingScannerView.startCamera();
    }
}