package com.bharatiyajob.bharatiyajob.Company.HomePage;

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
        holder.jobExperience.setText(appliedjoblist.get(position).getWork_experience());
        holder.jobLocation.setText(appliedjoblist.get(position).getLocation());
        holder.jobSkill.setText(appliedjoblist.get(position).getRequiredskill());
        holder.jobSalary.setText(appliedjoblist.get(position).getSalary());
        holder.jobVacanncy.setText(appliedjoblist.get(position).getNumber_of_vacancy());
        holder.txt_date.setText(appliedjoblist.get(position).getJobregdte());
        holder.txt_date.setText(appliedjoblist.get(position).getJobregdte());
        Log.d("MyTAG",String.valueOf(position));
//        Picasso.get().load(appliedjoblist.get(position).getCompany_logo()).into(holder.bookmarkedStar);
        holder.viewJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.bookmarkedStar.getContext(), CustomerCareActivity.class);
                intent.putExtra("position",position);
                holder.txt_date.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appliedjoblist.size();
    }

    public class Vholder extends RecyclerView.ViewHolder {
        TextView jobExperience,jobLocation,jobSalary,jobVacanncy,jobSkill,txt_date;
        ImageView bookmarkedStar;
        Button viewJob;
        public Vholder(@NonNull View itemView) {
            super(itemView);
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
}
