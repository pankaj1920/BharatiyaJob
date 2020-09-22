package com.bharatiyajob.bharatiyajob.UpdateDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.Json.UpdateUserName.UpdateUserName;
import com.bharatiyajob.bharatiyajob.Json.UpdateUserName.UpdateUserNameResponse;
import com.bharatiyajob.bharatiyajob.ProfileSettingActivity;
import com.bharatiyajob.bharatiyajob.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateNameActivity extends AppCompatActivity {

EditText UUName;
Button UUNameBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_name);
        UUName=findViewById(R.id.UUName);
        UUNameBtn=findViewById(R.id.UUNameBtn);

    }

public void updateName(){
    String name=UUName.getText().toString();
        if (!name.isEmpty()){
            Retrofit retrofit= BaseClient.getBaseClient();
            JobApi jobApi=retrofit.create(JobApi.class);

            Call<UpdateUserName> call=jobApi.upDateUserName("45",name);

            call.enqueue(new Callback<UpdateUserName>() {
                @Override
                public void onResponse(Call<UpdateUserName> call, Response<UpdateUserName> response) {
                    Toast.makeText(UpdateNameActivity.this, "successfully updated", Toast.LENGTH_SHORT).show();
                    Intent intentgotoprofilactivity=new Intent(UpdateNameActivity.this, ProfileSettingActivity.class);
                    startActivity(intentgotoprofilactivity);
                }

                @Override
                public void onFailure(Call<UpdateUserName> call, Throwable t) {
                    Toast.makeText(UpdateNameActivity.this, "Failed to update name", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(this, "please enter name to update", Toast.LENGTH_SHORT).show();
        }

}

    public void Onclickuploadname(View view) {
        updateName();
    }
}