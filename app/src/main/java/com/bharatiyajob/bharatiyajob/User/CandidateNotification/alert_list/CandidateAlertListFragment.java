package com.bharatiyajob.bharatiyajob.User.CandidateNotification.alert_list;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Candidate.JobAlertList.GetJobAlertDataResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.remove_alert.RemoveAlertResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;
import com.bharatiyajob.bharatiyajob.User.CreateJobAlert.CreateJobAlertActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CandidateAlertListFragment extends Fragment {
    RecyclerView jobAlertRecycler;
    String userId;
    int deletePosition;
    JobAlertAdapter jobAlertAdapter;
    FloatingActionButton createAlert;
    ShimmerFrameLayout alertJobSimmerEffect;
    ConstraintLayout noAlertLayout;


    public CandidateAlertListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_candidate_alert_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        jobAlertRecycler = view.findViewById(R.id.jobAlertRecycler);
        createAlert = view.findViewById(R.id.createAlert);

        noAlertLayout = view.findViewById(R.id.noAlertLayout);

        getCanDetail();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        jobAlertRecycler.setLayoutManager(layoutManager);

        createAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateJobAlertActivity.class);
                startActivity(intent);
            }
        });

        getAlertList();
    }

    private void getAlertList() {
        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<GetJobAlertDataResponse> call = jobApi.getJobAlert(userId);
        call.enqueue(new Callback<GetJobAlertDataResponse>() {
            @Override
            public void onResponse(Call<GetJobAlertDataResponse> call, Response<GetJobAlertDataResponse> response) {

                GetJobAlertDataResponse alertDataResponse = response.body();
                if (response.isSuccessful() && alertDataResponse.getStatus().equals("1")){

                    jobAlertAdapter = new JobAlertAdapter(alertDataResponse.getData());
                    jobAlertRecycler.setAdapter(jobAlertAdapter);

                    jobAlertAdapter.setOnnAlertDeleteListner(new JobAlertAdapter.OnAlertDeleteListner() {

                        @Override
                        public void onAlertDeleteClicked(String alertId) {
                            deletAlert(alertId);
                        }
                    });

                }else {
                    Toast.makeText(getActivity(), "Try Again", Toast.LENGTH_SHORT).show();

                    noAlertLayout.setVisibility(View.VISIBLE);
                    jobAlertRecycler.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<GetJobAlertDataResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "On Failure gET Alert jOB", Toast.LENGTH_SHORT).show();
                noAlertLayout.setVisibility(View.VISIBLE);
                jobAlertRecycler.setVisibility(View.GONE);
            }
        });

    }

    private void deletAlert(String alertId) {
        Toast.makeText(getActivity(), "Alert Deleted : "+alertId, Toast.LENGTH_SHORT).show();
        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<RemoveAlertResponse> call = jobApi.removeAlert(userId,alertId);
        call.enqueue(new Callback<RemoveAlertResponse>() {
            @Override
            public void onResponse(Call<RemoveAlertResponse> call, Response<RemoveAlertResponse> response) {
                RemoveAlertResponse removeAlertResponse = response.body();
                if (response.isSuccessful() && removeAlertResponse.getStatus().equals("1")){
                    Toast.makeText(getContext(), removeAlertResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    getAlertList();
                }else {
                    Toast.makeText(getContext(), "Try Again Late", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RemoveAlertResponse> call, Throwable t) {
                Toast.makeText(getContext(), "On Failure in Remove Alert", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getCanDetail() {
//        LoginOtpResponse loginOtpResponse = LoginDetailSharePref.getInstance(getActivity()).getDetail();
        LoginDetailSharePref loginDetailSharePref = new LoginDetailSharePref(getActivity());
        LoginOtpResponse loginOtpResponse = loginDetailSharePref.getDetail();
        userId = loginOtpResponse.getId();
    }
}