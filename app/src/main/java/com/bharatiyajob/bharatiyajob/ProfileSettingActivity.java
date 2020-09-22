package com.bharatiyajob.bharatiyajob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Candidate.GetUserDetails.GetUserDetailResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.UpdateDetails.UpdateNameActivity;
import com.bharatiyajob.bharatiyajob.UpdateDetails.UpdatePasswordActivity;
import com.bharatiyajob.bharatiyajob.UpdateDetails.UpdateProfileImageActivity;
import com.bharatiyajob.bharatiyajob.UpdateDetails.UpdateSkillsActivity;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileSettingActivity extends AppCompatActivity {

    TextView NameTextView,PasswordTextView,SkillTextView,NumberTextView,EmailTextView;
    ImageView userImage;

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
        setUserDetils();


    }

    private void goToUpdateImage() {

        Intent intent = new Intent(ProfileSettingActivity.this, UpdateProfileImageActivity.class);
        startActivity(intent);
    }

    private void goToUpdatePassword() {
        Intent intent = new Intent(ProfileSettingActivity.this, UpdatePasswordActivity.class);
        startActivity(intent);
    }

    private void goToUpdateName() {
        Intent intent = new Intent(ProfileSettingActivity.this, UpdateNameActivity.class);
        startActivity(intent);
    }

    private void goToUpdateSkill() {
        Intent intent = new Intent(ProfileSettingActivity.this, UpdateSkillsActivity.class);
        startActivity(intent);
    }

  public void setUserDetils(){
      Retrofit retrofit= BaseClient.getBaseClient();
      JobApi jobApi=retrofit.create(JobApi.class);

      Call<GetUserDetailResponse> call=jobApi.getUserDetails("45");
      call.enqueue(new Callback<GetUserDetailResponse>() {
          @Override
          public void onResponse(Call<GetUserDetailResponse> call, Response<GetUserDetailResponse> response) {
               GetUserDetailResponse getUserDetailResponse=response.body();
              Picasso.get().load(getUserDetailResponse.getData().getProfile_pic()).into(userImage);
              NameTextView.setText(getUserDetailResponse.getData().getName());
              NumberTextView.setText(getUserDetailResponse.getData().getMobile());
              EmailTextView.setText(getUserDetailResponse.getData().getEmail());
              SkillTextView.setText(getUserDetailResponse.getData().getSkill());

          }

          @Override
          public void onFailure(Call<GetUserDetailResponse> call, Throwable t) {
              Toast.makeText(ProfileSettingActivity.this, "failed to fetch detail", Toast.LENGTH_SHORT).show();
          }
      });

  }

}