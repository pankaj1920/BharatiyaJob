package com.bharatiyajob.bharatiyajob.HomePage.FindJob;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.HomePage.HomeJob.HomeJobAdapter;
import com.bharatiyajob.bharatiyajob.HomePage.JobDetailActivity;
import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Candidate.ApplyJob.ApplyJobResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.MakeBookmark.MakeBookmarkResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.UnBookmarkJob.UnBookmarJobResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.Json.Candidate.SearchJob.JobResponse;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobByTypeFragment extends Fragment {

    TextView jbtSearchJob;
    RecyclerView JbtRecycleView;
    HomeJobAdapter homeJobAdapter;
    String skill,location;
    ShimmerFrameLayout JbtSimmerEffect;
    String userId,jobId,jobTitle,starPosition;
    JobResponse jobResponse;
    Button jobApplyBtn,homeAppliedJobBtn,homeApplyJobBtn;

    public JobByTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job_by_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final NavController navController = Navigation.findNavController(view);

        jbtSearchJob = view.findViewById(R.id.jbtSearchJob);
        JbtRecycleView = view.findViewById(R.id.JbtRecycleView);
        JbtSimmerEffect = view.findViewById(R.id.JbtSimmerEffect);
        jobApplyBtn = view.findViewById(R.id.jobApplyBtn);
        homeAppliedJobBtn = view.findViewById(R.id.homeAppliedJobBtn);
        homeApplyJobBtn = view.findViewById(R.id.homeApplyJobBtn);

        JbtSimmerEffect.startShimmer();
        JbtRecycleView.setVisibility(View.GONE);

        getCanDetail();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        JbtRecycleView.setLayoutManager(layoutManager);

        jbtSearchJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_jobByTypeFragment3_to_searchJobFragment2);
            }
        });

        getJobList();

    }

    private void getJobList() {

        if (getArguments() != null){
            JobByTypeFragmentArgs args = JobByTypeFragmentArgs.fromBundle(getArguments());
            skill = args.getSearchJobArgument().getSkills();
            location = args.getSearchJobArgument().getLocation();

            jbtSearchJob.setText(skill);
        }

        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<JobResponse> call = jobApi.searchJob(skill, location,userId);
        call.enqueue(new Callback<JobResponse>() {
            @Override
            public void onResponse(Call<JobResponse> call, Response<JobResponse> response) {
                 jobResponse = response.body();
                if (jobResponse != null) {
                    if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code() && jobResponse.getStatus().equals("1")) {

                        JbtSimmerEffect.stopShimmer();
                        JbtSimmerEffect.setVisibility(View.GONE);
                        JbtRecycleView.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "Sucess", Toast.LENGTH_SHORT).show();
                        homeJobAdapter = new HomeJobAdapter(jobResponse.getData());
                        JbtRecycleView.setAdapter(homeJobAdapter);

                        homeJobAdapter.setOnItemClickListner(new HomeJobAdapter.OnItemClickListner() {
                            @Override
                            public void onJobLayoutClicked(int position) {
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
                                starPosition = jobResponse.getData().get(position).getJob_id();
                                BookmarkJob();
                            }

                            @Override
                            public void onUnBookmarkBtnClicked(int position) {
                                unBookmarkJob();
                            }

                            @Override
                            public void onApplyJobButtonClicked(View itemview, int position) {
                                jobId = jobResponse.getData().get(position).getJob_id();
                                homeApplyJobBtn = itemview.findViewById(R.id.homeApplyJobBtn);
                                homeAppliedJobBtn = itemview.findViewById(R.id.homeAppliedJobBtn);

                                ApplyJob();
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
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void BookmarkJob() {
        final JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<MakeBookmarkResponse> call = jobApi.makeBookmark(starPosition,userId);
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


    private void ApplyJob() {
        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);

        Call<ApplyJobResponse> call = jobApi.applyJob(userId,jobId);
        call.enqueue(new Callback<ApplyJobResponse>() {
            @Override
            public void onResponse(Call<ApplyJobResponse> call, Response<ApplyJobResponse> response) {
                ApplyJobResponse applyJobResponse = response.body();
                if (response.isSuccessful() && applyJobResponse.getStatus().equals("1")){
                    Toast.makeText(getActivity(), applyJobResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    homeAppliedJobBtn.setVisibility(View.VISIBLE);
                    homeApplyJobBtn.setVisibility(View.GONE);
                }else {
                    Toast.makeText(getActivity(), applyJobResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApplyJobResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "On Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void unBookmarkJob(){

        Toast.makeText(getActivity(), "CanId : "+userId+ "JobId :" + jobId, Toast.LENGTH_SHORT).show();
        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<UnBookmarJobResponse> call = jobApi.unBookmarkJob(userId,jobId);
        call.enqueue(new Callback<UnBookmarJobResponse>() {
            @Override
            public void onResponse(Call<UnBookmarJobResponse> call, Response<UnBookmarJobResponse> response) {
                UnBookmarJobResponse unBookmarJobResponse = response.body();
                if (response.isSuccessful() && unBookmarJobResponse.getStatus().equals("1")){

                    Toast.makeText(getActivity(), unBookmarJobResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "Try Again Unnbookmark", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UnBookmarJobResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "On Failure UnBookmark Job", Toast.LENGTH_SHORT).show();
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