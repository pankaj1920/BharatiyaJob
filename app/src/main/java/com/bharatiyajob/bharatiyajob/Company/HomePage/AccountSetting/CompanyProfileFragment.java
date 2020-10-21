package com.bharatiyajob.bharatiyajob.Company.HomePage.AccountSetting;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.Company.HomePage.AccountSetting.CompanyNotification.CompanyNotificationActivity;
import com.bharatiyajob.bharatiyajob.Company.HomePage.AccountSetting.CompanyPayment.CompanyPaymentActivity;
import com.bharatiyajob.bharatiyajob.Company.HomePage.AccountSetting.CompanyProfile.CompanyProfileSetting;
import com.bharatiyajob.bharatiyajob.CustomerCare.CustomerCareActivity;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.Login.LoginActivity;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.ShareAppActivity;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;


public class CompanyProfileFragment extends Fragment {

    ConstraintLayout CAccountSettingLayout,CNotificationLayout,CPaymentLayout,CShareAppLayout,
            CRateLayout,CContactLayout,CAboutUsLayout,CSignoutLayout;
    String comId,regType;

    public CompanyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCompanyDetail();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_company_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CAccountSettingLayout = view.findViewById(R.id.CAccountSettingLayout);
        CNotificationLayout = view.findViewById(R.id.CNotificationLayout);
        CPaymentLayout = view.findViewById(R.id.CPaymentLayout);
        CShareAppLayout = view.findViewById(R.id.CShareAppLayout);
        CRateLayout = view.findViewById(R.id.CRateLayout);
        CContactLayout = view.findViewById(R.id.CContactLayout);
        CAboutUsLayout = view.findViewById(R.id.CAboutUsLayout);
        CSignoutLayout = view.findViewById(R.id.CSignoutLayout);

        CAccountSettingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CompanyProfileSetting.class);
                startActivity(intent);
            }
        });

        CNotificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CompanyNotificationActivity.class);
                startActivity(intent);
            }
        });


        CPaymentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CompanyPaymentActivity.class);
                startActivity(intent);
            }
        });

        CShareAppLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ShareAppActivity.class);
                startActivity(intent);
            }
        });

        CContactLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               contactUs();
            }
        });

        CSignoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signout();
            }
        });

    }

    public void Signout() {
        //we are callin Logout Method from SharePrefManager will will delet all user detail from share prefrences
//        LoginDetailSharePref.getInstance(getActivity()).Logout();
        LoginDetailSharePref loginDetailSharePref = new LoginDetailSharePref(getActivity());
        LoginOtpResponse loginOtpResponse = loginDetailSharePref.getDetail();

        Toast.makeText(getActivity(), "SignOut", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void contactUs() {
        Bundle bundle = new Bundle();
        bundle.putString("Id",comId);
        bundle.putString("regType",regType);
        Intent intent = new Intent(getActivity(), CustomerCareActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void getCompanyDetail() {
//        LoginOtpResponse loginOtpResponse = LoginDetailSharePref.getInstance(getActivity()).getDetail();
        LoginDetailSharePref loginDetailSharePref = new LoginDetailSharePref(getActivity());
        LoginOtpResponse loginOtpResponse = loginDetailSharePref.getDetail();
        comId = loginOtpResponse.getId();
        regType = loginOtpResponse.getReg_type();

    }
}