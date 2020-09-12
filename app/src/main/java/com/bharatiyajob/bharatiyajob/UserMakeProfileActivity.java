package com.bharatiyajob.bharatiyajob;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserMakeProfileActivity extends AppCompatActivity {
    RadioButton male;
    ImageButton formCameraPick;
    CircleImageView userImage;
    String photoPath;
    Button saveProceed;
    private  static final int IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_make_profile);

        male = findViewById(R.id.male);
        userImage = findViewById(R.id.userImage);
        formCameraPick = findViewById(R.id.formCameraPick);
        saveProceed = findViewById(R.id.saveProceed);

        formCameraPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dispatchPictureTakerAction();
            }
        });

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Permission for to access Gallery
//                requestMediaFilePermission();
            }
        });

        saveProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMakeProfileActivity.this,RegistrationSucessfulActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void dispatchPictureTakerAction() {
//        Create a Intent to Open a Camera
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        
        //Checking if there is any app to handle our intent in our case it is camera appp
        if(takePic.resolveActivity(getPackageManager()) != null){
            // it mean there is an app avilable to handle action of our intent
            
            //we will create a file where photo will stored
            File photoFile = null;
            try {
//                photoFile = createPhotoFile();
            }catch (Exception e){

            }
        }
    }

//    // In this method we will create a file where photo will be stored
//    private File createPhotoFile() {
//        String name = new  SimpleDateFormat((getString(R.string.DateFormat))).format(new Date());
//        File storageDir = getExternalStoragePublicDirectory()
//    }


    public void selectGender(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.male:
                if (checked)
                    Toast.makeText(this, "Male is selected", Toast.LENGTH_SHORT).show();
                    break;
            case R.id.female:
                if (checked)
                    Toast.makeText(this, "Female is selected", Toast.LENGTH_SHORT).show();
                break;

        }
    }


    private void showFileChooser() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 0);
    }

    



    //Runtime Permission so that it can AutoVerify The Otp in EnterOtpActivity
    private void requestMediaFilePermission() {
        if (askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 1)) {

            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            displayPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 1);
        }
    }

    private boolean askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(UserMakeProfileActivity.this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {

            showFileChooser();
            //  Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    void displayPermission(String permission, Integer requestCode) {
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                UserMakeProfileActivity.this, permission)) {

            //This is called if user has denied the permission before
            //In this case I am just asking the permission again
            ActivityCompat.requestPermissions(UserMakeProfileActivity.this,
                    new String[]{permission}, requestCode);

        } else {

            ActivityCompat.requestPermissions(UserMakeProfileActivity.this,
                    new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {

            if (requestCode == 1) {
                requestMediaFilePermission();
            }

        }
        // this else statment will run wen user click on dont ask again and deined the permission and still click on allow button
        else {
            Toast.makeText(this, "Enable Message permission from Setting", Toast.LENGTH_SHORT).show();
        }
    }


}