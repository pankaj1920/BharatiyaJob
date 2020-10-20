package com.bharatiyajob.bharatiyajob.User.CandidateNotification.alert_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatiyajob.bharatiyajob.Json.Candidate.JobAlertList.GetJobAlertData;
import com.bharatiyajob.bharatiyajob.R;

import java.util.List;

public class JobAlertAdapter extends RecyclerView.Adapter<JobAlertAdapter.JobAlert_VH> {

    List<GetJobAlertData> jobAlertDataList;

    public OnAlertDeleteListner onAlertDeleteListner;

    public void setOnnAlertDeleteListner(OnAlertDeleteListner onAlertDeleteListner) {
        this.onAlertDeleteListner = onAlertDeleteListner;
    }

    public JobAlertAdapter(List<GetJobAlertData> jobAlertDataList) {
        this.jobAlertDataList = jobAlertDataList;
    }

    @NonNull
    @Override
    public JobAlert_VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_alert_list,parent,false);
        return new JobAlertAdapter.JobAlert_VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final JobAlert_VH holder, final int position) {
        GetJobAlertData data = jobAlertDataList.get(position);
        holder.alertTitle.setText(data.getAlert_name());
        holder.alertJobExperience.setText(data.getExperience());
        holder.alertjobLocation.setText(data.getLocation());
        holder.alertSalary.setText(data.getAnnual_salary());
        holder.alertIndustry.setText(data.getIndustry());
//        holder.alertJobSkill.setText(data.get());

        holder.deleteAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAlertDeleteListner.onAlertDeleteClicked(holder.itemView,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobAlertDataList.size();
    }

    class JobAlert_VH extends RecyclerView.ViewHolder{

        TextView alertTitle,alertJobExperience,alertjobLocation,alertSalary,alertIndustry,alertJobSkill;
        ImageView deleteAlert;

        public JobAlert_VH(@NonNull View itemView) {
            super(itemView);

            alertTitle = itemView.findViewById(R.id.alertTitle);
            alertJobExperience = itemView.findViewById(R.id.alertJobExperience);
            alertjobLocation = itemView.findViewById(R.id.alertjobLocation);
            alertSalary = itemView.findViewById(R.id.alertSalary);
            alertIndustry = itemView.findViewById(R.id.alertIndustry);
            alertJobSkill = itemView.findViewById(R.id.alertJobSkill);
            deleteAlert = itemView.findViewById(R.id.deleteAlert);
        }
    }

    public interface OnAlertDeleteListner{
        void  onAlertDeleteClicked(View itemview,int position);
    }
}
