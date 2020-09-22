package com.bharatiyajob.bharatiyajob.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
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
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPaymentActivity extends AppCompatActivity implements PaymentResultListener {

    RecyclerView candidateRecycler;
    CandidatePaymentAdapter candidatePaymentAdapter;
    int row_index = -1;
    int Position;
    RadioButton subscriptionRadioBtn;
    ConstraintLayout selectSubscriptionLayout;
    String SubscriptionFee = null;
    Button buySubscription;
    TextView amount;
    int razorpayLogo = R.drawable.razorpay_logo;

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

                startPayment();

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



    private void startPayment() {

        double totalAmount = Double.parseDouble(SubscriptionFee);
        totalAmount = totalAmount*100;

        //Initiate Checkout
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_live_Dt4bfKj6DDu1gi");

        //Set your logo here
        checkout.setImage(R.drawable.razorpay_logo);

        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Bharatiya Job");
            options.put("description", "Subscription Fees");
            //You can omit the image option to fetch the image from dashboard
            options.put("currency", "INR");
            options.put("amount", totalAmount);//pass amount in currency subunits Eg 100 => INR 1.00 Rs

//            Email and contact number of Customer who is going to pay
            JSONObject preFill = new JSONObject();
            preFill.put("email", "bohrapankaj1920@gmail.com");
            preFill.put("contact", "8755420120");

            options.put("prefill", preFill);

            checkout.open(activity, options);
        } catch(Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }

    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putString("TransactionId",razorpayPaymentID);
            Intent intent = new Intent(UserPaymentActivity.this,CPaymentSucessfulActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);


        } catch (Exception e) {
//            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
//            Log.e(TAG, "Exception in onPaymentError", e);e
        }
    }


}