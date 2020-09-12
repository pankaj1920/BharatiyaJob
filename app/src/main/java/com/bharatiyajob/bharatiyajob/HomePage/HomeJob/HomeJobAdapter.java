package com.bharatiyajob.bharatiyajob.HomePage.HomeJob;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatiyajob.bharatiyajob.Json.SearchJob.JobData;
import com.bharatiyajob.bharatiyajob.R;

import java.util.List;

public class HomeJobAdapter extends RecyclerView.Adapter<HomeJobAdapter.HomeJob_VH> {

    List<JobData> data;

    public HomeJobAdapter(List<JobData> data) {
        this.data = data;
    }


    @NonNull
    @Override
    public HomeJob_VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_job_item_list,parent,false);
        return new HomeJob_VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeJob_VH holder, int position) {
        JobData jobList = data.get(position);
        holder.jobTitle.setText(jobList.getJob_title());
        holder.jobExperience.setText(jobList.getExperience());
        holder.jobLocation.setText(jobList.getLocation());
        holder.jobSkill.setText(jobList.getSkill());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private OnItemClickListner onItemClickListner;

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    class HomeJob_VH extends RecyclerView.ViewHolder{

        TextView jobTitle,jobExperience,jobLocation,jobSkill;
        Button jobApplyBtn;
        ConstraintLayout jobLayout;
        ImageView bookmarkedStar;

        public HomeJob_VH(@NonNull final View itemView) {
            super(itemView);

            jobTitle = itemView.findViewById(R.id.jobTitle);
            jobExperience = itemView.findViewById(R.id.jobExperience);
            jobLocation = itemView.findViewById(R.id.jobLocation);
            jobSkill = itemView.findViewById(R.id.jobSkill);
            jobApplyBtn = itemView.findViewById(R.id.jobApplyBtn);
            jobLayout = itemView.findViewById(R.id.jobLayout);
            bookmarkedStar = itemView.findViewById(R.id.bookmarkedStar);


            bookmarkedStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListner!=null){
                        int starPosition = getAdapterPosition();

                        if (starPosition!=RecyclerView.NO_POSITION){
                            onItemClickListner.onBookmarkBtnClicked(itemView,starPosition);
                        }
                    }
                }
            });

            jobLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListner!=null){
                        int postion = getAdapterPosition();

                        if (postion!=RecyclerView.NO_POSITION){

                            onItemClickListner.onJobLayoutClicked(itemView,postion);
                        }
                    }
                }
            });
        }
    }

    // Define the listener interface
    public interface OnItemClickListner {

        void onJobLayoutClicked(View itemview, int position);

        void onBookmarkBtnClicked(View itemview,int position);

    }

}
