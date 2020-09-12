package com.bharatiyajob.bharatiyajob.HomePage.FindJob;

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
import android.widget.TextView;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.HomePage.HomeJob.HomeJobAdapter;
import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.Json.SearchJob.JobResponse;
import com.bharatiyajob.bharatiyajob.R;
import com.facebook.shimmer.Shimmer;
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

        JbtSimmerEffect.startShimmer();
        JbtRecycleView.setVisibility(View.GONE);

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
        Call<JobResponse> call = jobApi.searchJob(skill, location);
        call.enqueue(new Callback<JobResponse>() {
            @Override
            public void onResponse(Call<JobResponse> call, Response<JobResponse> response) {
                JobResponse jobResponse = response.body();
                if (jobResponse != null) {
                    if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code() && jobResponse.getStatus().equals("1")) {

                        JbtSimmerEffect.stopShimmer();
                        JbtSimmerEffect.setVisibility(View.GONE);
                        JbtRecycleView.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "Sucess", Toast.LENGTH_SHORT).show();
                        homeJobAdapter = new HomeJobAdapter(jobResponse.getData());
                        JbtRecycleView.setAdapter(homeJobAdapter);
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
    }