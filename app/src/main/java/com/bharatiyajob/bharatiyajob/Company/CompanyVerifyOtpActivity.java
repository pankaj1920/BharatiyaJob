package com.bharatiyajob.bharatiyajob.Company;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Company.VerifyOtpResponse.ComVerifyOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyVerifyOtpActivity extends AppCompatActivity {

    String companyName,email,mobile,typeOfRegistration,gstNo="gstNo",aadhaarNo="aadharNO",address,state,country,password,profilePic,profilePicName;
    Button ComVerifyOtpBtn;
    EditText ComOtpEditText;
    //    String cname, gst_no, adhar_no, email, password, number, country, state, address;
    Bitmap bitmap;
    Uri myUri;
    byte[] IMageInByte;
    String ImageString;
    String path;
    TextView otpTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_verify_otp);

        ComVerifyOtpBtn = findViewById(R.id.ComVerifyOtpBtn);
        ComOtpEditText = findViewById(R.id.ComOtpEditText);
        otpTxt = findViewById(R.id.otpTxt);

        if (gstNo.isEmpty()) {
            typeOfRegistration = "No";
        } else {
            typeOfRegistration = "yes";
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            companyName = bundle.getString("cname");
            typeOfRegistration = bundle.getString("typeOfRegistration");
            aadhaarNo = bundle.getString("Adhar");
            gstNo = bundle.getString("gstNo");
            email = bundle.getString("email");
            password = bundle.getString("password");
            mobile = bundle.getString("number");
            country = bundle.getString("country");
            state = bundle.getString("state");
            address = bundle.getString("address");
//        Log.d("ImageInString",ImageInString);
            path = bundle.getString("byte");


            myUri = Uri.parse(path);
            Toast.makeText(this, myUri.toString(), Toast.LENGTH_SHORT).show();
        }


        ComVerifyOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterCompany();
            }
        });
    }

    public void RegisterCompany() {
        if (ComOtpEditText.getText().toString().isEmpty()) {
            ComOtpEditText.setError("required");
            ComOtpEditText.setFocusable(true);
            return;
        }
        File file=new File(path);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        try {
            bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),myUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        IMageInByte=byteArrayOutputStream.toByteArray();
        ImageString= Base64.encodeToString(IMageInByte,Base64.DEFAULT);
//        Toast.makeText(this, ImageString, Toast.LENGTH_SHORT).show();
        Log.d("ImageString",ImageString);
        String otp = ComOtpEditText.getText().toString();
        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);

        otpTxt.setText(mobile+" "+otp+" "+email+" "+companyName+" "+password+" "+typeOfRegistration+" "+gstNo+" "+
                address+" "+country+" "+state+" "+address);

//        ImageString,file.getName()
        Call<ComVerifyOtpResponse> call = jobApi.RegisterCompany("8755420120","109513","email","companyName","password","typeOfRegistration","gstNo",
                "address","country","state","city","address",file.getName(),ImageString);

        call.enqueue(new Callback<ComVerifyOtpResponse>() {
            @Override
            public void onResponse(Call<ComVerifyOtpResponse> call, Response<ComVerifyOtpResponse> response) {
                ComVerifyOtpResponse comVerifyOtpResponse = response.body();

                if (response.isSuccessful() &&  comVerifyOtpResponse.getStatus().equals("success")){
                    Toast.makeText(CompanyVerifyOtpActivity.this, "Success"+ comVerifyOtpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(CompanyVerifyOtpActivity.this, "Failed"+ comVerifyOtpResponse.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ComVerifyOtpResponse> call, Throwable t) {
                Toast.makeText(CompanyVerifyOtpActivity.this, "OnFaiure"+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}