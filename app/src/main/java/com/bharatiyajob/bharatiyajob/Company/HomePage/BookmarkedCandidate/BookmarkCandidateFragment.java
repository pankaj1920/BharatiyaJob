package com.bharatiyajob.bharatiyajob.Company.HomePage.BookmarkedCandidate;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Company.GetBookmarkedCanList.GetBookMarkedCandidateResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.RemoveBookmarkedCan.RemoveBookMarkedCandidateResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookmarkCandidateFragment extends Fragment {

    GetBookMarkCandidateRecylerAdapter adapter;
    RecyclerView recycler_bookmark_fragment;
    String canId;
    ShimmerFrameLayout BcanSimmerEffect;
    ConstraintLayout BcanListLayout;

    public BookmarkCandidateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    public void getBookMaredCandidate(){
        JobApi jobApi= BaseClient.getBaseClient().create(JobApi.class);

        Call<GetBookMarkedCandidateResponse> call=jobApi.getbookMarkCandidate("1");
        call.enqueue(new Callback<GetBookMarkedCandidateResponse>() {
            @Override
            public void onResponse(Call<GetBookMarkedCandidateResponse> call, Response<GetBookMarkedCandidateResponse> response) {

                final GetBookMarkedCandidateResponse getBookMarkedCanResponse = response.body();
                if (response.isSuccessful()){

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

    public void removeBookMarkCandidate(){
        JobApi jobApi= BaseClient.getBaseClient().create(JobApi.class);

        Call<RemoveBookMarkedCandidateResponse> call=jobApi.removebookmark("1",canId);
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
}