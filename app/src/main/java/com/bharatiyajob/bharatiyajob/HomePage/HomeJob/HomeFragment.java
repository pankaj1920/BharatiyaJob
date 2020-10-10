package com.bharatiyajob.bharatiyajob.HomePage.HomeJob;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.HomePage.JobDetailActivity;
import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Candidate.ApplyJob.ApplyJobResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.GetUserDetails.GetUserDetailResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.UnBookmarkJob.UnBookmarJobResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.MakeBookmark.MakeBookmarkResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.SearchJob.JobResponse;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.net.HttpURLConnection;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    Button jobApplyBtn,homeAppliedJobBtn,homeApplyJobBtn;
    RecyclerView homeJobRecycler;
    HomeJobAdapter homeJobAdapter;
    ShimmerFrameLayout findJobSimmerEffect,homeProfileSimmerEffect;
    String jobId,jobTitle,starPosition;
    TextView candidateName,candidateJobTitle;
    String userId,skill,location;
    ConstraintLayout homeNoJobLayout,homProfieLay,homeProfileDetail;
    CircleImageView candidateLogo;
    ImageView bookmarkedStar,bookmarkStar;



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
        homeNoJobLayout = view.findViewById(R.id.homeNoJobLayout);
        candidateLogo = view.findViewById(R.id.candidateLogo);
        homProfieLay = view.findViewById(R.id.homProfieLay);
        homeProfileDetail = view.findViewById(R.id.homeProfileDetail);
        homeProfileSimmerEffect = view.findViewById(R.id.homeProfileSimmerEffect);


        getCanDetail();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        homeJobRecycler.setLayoutManager(layoutManager);

        findJobSimmerEffect.startShimmer();
        homeProfileSimmerEffect.startShimmer();

        homeJobRecycler.setVisibility(View.GONE);

        getUserDetail();

        return view;
    }

    private void getCanDetail() {
        LoginOtpResponse loginOtpResponse = LoginDetailSharePref.getInstance(getActivity()).getDetail();

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
                    homeProfileSimmerEffect.stopShimmer();
                    homeProfileSimmerEffect.setVisibility(View.GONE);
                    homProfieLay.setVisibility(View.GONE);
                    homeProfileDetail.setVisibility(View.VISIBLE);

                    candidateName.setText(getUserDetailResponse.getData().getName());
                    skill = getUserDetailResponse.getData().getSkill();
                    candidateJobTitle.setText(skill);
                    if (!getUserDetailResponse.getData().getProfile_pic().isEmpty()) {
                        Picasso.get().load(getUserDetailResponse.getData().getProfile_pic()).into(candidateLogo);
                    }

                    getHomeJob();
//                    Toast.makeText(getActivity(), "Sucess" + getUserDetailResponse.getData().getName(), Toast.LENGTH_SHORT).show();

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
        Call<JobResponse> call = jobApi.searchJob(skill, "");
        call.enqueue(new Callback<JobResponse>() {
            @Override
            public void onResponse(Call<JobResponse> call, Response<JobResponse> response) {

                final JobResponse jobResponse = response.body();

                if (jobResponse != null) {
                    if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code() && jobResponse.getStatus().equals("1")) {
                        Toast.makeText(getActivity(), "Sucess", Toast.LENGTH_SHORT).show();
                        findJobSimmerEffect.stopShimmer();
                        homeNoJobLayout.setVisibility(View.GONE);
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
                               starPosition = jobResponse.getData().get(position).getJob_id();
                               bookmarkStar = itemview.findViewById(R.id.bookmarkStar);
                               bookmarkedStar = itemview.findViewById(R.id.bookmarkedStar);
                                BookmarkJob();
                           }

                           @Override
                           public void onUnBookmarkBtnClicked(View itemview, int position) {
                               jobId = jobResponse.getData().get(position).getJob_id();
                               starPosition = jobResponse.getData().get(position).getJob_id();
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

                    findJobSimmerEffect.stopShimmer();
                    findJobSimmerEffect.setVisibility(View.GONE);
                    homeJobRecycler.setVisibility(View.GONE);
                    homeNoJobLayout.setVisibility(View.VISIBLE);
//                    homeNoJobLayout.setBackgroundColor(getResources().getColor(R.color.white));
                    Toast.makeText(getActivity(), "Response is null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JobResponse> call, Throwable t) {
                findJobSimmerEffect.stopShimmer();
                findJobSimmerEffect.setVisibility(View.GONE);
                homeJobRecycler.setVisibility(View.GONE);
                homeNoJobLayout.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Failed + onFailure", Toast.LENGTH_SHORT).show();
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
                    bookmarkedStar.setVisibility(View.VISIBLE);
                    bookmarkStar.setVisibility(View.GONE);
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
                    bookmarkStar.setVisibility(View.VISIBLE);
                    bookmarkedStar.setVisibility(View.GONE);
//                    Toast.makeText(getActivity(), unBookmarJobResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
//                    Toast.makeText(getActivity(), "Try Again Unnbookmark", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UnBookmarJobResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "On Failure UnBookmark Job", Toast.LENGTH_SHORT).show();
            }
        });
    }
}