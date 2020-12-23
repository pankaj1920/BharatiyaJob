package com.bharatiyajob.bharatiyajob.HomePage.BokmarkedJob;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.HomePage.JobDetailActivity;
import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Candidate.UnBookmarkJob.UnBookmarJobResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.SavedJob.BookmarkJobResponse;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;
import com.facebook.shimmer.ShimmerFrameLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveJobFragment extends Fragment {

    Button jobApplyBtn;
    RecyclerView savedJobRecycler;
    SaveJobAdapter saveJobAdapter;
    ShimmerFrameLayout findJobSimmerEffect;
    String jobId,jobTitle,canId,userId;
    TextView candidateName,candidateJobTitle;
    ConstraintLayout noJobFoundLayout,canBookmarkedJob;

    public SaveJobFragment() {
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
        return inflater.inflate(R.layout.fragment_save_job, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        jobApplyBtn = view.findViewById(R.id.jobApplyBtn);
        savedJobRecycler = view.findViewById(R.id.savedJobRecycler);
        findJobSimmerEffect = view.findViewById(R.id.findJobSimmerEffect);
        candidateName = view.findViewById(R.id.candidateName);
        candidateJobTitle = view.findViewById(R.id.candidateJobTitle);
        noJobFoundLayout = view.findViewById(R.id.noJobFoundLayout);
        canBookmarkedJob = view.findViewById(R.id.canBookmarkedJob);

        getCanDetail();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        savedJobRecycler.setLayoutManager(layoutManager);

        findJobSimmerEffect.startShimmer();
        savedJobRecycler.setVisibility(View.GONE);
        getBookmarkJob();

        Toast.makeText(getContext(), canId, Toast.LENGTH_SHORT).show();
    }

    private void getCanDetail() {
//        LoginOtpResponse loginOtpResponse = LoginDetailSharePref.getInstance(getContext()).getDetail();

        LoginDetailSharePref loginDetailSharePref = new LoginDetailSharePref(getContext());
        LoginOtpResponse loginOtpResponse = loginDetailSharePref.getDetail();

        userId = loginOtpResponse.getId();
    }


    private void getBookmarkJob() {
        final JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<BookmarkJobResponse> call = jobApi.getBookmarkJob(userId);
        call.enqueue(new Callback<BookmarkJobResponse>() {
            @Override
            public void onResponse(Call<BookmarkJobResponse> call, Response<BookmarkJobResponse> response) {
                final BookmarkJobResponse jobResponse = response.body();
                if (response.isSuccessful() && jobResponse.getStatus().equals("1")){

                    Toast.makeText(getContext(), "Sucess", Toast.LENGTH_SHORT).show();

                    findJobSimmerEffect.stopShimmer();
                    findJobSimmerEffect.setVisibility(View.GONE);
                    savedJobRecycler.setVisibility(View.VISIBLE);

                    saveJobAdapter = new SaveJobAdapter(jobResponse.getData());
                    savedJobRecycler.setAdapter(saveJobAdapter);

                    saveJobAdapter.setOnItemClickListner(new SaveJobAdapter.OnSaveJobItemClickListner() {
                        @Override
                        public void onJobLayoutClicked(int position) {
                            jobId = jobResponse.getData().get(position).getJob_id();
                            jobTitle = jobResponse.getData().get(position).getJob_title();

                            Toast.makeText(getContext(), jobId + " " + jobTitle, Toast.LENGTH_SHORT).show();

                            Bundle bundle = new Bundle();
                            bundle.putString("jobId",jobId);
                            Intent intent = new Intent(getContext(), JobDetailActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }

                        @Override
                        public void onBookmarkedButtonCicked(int position) {
                            jobId = jobResponse.getData().get(position).getJob_id();
                            unBookmarkJob();
                            Toast.makeText(getContext(), "Bookmarked Clicked", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {
                    findJobSimmerEffect.stopShimmer();
                    findJobSimmerEffect.setVisibility(View.GONE);
                    savedJobRecycler.setVisibility(View.GONE);
                    canBookmarkedJob.setVisibility(View.VISIBLE);

                    Toast.makeText(getContext(), "UnSucess", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookmarkJobResponse> call, Throwable t) {
                findJobSimmerEffect.stopShimmer();
                findJobSimmerEffect.setVisibility(View.GONE);
                savedJobRecycler.setVisibility(View.GONE);
                canBookmarkedJob.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "On Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void unBookmarkJob(){

        Toast.makeText(getContext(), "CanId : "+userId+ "JobId :" + jobId, Toast.LENGTH_SHORT).show();
        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<UnBookmarJobResponse> call = jobApi.unBookmarkJob(userId,jobId);
        call.enqueue(new Callback<UnBookmarJobResponse>() {
            @Override
            public void onResponse(Call<UnBookmarJobResponse> call, Response<UnBookmarJobResponse> response) {
                UnBookmarJobResponse unBookmarJobResponse = response.body();
                if (response.isSuccessful() && unBookmarJobResponse.getStatus().equals("1")){

                    getBookmarkJob();
                    Toast.makeText(getContext(), unBookmarJobResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Try Again Unnbookmark", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UnBookmarJobResponse> call, Throwable t) {
                Toast.makeText(getContext(), "On Failure UnBookmark Job", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

