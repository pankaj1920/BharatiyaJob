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
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.AboutUs;
import com.bharatiyajob.bharatiyajob.CustomerCare.CustomerCareActivity;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.User.CreateJobAlert.CreateJobAlertActivity;
import com.bharatiyajob.bharatiyajob.Login.LoginActivity;
import com.bharatiyajob.bharatiyajob.User.CandidateNotification.CandidateNotificationActivity;
import com.bharatiyajob.bharatiyajob.ProfileSettingActivity;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.ShareAppActivity;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;
import com.bharatiyajob.bharatiyajob.User.UserPaymentActivity;


public class ProfileFragment extends Fragment {

    ConstraintLayout PAccountSettingLayout,PNotificationLayout,PCreateAlertLayout,
            PShareAppLayout,PRateLayout,PContactLayout,PAboutUsLayout,SignoutLayout,PPaymentLayout;

    String userId,regType;

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
        PPaymentLayout = view.findViewById(R.id.PPaymentLayout);
        SignoutLayout = view.findViewById(R.id.SignoutLayout);
        PCreateAlertLayout = view.findViewById(R.id.PCreateAlertLayout);

        getCanDetail();


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

        PPaymentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPayment();
            }
        });

        SignoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signout();
            }
        });

        PCreateAlertLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getToCreateAlert();
            }
        });
    }

    private void getToCreateAlert() {
        Intent intent = new Intent(getActivity(), CreateJobAlertActivity.class);
        startActivity(intent);
    }

    private void goToPayment() {
        Intent intent = new Intent(getActivity(), UserPaymentActivity.class);
        startActivity(intent);
    }

    private void aboutUs() {
        Intent intent = new Intent(getActivity(), AboutUs.class);
        startActivity(intent);
    }

    private void contactUs() {
        Bundle bundle = new Bundle();
        bundle.putString("Id",userId);
        bundle.putString("regType",regType);
        Intent intent = new Intent(getActivity(), CustomerCareActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void rateUs() {
    }

    private void shareApp() {
        Intent intent = new Intent(getActivity(), ShareAppActivity.class);
        startActivity(intent);
    }

    private void goToNotification() {
        Intent intent = new Intent(getActivity(), CandidateNotificationActivity.class);
        startActivity(intent);
    }

    private void goToAccountSetting() {
        Intent intent = new Intent(getActivity(), ProfileSettingActivity.class);
        startActivity(intent);
    }


    public void Signout() {
        //we are callin Logout Method from SharePrefManager will will delet all user detail from share prefrences
        LoginDetailSharePref.getInstance(getActivity()).Logout();

        Toast.makeText(getActivity(), "SignOut", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onStart() {

        super.onStart();
    }

    private void getCanDetail() {
        LoginOtpResponse loginOtpResponse = LoginDetailSharePref.getInstance(getActivity()).getDetail();

        userId = loginOtpResponse.getId();
        regType = loginOtpResponse.getReg_type();

    }
}