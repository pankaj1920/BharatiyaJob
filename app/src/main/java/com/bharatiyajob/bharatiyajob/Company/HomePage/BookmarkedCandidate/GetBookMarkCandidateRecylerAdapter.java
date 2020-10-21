package com.bharatiyajob.bharatiyajob.Company.HomePage.BookmarkedCandidate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatiyajob.bharatiyajob.Json.Company.GetBookmarkedCanList.GetBookMarkedCandidate;
import com.bharatiyajob.bharatiyajob.R;

import java.util.List;

public class GetBookMarkCandidateRecylerAdapter extends RecyclerView.Adapter<GetBookMarkCandidateRecylerAdapter.Vholder> {

    final List<GetBookMarkedCandidate> bokkmarklist;
    final Context context;
    String canId,jobId;
    OnUnBcItemClickListner onUnBcItemClickListner;

    public void setOnUnBcItemClickListner(OnUnBcItemClickListner onUnBcItemClickListner){
        this.onUnBcItemClickListner = onUnBcItemClickListner;
    }

    Vholder holder;
    public GetBookMarkCandidateRecylerAdapter(List<GetBookMarkedCandidate> bokkmarklist, Context context) {
        this.bokkmarklist = bokkmarklist;
        this.context = context;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmarked_candidate_list,parent,false);

        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Vholder holder, final int position) {

        GetBookMarkedCandidate data = bokkmarklist.get(position);
        canId = data.getCandidate_id();

        holder.BCanName.setText(bokkmarklist.get(position).getCandidate_name());

        holder.BCanStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUnBcItemClickListner.onUnBookmarkCanClicked(position);
            }
        });

        holder.BCanViewJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUnBcItemClickListner.onBookCanViewDetailCilcked(position);
            }
        });

        holder.BCanRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUnBcItemClickListner.onBCanRemoveCilcked(canId,"1");
            }
        });
    }

    @Override
    public int getItemCount() {
        return bokkmarklist.size();
    }



    public static class Vholder  extends RecyclerView.ViewHolder{
        final TextView BCanName;
        final ImageView BCanStar;
        final Button BCanViewJob;
        final Button BCanRemove;
        public Vholder(@NonNull View itemView) {
            super(itemView);
            BCanName=itemView.findViewById(R.id.BCanName);
            BCanStar=itemView.findViewById(R.id.BCanStar);
            BCanViewJob=itemView.findViewById(R.id.BCanViewJob);
            BCanRemove=itemView.findViewById(R.id.BCanRemove);
        }
    }

    public interface OnUnBcItemClickListner{
        void onUnBookmarkCanClicked(int position);
        void onBookCanViewDetailCilcked(int position);
        void onBCanRemoveCilcked(String canId, String jobId);
    }
}
