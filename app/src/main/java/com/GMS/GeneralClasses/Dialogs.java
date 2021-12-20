package com.GMS.GeneralClasses;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import com.GMS.R;
import com.google.android.material.textfield.TextInputEditText;

public final class Dialogs {

    boolean done = false;
    private static Dialog mDialog , stationDialog , moneyDialog;
    private static TextInputEditText textInputEditTextQTY;
    private static  TextView tvTotal, tvRequiredQty;


    public Dialogs() {


    }

    public static Dialog acceptQrDialog(Context context) {
        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.accept_qr_scanner_dialoge);
        mDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        mDialog.setCancelable(true);
        textInputEditTextQTY = mDialog.findViewById(R.id.quantity_text_input);
        tvTotal = mDialog.findViewById(R.id.tv_price);
        tvRequiredQty = mDialog.findViewById(R.id.tv_required_Qty);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        return mDialog;
    }

    private void stationDialog(Context context)
    {
        stationDialog = new Dialog(context);
        stationDialog.setContentView(R.layout.station_dialoge_details);
        stationDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        Window window = stationDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        stationDialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

    }
    private void moneyDialog(Context context)
    {
        moneyDialog = new Dialog(context);
        moneyDialog.setContentView(R.layout.recieving_dialog);
        moneyDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        Window window = moneyDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        // window.getAttributes().windowAnimations = R.style.DialogAnimation;
        moneyDialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
    }
    private void showMoneyDialog()
    {
        moneyDialog.show();
    }
}
