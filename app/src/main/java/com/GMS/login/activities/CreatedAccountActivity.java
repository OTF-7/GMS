package com.GMS.login.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.GMS.databinding.ActivityCreatedAccountBinding;
import com.google.firebase.auth.FirebaseAuth;

public class CreatedAccountActivity extends AppCompatActivity {

    ActivityCreatedAccountBinding mCreatedAccountBinding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCreatedAccountBinding = ActivityCreatedAccountBinding.inflate(getLayoutInflater());
        setContentView(mCreatedAccountBinding.getRoot());
        mCreatedAccountBinding.createdAccountOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                startActivity(new Intent(CreatedAccountActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}