package com.bharatiyajob.bharatiyajob.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.Json.SubscriptionPackage.SubscriptionResponse;
import com.bharatiyajob.bharatiyajob.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPaymentActivity extends AppCompatActivity {

    RecyclerView candidateRecycler;
    CandidatePaymentAdapter candidatePaymentAdapter;
    int row_index = -1;
    int Position;
    RadioButton subscriptionRadioBtn;
    ConstraintLayout selectSubscriptionLayout;
    String SubscriptionFee = null;
    Button buySubscription;
    TextView amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_payment);

        candidateRecycler = findViewById(R.id.candidateRecycler);

        buySubscription = findViewById(R.id.buySubscription);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        candidateRecycler.setLayoutManager(layoutManager);

        getSubscriptionDetail();

        buySubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(UserPaymentActivity.this, "Sub"+ SubscriptionFee, Toast.LENGTH_SHORT).show();
                Toast.makeText(UserPaymentActivity.this, "Sub"+ SubscriptionFee, Toast.LENGTH_SHORT).show();
//                if (SubscriptionFee != null) {
//                    Toast.makeText(UserPaymentActivity.this, SubscriptionFee, Toast.LENGTH_SHORT).show();
//
//                }else {
//                    Toast.makeText(UserPaymentActivity.this, "Selet The Price", Toast.LENGTH_SHORT).show();
//                }

            }
        });

    }

    private void getSubscriptionDetail() {

        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<SubscriptionResponse> call = jobApi.getCandidateSubscription();

        call.enqueue(new Callback<SubscriptionResponse>() {
            @Override
            public void onResponse(Call<SubscriptionResponse> call, Response<SubscriptionResponse> response) {

                final SubscriptionResponse subscriptionResponse = response.body();

                if (response.isSuccessful() && subscriptionResponse.getStatus().equals("1")){

                    candidatePaymentAdapter = new CandidatePaymentAdapter(subscriptionResponse.getData(),UserPaymentActivity.this);
                    candidateRecycler.setAdapter(candidatePaymentAdapter);

                    candidatePaymentAdapter.setOnItemClickListner(new CandidatePaymentAdapter.OnItemClickListner() {
                        @Override
                        public void onSubscriptionLayoutClicked(View itemview, int position, String price) {
                            SubscriptionFee = price;
                            Toast.makeText(UserPaymentActivity.this, "This is Price "+ SubscriptionFee, Toast.LENGTH_SHORT).show();
                        }
                    });




                }else {
                    Toast.makeText(UserPaymentActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SubscriptionResponse> call, Throwable t) {
                Toast.makeText(UserPaymentActivity.this, "On failure" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}