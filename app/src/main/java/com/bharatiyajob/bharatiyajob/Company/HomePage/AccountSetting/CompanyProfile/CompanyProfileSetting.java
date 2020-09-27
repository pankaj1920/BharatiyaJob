package com.bharatiyajob.bharatiyajob.Company.HomePage.AccountSetting.CompanyProfile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.bharatiyajob.bharatiyajob.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompanyProfileSetting extends AppCompatActivity {

    CircleImageView companyLogo;
    TextView CNameTextView,CNumberTextView,CEmailTextView,CPasswordTextView,CAdressTextView;
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

    }
}