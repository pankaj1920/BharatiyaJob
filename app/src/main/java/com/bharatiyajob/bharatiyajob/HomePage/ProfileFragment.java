package com.bharatiyajob.bharatiyajob.HomePage;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.AboutUs;
import com.bharatiyajob.bharatiyajob.CustomerCareActivity;
import com.bharatiyajob.bharatiyajob.Json.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.Login.LoginActivity;
import com.bharatiyajob.bharatiyajob.NotificationActivity;
import com.bharatiyajob.bharatiyajob.ProfileSettingActivity;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.ShareAppActivity;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;


public class ProfileFragment extends Fragment {

    ConstraintLayout PAccountSettingLayout,PNotificationLayout,
            PShareAppLayout,PRateLayout,PContactLayout,PAboutUsLayout,SignoutLayout;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PAccountSettingLayout = view.findViewById(R.id.PAccountSettingLayout);
        PNotificationLayout = view.findViewById(R.id.PNotificationLayout);
        PShareAppLayout = view.findViewById(R.id.PShareAppLayout);
        PRateLayout = view.findViewById(R.id.PRateLayout);
        PContactLayout = view.findViewById(R.id.PContactLayout);
        PAboutUsLayout = view.findViewById(R.id.PAboutUsLayout);
        SignoutLayout = view.findViewById(R.id.SignoutLayout);


        PAccountSettingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAccountSetting();
            }
        });

        PNotificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNotification();
            }
        });

        PShareAppLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareApp();
            }
        });

        PRateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateUs();
            }
        });

        PContactLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactUs();
            }
        });

        PAboutUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aboutUs();
            }
        });

        SignoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signout();
            }
        });
    }

    private void aboutUs() {
        Intent intent = new Intent(getActivity(), AboutUs.class);
        startActivity(intent);
    }

    private void contactUs() {
        Intent intent = new Intent(getActivity(), CustomerCareActivity.class);
        startActivity(intent);
    }

    private void rateUs() {
    }

    private void shareApp() {
        Intent intent = new Intent(getActivity(), ShareAppActivity.class);
        startActivity(intent);
    }

    private void goToNotification() {
        Intent intent = new Intent(getActivity(), NotificationActivity.class);
        startActivity(intent);
    }

    private void goToAccountSetting() {
        Intent intent = new Intent(getActivity(), ProfileSettingActivity.class);
        startActivity(intent);
    }

    public void Signout() {
        //we are callin Logout Method from SharePrefManager will will delet all user detail from share prefrences
        LoginDetailSharePref.getInstance(getActivity()).UserLogout();

        Toast.makeText(getActivity(), "SignOut", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onStart() {

        super.onStart();
    }

    private void getUserDetail() {
     LoginOtpResponse loginOtpResponse =  LoginDetailSharePref.getInstance(getActivity()).getUserDetail();
//     email = loginOtpResponse.getEmail();
//     name = loginOtpResponse.getName();
//     number = loginOtpResponse.getMobile();


    }
}