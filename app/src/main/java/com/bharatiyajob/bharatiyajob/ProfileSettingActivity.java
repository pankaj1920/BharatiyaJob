package com.bharatiyajob.bharatiyajob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bharatiyajob.bharatiyajob.UpdateDetails.UpdateNameActivity;
import com.bharatiyajob.bharatiyajob.UpdateDetails.UpdatePasswordActivity;
import com.bharatiyajob.bharatiyajob.UpdateDetails.UpdateProfileImageActivity;

public class ProfileSettingActivity extends AppCompatActivity {

    TextView NameTextView,PasswordTextView,SkillTextView;
    ImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        NameTextView = findViewById(R.id.NameTextView);
        PasswordTextView = findViewById(R.id.PasswordTextView);
        SkillTextView = findViewById(R.id.SkillTextView);
        userImage = findViewById(R.id.userImage);

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

}