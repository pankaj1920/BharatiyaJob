package com.bharatiyajob.bharatiyajob;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Register.MobileRegisterResponse;
import com.bharatiyajob.bharatiyajob.Login.LoginActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    Button rigsterBtn;
    TextView loginTextView;
    EditText R_fname,R_email,R_password,R_mobile;
    String name, email,password,mobile;
    SignInButton R_googleSign;
    GoogleSignInClient googleSignInClient;
    int RC_Sign_In = 0;
    String G_email,personEmail;
    String canId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);



        rigsterBtn = findViewById(R.id.rigsterBtn);
        loginTextView = findViewById(R.id.loginTextView);
        R_fname = findViewById(R.id.R_fname);
        R_email = findViewById(R.id.R_email);
        R_password = findViewById(R.id.R_password);
        R_mobile = findViewById(R.id.R_mobile);

        R_googleSign = findViewById(R.id.R_googleSign);
        R_googleSign.setSize(SignInButton.SIZE_STANDARD);

        R_googleSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        rigsterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegistrationType();
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginActivity();
            }
        });
    }


    private void goToRegistrationType() {

        name = R_fname.getText().toString();
        email = R_email.getText().toString();
        password = R_password.getText().toString();
        mobile = R_mobile.getText().toString();

        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<MobileRegisterResponse> call = jobApi.mobileRegister(mobile,"candidate");

        call.enqueue(new Callback<MobileRegisterResponse>() {
            @Override
            public void onResponse(Call<MobileRegisterResponse> call, Response<MobileRegisterResponse> response) {
                MobileRegisterResponse mobileRegisterResponse = response.body();

                if (response.isSuccessful() && mobileRegisterResponse.getError().equals("success")){
                    Toast.makeText(RegistrationActivity.this, mobileRegisterResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    canId = mobileRegisterResponse.getCan_id();

                    Bundle bundle = new Bundle();
                    bundle.putString("name",name);
                    bundle.putString("email",email);
                    bundle.putString("password",password);
                    bundle.putString("mobile",mobile);
                    bundle.putString("canId",canId);
                    Intent intent = new Intent(RegistrationActivity.this,RegisterOtpActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                        Toast.makeText(RegistrationActivity.this, mobileRegisterResponse.getMessage()+" failed", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<MobileRegisterResponse> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                Toast.makeText(RegistrationActivity.this, t.getMessage()+" Try Again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_Sign_In);

        Toast.makeText(this, "sfdfsda"+G_email, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Google Mail : "+personEmail, Toast.LENGTH_SHORT).show();
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_Sign_In) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }else {
            Toast.makeText(this, "nnnnnnnnn", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            R_email.setText(account.getEmail());
            Toast.makeText(this, "aaaaaaaaaaaaa", Toast.LENGTH_SHORT).show();
           abc();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, "ffffffffff", Toast.LENGTH_SHORT).show();

        }
    }

    private void abc() {

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
             personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            R_email.setText(personEmail);
            Toast.makeText(this, "Google Mail : "+personEmail, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "rrrrrrrrrrrrrr", Toast.LENGTH_SHORT).show();
        }

    }


}