package com.bharatiyajob.bharatiyajob.HomePage.HomeJob;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.HomePage.JobDetailActivity;
import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Candidate.GetUserDetails.GetUserDetailResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.MakeBookmark.MakeBookmarkResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.SearchJob.JobResponse;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    Button jobApplyBtn;
    RecyclerView homeJobRecycler;
    HomeJobAdapter homeJobAdapter;
    ShimmerFrameLayout findJobSimmerEffect;
    String jobId,jobTitle;
    TextView candidateName,candidateJobTitle;
    String userId;



    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        jobApplyBtn = view.findViewById(R.id.jobApplyBtn);
        homeJobRecycler = view.findViewById(R.id.homeJobRecycler);
        findJobSimmerEffect = view.findViewById(R.id.findJobSimmerEffect);
        candidateName = view.findViewById(R.id.candidateName);
        candidateJobTitle = view.findViewById(R.id.candidateJobTitle);

        getCanDetail();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        homeJobRecycler.setLayoutManager(layoutManager);

        findJobSimmerEffect.startShimmer();
        homeJobRecycler.setVisibility(View.GONE);

        getUserDetail();
        
        getHomeJob();

        return view;
    }

    private void getCanDetail() {
        LoginOtpResponse loginOtpResponse = LoginDetailSharePref.getInstance(getActivity()).getUserDetail();

        userId = loginOtpResponse.getId();

    }

    private void getUserDetail() {
        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<GetUserDetailResponse> call = jobApi.getUserDetails(userId);

        call.enqueue(new Callback<GetUserDetailResponse>() {
            @Override
            public void onResponse(Call<GetUserDetailResponse> call, Response<GetUserDetailResponse> response) {
                GetUserDetailResponse getUserDetailResponse = response.body();
                if (response.isSuccessful() && getUserDetailResponse.getStatus().equals("1")){
                    candidateName.setText(getUserDetailResponse.getData().getName());
                    candidateJobTitle.setText(getUserDetailResponse.getData().getSkill());
                    Toast.makeText(getActivity(), "Sucess" + getUserDetailResponse.getData().getFull_name(), Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getActivity(), "No Data Found" + userId, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<GetUserDetailResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed + onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getHomeJob() {
        final JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<JobResponse> call = jobApi.searchJob("p", "");
        call.enqueue(new Callback<JobResponse>() {
            @Override
            public void onResponse(Call<JobResponse> call, Response<JobResponse> response) {

                final JobResponse jobResponse = response.body();

                if (jobResponse != null) {
                    if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code() && jobResponse.getStatus().equals("1")) {
//                        Toast.makeText(getActivity(), "Sucess", Toast.LENGTH_SHORT).show();
                        findJobSimmerEffect.stopShimmer();
                        findJobSimmerEffect.setVisibility(View.GONE);
                        homeJobRecycler.setVisibility(View.VISIBLE);
                        homeJobAdapter = new HomeJobAdapter(jobResponse.getData());
                        homeJobRecycler.setAdapter(homeJobAdapter);

                       homeJobAdapter.setOnItemClickListner(new HomeJobAdapter.OnItemClickListner() {
                           @Override
                           public void onJobLayoutClicked(View itemview, int position) {

                               jobId = jobResponse.getData().get(position).getJob_id();
                               jobTitle = jobResponse.getData().get(position).getJob_title();

                               Toast.makeText(getActivity(), jobId + " " + jobTitle, Toast.LENGTH_SHORT).show();

                               Bundle bundle = new Bundle();
                               bundle.putString("jobId",jobId);
                               Intent intent = new Intent(getActivity(), JobDetailActivity.class);
                               intent.putExtras(bundle);
                               startActivity(intent);

                           }

                           @Override
                           public void onBookmarkBtnClicked(View itemview, int position) {
                                BookmarkJob();
                           }
                       });
                    } else {

                        Toast.makeText(getActivity(), "UnSucessful", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "Response is null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JobResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed + onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void BookmarkJob() {
        final JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<MakeBookmarkResponse> call = jobApi.makeBookmark(jobId,userId);
        call.enqueue(new Callback<MakeBookmarkResponse>() {
            @Override
            public void onResponse(Call<MakeBookmarkResponse> call, Response<MakeBookmarkResponse> response) {
                MakeBookmarkResponse bookmarkResponse = response.body();
                if (response.isSuccessful() && bookmarkResponse.getStatus().equals("1")){

                    Toast.makeText(getActivity(), bookmarkResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "Unsucess" + bookmarkResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MakeBookmarkResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "OnFailure Bookmark", Toast.LENGTH_SHORT).show();
            }
        });
    }

}