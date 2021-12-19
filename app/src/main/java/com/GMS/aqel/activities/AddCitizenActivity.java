package com.GMS.aqel.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.GMS.R;
import com.GMS.databinding.ActivityAddCitizenBinding;
import com.GMS.firebaseFireStore.CitizenCollection;
import com.GMS.firebaseFireStore.CollectionName;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class AddCitizenActivity extends AppCompatActivity {

    ActivityAddCitizenBinding mBinding;
    private static final int CAMERA_PERMISSION = 101;
    private static final int PICK_IMAGE_REQUEST = 100;
    private static final int IMAGE_CAPTURE = 102;
    private Uri mImageUri;
    private Dialog loadingDialog;
    private static boolean fromGallery = false;
    private static boolean fromCamera = false;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference fileRef;
    private final CollectionReference mCollectionRef = db.collection(CollectionName.CITIZENS.name());
    private final CollectionReference mNeighborhood = db.collection(CollectionName.NEIGHBORHOODS.name());

    private final CollectionReference mCollectionReference = db.collection(CollectionName.ADDITION_REQUESTS.toString());
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAddCitizenBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        this.getWindow().setStatusBarColor(getResources().getColor(R.color.md_theme_light_primary));
        setSupportActionBar(mBinding.toolBarAddCitizen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mStorageRef = FirebaseStorage.getInstance().getReference(CollectionName.StorageFolder.CITIZENPICSOFDOCUMENT.name());

        String[] array = getResources().getStringArray(R.array.items_array);
        ArrayAdapter mAdapter = new ArrayAdapter(getBaseContext(), R.layout.spinner_item, array);
        mBinding.actvNeighborhoodName.setAdapter(mAdapter);

        mBinding.tvChooseFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
        mBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getBaseContext(), " upload in progress", Toast.LENGTH_SHORT).show();
                } else {

                    if (checkFields() && fromGallery && mImageUri != null)
                        uploadCitizenDetails();
                    else if (fromCamera && checkFields()) {
                        uploadCitizenDetails();
                    }
                    else if ((!fromCamera || !fromGallery) &&!checkFields())
                    {
                        Snackbar snackbar = Snackbar
                                .make(mBinding.getRoot(), getString(R.string.fill_all_fields), Snackbar.LENGTH_LONG);
                        snackbar.show();


                    }
                    else
                    {
                        Snackbar snackbar = Snackbar
                                .make(mBinding.getRoot(), getString(R.string.chose_image), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                   }
            }
        });
        mBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // finish();
                showDialog();
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
                fromCamera = true;
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                mBinding.ivDocumentPicture.setImageBitmap(bitmap);
            }
            else
            {
                fromCamera=false;
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            fromGallery = true;
            Toast.makeText(getBaseContext(), mImageUri.toString(), Toast.LENGTH_SHORT).show();
            Picasso.with(this).load(mImageUri).into(mBinding.ivDocumentPicture);
            Toast.makeText(getBaseContext(), "you have chosen a picture", Toast.LENGTH_SHORT).show();

        } else {
            fromGallery=false;
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

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadCitizenDetails() {

        if (mImageUri != null && fromGallery) {
            createDialog();
            showDialog();
            Toast.makeText(getBaseContext(), "if", Toast.LENGTH_SHORT).show();
            fileRef = mStorageRef.child(getNameOFPic());
            mUploadTask = fileRef.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //   Toast.makeText(getBaseContext() , "have saved image" ,Toast.LENGTH_SHORT).show();
                }
            })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            ((TextView) loadingDialog.findViewById(R.id.tv_value)).setText((int) progress + "%");

                            ((ProgressBar) loadingDialog.findViewById(R.id.progress_loading)).setProgress((int) progress);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            setDialogError();
                        }
                    });

            Task<Uri> urlTask = mUploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();

                }
            })
                    .addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                // Toast.makeText(getBaseContext() , "on Complete" ,Toast.LENGTH_SHORT).show();
                                Map<String, Object> additionDetails = new HashMap<>();
                                additionDetails.put(CollectionName.Fields.aqelAddition.name(), "Abdulrahman");
                                additionDetails.put(CollectionName.Fields.hireDate.name(), String.valueOf(new java.sql.Date(System.currentTimeMillis())));
                                additionDetails.put(CollectionName.Fields.representativeCertain.name(), " ");
                                additionDetails.put(CollectionName.Fields.dateCertain.name(), " ");
                                Uri uri = (Uri) task.getResult();
                                CitizenCollection citizenDetails = new CitizenCollection(
                                        mBinding.citizenFirstNameLayout.getEditText().getText().toString() + " "
                                                + mBinding.citizenSecondNameLayout.getEditText().getText().toString() + " "
                                                + mBinding.citizenLastNameLayout.getEditText().getText().toString(),
                                        additionDetails, uri.toString(),
                                        mBinding.neighborhoodNameLayout.getEditText().getText().toString(),
                                        Integer.valueOf(mBinding.citizenNumberOfFamilyMembersLayout.getEditText().getText().toString()),
                                        Integer.valueOf(mBinding.citizenNumberOfCylindersLayout.getEditText().getText().toString())
                                        , false, false
                                );
                                uploadDataDetails(citizenDetails);




                            }
                        }
                    });


        }
        else if (fromCamera) {
            createDialog();
            showDialog();
            mBinding.ivDocumentPicture.setDrawingCacheEnabled(true);
            mBinding.ivDocumentPicture.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) mBinding.ivDocumentPicture.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            fileRef = mStorageRef.child(System.currentTimeMillis() + "." + "jpeg");
            mUploadTask = fileRef.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //   Toast.makeText(getBaseContext() , "have saved image" ,Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    ((TextView) loadingDialog.findViewById(R.id.tv_value)).setText((int) progress + "%");
                    ((ProgressBar) loadingDialog.findViewById(R.id.progress_loading)).setProgress((int) progress);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    setDialogError();
                }
            });
            Task<Uri> urlTask = mUploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();

                }
            }).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        // Toast.makeText(getBaseContext() , "on Complete" ,Toast.LENGTH_SHORT).show();
                        Map<String, Object> additionDetails = new HashMap<>();
                        additionDetails.put(CollectionName.Fields.aqelAddition.name(), "Abdulrahman");
                        additionDetails.put(CollectionName.Fields.hireDate.name(), String.valueOf(new java.sql.Date(System.currentTimeMillis())));
                        additionDetails.put(CollectionName.Fields.representativeCertain.name(), " ");
                        additionDetails.put(CollectionName.Fields.dateCertain.name(), " ");
                        Uri uri = (Uri) task.getResult();
                        CitizenCollection citizenDetails = new CitizenCollection(
                                mBinding.citizenFirstNameLayout.getEditText().getText().toString() + " "
                                        + mBinding.citizenSecondNameLayout.getEditText().getText().toString() + " "
                                        + mBinding.citizenLastNameLayout.getEditText().getText().toString(),
                                additionDetails, uri.toString(),
                                mBinding.neighborhoodNameLayout.getEditText().getText().toString(),
                                Integer.valueOf(mBinding.citizenNumberOfFamilyMembersLayout.getEditText().getText().toString()),
                                Integer.valueOf(mBinding.citizenNumberOfCylindersLayout.getEditText().getText().toString())
                                , false, false
                        );
                        uploadDataDetails(citizenDetails);

                    }
                }
            });

        }


    }
    private void uploadDataDetails(CitizenCollection citizenDetails)
    {
        mNeighborhood.whereEqualTo("name", mBinding.neighborhoodNameLayout.getEditText().getText().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                        db.collection(CollectionName.NEIGHBORHOODS.name()).document(queryDocumentSnapshots.getDocuments().get(0).getId().toString()).collection(CollectionName.CITIZENS.name())
                                .add(citizenDetails)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((TextView) loadingDialog.findViewById(R.id.tv_loading)).setText(R.string.done);
                                            }
                                        }, 3000);

                                    }
                                })

                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        setDialogError();
                                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                }).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                loadingDialog.dismiss();
                                initializeFields();
                            }
                        });



                }
            }
        });

    }

    private String getNameOFPic() {
        String name = String.valueOf(System.currentTimeMillis());
        name += ".";
        name += getFileExtension(mImageUri);

        return name;

    }

    private Boolean checkFields() {
        int number = 0;
        if (mBinding.citizenFirstNameLayout.getEditText().getText().toString().trim().isEmpty()) {
            mBinding.citizenFirstNameLayout.setError(" First name is required");
            mBinding.citizenFirstNameLayout.requestFocus();
        } else {
            number++;
        }
        if (mBinding.citizenSecondNameLayout.getEditText().getText().toString().trim().isEmpty()) {
            mBinding.citizenSecondNameLayout.setError(" Second name is required");
            mBinding.citizenSecondNameLayout.requestFocus();
        } else {
            number++;
        }
        if (mBinding.citizenLastNameLayout.getEditText().getText().toString().trim().isEmpty()) {
            mBinding.citizenLastNameLayout.setError(" Last name is required");
            mBinding.citizenLastNameLayout.requestFocus();
        } else {
            number++;
        }
        if (mBinding.neighborhoodNameLayout.getEditText().getText().toString().trim().isEmpty()) {
            mBinding.neighborhoodNameLayout.setError(" Neighborhood name is required");
            mBinding.neighborhoodNameLayout.requestFocus();
        } else {
            number++;
        }
        if (mBinding.citizenNumberOfCylindersLayout.getEditText().getText().toString().trim().isEmpty()) {
            mBinding.citizenNumberOfCylindersLayout.setError(" Number Of Cylinders  is required");
            mBinding.citizenNumberOfCylindersLayout.requestFocus();
        } else {
            number++;
        }
        if (mBinding.citizenNumberOfFamilyMembersLayout.getEditText().getText().toString().trim().isEmpty()) {
            mBinding.citizenNumberOfFamilyMembersLayout.setError(" family member  is required");
            mBinding.citizenNumberOfFamilyMembersLayout.requestFocus();
        } else {
            number++;
        }

        return number == 6;
    }

    private void createDialog() {
        loadingDialog = new Dialog(mBinding.getRoot().getContext());
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        Window window = loadingDialog.getWindow();
        window.setGravity(Gravity.CENTER);
      //  window.getAttributes().windowAnimations = R.style.FadeDialogAnimation;
        loadingDialog.setCancelable(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
    }

    private void showDialog() {

        loadingDialog.show();

    }

    private void setDialogError() {
        loadingDialog.findViewById(R.id.progress_loading).setVisibility(View.GONE);
        loadingDialog.findViewById(R.id.tv_value).setVisibility(View.GONE);
        loadingDialog.findViewById(R.id.tv_loading).setVisibility(View.GONE);

        loadingDialog.findViewById(R.id.tv_error).setVisibility(View.VISIBLE);
        loadingDialog.findViewById(R.id.btn_okay).setVisibility(View.VISIBLE);
        loadingDialog.findViewById(R.id.btn_okay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.dismiss();
            }
        });

    }

    private void generateQR() {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode("hello", BarcodeFormat.QR_CODE, 400, 400);
            Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.RGB_565);
            for (int i = 0; i < 400; i++) {
                for (int j = 0; j < 400; j++) {
                    bitmap.setPixel(i, j, bitMatrix.get(i, j) ? Color.BLACK : Color.WHITE);
                }
            }
            mBinding.ivDocumentPicture.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }
    private void initializeFields()
    {

        mBinding.ivDocumentPicture.setImageResource(R.drawable.ic_document);
        mBinding.citizenFirstNameLayout.getEditText().setText(null);
        mBinding.citizenSecondNameLayout.getEditText().setText(null);
        mBinding.citizenLastNameLayout.getEditText().setText(null);
        mBinding.neighborhoodNameLayout.getEditText().setText(null);
        mBinding.citizenNumberOfCylindersLayout.getEditText().setText(null);
        mBinding.citizenNumberOfFamilyMembersLayout.getEditText().setText(null);
        fromCamera=false;
        fromGallery=false;
    }
}

