package com.bharatiyajob.bharatiyajob.User.UpdateDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.Json.UpdateCandidateProfile.UpdateCandidateProfileResponse;
import com.bharatiyajob.bharatiyajob.ProfileSettingActivity;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateSkillsActivity extends AppCompatActivity {

    EditText UPSkill;
    Button UPSkillBtn;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_skills);

        UPSkill=findViewById(R.id.UPSkill);
        UPSkillBtn=findViewById(R.id.UPSkillBtn);

        getCanDetail();
    }

    public void UpdateSkills(){
       String skill= UPSkill.getText().toString();
       if (!skill.isEmpty()){
           Retrofit retrofit= BaseClient.getBaseClient();
           JobApi jobApi=retrofit.create(JobApi.class);

           Call<UpdateCandidateProfileResponse> call=jobApi.updateUserSkill(userId,skill);
           call.enqueue(new Callback<UpdateCandidateProfileResponse>() {
               @Override
               public void onResponse(Call<UpdateCandidateProfileResponse> call, Response<UpdateCandidateProfileResponse> response) {

                   UpdateCandidateProfileResponse profileResponse = response.body();

                   if (response.isSuccessful() && profileResponse.getError().equals("false")){
                       Toast.makeText(UpdateSkillsActivity.this, "Skill uppdate", Toast.LENGTH_SHORT).show();
                       Intent intentgotoprofilactivity=new Intent(UpdateSkillsActivity.this, ProfileSettingActivity.class);
                       startActivity(intentgotoprofilactivity);
                   }else{
                       Toast.makeText(UpdateSkillsActivity.this, "Failed to update", Toast.LENGTH_SHORT).show();
                   }

               }

               @Override
               public void onFailure(Call<UpdateCandidateProfileResponse> call, Throwable t) {
                   Toast.makeText(UpdateSkillsActivity.this, "failed to uppdate Skills", Toast.LENGTH_SHORT).show();

               }
           });

       }else{
           Toast.makeText(this, "please enter skills to update", Toast.LENGTH_SHORT).show();
       }

    }

    public void OnclickuploadSkills(View view) {
        UpdateSkills();
    }

    private void getCanDetail() {
        LoginOtpResponse loginOtpResponse = LoginDetailSharePref.getInstance(this).getDetail();
        userId = loginOtpResponse.getId();
    }
}