package com.bharatiyajob.bharatiyajob;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Candidate.ProfileForm.CanProfileResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserMakeProfileActivity extends AppCompatActivity {
    RadioButton male, female;
    ImageButton formCameraPick;
    CircleImageView userImage;
    Button saveProceed;
    private static final int IMAGE = 100;
    EditText R_fname, userSkill, locationState, currentLocality;
    Uri imageuri;
    Bitmap bitmap;
    String filepath;
    String f_name;
    String canId, Gender, HQlification, workExperience, state, skill, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_make_profile);

        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        userImage = findViewById(R.id.userImage);
        formCameraPick = findViewById(R.id.formCameraPick);
        saveProceed = findViewById(R.id.saveProceed);

        R_fname = findViewById(R.id.R_fname);
        userSkill = findViewById(R.id.userSkill);
        currentLocality = findViewById(R.id.currentLocality);
        locationState = findViewById(R.id.locationState);

        Bundle bundle = getIntent().getExtras();
        canId = bundle.getString("canId");


        formCameraPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dispatchPictureTakerAction();

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
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
                SaveCanData();
            }
        });


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 2);
        }
    }

    public void selectGender(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked

        if (view.getId() == R.id.male) {
            if (checked)
                Gender = "male";
            Toast.makeText(this, "Male is selected", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.female) {
            if (checked)
                Gender = "Female";
            Toast.makeText(this, "Female is selected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Nothing is selected", Toast.LENGTH_SHORT).show();
        }
//        switch (view.getId()) {
//            case R.id.male:
//                if (checked)
//                    Gender="male";
//                Toast.makeText(this, "Male is selected", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.female:
//                if (checked)
//                    Gender="Female";
//                Toast.makeText(this, "Female is selected", Toast.LENGTH_SHORT).show();
//                break;
//
//        }
    }

    public void selectQulification(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        if (view.getId() == R.id.belowTen) {
            if (checked)
                HQlification = "Below 10th";
            Toast.makeText(this, "Below 10th is selected", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.TenPass) {
            if (checked)
                HQlification = "10th Pass";
            Toast.makeText(this, "10th Pass is selected", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.TwelevePass) {
            if (checked)
                HQlification = "12th Pass";
            Toast.makeText(this, "12th Pass is selected", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.Diploma) {
            if (checked)
                HQlification = "Diploma";
            Toast.makeText(this, "Diploma is selected", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.Graduate) {
            if (checked)
                HQlification = "Graduate";
            Toast.makeText(this, "Graduate is selected", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.PostGraduate) {
            if (checked)
                HQlification = "Post Graduate";
            Toast.makeText(this, "Post Graduate is selected", Toast.LENGTH_SHORT).show();
        }

//        switch (view.getId()) {
//            case R.id.belowTen:
//                if (checked)
//                    HQlification="Below 10th";
//                Toast.makeText(this, "Below 10th is selected", Toast.LENGTH_SHORT).show();
//                break;
//
//            case R.id.TenPass:
//                if (checked)
//                    HQlification="10th Pass";
//                Toast.makeText(this, "10th Pass is selected", Toast.LENGTH_SHORT).show();
//                break;
//
//            case R.id.TwelevePass:
//                if (checked)
//                    HQlification="12th Pass";
//                Toast.makeText(this, "12th Pass is selected", Toast.LENGTH_SHORT).show();
//                break;

//            case R.id.Diploma:
//                if (checked)
//                    HQlification="Diploma";
//                Toast.makeText(this, "Diploma is selected", Toast.LENGTH_SHORT).show();
//                break;
//
//            case R.id.Graduate:
//                if (checked)
//                    HQlification="Graduate";
//                Toast.makeText(this, "Graduate is selected", Toast.LENGTH_SHORT).show();
//                break;
//
//            case R.id.PostGraduate:
//                if (checked)
//                    HQlification="Post Graduate";
//                Toast.makeText(this, "Post Graduate is selected", Toast.LENGTH_SHORT).show();
//                break;

//        }
    }

    public void selectExperience(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        if (view.getId()==R.id.experience){
            if (checked)
                workExperience = "Experience";
            Toast.makeText(this, "Experience is selected", Toast.LENGTH_SHORT).show();
        }else if (view.getId()==R.id.fresher){
            if (checked)
                workExperience = "Fresher";
            Toast.makeText(this, "fresher is selected", Toast.LENGTH_SHORT).show();
        }

//        switch (view.getId()) {
//            case R.id.experience:
//                if (checked)
//                    workExperience = "Experience";
//                Toast.makeText(this, "Experience is selected", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.fresher:
//                if (checked)
//                    workExperience = "Fresher";
//                Toast.makeText(this, "fresher is selected", Toast.LENGTH_SHORT).show();
//                break;
//
//        }
    }

    private void showFileChooser() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 0);
    }

    //Runtime Permission so that it can AutoVerify The Otp in EnterOtpActivity
    private void requestMediaFilePermission() {
        if (askForPermission()) {

            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            displayPermission();


        }
    }

    private boolean askForPermission() {
        if (ContextCompat.checkSelfPermission(UserMakeProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {

            showFileChooser();
            //  Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    void displayPermission() {
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                UserMakeProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            //This is called if user has denied the permission before
            //In this case I am just asking the permission again
            ActivityCompat.requestPermissions(UserMakeProfileActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        } else {

            ActivityCompat.requestPermissions(UserMakeProfileActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
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

    public void SaveCanData() {
        f_name = R_fname.getText().toString();
        state = locationState.getText().toString();
        skill = userSkill.getText().toString();
        address = currentLocality.getText().toString();

        if (f_name.equals("")) {
            R_fname.setError("Enter Name");
            R_fname.requestFocus();
            return;
        }

        if (state.equals("")) {
            locationState.setError("Enter Name");
            locationState.requestFocus();
            return;
        }

        if (skill.equals("")) {
            userSkill.setError("Enter Name");
            userSkill.requestFocus();
            return;
        }

        if (address.equals("")) {
            currentLocality.setError("Enter Name");
            currentLocality.requestFocus();
            return;
        }


        File file = new File(filepath);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageinbyte = byteArrayOutputStream.toByteArray();

        final String imagestring = Base64.encodeToString(imageinbyte, Base64.DEFAULT);


        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);

        Call<CanProfileResponse> call = jobApi.SaveCanDetail(canId, imagestring, Gender, HQlification,
                workExperience, state, skill, address, file.getName());

        call.enqueue(new Callback<CanProfileResponse>() {
            @Override
            public void onResponse(Call<CanProfileResponse> call, Response<CanProfileResponse> response) {
                CanProfileResponse saveCanDetailResponse = response.body();
                if (response.isSuccessful()) {
                    Toast.makeText(UserMakeProfileActivity.this, saveCanDetailResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserMakeProfileActivity.this, RegistrationSucessfulActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(UserMakeProfileActivity.this, "Try Agarin", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CanProfileResponse> call, Throwable t) {
                Toast.makeText(UserMakeProfileActivity.this, "failed", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data.getData() != null) {
            filepath = data.getData().toString();
            imageuri = Uri.parse(filepath);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageuri);
                userImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "data empty", Toast.LENGTH_SHORT).show();
        }

    }


}