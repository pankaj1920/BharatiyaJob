package com.bharatiyajob.bharatiyajob.Company.HomePage.BookmarkedCandidate;

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

import com.bharatiyajob.bharatiyajob.Company.HomePage.CandidateDetail.CandidateDetailActivity;
import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.GetBookmarkedCanList.GetBookMarkedCandidateResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.RemoveBookmarkedCan.RemoveBookMarkedCandidateResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.remove_candidate.RemoveCandidateResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;
import com.facebook.shimmer.ShimmerFrameLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookmarkCandidateFragment extends Fragment {

    GetBookMarkCandidateRecylerAdapter adapter;
    RecyclerView recycler_bookmark_fragment;
    String canId,companyId;
    ShimmerFrameLayout BcanSimmerEffect;
    ConstraintLayout BcanListLayout;

    public BookmarkCandidateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getCompanyDetail();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getBookMaredCandidate();
        View view= inflater.inflate(R.layout.fragment_bookmark_candidate, container, false);
        recycler_bookmark_fragment=view.findViewById(R.id.recycler_bookmark_fragment);
        BcanSimmerEffect=view.findViewById(R.id.BcanSimmerEffect);
        BcanListLayout=view.findViewById(R.id.BcanListLayout);

        BcanSimmerEffect.startShimmer();

        recycler_bookmark_fragment.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    public void getBookMaredCandidate()                                          {
        JobApi jobApi= BaseClient.getBaseClient().create(JobApi.class);

        Call<GetBookMarkedCandidateResponse> call=jobApi.getbookMarkCandidate(companyId);
        call.enqueue(new Callback<GetBookMarkedCandidateResponse>() {
            @Override
            public void onResponse(Call<GetBookMarkedCandidateResponse> call, Response<GetBookMarkedCandidateResponse> response) {

                final GetBookMarkedCandidateResponse getBookMarkedCanResponse = response.body();
                if (response.isSuccessful() && getBookMarkedCanResponse.getStatus().equals("1")){

                    BcanSimmerEffect.stopShimmer();
                    BcanSimmerEffect.setVisibility(View.GONE);
                    BcanListLayout.setVisibility(View.GONE);
                    recycler_bookmark_fragment.setVisibility(View.VISIBLE);

                    Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                    adapter=new GetBookMarkCandidateRecylerAdapter(response.body().getData(),getContext());
                    recycler_bookmark_fragment.setAdapter(adapter);

                    adapter.setOnUnBcItemClickListner(new GetBookMarkCandidateRecylerAdapter.OnUnBcItemClickListner() {
                        @Override
                        public void onUnBookmarkCanClicked(View view, int position) {
                            canId = getBookMarkedCanResponse.getData().get(position).getCandidate_id();
                            removeBookMarkCandidate();
                        }

                        @Override
                        public void onBookCanViewDetailCilcked(View view, int position) {
                            String canDetId = getBookMarkedCanResponse.getData().get(position).getCandidate_id();
                            Bundle bundle = new Bundle();
                            bundle.putString("canDetId",canDetId);
                            Intent intent = new Intent(getActivity(), CandidateDetailActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }

                        @Override
                        public void onBCanRemoveCilcked(View view, String canId, String jobId) {
                            removeCandidate(canId, jobId);
                        }
                    });
                }else{
                    Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                    BcanSimmerEffect.stopShimmer();
                    BcanSimmerEffect.setVisibility(View.GONE);
                    BcanListLayout.setVisibility(View.VISIBLE);
                    recycler_bookmark_fragment.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<GetBookMarkedCandidateResponse> call, Throwable t) {
                Toast.makeText(getContext(), "OnFailure", Toast.LENGTH_SHORT).show();
                BcanSimmerEffect.stopShimmer();
                BcanSimmerEffect.setVisibility(View.GONE);
                BcanListLayout.setVisibility(View.VISIBLE);
                recycler_bookmark_fragment.setVisibility(View.GONE);
            }
        });

    }

//    Removing the bookmark
    public void removeBookMarkCandidate(){
        JobApi jobApi= BaseClient.getBaseClient().create(JobApi.class);

        Call<RemoveBookMarkedCandidateResponse> call=jobApi.removebookmark(companyId,canId);
        call.enqueue(new Callback<RemoveBookMarkedCandidateResponse>() {
            @Override
            public void onResponse(Call<RemoveBookMarkedCandidateResponse> call, Response<RemoveBookMarkedCandidateResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getContext(), "book mark removed success"+canId, Toast.LENGTH_SHORT).show();
                    getBookMaredCandidate();

                }else{
                    Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RemoveBookMarkedCandidateResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Onfailure", Toast.LENGTH_SHORT).show();

            }
        });
    }


//    Removing Candidate
    private void removeCandidate(String canId, String jobId) {
        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<RemoveCandidateResponse> call = jobApi.removeCandidate(canId, jobId);
        call.enqueue(new Callback<RemoveCandidateResponse>() {
            @Override
            public void onResponse(Call<RemoveCandidateResponse> call, Response<RemoveCandidateResponse> response) {
                RemoveCandidateResponse removeCandidateResponse = response.body();
                if (response.isSuccessful() && removeCandidateResponse.getStatus().equals("1")) {
                    Toast.makeText(getActivity(), removeCandidateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    getBookMaredCandidate();
                } else {
                    Toast.makeText(getActivity(), "Try Again Later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RemoveCandidateResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "On Failure remove Candidate", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCompanyDetail() {
        LoginOtpResponse loginOtpResponse = LoginDetailSharePref.getInstance(getActivity()).getDetail();

        companyId = loginOtpResponse.getId();
    }
}