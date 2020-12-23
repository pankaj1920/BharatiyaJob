package com.bharatiyajob.bharatiyajob.Company.HomePage.update_company_details;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.bharatiyajob.bharatiyajob.Company.HomePage.AccountSetting.CompanyProfile.CompanyProfileSetting;
import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.Json.UpdateCanImage.UpdateImageResponse;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateCompanyLogo extends AppCompatActivity {

    ImageView comImgPick;
    final int IMAGE_PICK_REQUEST_CODE=101;
    Uri imageuri;
    Bitmap bitmap;
    CircleImageView comProfileImage;
    String filepath;
    Button uploadComImgBtn;
    ProgressBar progrssbar_image_uplaod;
    LazyLoader CuiLazyloader;
    ConstraintLayout comUpLayout;
    String companyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_company_logo);

        comImgPick = findViewById(R.id.comImgPick);
        comProfileImage = findViewById(R.id.comProfileImage);
        uploadComImgBtn = findViewById(R.id.uploadComImgBtn);
        CuiLazyloader = findViewById(R.id.CuiLazyloader);
        comUpLayout = findViewById(R.id.comUpLayout);

        getCompanyDetail();
    }


    public void selectImageFromGallery(View view) {
        Intent intent_pick_image=new Intent(Intent.ACTION_PICK);
        intent_pick_image.setType("image/*");
        startActivityForResult(intent_pick_image,IMAGE_PICK_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==IMAGE_PICK_REQUEST_CODE && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            filepath=data.getData().toString();
            imageuri= Uri.parse(filepath);

            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imageuri);
                comProfileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void uploadProfileImage(){
        uploadComImgBtn.setClickable(false);
        uploadComImgBtn.setText("Uploading...");
        comUpLayout.setAlpha(0.5f);
        File file=new File(filepath);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imageInbyte=byteArrayOutputStream.toByteArray();
        String ImageInString = Base64.encodeToString(imageInbyte,Base64.DEFAULT);
        String imageName = randomName(8);

        Retrofit retrofit= BaseClient.getBaseClient();
        JobApi jobApi=retrofit.create(JobApi.class);
        Call<UpdateImageResponse> call=jobApi.updateCompanyImage(ImageInString,imageName,companyId);

        call.enqueue(new Callback<UpdateImageResponse>() {
            @Override
            public void onResponse(Call<UpdateImageResponse> call, Response<UpdateImageResponse> response) {
                Toast.makeText(UpdateCompanyLogo.this, "success", Toast.LENGTH_SHORT).show();
                Intent intent_gobackprofilesetting=new Intent(UpdateCompanyLogo.this, CompanyProfileSetting.class);
                startActivity(intent_gobackprofilesetting);
                finish();
            }

            @Override
            public void onFailure(Call<UpdateImageResponse> call, Throwable t) {
                Toast.makeText(UpdateCompanyLogo.this, "failed", Toast.LENGTH_SHORT).show();

                uploadComImgBtn.setClickable(true);
                uploadComImgBtn.setText("Uploading");
                comUpLayout.setAlpha(0.9f);
            }
        });
    }

    public void OnClickuploadProfileImage(View view) {

        LazyLoader loader = new LazyLoader(this, 30, 20, ContextCompat.getColor(this, R.color.loader_selected),
                ContextCompat.getColor(this, R.color.loader_selected),
                ContextCompat.getColor(this, R.color.loader_selected));
        loader.setAnimDuration(500);
        loader.setFirstDelayDuration(100);
        loader.setSecondDelayDuration(200);
        loader.setInterpolator(new LinearInterpolator());

        Toast.makeText(this, "Uploading...", Toast.LENGTH_SHORT).show();

        CuiLazyloader.addView(loader);

        uploadProfileImage();
        CuiLazyloader.setVisibility(View.VISIBLE);


    }

    public String randomName(int length){
        String passworwdSet="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%";
        char[] password=new char[length];
        for (int i=0;i<length;i++){
            int random=(int)(Math.random()*passworwdSet.length());
            password[i]=passworwdSet.charAt(random);
        }
        return new String(password);

    }

    private void getCompanyDetail() {
//        LoginOtpResponse loginOtpResponse = LoginDetailSharePref.getInstance(this).getDetail();
        LoginDetailSharePref loginDetailSharePref = new LoginDetailSharePref(this);
        LoginOtpResponse loginOtpResponse = loginDetailSharePref.getDetail();
        companyId = loginOtpResponse.getId();
    }
}