package com.example.pros;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.Manifest;
import android.app.*;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class AfterLoadingActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Task<AuthResult> task;

    private String usernameChosen;
    private ImageButton chooseDialogButton;
    private ImageButton addImageButton;
    private Dialog usernameDialog;
    private EditText usernameDialogEditText;
    private int PICK_IMAGE =100;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register_screen);
        usernameDialog = new Dialog(this);
        usernameDialog.setContentView(R.layout.dialog_register_username_pick);
        usernameDialogEditText = usernameDialog.findViewById(R.id.editText_usernamePickDialog_username);
        chooseDialogButton = usernameDialog.findViewById(R.id.imageButton_usernamePickDialog_choose);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        imageUri = null;//בשביל התנאי בדיאלוג שזה לא יהיה נל
        if(currentUser != null) {
            startActivity(new Intent(AfterLoadingActivity.this, MainScreenActivity.class));
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


    public void onLogIn(View view){
        String emailInput = emailEditText.getText().toString();
        String passwordInput = passwordEditText.getText().toString();
        if(emailInput.matches("") || passwordInput.matches("")){
            Toast.makeText(AfterLoadingActivity.this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
        }
        else {
            task = mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString());
            task.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                finish();
                                startActivity(new Intent(AfterLoadingActivity.this, MainScreenActivity.class));
                            }
                            else {
                                Toast.makeText(AfterLoadingActivity.this, "This user does not exist, try to create a user", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
        }
    }

    public void onRegister(View view){
        final String emailInput = emailEditText.getText().toString();
        String passwordInput = passwordEditText.getText().toString();
        if(emailInput.matches("") || passwordInput.matches("")){
            Toast.makeText(AfterLoadingActivity.this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
        }
        else{
            task = mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString());
            task.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task){
                            if (task.isSuccessful()) {
                                usernameDialog.show();
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
                                        usernameChosen = usernameDialogEditText.getText().toString();
                                        if(usernameChosen.matches("") || imageUri == null){
                                            Toast.makeText(AfterLoadingActivity.this, "Please choose a username and a profile picture", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            usernameChosen = usernameDialogEditText.getText().toString();
                                            Intent i = new Intent(AfterLoadingActivity.this, MainScreenActivity.class);
                                            User user = new User(usernameChosen, 0, 1, R.drawable.skin_basic, true);
                                            currentUser = mAuth.getCurrentUser();
                                            uploadPhotoToFirebase(currentUser.getUid());
                                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                                            DatabaseReference myRef = database.getReference().child("Pros").child("users").child(currentUser.getUid());
                                            myRef.setValue(user);
                                            startActivity(i);
                                        }
                                    }
                                });
                            }
                            else {
                                Toast.makeText(AfterLoadingActivity.this, "The email or the password are taken or invalid. Please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
        }
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
        }
    }

    private void uploadPhotoToFirebase(final String id){

        FirebaseStorage.getInstance().getReference().child(id).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
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
