package com.GMS.login.fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.GMS.R;
import com.GMS.databinding.FragmentSingInBinding;
import com.GMS.login.activities.LoginActivity;
import com.GMS.login.utilities.Validation;
import com.GMS.login.utilities.changePagerState;
import com.GMS.manager.activities.ManagerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SingInFragment extends Fragment {
    //public static final int RC_SIGN_IN = 1;
    final int DELAYED_TIME = 2700;
    final Handler handler = new Handler();
    /*List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build(),
            new AuthUI.IdpConfig.PhoneBuilder().build());*/
    FragmentSingInBinding signinBinding;
    private FirebaseFirestore mFirestore;
    //private ArrayList<User> users;
    private FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener mAuthStateListner;

    public SingInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        signinBinding = FragmentSingInBinding.inflate(inflater, container, false);
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        //users = new ArrayList<User>();

        signinBinding.signinUserNameField.setTranslationY(800);
        signinBinding.signinUserNameField.setAlpha(0);
        signinBinding.signinPasswordField.setTranslationY(800);
        signinBinding.signinPasswordField.setAlpha(0);
        signinBinding.signInButton.setTranslationY(1600);
        signinBinding.signInButton.setAlpha(0);
        signinBinding.forgotPasswordButton.setTranslationX(2000);
        signinBinding.forgotPasswordButton.setAlpha(0);
        signinBinding.signinWithButton.setTranslationX(2000);
        signinBinding.signinWithButton.setAlpha(0);
        signinBinding.facebookSignInButton.setTranslationX(2000);
        signinBinding.facebookSignInButton.setAlpha(0.0f);
        signinBinding.googleSignInButton.setTranslationX(2000);
        signinBinding.googleSignInButton.setAlpha(0.0f);
        signinBinding.loginWithText.setTranslationY(2000);
        signinBinding.loginWithText.setAlpha(0);

        signinBinding.fingerPrintAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinBinding.fingerPrintAnimation.playAnimation();
            }
        });

        signinBinding.gotoSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                changePagerState.gotoSignUp(ActivityLoginBinding.inflate(getActivity().getLayoutInflater()));
                changePagerState.gotoSignUp(LoginActivity.loginBinding);
            }
        });
        signinBinding.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = Objects.requireNonNull(signinBinding.signinUserNameField.getEditText()).getText().toString().trim();
                String password = Objects.requireNonNull(signinBinding.signinPasswordField.getEditText()).getText().toString().trim();
                if (Validation.validate(userName.isEmpty(), signinBinding.signinUserNameField, "Enter your user name or your email") &&
                        Validation.validate(password.isEmpty(), signinBinding.signinPasswordField, "Enter your password")) {
                    mAuth.signInWithEmailAndPassword(userName, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                assert user != null;
                                if (user.isEmailVerified()) {
                                    Toast.makeText(getContext(), "You logged in successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getContext(), ManagerActivity.class));
                                } else {
                                    user.sendEmailVerification();
                                    signinBinding.signinUserNameField.setHelperText("Your email is not verified, call you manager");
                                    signinBinding.signinUserNameField.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.md_theme_light_primary)));
                                }
                            } else {
                                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        return signinBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        signinBinding.signinUserNameField.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1200);
        signinBinding.signinPasswordField.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1800);
        signinBinding.signInButton.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(2400);
        signinBinding.forgotPasswordButton.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(3000);
        signinBinding.signinWithButton.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(3200);
        signinBinding.facebookSignInButton.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(3200);
        signinBinding.googleSignInButton.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(3400);
        signinBinding.loginWithText.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(3600);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mFirebaseAuth = FirebaseAuth.getInstance();
       /* mAuthStateListner = new FirebaseAuth.AuthStateListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(getContext(), AqelActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    startActivityForResult(AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(true)
                            .setAvailableProviders(providers)
                            .setLogo(R.drawable.sign_in_image).setTheme(R.style.LoginTheme)
                            .build(), RC_SIGN_IN);

                }
            }
        };*/
    }

    private void signIn(String userName, String password) {
       /* if (validation(userName, password)) {
            if (users == null) {
                Task<QuerySnapshot> tasks = mFirestore.collection("Employees").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                            Toast.makeText(getContext(), "succeed", Toast.LENGTH_SHORT).show();
                    }
                });
                if (tasks.getResult() != null)
                    for (QueryDocumentSnapshot document :
                            tasks.getResult()) {
                        users.add(document.toObject(User.class));
                    }
            }*/
         /*   for (User user :
                    users) {
                if (userName.equals(user.getUserName())) {
                    if (password.equals(user.getPassword())) {
                        mFirebaseAuth.signInWithEmailAndPassword(user.get)
                        Intent intent = new Intent(getActivity().getApplicationContext(), AqelActivity.class);
                        startActivity(intent);
                    } else signinBinding.signinPasswordField.setError("Password is not correct!");
                } else signinBinding.signinUserNameField.setError("User name is not correct!");

            }
        }*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        signinBinding = null;
    }
}