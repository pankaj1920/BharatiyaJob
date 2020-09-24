package com.bharatiyajob.bharatiyajob.User.UpdateDetails;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.Json.UpdateCanImage.UpdateImageResponse;
import com.bharatiyajob.bharatiyajob.ProfileSettingActivity;
import com.bharatiyajob.bharatiyajob.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateProfileImageActivity extends AppCompatActivity {
    ImageView ibpick;
    int IMAGE_PICK_REQUEST_CODE=101;
    Uri imageuri;
    Bitmap bitmap;
    CircleImageView ProfileImage;
    String filepath;
    Button upladBtn;
    ProgressBar progrssbar_image_uplaod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_image);

        ibpick=findViewById(R.id.ibpick);
        ProfileImage=findViewById(R.id.ProfileImage);
        upladBtn=findViewById(R.id.upladBtn);
        progrssbar_image_uplaod=findViewById(R.id.progrssbar_image_uplaod);


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
                ProfileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void uploadProfileImage(){
        File file=new File(filepath);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imageInbyte=byteArrayOutputStream.toByteArray();
        String ImageInString = Base64.encodeToString(imageInbyte,Base64.DEFAULT);

        Retrofit retrofit= BaseClient.getBaseClient();
        JobApi jobApi=retrofit.create(JobApi.class);
        Call<UpdateImageResponse> call=jobApi.updateCanImage(ImageInString,file.getName(),"45");

        call.enqueue(new Callback<UpdateImageResponse>() {
            @Override
            public void onResponse(Call<UpdateImageResponse> call, Response<UpdateImageResponse> response) {
                Toast.makeText(UpdateProfileImageActivity.this, "success", Toast.LENGTH_SHORT).show();
               Intent intent_gobackprofilesetting=new Intent(UpdateProfileImageActivity.this, ProfileSettingActivity.class);
               startActivity(intent_gobackprofilesetting);
               finish();
            }

            @Override
            public void onFailure(Call<UpdateImageResponse> call, Throwable t) {
                Toast.makeText(UpdateProfileImageActivity.this, "failed", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void OnClickuploadProfileImage(View view) {
        uploadProfileImage();
        upladBtn.setVisibility(View.GONE);
        progrssbar_image_uplaod.setVisibility(View.VISIBLE);
        ProfileImage.setVisibility(View.GONE);
        ibpick.setVisibility(View.GONE);


    }
}