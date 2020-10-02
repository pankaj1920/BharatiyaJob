package com.bharatiyajob.bharatiyajob.Company.HomePage.AppliedCandidateList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatiyajob.bharatiyajob.HomePage.HomeJob.HomeJobAdapter;
import com.bharatiyajob.bharatiyajob.Json.Company.CandidateApplied.CandidateAppliedData;
import com.bharatiyajob.bharatiyajob.R;

import java.util.List;

public class CandidateListAdapter extends RecyclerView.Adapter<CandidateListAdapter.Candidate_VH> {

    List<CandidateAppliedData>candidateAppliedList;

    private OnItemClickListner onItemClickListner;

    public void setOnItemClickListner(OnItemClickListner onItemClickListner){
         this.onItemClickListner = onItemClickListner;
    }

    public CandidateListAdapter(List<CandidateAppliedData> candidateAppliedList) {
        this.candidateAppliedList = candidateAppliedList;
    }

    @NonNull
    @Override
    public Candidate_VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.candidate_list_item,parent,false);
        return new CandidateListAdapter.Candidate_VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Candidate_VH holder, final int position) {
        CandidateAppliedData data = candidateAppliedList.get(position);
        holder.CanName.setText(data.getName());
        holder.CanJobSkill.setText(data.getSkill());
        holder.canJobExperience.setText(data.getWork_experience());
        holder.CanjobLocation.setText(data.getState());
        holder.CanEmail.setText(data.getEmail());

        holder.bookmarkCanStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListner.onCanBookmarkClicked(holder.itemView,position);
            }
        });

        holder.CanViewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListner.onCanViewDetailCilcked(holder.itemView,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return candidateAppliedList.size();
    }

    class Candidate_VH extends RecyclerView.ViewHolder{

        ImageView canPic,bookmarkCanStar;
        TextView CanName,CanEmail,canJobExperience,CanjobLocation,CanJobSkill;
        Button CanViewDetail;

        public Candidate_VH(@NonNull View itemView) {
            super(itemView);
             canPic = itemView.findViewById(R.id.canPic);
            bookmarkCanStar = itemView.findViewById(R.id.bookmarkCanStar);
            CanName = itemView.findViewById(R.id.CanName);
            canJobExperience = itemView.findViewById(R.id.canJobExperience);
            CanjobLocation = itemView.findViewById(R.id.CanjobLocation);
            CanJobSkill = itemView.findViewById(R.id.CanJobSkill);
            CanViewDetail = itemView.findViewById(R.id.CanViewDetail);
            CanEmail = itemView.findViewById(R.id.CanEmail);
        }
    }

    public interface OnItemClickListner{
        void onCanBookmarkClicked(View view,int position);
        void onCanViewDetailCilcked(View view,int position);
    }
}
