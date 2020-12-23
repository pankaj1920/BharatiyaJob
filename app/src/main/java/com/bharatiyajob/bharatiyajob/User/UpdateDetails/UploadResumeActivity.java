package com.bharatiyajob.bharatiyajob.User.UpdateDetails;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.bharatiyajob.bharatiyajob.Company.HomePage.AccountSetting.CompanyProfile.CompanyProfileSetting;
import com.bharatiyajob.bharatiyajob.Company.HomePage.update_company_details.UpdateCompanyLogo;
import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.Json.UpdateCanImage.UpdateImageResponse;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UploadResumeActivity extends AppCompatActivity {
    int IMAGE_PICK_REQUEST_CODE = 100;
    Uri path;
    String userId;
    Button upladResumeBtn;
    private String encodedDocument;
    LazyLoader ResumeLazyloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_resume);
        upladResumeBtn = findViewById(R.id.upladResumeBtn);
        ResumeLazyloader = findViewById(R.id.ResumeLazyloader);

        getCanDetail();

        upladResumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LazyLoader loader = new LazyLoader(UploadResumeActivity.this, 30, 20, ContextCompat.getColor(UploadResumeActivity.this, R.color.loader_selected),
                        ContextCompat.getColor(UploadResumeActivity.this, R.color.loader_selected),
                        ContextCompat.getColor(UploadResumeActivity.this, R.color.loader_selected));
                loader.setAnimDuration(500);
                loader.setFirstDelayDuration(100);
                loader.setSecondDelayDuration(200);
                loader.setInterpolator(new LinearInterpolator());


                ResumeLazyloader.addView(loader);

                ResumeLazyloader.setVisibility(View.VISIBLE);
                uploadProfileImage();
            }
        });
    }

    public void uploadProfileImage() {

        File file = new File(path.toString());
        String extension = file.getPath().substring(file.getPath().lastIndexOf("."));
        Toast.makeText(this, "This is extension   "+extension, Toast.LENGTH_SHORT).show();

        String docName= randomName(8);

        Retrofit retrofit = BaseClient.getBaseClient();
        JobApi jobApi = retrofit.create(JobApi.class);
        Call<UpdateImageResponse> call = jobApi.uploadResume(encodedDocument,docName+extension, userId);

        call.enqueue(new Callback<UpdateImageResponse>() {
            @Override
            public void onResponse(Call<UpdateImageResponse> call, Response<UpdateImageResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UploadResumeActivity.this, "success", Toast.LENGTH_SHORT).show();
//                    Intent intent_gobackprofilesetting=new Intent(UploadResumeActivity.this, CompanyProfileSetting.class);
//                    startActivity(intent_gobackprofilesetting);
//                    finish();
                }

            }

            @Override
            public void onFailure(Call<UpdateImageResponse> call, Throwable t) {
                Toast.makeText(UploadResumeActivity.this, "failed", Toast.LENGTH_SHORT).show();


            }
        });
    }

    public void selectPdfFromGallery(View view) {
        Intent intent_pick_image = new Intent(Intent.ACTION_GET_CONTENT);
        intent_pick_image.setType("*/*");
        startActivityForResult(intent_pick_image, IMAGE_PICK_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == RESULT_OK && data.getData() != null) {
            path = data.getData();

            try {
                InputStream inputStream = UploadResumeActivity.this.getContentResolver().openInputStream(path);
                byte[] documentInByte = new byte[inputStream.available()];
                inputStream.read(documentInByte);
                encodedDocument = Base64.encodeToString(documentInByte,Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String randomName(int length){
        String passworwdSet="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        char[] password=new char[length];
        for (int i=0;i<length;i++){
            int random=(int)(Math.random()*passworwdSet.length());
            password[i]=passworwdSet.charAt(random);
        }
        return new String(password);

    }

    private void getCanDetail() {
//        LoginOtpResponse loginOtpResponse = LoginDetailSharePref.getInstance(this).getDetail();
        LoginDetailSharePref loginDetailSharePref = new LoginDetailSharePref(this);
        LoginOtpResponse loginOtpResponse = loginDetailSharePref.getDetail();
        userId = loginOtpResponse.getId();
    }
}