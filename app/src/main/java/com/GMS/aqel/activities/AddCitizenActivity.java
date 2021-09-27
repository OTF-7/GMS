package com.GMS.aqel.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.GMS.R;
import com.GMS.databinding.ActivityAddCitizenBinding;

import org.jetbrains.annotations.NotNull;

public class AddCitizenActivity extends AppCompatActivity {

    ActivityAddCitizenBinding mBinding;
    private static final int CAMERA_PERMISSION = 101;
    private static final int IMAGE_CAPTURE = 102;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAddCitizenBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(mBinding.toolBarAddCitizen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] array = getResources().getStringArray(R.array.items_array);
        ArrayAdapter mAdapter = new ArrayAdapter(getBaseContext(), R.layout.spinner_item, array);
        mBinding.actvNeighborhoodName.setAdapter(mAdapter);

        mBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * to get the value of edit text by below line
                 ** mBinding.citizenFirstNameLayout.getEditText().getText().toString()
                 */
                Toast.makeText(getBaseContext(), "success", Toast.LENGTH_SHORT).show();
            }
        });
        mBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.tvTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddCitizenActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
                    return;
                }
                openCamera();
            }
        });
    }

    private void checkCameraPermission() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
            return;
        }
        openCamera();

    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSION:
                if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(getBaseContext(), "camera permission id requested to use camera ", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == IMAGE_CAPTURE) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                mBinding.ivDocumentPicture.setImageBitmap(bitmap);
            }
        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }
}