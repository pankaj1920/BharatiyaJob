package com.bharatiyajob.bharatiyajob.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatiyajob.bharatiyajob.Json.SubscriptionPackage.SubscriptionData;
import com.bharatiyajob.bharatiyajob.R;

import java.util.List;

public class CandidatePaymentAdapter extends RecyclerView.Adapter<CandidatePaymentAdapter.Subscription_VH> {

    private CandidatePaymentAdapter.OnItemClickListner onItemClickListner;

    public void setOnItemClickListner(CandidatePaymentAdapter.OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    List<SubscriptionData> subscriptionDataList;
    Context context;
    int row_index = -1;
    String fee;
    int Position;

    public CandidatePaymentAdapter(int position) {
        Position = position;
    }

    public CandidatePaymentAdapter(List<SubscriptionData> subscriptionDataList, Context context) {
        this.subscriptionDataList = subscriptionDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public Subscription_VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_list_item,parent,false);
        return new Subscription_VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Subscription_VH holder, final int position) {
        SubscriptionData data = subscriptionDataList.get(position);
         final String price = data.getCost();
        final String days = data.getDays();

        String Subscription = "INR " +price+ " for "+ days+ " days ";
        holder.amount.setText(Subscription);



        holder.selectSubscriptionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
                notifyDataSetChanged();

                onItemClickListner.onSubscriptionLayoutClicked(price,days);
            }
        });

        holder.subscriptionRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
                notifyDataSetChanged();
            }
        });

        if (row_index == position){
            holder.selectSubscriptionLayout.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorPrimary));
            holder.selectSubscriptionLayout.setBackground(ContextCompat.getDrawable(context,R.drawable.form_experience_selector));
            holder.amount.setTextColor(ContextCompat.getColor(context,R.color.white));
            holder.subscriptionRadioBtn.setSelected(false);
            holder.subscriptionRadioBtn.setChecked(true);
//            Toast.makeText(context, "ewrwr" + price, Toast.LENGTH_SHORT).show();

            fee = subscriptionDataList.get(position).getCost();
        }else {
            holder.selectSubscriptionLayout.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.white));
            holder.selectSubscriptionLayout.setBackground(ContextCompat.getDrawable(context,R.drawable.form_experience_selector));
            holder.amount.setTextColor(ContextCompat.getColor(context,R.color.black));
            holder.subscriptionRadioBtn.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return subscriptionDataList.size();
    }



    static class Subscription_VH extends RecyclerView.ViewHolder{

        final TextView amount;
        final RadioButton subscriptionRadioBtn;
        final ConstraintLayout selectSubscriptionLayout;
        final Button buySubscription;


        public Subscription_VH(@NonNull final View itemView) {
            super(itemView);

            amount = itemView.findViewById(R.id.amount);
            subscriptionRadioBtn = itemView.findViewById(R.id.subscriptionRadioBtn);
            selectSubscriptionLayout = itemView.findViewById(R.id.selectSubscriptionLayout);
            buySubscription = itemView.findViewById(R.id.buySubscription);

//            selectSubscriptionLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    Toast.makeText(context, "sdfsdf", Toast.LENGTH_SHORT).show();
//
//                    if(onItemClickListner != null){
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION){
//
//                            onItemClickListner.onSubscriptionLayoutClicked(itemView,position);
//
//
//
//                        }
//                    }
//                }
//            });


        }
    }

    // Define the listener interface
    public interface OnItemClickListner {

        void onSubscriptionLayoutClicked(String price, String days);

//        void onSubscriptionRadioBtnClicked(View itemview,int position);

    }
}
