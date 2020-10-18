package com.bharatiyajob.bharatiyajob.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginEntrNumResponse;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.RegisterAsActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView Register;
    EditText loginEditext;
    String loginId;
    Button signBtn;
    SignInButton loginGoogleSign;
    GoogleSignInClient mGoogleSignInClient;
    int RC_Sign_In = 0;
    Bundle bundle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Register = findViewById(R.id.RegisterTextView);
        loginEditext = findViewById(R.id.loginEditext);
        signBtn = findViewById(R.id.signBtn);

        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToRegister();
            }
        });

    }


    private void signIn() {
        loginId = loginEditext.getText().toString().trim();

        if (loginId.equals("")) {
            loginEditext.setError("Enter Email or Number");
            loginEditext.requestFocus();
        } else {

            bundle = new Bundle();
            bundle.putString("logiId", loginId);

            if (loginId.matches("[0-9]+")) {

                if (loginId.length() == 10) {

                    JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);

                    Call<LoginEntrNumResponse> call = jobApi.enterLoginNum((loginId));

                    call.enqueue(new Callback<LoginEntrNumResponse>() {
                        @Override
                        public void onResponse(Call<LoginEntrNumResponse> call, Response<LoginEntrNumResponse> response) {

                            LoginEntrNumResponse entrNumResponse = response.body();

                            if (response.isSuccessful() && entrNumResponse.getStatus().equals("1")) {
                                Toast.makeText(LoginActivity.this, entrNumResponse.getData(), Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LoginActivity.this, EnterOtpActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Mobile Number not Register", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<LoginEntrNumResponse> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                            Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                } else {
                    loginEditext.setError("Enter valid number");
                    loginEditext.requestFocus();
                    return;
                }

            } else {
                Intent intent = new Intent(LoginActivity.this, LoginPasswordActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }

    private void GoToRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterAsActivity.class);
        startActivity(intent);

    }
}