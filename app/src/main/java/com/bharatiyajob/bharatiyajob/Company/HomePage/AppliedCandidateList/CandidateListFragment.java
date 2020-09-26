package com.bharatiyajob.bharatiyajob.Company.HomePage.AppliedCandidateList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Company.BookmarkCandidate.BookmarkCandidateResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.CandidateApplied.CandidateAppliedResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CandidateListFragment extends Fragment {

    RecyclerView candidateListRecyclerView;
    CandidateListAdapter candidateListAdapter;
    String boomarkCanId;
    ImageView bookmarkCanStar;
    ShimmerFrameLayout canListSimmerEffect;
    ConstraintLayout canListLayout;

    public CandidateListFragment() {
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
//        return inflater.inflate(R.layout.candidate_list_item, container, false);
        return inflater.inflate(R.layout.fragment_candidate_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        candidateListRecyclerView = view.findViewById(R.id.candidateListRecyclerView);
        canListSimmerEffect = view.findViewById(R.id.canListSimmerEffect);
        canListLayout = view.findViewById(R.id.canListLayout);

        canListSimmerEffect.startShimmer();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        candidateListRecyclerView.setLayoutManager(layoutManager);


        getCandidateList();
    }

    private void getCandidateList() {
        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<CandidateAppliedResponse> call = jobApi.getCandidateList("1");

        call.enqueue(new Callback<CandidateAppliedResponse>() {
            @Override
            public void onResponse(Call<CandidateAppliedResponse> call, Response<CandidateAppliedResponse> response) {
                final CandidateAppliedResponse appliedResponse = response.body();
                if (response.isSuccessful() && appliedResponse.getStatus().equals("1")) {

                    canListSimmerEffect.stopShimmer();
                    canListSimmerEffect.setVisibility(View.GONE);
                    candidateListRecyclerView.setVisibility(View.VISIBLE);
                    canListLayout.setVisibility(View.GONE);

                    candidateListAdapter = new CandidateListAdapter(appliedResponse.getData());
                    candidateListRecyclerView.setAdapter(candidateListAdapter);

                    candidateListAdapter.setOnItemClickListner(new CandidateListAdapter.OnItemClickListner() {
                        @Override
                        public void onCanBookmarkClicked(View view, int position ) {
                            bookmarkCanStar = view.findViewById(R.id.bookmarkCanStar);

                            boomarkCanId = appliedResponse.getData().get(position).getCandidate_id();
                            bookMarkCandidate();
                        }
                    });

                } else {
                    Toast.makeText(getActivity(), "Try Again", Toast.LENGTH_SHORT).show();
                    canListSimmerEffect.stopShimmer();
                    canListSimmerEffect.setVisibility(View.GONE);
                    candidateListRecyclerView.setVisibility(View.GONE);
                    canListLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<CandidateAppliedResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "on Failure "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                canListSimmerEffect.stopShimmer();
                canListSimmerEffect.setVisibility(View.GONE);
                candidateListRecyclerView.setVisibility(View.GONE);
                canListLayout.setVisibility(View.VISIBLE);

            }

        });
    }

    private void bookMarkCandidate() {
        Toast.makeText(getActivity(), "bookmar Can id "+boomarkCanId, Toast.LENGTH_SHORT).show();
        
        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<BookmarkCandidateResponse> call = jobApi.bookmarkCandidate("1",boomarkCanId);
        call.enqueue(new Callback<BookmarkCandidateResponse>() {
            @Override
            public void onResponse(Call<BookmarkCandidateResponse> call, Response<BookmarkCandidateResponse> response) {
                BookmarkCandidateResponse candidateResponse = response.body();
                if (response.isSuccessful() && candidateResponse.getStatus().equals("1")){
                    Toast.makeText(getActivity(), "Bookmark added Sucessfuly", Toast.LENGTH_SHORT).show();
                    bookmarkCanStar.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.ic_fill));
                }else {
                    Toast.makeText(getActivity(), "Try Again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookmarkCandidateResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "On Failure" + t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}