package com.bharatiyajob.bharatiyajob.HomePage.BokmarkedJob;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatiyajob.bharatiyajob.HomePage.HomeJob.HomeJobAdapter;
import com.bharatiyajob.bharatiyajob.Json.Candidate.SavedJob.BookmarkJobData;
import com.bharatiyajob.bharatiyajob.R;

import java.util.List;

public class SaveJobAdapter extends RecyclerView.Adapter<SaveJobAdapter.SaveJob_VH> {

    List<BookmarkJobData> jobResponseList;

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
    public void onBindViewHolder(@NonNull SaveJob_VH holder, int position) {
        BookmarkJobData data = jobResponseList.get(position);
        holder.bookmarkTitle.setText(data.getJob_title());
        holder.bookmarkExperience.setText(data.getExperience());
        holder.bookmarkLocation.setText(data.getLocation());
        holder.bookmarkSkill.setText(data.getSkill());
    }

    @Override
    public int getItemCount() {
        return jobResponseList.size();
    }

    private HomeJobAdapter.OnItemClickListner onItemClickListner;


    class SaveJob_VH extends RecyclerView.ViewHolder{

        TextView bookmarkTitle,bookmarkExperience,bookmarkLocation,bookmarkSkill;
        Button savedJobApplyBtn;
        ConstraintLayout bookmarkedLayout;
        ImageView bookmarkedStar;

        public SaveJob_VH(@NonNull View itemView) {
            super(itemView);

            bookmarkTitle = itemView.findViewById(R.id.bookmarkTitle);
            bookmarkExperience = itemView.findViewById(R.id.bookmarkExperience);
            bookmarkLocation = itemView.findViewById(R.id.bookmarkLocation);
            bookmarkSkill = itemView.findViewById(R.id.bookmarkSkill);
            savedJobApplyBtn = itemView.findViewById(R.id.savedJobApplyBtn);
            bookmarkedLayout = itemView.findViewById(R.id.bookmarkedLayout);
            bookmarkedStar = itemView.findViewById(R.id.bookmarkedStar);

            bookmarkedLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListner!=null){
                        int postion = getAdapterPosition();

                        if (postion!=RecyclerView.NO_POSITION){

//                            onItemClickListner.onJobLayoutClicked(itemView,postion);
                        }
                    }
                }
            });
        }
        }
// Define the listener interface
public interface OnItemClickListner {

    void onJobLayoutClicked(View itemview, int position);

}
}
