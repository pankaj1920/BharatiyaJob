package com.bharatiyajob.bharatiyajob.Company.HomePage.BookmarkedCandidate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Company.GetBookmarkedCanList.GetBookMarkedCandidate;
import com.bharatiyajob.bharatiyajob.Json.Company.RemoveBookmarkedCan.RemoveBookMarkedCandidateResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetBookMarkCandidateRecylerAdapter extends RecyclerView.Adapter<GetBookMarkCandidateRecylerAdapter.Vholder> {

    List<GetBookMarkedCandidate> bokkmarklist;
    Context context;
    String canId;
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
                onUnBcItemClickListner.onUnBookmarkCanClicked(holder.itemView,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bokkmarklist.size();
    }



    public class Vholder  extends RecyclerView.ViewHolder{
        TextView BCanName;
        ImageView BCanStar;
        public Vholder(@NonNull View itemView) {
            super(itemView);
            BCanName=itemView.findViewById(R.id.BCanName);
            BCanStar=itemView.findViewById(R.id.BCanStar);
        }
    }

    public interface OnUnBcItemClickListner{
        void onUnBookmarkCanClicked(View view,int position);
    }
}