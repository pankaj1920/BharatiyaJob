package com.bharatiyajob.bharatiyajob.HomePage.BokmarkedJob;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatiyajob.bharatiyajob.HomePage.HomeJob.HomeJobAdapter;
import com.bharatiyajob.bharatiyajob.Json.Candidate.SavedJob.BookmarkJobData;
import com.bharatiyajob.bharatiyajob.R;

import java.util.List;

public class SaveJobAdapter extends RecyclerView.Adapter<SaveJobAdapter.SaveJob_VH> {

    List<BookmarkJobData> jobResponseList;
    OnSaveJobItemClickListner onSaveJobItemClickListner;

    public void setOnItemClickListner(OnSaveJobItemClickListner onSaveJobItemClickListner) {
        this.onSaveJobItemClickListner = onSaveJobItemClickListner;
    }

    public SaveJobAdapter(List<BookmarkJobData> jobResponseList) {
        this.jobResponseList = jobResponseList;
    }

    @NonNull
    @Override
    public SaveJob_VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_job_list,parent,false);
        return new SaveJob_VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SaveJob_VH holder, final int position) {
        BookmarkJobData data = jobResponseList.get(position);
        holder.bookmarkTitle.setText(data.getJob_title());
        holder.bookmarkExperience.setText(data.getExperience());
        holder.bookmarkLocation.setText(data.getLocation());
        holder.bookmarkSkill.setText(data.getSkill());

        holder.bookmarkedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveJobItemClickListner.onJobLayoutClicked(holder.itemView,position);
//                Toast.makeText(holder.bookmarkSkill.getContext(), "Layout Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        holder.bookmarkStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveJobItemClickListner.onBookmarkedButtonCicked(holder.itemView,position);
//                Toast.makeText(holder.bookmarkSkill.getContext(), "Star Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobResponseList.size();
    }


    static class SaveJob_VH extends RecyclerView.ViewHolder{

        TextView bookmarkTitle,bookmarkExperience,bookmarkLocation,bookmarkSkill;
        Button savedJobApplyBtn;
        ConstraintLayout bookmarkedLayout;
        ImageView bookmarkStar;

        public SaveJob_VH(@NonNull View itemView) {
            super(itemView);

            bookmarkTitle = itemView.findViewById(R.id.bookmarkTitle);
            bookmarkExperience = itemView.findViewById(R.id.bookmarkExperience);
            bookmarkLocation = itemView.findViewById(R.id.bookmarkLocation);
            bookmarkSkill = itemView.findViewById(R.id.bookmarkSkill);
            savedJobApplyBtn = itemView.findViewById(R.id.savedJobApplyBtn);
            bookmarkedLayout = itemView.findViewById(R.id.bookmarkedLayout);
            bookmarkStar = itemView.findViewById(R.id.bookmarkStar);


        }
        }
// Define the listener interface
public interface OnSaveJobItemClickListner {

    void onJobLayoutClicked(View itemview, int position);

    void onBookmarkedButtonCicked(View itemview,int position);


}
}
