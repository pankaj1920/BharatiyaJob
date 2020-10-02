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
import android.widget.Toast;
import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Company.VerifyOtpResponse.VerifyOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyVerifyOtpActivity extends AppCompatActivity {

    String cname, gst_no = " ", adhar_no, password, email, number, country, state, address, gst_reg = "Yes", ImageInString = "";
    Button ComVerifyOtpBtn;
    EditText ComOtpEditText;
    //    String cname, gst_no, adhar_no, email, password, number, country, state, address;
    Bitmap bitmap;
    Uri myUri;
    byte[] IMageInByte;
    String ImageString;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_verify_otp);

        ComVerifyOtpBtn = findViewById(R.id.ComVerifyOtpBtn);
        ComOtpEditText = findViewById(R.id.ComOtpEditText);

        if (gst_no.isEmpty()) {
            gst_reg = "No";
        } else {
            gst_reg = "yes";
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            cname = bundle.getString("cname");
            gst_no = bundle.getString("gst");
            adhar_no = bundle.getString("Adhar");
            email = bundle.getString("email");
            password = bundle.getString("password");
            number = bundle.getString("number");
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
        Toast.makeText(this, ImageString, Toast.LENGTH_SHORT).show();
        Log.d("ImageString",ImageString);
        String otp = ComOtpEditText.getText().toString();
        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);

        Call<VerifyOtpResponse> call = jobApi.RegisterCmpany(cname, email, number, otp
                , gst_reg, gst_no,adhar_no, address,"my" , state, country, password,ImageString,file.getName());

        call.enqueue(new Callback<VerifyOtpResponse>() {
            @Override
            public void onResponse(Call<VerifyOtpResponse> call, Response<VerifyOtpResponse> response) {
                VerifyOtpResponse registerCompanyResponse = response.body();
                if (response.isSuccessful()) {
                    Toast.makeText(CompanyVerifyOtpActivity.this, registerCompanyResponse.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CompanyVerifyOtpActivity.this, "failed", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<VerifyOtpResponse> call, Throwable t) {
                Toast.makeText(CompanyVerifyOtpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}