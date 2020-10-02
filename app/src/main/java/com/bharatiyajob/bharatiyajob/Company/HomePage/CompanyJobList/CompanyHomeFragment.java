package com.bharatiyajob.bharatiyajob.Company.HomePage.CompanyJobList;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.CustomerCareActivity;
import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Company.CompanyJobList.CompanyJobListResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CompanyHomeFragment extends Fragment {

    RecyclerView recycler_home_fragment;
    CompanyHomePageRecyleAdapter companyHomePageRecyleAdapter;
    ConstraintLayout creatDocLayout;
    ShimmerFrameLayout jobPostSimmerEffect;

    public CompanyHomeFragment() {
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
        View view=inflater.inflate(R.layout.fragment_company_home, container, false);
        recycler_home_fragment=view.findViewById(R.id.recycler_home_fragment);
        creatDocLayout=view.findViewById(R.id.creatDocLayout);
        jobPostSimmerEffect=view.findViewById(R.id.jobPostSimmerEffect);

        jobPostSimmerEffect.startShimmer();

        recycler_home_fragment.setLayoutManager(new LinearLayoutManager(getActivity()));

        GetJobPostedList();
        return view;
    }

    public void GetJobPostedList(){
        JobApi jobApi= BaseClient.getBaseClient().create(JobApi.class);

        Call<CompanyJobListResponse> call=jobApi.getPostedJob("1");
        call.enqueue(new Callback<CompanyJobListResponse>() {
            @Override
            public void onResponse(Call<CompanyJobListResponse> call, Response<CompanyJobListResponse> response) {
                final CompanyJobListResponse companyJobListResponse = response.body();
                if (response.isSuccessful() && companyJobListResponse.getStatus().equals("1")){
                    jobPostSimmerEffect.stopShimmer();
                    jobPostSimmerEffect.setVisibility(View.GONE);
                    recycler_home_fragment.setVisibility(View.VISIBLE);
                    creatDocLayout.setVisibility(View.GONE);
                    companyHomePageRecyleAdapter=new CompanyHomePageRecyleAdapter(response.body().getData(),getContext());
                    recycler_home_fragment.setAdapter(companyHomePageRecyleAdapter);

                    companyHomePageRecyleAdapter.setOnJobItemClickListner(new CompanyHomePageRecyleAdapter.OnJobItemClickListner() {
                        @Override
                        public void onJobItemClicked(View view, int position) {
                            Bundle bundle = new Bundle();
                            String jobId = companyJobListResponse.getData().get(position).getJob_id();
                            bundle.putString("comJdJobId",jobId);
                            Intent intent=new Intent(getActivity(), CompanyJobDetailActivity.class);
                            intent.putExtras(bundle);
                           startActivity(intent);
                        }
                    });

                }else {
                    Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                    jobPostSimmerEffect.stopShimmer();
                    jobPostSimmerEffect.setVisibility(View.GONE);
                    recycler_home_fragment.setVisibility(View.GONE);
                    creatDocLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<CompanyJobListResponse> call, Throwable t) {
//                Toast.makeText(getContext(), "onFailed" + t.getMessage(), Toast.LENGTH_SHORT).show();
                jobPostSimmerEffect.stopShimmer();
                jobPostSimmerEffect.setVisibility(View.GONE);
                recycler_home_fragment.setVisibility(View.GONE);
                creatDocLayout.setVisibility(View.VISIBLE);
            }
        });
    }
}