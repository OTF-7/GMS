package com.GMS.login.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.GMS.R;
import com.GMS.login.adapters.RecyclerVieAdappterActions;
import com.GMS.login.helperClasses.ActionItem;

import java.util.ArrayList;


public class FavoriteFragment extends Fragment {

    RecyclerView rvActions  ;
    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_favorite_mgr, container, false);

        rvActions = view.findViewById(R.id.rv_Actions);
        ArrayList<com.GMS.login.helperClasses.ActionItem> items = new ArrayList<>();
        items.add(new ActionItem("Abdurahman" , "Omar Taha" , "Mohmmed shihab",200 , 3800 , 150 , "Alkornish"));
        items.add(new ActionItem("Abdurahman" , "Omar Taha" , "Mohmmed shihab",200 , 3800 , 150 , "Alkornish"));
        items.add(new ActionItem("Abdurahman" , "Omar Taha" , "Mohmmed shihab",200 , 3800 , 150 , "Alkornish"));
        items.add(new ActionItem("Abdurahman" , "Omar Taha" , "Mohmmed shihab",200 , 3800 , 150 , "Alkornish"));
        items.add(new ActionItem("Abdurahman" , "Omar Taha" , "Mohmmed shihab",200 , 3800 , 150 , "Alkornish"));
        items.add(new ActionItem("Abdurahman" , "Omar Taha" , "Mohmmed shihab",200 , 3800 , 150 , "Alkornish"));
        items.add(new ActionItem("Abdurahman" , "Omar Taha" , "Mohmmed shihab",200 , 3800 , 150 , "Alkornish"));

        RecyclerVieAdappterActions adapter = new RecyclerVieAdappterActions(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvActions.setHasFixedSize(true);
        rvActions.setLayoutManager(layoutManager);
        rvActions.setAdapter(adapter);
    return  view;
    }
}