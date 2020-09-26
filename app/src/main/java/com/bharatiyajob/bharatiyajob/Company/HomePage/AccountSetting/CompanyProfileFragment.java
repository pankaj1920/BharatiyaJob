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

import com.bharatiyajob.bharatiyajob.Company.HomePage.AccountSetting.CompanyNotification.CompanyNotificationActivity;
import com.bharatiyajob.bharatiyajob.Company.HomePage.AccountSetting.CompanyPayment.CompanyPaymentActivity;
import com.bharatiyajob.bharatiyajob.Company.HomePage.AccountSetting.CompanyProfile.CompanyProfileSetting;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.ShareAppActivity;


public class CompanyProfileFragment extends Fragment {

    ConstraintLayout CAccountSettingLayout,CNotificationLayout,CPaymentLayout,CShareAppLayout,
            CRateLayout,CContactLayout,CAboutUsLayout,CSignoutLayout;

    public CompanyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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


    }
}