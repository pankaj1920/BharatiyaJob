package com.bharatiyajob.bharatiyajob.Company.HomePage.AccountSetting.CompanyProfile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.Company.HomePage.update_company_details.UpdateCompanyAddress;
import com.bharatiyajob.bharatiyajob.Company.HomePage.update_company_details.UpdateCompanyName;
import com.bharatiyajob.bharatiyajob.Company.HomePage.update_company_details.UpdateCompanyPassword;
import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.GetCompanyDetails.GetCompanyDetailsResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyProfileSetting extends AppCompatActivity {

    CircleImageView companyLogo;
    TextView CNameTextView,CNumberTextView,CEmailTextView,CPasswordTextView,CAdressTextView;
    String companyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile_setting);

        companyLogo = findViewById(R.id.companyLogo);
        CNameTextView = findViewById(R.id.CNameTextView);
        CNumberTextView = findViewById(R.id.CNumberTextView);
        CEmailTextView = findViewById(R.id.CEmailTextView);
        CPasswordTextView = findViewById(R.id.CPasswordTextView);
        CAdressTextView = findViewById(R.id.CAdressTextView);
        getCompanyDetail();

        getCompanyDetails();
    }

    private void getCompanyDetails() {
        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<GetCompanyDetailsResponse> call = jobApi.getCompanyDetails(companyId);
        call.enqueue(new Callback<GetCompanyDetailsResponse>() {
            @Override
            public void onResponse(Call<GetCompanyDetailsResponse> call, Response<GetCompanyDetailsResponse> response) {

                GetCompanyDetailsResponse companyDetailsResponse = response.body();
                if (response.isSuccessful() && companyDetailsResponse.getStatus().equals("1")){
                    CNameTextView.setText(companyDetailsResponse.getData().getCompany_name());
                    CNumberTextView.setText(companyDetailsResponse.getData().getOwner_mobile());
                    CEmailTextView.setText(companyDetailsResponse.getData().getFirm_email());
                    CAdressTextView.setText(companyDetailsResponse.getData().getAddress());
                }else {
                    Toast.makeText(CompanyProfileSetting.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetCompanyDetailsResponse> call, Throwable t) {

            }
        });
    }

    private void getCompanyDetail() {
        LoginOtpResponse loginOtpResponse = LoginDetailSharePref.getInstance(this).getDetail();

        companyId = loginOtpResponse.getId();
    }

    public void updateCompanyName(View view) {
        Intent intent = new Intent(CompanyProfileSetting.this, UpdateCompanyName.class);
        startActivity(intent);

    }

    public void updateCompanyPassword(View view) {
        Intent intent = new Intent(CompanyProfileSetting.this, UpdateCompanyPassword.class);
        startActivity(intent);
    }

    public void updateCompanyAddress(View view) {
        Intent intent = new Intent(CompanyProfileSetting.this, UpdateCompanyAddress.class);
        startActivity(intent);
    }
}