package com.example.pros.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pros.R;
import com.example.pros.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

public class LoginSignUpActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Task<AuthResult> task;

    private String userNameChosen;
    private ImageButton chooseDialogButton;
    private ImageButton addImageButton;
    private Dialog usernameDialog;
    private EditText usernameDialogEditText;
    private int PICK_IMAGE = 100;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register_screen);
        TextView title = findViewById(R.id.textView_loginScreen_title);
        title.setText("Log In,\nOr Create a\nUser");
        usernameDialog = new Dialog(this);
        usernameDialog.setContentView(R.layout.dialog_register_username_pick);
        usernameDialogEditText = usernameDialog.findViewById(R.id.editText_usernamePickDialog_username);
        chooseDialogButton = usernameDialog.findViewById(R.id.imageButton_usernamePickDialog_choose);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
//        mAuth.signOut();
        imageUri = null;//בשביל התנאי בדיאלוג שזה לא יהיה נל
        if (currentUser != null) {
            startActivity(new Intent(LoginSignUpActivity.this, MainScreenActivity.class));
        }
        emailEditText = findViewById(R.id.editText_loginScreen_email);
        passwordEditText = findViewById(R.id.editText_loginScreen_password);
        addImageButton = findViewById(R.id.imageButton_usernamePickDialog_addImage);
//        addImageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openGalleryGroup();
//            }
//        });
    }

    @Override
    public void onBackPressed() {

    }


    public void onLogIn(View view) {
        String emailInput = emailEditText.getText().toString();
        String passwordInput = passwordEditText.getText().toString();
        if (emailInput.matches("") || passwordInput.matches("")) {
            Toast.makeText(LoginSignUpActivity.this, getResources().getString(R.string.loginScreen_emptyErrorMessage), Toast.LENGTH_SHORT).show();
        } else {
            task = mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString());
            task.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                finish();
                                startActivity(new Intent(LoginSignUpActivity.this, MainScreenActivity.class));
                            } else {
                                Toast.makeText(LoginSignUpActivity.this, getResources().getString(R.string.loginScreen_notExistErrorMessage), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
        }
    }

    public void onRegister(View view) {
        final String emailInput = emailEditText.getText().toString();
        String passwordInput = passwordEditText.getText().toString();
        if (emailInput.matches("") || passwordInput.matches("")) {
            Toast.makeText(LoginSignUpActivity.this, getResources().getString(R.string.loginScreen_emptyErrorMessage), Toast.LENGTH_SHORT).show();
        } else {
            task = mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString());
            task.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                usernameDialog.show();
                                TextView titleDialog = usernameDialog.findViewById(R.id.textView_usernamePickDialog_title);
                                titleDialog.setText("Pick a\nUsername");
                                usernameDialog.setCancelable(false);
                                usernameDialog.setCanceledOnTouchOutside(false);

                                addImageButton = usernameDialog.findViewById(R.id.imageButton_usernamePickDialog_addImage);
                                addImageButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        openGallery();
                                    }
                                });

                                chooseDialogButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        userNameChosen = usernameDialogEditText.getText().toString();
                                        if (userNameChosen.matches("") || imageUri == null) {
                                            Toast.makeText(LoginSignUpActivity.this, getResources().getString(R.string.usernamePickDialog_emptyError), Toast.LENGTH_SHORT).show();
                                        } else {
                                            userNameChosen = usernameDialogEditText.getText().toString();
                                            Intent i = new Intent(LoginSignUpActivity.this, MainScreenActivity.class);
                                            User.getInstance().createNewUser(userNameChosen);
//                                            User.getInstance().setUserName(userNameChosen);
//                                            User.getInstance().setChosenSkinImageId(R.drawable.skin_basic);
//                                            User.getInstance().setMusicOn(true);
//                                            User.getInstance().setNumOfSkins(1);
//                                            User.getInstance().setNumOfWins(0);
//                                            currentUser = mAuth.getCurrentUser();
                                            uploadPhotoToFirebase(FirebaseAuth.getInstance().getUid());
//                                            FirebaseDatabase database = FirebaseDatabase.getInstance();
//                                            DatabaseReference myRef = database.getReference().child("Pros").child("users").child(currentUser.getUid());
//                                            User.getInstance().setFirebaseUserId(currentUser.getUid());
//                                            myRef.setValue(User.getInstance());
                                            startActivity(i);
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(LoginSignUpActivity.this, getResources().getString(R.string.loginScreen_takenOrInvalidErrorMessage), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
        }
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    private void uploadPhotoToFirebase(final String id) {

        FirebaseStorage.getInstance().getReference().child(id).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    FirebaseStorage.getInstance().getReference().child(id).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            String url = task.getResult().toString();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            database.getReference().child("Pros").child("users").child(currentUser.getUid()).child("photoImageURL").setValue(url);
                        }
                    });
                }
            }
        });
    }


//    protected void openGalleryGroup() {
//        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//        startActivityForResult(gallery, PICK_IMAGE);
//    }
}
