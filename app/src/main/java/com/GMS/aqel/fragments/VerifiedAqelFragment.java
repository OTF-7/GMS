package com.GMS.aqel.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.GMS.R;
import com.GMS.SettingActivity;
import com.GMS.aqel.activities.AddCitizenActivity;
import com.GMS.aqel.adapters.RecyclerViewAqelAdapter;
import com.GMS.aqel.helperClass.CitizenItemOfAqel;
import com.GMS.databinding.FragmentVerifiedAqelBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class VerifiedAqelFragment extends Fragment {

    FragmentVerifiedAqelBinding mBinding;
    RecyclerViewAqelAdapter adapter;
    public static final int FRAGMENT_ID=1;
    public VerifiedAqelFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentVerifiedAqelBinding.inflate(inflater , container , false);


        ArrayList<CitizenItemOfAqel> items = new ArrayList<>();
        items.add(new CitizenItemOfAqel("Abdulrahman Khalid" , "45d55d45s55g" , 3  , R.drawable.ic_qr_need_scan));
        items.add(new CitizenItemOfAqel("Omar Taha" , "45d55d45s55g" , 3 ,R.drawable.ic_qr_need_scan ));
        items.add(new CitizenItemOfAqel("Abubaker Khalid" , "45d55d45s55g" , 3 , R.drawable.ic_qr_need_scan));
        items.add(new CitizenItemOfAqel("Mohammed Shihab" , "45d55d45s55g" ,  3, R.drawable.ic_qr_need_scan));
        items.add(new CitizenItemOfAqel("Omar swaid" , "45d55d45s55g" , 3 , R.drawable.ic_qr_need_scan));
        items.add(new CitizenItemOfAqel("Khalid Someeri" , "45d55d45s55g" , 3 , R.drawable.ic_qr_need_scan));

        adapter = new RecyclerViewAqelAdapter(items , FRAGMENT_ID);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.rvVerifiedFragment.setHasFixedSize(true);
        mBinding.rvVerifiedFragment.setLayoutManager(layoutManager);
        mBinding.rvVerifiedFragment.setAdapter(adapter);


        return mBinding.getRoot();

    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_aqel_top_bar , menu);
        MenuItem searchItem = menu.findItem(R.id.search_ic);
        SearchView searchView= (SearchView) searchItem.getActionView();

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

        switch(item.getItemId())
        {
            case R.id.add_citizen :
                Intent mAddCitizenIntent = new Intent(this.getActivity()  , AddCitizenActivity.class);
                startActivity(mAddCitizenIntent);
                break;
            case R.id.setting_item:
                Intent mSettingIntent = new Intent(this.getActivity() , SettingActivity.class);
                startActivity(mSettingIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}