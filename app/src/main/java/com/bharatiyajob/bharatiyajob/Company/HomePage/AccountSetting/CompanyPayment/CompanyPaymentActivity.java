package com.bharatiyajob.bharatiyajob.Company.HomePage.AccountSetting.CompanyPayment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.Json.SubscriptionPackage.SubscriptionResponse;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.User.CPaymentSucessfulActivity;
import com.bharatiyajob.bharatiyajob.User.CandidatePaymentAdapter;
import com.bharatiyajob.bharatiyajob.User.UserPaymentActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyPaymentActivity extends AppCompatActivity implements PaymentResultListener {

    RecyclerView comPaymentRecycler;
    CandidatePaymentAdapter candidatePaymentAdapter;
    String SubscriptionFee = null;
    Button comBuySubscription;
    ShimmerFrameLayout companyPaymentSimmerEffect;
    ScrollView comPaymentScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_payment);

        comPaymentRecycler = findViewById(R.id.comPaymentRecycler);

        comBuySubscription = findViewById(R.id.comBuySubscription);
        companyPaymentSimmerEffect = findViewById(R.id.companyPaymentSimmerEffect);
        comPaymentScrollView = findViewById(R.id.comPaymentScrollView);

        companyPaymentSimmerEffect.startShimmer();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        comPaymentRecycler.setLayoutManager(layoutManager);

        getSubscriptionDetail();

        comBuySubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(CompanyPaymentActivity.this, "Sub"+ SubscriptionFee, Toast.LENGTH_SHORT).show();

                startPayment();

            }
        });
    }


    private void getSubscriptionDetail() {

        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);
        Call<SubscriptionResponse> call = jobApi.getCompanySubscription();

        call.enqueue(new Callback<SubscriptionResponse>() {
            @Override
            public void onResponse(Call<SubscriptionResponse> call, Response<SubscriptionResponse> response) {

                final SubscriptionResponse subscriptionResponse = response.body();

                if (response.isSuccessful() && subscriptionResponse.getStatus().equals("1")){

                    companyPaymentSimmerEffect.stopShimmer();
                    companyPaymentSimmerEffect.setVisibility(View.GONE);
                    comBuySubscription.setVisibility(View.VISIBLE);
                    comPaymentScrollView.setVisibility(View.VISIBLE);


                    candidatePaymentAdapter = new CandidatePaymentAdapter(subscriptionResponse.getData(),CompanyPaymentActivity.this);
                    comPaymentRecycler.setAdapter(candidatePaymentAdapter);

                    candidatePaymentAdapter.setOnItemClickListner(new CandidatePaymentAdapter.OnItemClickListner() {
                        @Override
                        public void onSubscriptionLayoutClicked(View itemview, int position, String price) {
                            SubscriptionFee = price;
                            Toast.makeText(CompanyPaymentActivity.this, "This is Price "+ SubscriptionFee, Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {
                    Toast.makeText(CompanyPaymentActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SubscriptionResponse> call, Throwable t) {
                Toast.makeText(CompanyPaymentActivity.this, "On failure" + t.getMessage(), Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(CompanyPaymentActivity.this, CompanyPaymentSucessfull.class);
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