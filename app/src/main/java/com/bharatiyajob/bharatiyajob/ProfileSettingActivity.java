package com.bharatiyajob.bharatiyajob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Candidate.GetUserDetails.GetUserDetailResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;
import com.bharatiyajob.bharatiyajob.User.UpdateDetails.UpdateNameActivity;
import com.bharatiyajob.bharatiyajob.User.UpdateDetails.UpdatePasswordActivity;
import com.bharatiyajob.bharatiyajob.User.UpdateDetails.UpdateProfileImageActivity;
import com.bharatiyajob.bharatiyajob.User.UpdateDetails.UpdateSkillsActivity;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileSettingActivity extends AppCompatActivity {

    TextView NameTextView,PasswordTextView,SkillTextView,NumberTextView,EmailTextView;
    ImageView userImage;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        NameTextView = findViewById(R.id.NameTextView);
        PasswordTextView = findViewById(R.id.PasswordTextView);
        SkillTextView = findViewById(R.id.SkillTextView);
        userImage = findViewById(R.id.userImage);
        NumberTextView = findViewById(R.id.NumberTextView);
        EmailTextView = findViewById(R.id.EmailTextView);

        getCanDetail();

        setUserDetils();

        NameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUpdateName();
            }
        });

        PasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUpdatePassword();
            }
        });

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUpdateImage();
            }
        });

        SkillTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUpdateSkill();
            }
        });





    }

    private void goToUpdateImage() {

        Intent intent = new Intent(ProfileSettingActivity.this, UpdateProfileImageActivity.class);
        startActivity(intent);
    }

    private void goToUpdateName() {
        Intent intent = new Intent(ProfileSettingActivity.this, UpdateNameActivity.class);
        startActivity(intent);
    }

    private void goToUpdatePassword() {
        Intent intent = new Intent(ProfileSettingActivity.this, UpdatePasswordActivity.class);
        startActivity(intent);
    }

    private void goToUpdateSkill() {
        Intent intent = new Intent(ProfileSettingActivity.this, UpdateSkillsActivity.class);
        startActivity(intent);
    }

  public void setUserDetils(){
      Retrofit retrofit= BaseClient.getBaseClient();
      JobApi jobApi=retrofit.create(JobApi.class);

      Call<GetUserDetailResponse> call=jobApi.getUserDetails(userId);
      call.enqueue(new Callback<GetUserDetailResponse>() {
          @Override
          public void onResponse(Call<GetUserDetailResponse> call, Response<GetUserDetailResponse> response) {

               GetUserDetailResponse getUserDetailResponse=response.body();

               if (response.isSuccessful() && getUserDetailResponse.getStatus().equals("1")){
                   if (!getUserDetailResponse.getData().getProfile_pic().isEmpty() || getUserDetailResponse.getData().getProfile_pic().equals("")){
                       Picasso.get().load(getUserDetailResponse.getData().getProfile_pic()).into(userImage);
                   }else{
                       userImage.setImageDrawable(ContextCompat.getDrawable(ProfileSettingActivity.this,R.drawable.accont_img));
                   }

                   NameTextView.setText(getUserDetailResponse.getData().getName());
                   SkillTextView.setText(getUserDetailResponse.getData().getSkill());
                   NumberTextView.setText(getUserDetailResponse.getData().getMobile());
                   EmailTextView.setText(getUserDetailResponse.getData().getEmail());
               }else {
                   Toast.makeText(ProfileSettingActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
               }


          }

          @Override
          public void onFailure(Call<GetUserDetailResponse> call, Throwable t) {
              Toast.makeText(ProfileSettingActivity.this, "failed to fetch detail", Toast.LENGTH_SHORT).show();
          }
      });

  }

  private void getCanDetail() {
        LoginOtpResponse loginOtpResponse = LoginDetailSharePref.getInstance(this).getDetail();
        userId = loginOtpResponse.getId();
    }

}