package com.bharatiyajob.bharatiyajob.Company.HomePage.CompanyJobList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bharatiyajob.bharatiyajob.CustomerCareActivity;
import com.bharatiyajob.bharatiyajob.Json.Company.CompanyJobList.CompanyJobListData;
import com.bharatiyajob.bharatiyajob.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CompanyHomePageRecyleAdapter extends RecyclerView.Adapter<CompanyHomePageRecyleAdapter.Vholder> {

    List<CompanyJobListData> appliedjoblist;
    Context context;
    private OnJobItemClickListner onJobItemClickListner;
    String [] dateTime;
    String date,time;

    public void setOnJobItemClickListner(OnJobItemClickListner onJobItemClickListner) {
        this.onJobItemClickListner = onJobItemClickListner;
    }

    public CompanyHomePageRecyleAdapter(List<CompanyJobListData> appliedjoblist, Context context) {
        this.appliedjoblist = appliedjoblist;
        this.context = context;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_jobpost_list, parent, false);
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Vholder holder, final int position) {
        CompanyJobListData data = appliedjoblist.get(position);

        dateTime = data.getJobregdte().split((" "));
        date = dateTime[0];
        time = dateTime[1];

        holder.jobTitle.setText(data.getJobtitle());
        holder.jobExperience.setText(data.getWork_experience());
        holder.jobLocation.setText(data.getLocation());
        holder.jobSkill.setText(data.getRequiredskill());
        holder.jobSalary.setText(data.getSalary());
        holder.jobVacanncy.setText(data.getNumber_of_vacancy());
        holder.txt_date.setText(date);
        Log.d("MyTAG",String.valueOf(position));
//        Picasso.get().load(appliedjoblist.get(position).getCompany_logo()).into(holder.bookmarkedStar);
        holder.viewJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onJobItemClickListner.onJobItemClicked(holder.itemView,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appliedjoblist.size();
    }

    public class Vholder extends RecyclerView.ViewHolder {
        TextView jobTitle,jobExperience,jobLocation,jobSalary,jobVacanncy,jobSkill,txt_date;
        ImageView bookmarkedStar;
        Button viewJob;
        public Vholder(@NonNull View itemView) {
            super(itemView);
            jobTitle = itemView.findViewById(R.id.jobTitle);
            jobExperience = itemView.findViewById(R.id.jobExperience);
            jobLocation = itemView.findViewById(R.id.jobLocation);
            jobSalary = itemView.findViewById(R.id.jobSalary);
            jobVacanncy = itemView.findViewById(R.id.jobVacanncy);
            jobSkill = itemView.findViewById(R.id.jobSkill);
            txt_date = itemView.findViewById(R.id.txt_date);
            bookmarkedStar = itemView.findViewById(R.id.bookmarkedStar);
            viewJob=itemView.findViewById(R.id.viewJob);
        }
    }

    interface OnJobItemClickListner {
        void onJobItemClicked(View view, int position);
    }
}
