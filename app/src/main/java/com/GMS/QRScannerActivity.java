package com.GMS;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.GMS.databinding.ActivityQrscannerBinding;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScannerActivity extends AppCompatActivity /*implements Detector.Processor */ implements ZXingScannerView.ResultHandler {

    private ActivityQrscannerBinding mBinding;
    private BarcodeDetector mBarcodeDetector;
    private CameraSource mCameraSource;
    private ZXingScannerView mZXingScannerView;
    final static int CAMERA_PERMISSION = 1024;
    private TextInputEditText textInputEditTextQTY;
    private Dialog mDialog;
    private static String idCitizen;
    private static int Qty;
    private String layoutName;
    private Intent layoutsIntent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mZXingScannerView = new ZXingScannerView(getLayoutInflater().getContext());
        mBinding = ActivityQrscannerBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        layoutsIntent = getIntent();
        layoutName = layoutsIntent.getStringExtra(IDOfLayout.ACTIVITY.toString());
        createDialog();

        mBinding.iBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBinding.iBtnFlashSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBinding.zxingScannerView.getFlash()) {
                    mBinding.iBtnFlashSwitch.setImageResource(R.drawable.ic_flash_off);
                    mBinding.zxingScannerView.setFlash(false);
                } else {
                    mBinding.iBtnFlashSwitch.setImageResource(R.drawable.ic_flash_on);
                    mBinding.zxingScannerView.setFlash(true);
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSION:
                if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startQRScanner();
                } else {
                    finish();
                }
        }
    }


    @Override
    public void handleResult(Result result) {
        idCitizen = result.getText();
        //Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT).show();

        // onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //mZXingScannerView.stopCamera();
        mBinding.zxingScannerView.stopCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();
        /*
        * from java code
        mZXingScannerView.stopCamera();
        mZXingScannerView = null;
        mBinding=null;
         */
        mBinding.zxingScannerView.stopCamera();
        mBinding = null;
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
        mBinding.zxingScannerView.setResultHandler(this);
        mBinding.zxingScannerView.startCamera();
        mBinding.zxingScannerView.setAutoFocus(true);
        if (layoutName.equals(IDOfLayout.AQELNEEDSCANFRAGNENT.toString())) {
            Toast.makeText(mBinding.getRoot().getContext(), layoutName, Toast.LENGTH_SHORT).show();
            mBinding.zxingScannerView.setFormats(Collections.singletonList(BarcodeFormat.QR_CODE));
        } else if (layoutName.equals(IDOfLayout.REPNEEDSCAN.toString())) {
            Toast.makeText(mBinding.getRoot().getContext(), layoutName, Toast.LENGTH_SHORT).show();
            mBinding.zxingScannerView.setFormats(Collections.singletonList(BarcodeFormat.CODABAR));
        }

    }

    private void showDialog() {
        mDialog.show();
        mDialog.findViewById(R.id.close_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        mDialog.findViewById(R.id.btn_accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptData();
            }
        });
    }

    private void createDialog() {
        mDialog = new Dialog(mBinding.getRoot().getContext());
        mDialog.setContentView(R.layout.accept_qr_scanner_dialoge);
        mDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        mDialog.setCancelable(true);
        textInputEditTextQTY = mDialog.findViewById(R.id.quantity_text_input);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
    }

    private void acceptData() {
        Qty = Integer.valueOf(textInputEditTextQTY.getText().toString());
        Toast.makeText(mBinding.getRoot().getContext(), idCitizen + "have " + "\n" + Qty, Toast.LENGTH_SHORT).show();
    }
}