package com.bharatiyajob.bharatiyajob.Company;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Company.CompanyRegistrationResponse.CompanyRegistrationResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.R;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout GstTextLayout, AdhaarTxtLayout;
    EditText companyNameEditTxt, enterGstNum, enterAdhaarNum, enterCemailNum, enterCpassword, enterCnumber,
            enterCountry, enterState, enterClocality;
    Button CsaveProceed;
    String selected = "";
    ImageView companyLogo,formCameraPick;
    boolean imagechecking = false;
    final int IMAGE_PICK = 100;
    String photo_path;
    Uri imageuri;
    Bitmap bitmap;
    byte[] imageInbyteArray;
    String ImageInString;
    RadioButton GST,NonGst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_registration);

        GstTextLayout = findViewById(R.id.GstTextLayout);
        AdhaarTxtLayout = findViewById(R.id.AdhaarTxtLayout);
        companyNameEditTxt = findViewById(R.id.companyNameEditTxt);
        enterGstNum = findViewById(R.id.enterGstNum);
        enterAdhaarNum = findViewById(R.id.enterAdhaarNum);
        enterCpassword = findViewById(R.id.enterCpassword);
        enterCemailNum = findViewById(R.id.enterCemailNum);
        enterCnumber = findViewById(R.id.enterCnumber);
        enterCountry = findViewById(R.id.enterCountry);
        enterState = findViewById(R.id.enterState);
        enterClocality = findViewById(R.id.enterClocality);
        CsaveProceed = findViewById(R.id.CsaveProceed);
        companyLogo = findViewById(R.id.companyLogo);
        formCameraPick = findViewById(R.id.formCameraPick);
        NonGst = findViewById(R.id.NonGst);
        GST = findViewById(R.id.GST);


