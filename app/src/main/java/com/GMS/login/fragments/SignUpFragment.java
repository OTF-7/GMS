package com.GMS.login.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.GMS.GeneralClasses.User;
import com.GMS.MainActivity;
import com.GMS.databinding.FragmentSignUpBinding;
import com.GMS.firebaseFireStore.CollectionName;
import com.GMS.login.activities.CreatedAccountActivity;
import com.GMS.login.activities.LoginActivity;
import com.GMS.login.utilities.Validation;
import com.GMS.login.utilities.changePagerState;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Objects;

public class SignUpFragment extends Fragment {
    public static final String TAG = MainActivity.class.getSimpleName();
    FragmentSignUpBinding signupBinding;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        signupBinding = FragmentSignUpBinding.inflate(inflater, container, false);
        signupBinding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
        signupBinding.gotoSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePagerState.gotoSignIn(LoginActivity.loginBinding);
            }
        });
        return signupBinding.getRoot();
    }

    public void createAccount() {
        mFirestore = FirebaseFirestore.getInstance();
        CollectionReference employees = mFirestore.collection(CollectionName.Employees.name());
        CollectionReference notifications = mFirestore.collection(CollectionName.Notifications.name());
        String firstName = Objects.requireNonNull(signupBinding.signupFirstNameField.getEditText()).getText().toString().trim();
        String midName = Objects.requireNonNull(signupBinding.signupMidNameField.getEditText()).getText().toString().trim();
        String lastName = Objects.requireNonNull(signupBinding.signupLastNameField.getEditText()).getText().toString().trim();
        String email = Objects.requireNonNull(signupBinding.signupEmailField.getEditText()).getText().toString().trim();
        String userName = Objects.requireNonNull(signupBinding.signupUserNameField.getEditText()).getText().toString().trim();
        String password = Objects.requireNonNull(signupBinding.signupPasswordField.getEditText()).getText().toString().trim();
        String repeatPassword = Objects.requireNonNull(signupBinding.repeatPasswordField.getEditText()).getText().toString().trim();
        String phone = Objects.requireNonNull(signupBinding.phoneField.getEditText()).getText().toString();
        int userType = signupBinding.radioGroupsEmployeesTypes.getCheckedRadioButtonId();
        //HashMap<String, String> socialAccounts = new HashMap<>();
        String age_text = Objects.requireNonNull(signupBinding.ageField.getEditText()).getText().toString();
        int age = Integer.parseInt((!age_text.isEmpty()) ? age_text : "0");
        if (Validation.validate(firstName.isEmpty(), signupBinding.signupFirstNameField, "Enter your first name") &&
                Validation.validate(midName.isEmpty(), signupBinding.signupMidNameField, "Enter your middle name") &&
                Validation.validate(lastName.isEmpty(), signupBinding.signupLastNameField, "Enter your last name") &&
                Validation.validate(email.isEmpty(), signupBinding.signupEmailField, "Enter your email") &&
                Validation.validate(getContext(), !Patterns.EMAIL_ADDRESS.matcher(email).matches(), signupBinding.signupEmailField, "Wrong email format", "Correct email format") &&
                Validation.validate(getContext(), userName.isEmpty(), signupBinding.signupUserNameField, "Enter your user name", "Your user name is unique") &&
                Validation.validate(phone.isEmpty(), signupBinding.phoneField, "Enter your phone number") &&
                Validation.validate(getContext(), password.isEmpty(), signupBinding.signupPasswordField, "Enter your password", "Your password is strong") &&
                Validation.validate(getContext(), password.length() < 8, signupBinding.signupPasswordField, "Password should contains more the 8 characters", "Your password is strong") &&
                Validation.validate(repeatPassword.isEmpty(), signupBinding.repeatPasswordField, "Repeat your password") &&
                Validation.validate(getContext(), !password.equals(repeatPassword), signupBinding.repeatPasswordField, "Passwords don't match!", "The passwords are identical") &&
                Validation.validate(age_text.isEmpty(), signupBinding.ageField, "Enter your age") &&
                Validation.validate(age < 15 || age > 60, signupBinding.ageField, "Your age should be between 15 and 60")
        ) {
            signupBinding.signupProgress.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "Please wait it will take some time", Toast.LENGTH_LONG).show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "user created", Toast.LENGTH_SHORT).show();
                        User user = new User(firstName, midName, lastName, email, userName, password, phone, userType);
                        Task<DocumentReference> employeesTask = employees.add(user);
                        Task<DocumentReference> notificationsTask = notifications.add(user);
                        Tasks.whenAllComplete().addOnCompleteListener(new OnCompleteListener<List<Task<?>>>() {
                            @Override
                            public void onComplete(@NonNull Task<List<Task<?>>> task) {
                                signupBinding.signupProgress.setVisibility(View.GONE);
                                if (task.isCanceled()) {
                                    if (employeesTask.isSuccessful() && employeesTask.getResult() != null)
                                        employeesTask.getResult().delete();
                                    if (notificationsTask.isSuccessful() && notificationsTask.getResult() != null)
                                        notificationsTask.getResult().delete();

                                    Toast.makeText(getContext(), "Internet connection error, reconnect and try again", Toast.LENGTH_LONG).show();
                                    Objects.requireNonNull(mAuth.getCurrentUser()).delete();
                                }

                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "User has been registered successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getContext(), CreatedAccountActivity.class));
                                    requireActivity().finish();
                                }
                            }
                        });

                    } else {
                        Toast.makeText(getContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                        Toast.makeText(getContext(), "Internet connection error, reconnect and try again", Toast.LENGTH_SHORT).show();
                        signupBinding.signupProgress.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        signupBinding = null;
    }
}