package com.bharatiyajob.bharatiyajob.User.CandidateNotification.recommended_job;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatiyajob.bharatiyajob.Json.Candidate.SearchJob.JobData;
import com.bharatiyajob.bharatiyajob.R;

import java.util.List;

public class RecomendedJobAdapter extends RecyclerView.Adapter<RecomendedJobAdapter.RecomemdedJob_VH>{

    final List<JobData> jobDataList;

    private RecomendedJobAdapter.OnItemClickListner onItemClickListner;

    public RecomendedJobAdapter(List<JobData> jobDataList) {
        this.jobDataList = jobDataList;
    }


    public void setOnItemClickListner(RecomendedJobAdapter.OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    @NonNull
    @Override
    public RecomendedJobAdapter.RecomemdedJob_VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_job_item_list,parent,false);
        return new RecomendedJobAdapter.RecomemdedJob_VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecomendedJobAdapter.RecomemdedJob_VH holder, final int position) {
        JobData jobData = jobDataList.get(position);
        holder.jobTitle.setText(jobData.getJob_title());
        holder.jobExperience.setText(jobData.getExperience());
        holder.jobLocation.setText(jobData.getLocation());
        holder.jobSkill.setText(jobData.getSkill());

        holder.bookmarkStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListner.onBookmarkBtnClicked(holder.itemView,position);
            }
        });

        holder.bookmarkedStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListner.onUnBookmarkBtnClicked(position);
            }
        });

        holder.homeApplyJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListner.onApplyJobButtonClicked(holder.itemView,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (jobDataList.size()>15){
            return 15;
        }else {
            return jobDataList.size();
        }
    }


    class RecomemdedJob_VH extends RecyclerView.ViewHolder{

        final TextView jobTitle;
        final TextView jobExperience;
        final TextView jobLocation;
        final TextView jobSkill;
        final Button homeApplyJobBtn;
        final ConstraintLayout jobLayout;
        final ImageView bookmarkStar;
        final ImageView bookmarkedStar;

        public RecomemdedJob_VH(@NonNull final View itemView) {
            super(itemView);

            jobTitle = itemView.findViewById(R.id.jobTitle);
            jobExperience = itemView.findViewById(R.id.jobExperience);
            jobLocation = itemView.findViewById(R.id.jobLocation);
            jobSkill = itemView.findViewById(R.id.jobSkill);
            homeApplyJobBtn = itemView.findViewById(R.id.homeApplyJobBtn);
            jobLayout = itemView.findViewById(R.id.jobLayout);
            bookmarkStar = itemView.findViewById(R.id.bookmarkStar);
            bookmarkedStar = itemView.findViewById(R.id.bookmarkedStar);

            jobLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListner!=null){
                        int postion = getAdapterPosition();

                        if (postion!=RecyclerView.NO_POSITION){

                            onItemClickListner.onJobLayoutClicked(postion);
                        }
                    }
                }
            });
        }
    }

    // Define the listener interface
    public interface OnItemClickListner {

        void onJobLayoutClicked(int position);

        void onBookmarkBtnClicked(View itemview,int position);

        void onUnBookmarkBtnClicked(int position);

        void  onApplyJobButtonClicked(View itemview,int position);

    }

}