//        companyLogo.setImageDrawable(ContextCompat.getDrawable(CompanyRegistrationActivity.this, R.drawable.ic_star));
        companyLogo.setTag("one");

        if (companyLogo.getTag().equals("one")) {
            imagechecking = true;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 2);
        }

        CsaveProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveCompanyMobileNumber();
            }
        });

        formCameraPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseImaeg();
            }
        });
    }


    public void selectGst(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        if (view.getId() == R.id.GST){
            if (checked)
                GstTextLayout.setVisibility(View.VISIBLE);
            AdhaarTxtLayout.setVisibility(View.GONE);
            selected = "Gst";
        }else if (view.getId() == R.id.NonGst){
            if (checked)
                GstTextLayout.setVisibility(View.GONE);
            AdhaarTxtLayout.setVisibility(View.VISIBLE);
            selected = "Adhar";
        }
//        switch (view.getId()) {
//            case R.id.GST:
//                if (checked)
//                    GstTextLayout.setVisibility(View.VISIBLE);
//                AdhaarTxtLayout.setVisibility(View.GONE);
//                selected = "Gst";
////                    Toast.makeText(this, "Gst is selected", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.NonGst:
//                if (checked)
//                    GstTextLayout.setVisibility(View.GONE);
//                AdhaarTxtLayout.setVisibility(View.VISIBLE);
//                selected = "Adhar";
////                    Toast.makeText(this, "Non Gst is selected", Toast.LENGTH_SHORT).show();
//                break;
//
//        }

    }

    public void sendToVerifyOtpActivity() {

        if (!imagechecking) {
            Toast.makeText(this, "please select the image", Toast.LENGTH_SHORT).show();
            return;
        }
        if (companyNameEditTxt.getText().toString().isEmpty()
        ) {
            companyNameEditTxt.setError("require");
            companyNameEditTxt.setFocusable(true);
            return;
        }
        if (selected.isEmpty()) {
            Toast.makeText(this, "please select Gst or Non Gst", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (selected.equals("Gst") && enterGstNum.getText().toString().isEmpty()) {
                enterGstNum.setError("reuired");
                enterGstNum.setFocusable(true);
                return;
            }
            if (selected.equals("Adhar") && enterAdhaarNum.getText().toString().isEmpty()) {
                enterAdhaarNum.setError("reuired");
                enterAdhaarNum.setFocusable(true);
                return;
            }

        }
        if (enterCemailNum.getText().toString().isEmpty()
        ) {
            enterCemailNum.setError("require");
            enterCemailNum.setFocusable(true);
            return;
        }
        if (enterCpassword.getText().toString().isEmpty()
        ) {
            enterCpassword.setError("require");
            enterCpassword.setFocusable(true);
            return;
        }


        if (enterCountry.getText().toString().isEmpty()
        ) {
            enterCountry.setError("require");
            enterCountry.setFocusable(true);
            return;
        }
        if (enterState.getText().toString().isEmpty()
        ) {
            enterState.setError("company name require");
            enterState.setFocusable(true);
            return;
        }
        if (enterClocality.getText().toString().isEmpty()
        ) {
            enterClocality.setError("require");
            enterClocality.setFocusable(true);
            return;
        }

        if (bitmap==null){
            Toast.makeText(this, "please select company Logo", Toast.LENGTH_SHORT).show();
            return;
        }
//        companyLogo.setImageDrawable(ContextCompat.getDrawable(CompanyRegistrationActivity.this,R.drawable.company));

        String cname = companyNameEditTxt.getText().toString();
        String typeOfRegistration = enterGstNum.getText().toString();
        String Adhar = enterAdhaarNum.getText().toString();
        String gstNo = enterGstNum.getText().toString();
        String email = enterCemailNum.getText().toString();
        String password = enterCpassword.getText().toString();
        String  number = enterCnumber.getText().toString();
        String country = enterCountry.getText().toString();
        String state = enterState.getText().toString();
        String address = enterClocality.getText().toString();
        Intent intent = new Intent(CompanyRegistrationActivity.this, CompanyVerifyOtpActivity.class);

        intent.putExtra("cname", cname);
        intent.putExtra("typeOfRegistration", typeOfRegistration);
        intent.putExtra("Adhar", Adhar);
        intent.putExtra("gstNo", gstNo);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("number", number);
        intent.putExtra("country", country);
        intent.putExtra("state", state);
        intent.putExtra("address", address);
//                intent.putExtra("ImageInString",ImageInString);
//        Log.d("Imageinastring",ImageInString);
        intent.putExtra("byte",photo_path);
        startActivity(intent);

    }

    public void ChooseImaeg() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK && resultCode == RESULT_OK && data.getData() != null) {
            photo_path = data.getData().toString();
            imageuri = data.getData();
            bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageuri);
                companyLogo.setImageBitmap(bitmap);
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
                imageInbyteArray=byteArrayOutputStream.toByteArray();
                ImageInString= Base64.encodeToString(imageInbyteArray,Base64.DEFAULT);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public void SaveCompanyMobileNumber(){
        if (enterCnumber.getText().toString().isEmpty()
        ) {
            enterCnumber.setError("require");
            enterCnumber.setFocusable(true);
            return;
        }
        String mnumber = enterCnumber.getText().toString();

        JobApi jobApi= BaseClient.getBaseClient().create(JobApi.class);

        Call<CompanyRegistrationResponse> call =jobApi.saveCompanyMobilenumber(mnumber,"company");

        call.enqueue(new Callback<CompanyRegistrationResponse>() {
            @Override
            public void onResponse(Call<CompanyRegistrationResponse> call, Response<CompanyRegistrationResponse> response) {
                CompanyRegistrationResponse companyMobileVerificationResponse=response.body();

                if (response.isSuccessful() && companyMobileVerificationResponse.getError().equals("success")){
//                    CompanyMobileVerificationResponse companyMobileVerificationResponse=response.body();
                    Toast.makeText(CompanyRegistrationActivity.this, companyMobileVerificationResponse.getMessage() +"Success", Toast.LENGTH_SHORT).show();

                    sendToVerifyOtpActivity();

                }else{
                    Toast.makeText(CompanyRegistrationActivity.this, companyMobileVerificationResponse.getMessage()+"Try Again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CompanyRegistrationResponse> call, Throwable t) {
                Toast.makeText(CompanyRegistrationActivity.this, "Onfailure", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View view) {

    }
}

