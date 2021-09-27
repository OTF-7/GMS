package com.GMS.aqel.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.GeneralClasses.CitizenItemClickListener;
import com.GMS.R;
import com.GMS.SettingActivity;
import com.GMS.aqel.activities.AddCitizenActivity;
import com.GMS.aqel.activities.AqelNotificationsActivity;
import com.GMS.aqel.adapters.RecyclerViewAqelAdapter;
import com.GMS.aqel.helperClass.CitizenItemOfAqel;
import com.GMS.databinding.FragmentNeedScanAqelBinding;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NeedScanAqelFragment extends Fragment {

    public static final int FRAGMENT_ID = 1;
    FragmentNeedScanAqelBinding mBinding;
    RecyclerViewAqelAdapter adapter;
    CitizenItemClickListener mItemClickListener;
    private Dialog mDialog;
    private TextInputEditText textInputEditTextQTY;
    private TextView tvTotal;
    private static int Qty;
    // this for dealing with Runnable an clean it
    Handler mHandler = new Handler();

    public NeedScanAqelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentNeedScanAqelBinding.inflate(inflater, container, false);
        createDialog();
        ArrayList<CitizenItemOfAqel> items = new ArrayList<>();
        items.add(new CitizenItemOfAqel("Abdulrahman Khalid", "45d55d45s55g", 3, R.drawable.ic_qr_need_scan));
        items.add(new CitizenItemOfAqel("Omar Taha", "45sd6fs", 3, R.drawable.ic_qr_need_scan));
        items.add(new CitizenItemOfAqel("Abubaker Khalid", "esfdds", 3, R.drawable.ic_qr_need_scan));
        items.add(new CitizenItemOfAqel("Mohammed Shihab", "afdes", 3, R.drawable.ic_qr_need_scan));
        items.add(new CitizenItemOfAqel("Omar swaid", "asdfa", 3, R.drawable.ic_qr_need_scan));
        items.add(new CitizenItemOfAqel("Hasan Someeri", "fasfs", 3, R.drawable.ic_qr_need_scan));

        mItemClickListener = new CitizenItemClickListener() {
            @Override
            public void onClick(int position, String id) {
                showDialog();
                Toast.makeText(getActivity().getApplicationContext(), "id  is : " + id, Toast.LENGTH_SHORT).show();
            }
        };
        adapter = new RecyclerViewAqelAdapter(items, FRAGMENT_ID, mItemClickListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.rvNeedScanFragment.setHasFixedSize(true);
        mBinding.rvNeedScanFragment.setLayoutManager(layoutManager);
        mBinding.rvNeedScanFragment.setAdapter(adapter);
        return mBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_aqel_top_bar, menu);
        MenuItem searchItem = menu.findItem(R.id.search_ic);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }

        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_citizen:
                Intent mAddCitizenIntent = new Intent(this.getActivity(), AddCitizenActivity.class);
                startActivity(mAddCitizenIntent);
                break;
            case R.id.setting_item:
                Intent mSettingIntent = new Intent(this.getActivity(), SettingActivity.class);
                startActivity(mSettingIntent);
                break;
            case R.id.notification:
                Intent mNotifications = new Intent(this.getActivity(), AqelNotificationsActivity.class);
                startActivity(mNotifications);
        }
        return super.onOptionsItemSelected(item);
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
        tvTotal = mDialog.findViewById(R.id.tv_price);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
    }

    private void showDialog() {
        mDialog.show();
        mRunnable.run();
        mDialog.findViewById(R.id.close_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.removeCallbacks(mRunnable);
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

    private void acceptData() {
        if (textInputEditTextQTY.getText().toString().trim().equals("")) {
            Toast.makeText(getActivity(), "no Quantity", Toast.LENGTH_SHORT).show();
        } else {
            Qty = Integer.valueOf(textInputEditTextQTY.getText().toString());
            mHandler.removeCallbacks(mRunnable);
            mDialog.dismiss();
            Toast.makeText(getActivity(), String.valueOf(Qty), Toast.LENGTH_SHORT).show();
        }
    }

    // this realtime listen for changes with editText at the dialog
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (textInputEditTextQTY.getText().toString().trim().equals("")) {
                tvTotal.setText(getString(R.string.total) + " " + 0);
            } else {
                tvTotal.setText(getString(R.string.total) + " " + Integer.valueOf(textInputEditTextQTY.getText().toString()) * 5);
            }
            mHandler.postDelayed(this, 500);
        }
    };
}