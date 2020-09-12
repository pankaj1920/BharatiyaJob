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

import com.bharatiyajob.bharatiyajob.HomePage.HomeJob.HomeJobAdapter;
import com.bharatiyajob.bharatiyajob.HomePage.JobDetailActivity;
import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.Json.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.SavedJob.BookmarkJobResponse;
import com.bharatiyajob.bharatiyajob.Json.SearchJob.JobResponse;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveJobFragment extends Fragment {

    Button jobApplyBtn;
    RecyclerView savedJobRecycler;
    SaveJobAdapter saveJobAdapter;
    ShimmerFrameLayout findJobSimmerEffect;
    String jobId,jobTitle,canId;
    TextView candidateName,candidateJobTitle;
    ConstraintLayout noJobFoundLayout;

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

        getCandidateDetail();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        savedJobRecycler.setLayoutManager(layoutManager);

        findJobSimmerEffect.startShimmer();
        savedJobRecycler.setVisibility(View.GONE);
        getBookmarkJob();

        Toast.makeText(getActivity(), canId, Toast.LENGTH_SHORT).show();
    }

    private void getCandidateDetail() {
        LoginOtpResponse loginOtpResponse = LoginDetailSharePref.getInstance(getActivity()).getUserDetail();

        canId = loginOtpResponse.getId();
    }

    private void getBookmarkJob() {
        final JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<BookmarkJobResponse> call = jobApi.getBookmarkJob(canId);
        call.enqueue(new Callback<BookmarkJobResponse>() {
            @Override
            public void onResponse(Call<BookmarkJobResponse> call, Response<BookmarkJobResponse> response) {
                BookmarkJobResponse jobResponse = response.body();
                if (response.isSuccessful() && jobResponse.getStatus().equals("1")){

                    Toast.makeText(getActivity(), "Sucess", Toast.LENGTH_SHORT).show();

                    findJobSimmerEffect.stopShimmer();
                    findJobSimmerEffect.setVisibility(View.GONE);
                    savedJobRecycler.setVisibility(View.VISIBLE);
                    saveJobAdapter = new SaveJobAdapter(jobResponse.getData());
                    savedJobRecycler.setAdapter(saveJobAdapter);

                }else {
                    Toast.makeText(getActivity(), "UnSucess", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookmarkJobResponse> call, Throwable t) {
                findJobSimmerEffect.stopShimmer();
                findJobSimmerEffect.setVisibility(View.GONE);
                noJobFoundLayout.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "On Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

