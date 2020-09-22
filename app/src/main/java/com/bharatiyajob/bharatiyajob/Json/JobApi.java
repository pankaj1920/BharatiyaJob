package com.bharatiyajob.bharatiyajob.Json;

import com.bharatiyajob.bharatiyajob.Json.Candidate.ForgetPassword.ChangePasswordResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.GetUserDetails.GetUserDetailResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.JobDetails.JobDetailsResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginEntrNumResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.MakeBookmark.MakeBookmarkResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Register.MobileRegisterResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Register.RegVerifyOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.SavedJob.BookmarkJobResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.SearchJob.JobResponse;
import com.bharatiyajob.bharatiyajob.Json.SubscriptionPackage.SubscriptionResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JobApi {

//    Login Enter Mobile Number
    @FormUrlEncoded
    @POST("Login_resend_otp.php")
    Call<LoginEntrNumResponse> enterLoginNum(
      @Field("mobile") String mobile
    );

    //    Email Login
    @FormUrlEncoded
    @POST("Login.php")
    Call<LoginOtpResponse> emailLogin(
            @Field("email") String email,
            @Field(("password")) String password
    );

    //    Mobile Login
    @FormUrlEncoded
    @POST("Login.php")
    Call<LoginOtpResponse> mobilelLogin(
            @Field("mobile") String mobile,
            @Field(("otp")) String otp
    );

    //MobileRegister
    @FormUrlEncoded
    @POST("SaveUserMobileNumber.php")
    Call<MobileRegisterResponse> mobileRegister(
            @Field("mobile") String mobile
    );

    //Verify Register OTP
    @FormUrlEncoded
    @POST("Registration.php")
    Call<RegVerifyOtpResponse> regVerifyOtp(
      @Field("name") String name,
      @Field("email") String email,
      @Field("password") String password,
      @Field("mobile") String mobile,
      @Field("otp") String otp
    );

    //Change Password
    @FormUrlEncoded
    @POST("user_changepass.php")
    Call <ChangePasswordResponse> changePassword(
            @Field("mobile") String mobile,
            @Field("otp") String otp,
            @Field("password") String password,
            @Field("cpassword") String cpassword
    );

    //Get Job
    @GET("search_job.php")
    Call<JobResponse> searchJob(
      @Query("skill") String skills,
      @Query("location") String location
    );

    //Get Job Details
    @GET("job_description.php")
    Call<JobDetailsResponse> getJobDetail(
            @Query("job_id") String jobId
    );

    //Get BOOKMARK Job
    @GET("get_bookmark_job.php")
    Call<BookmarkJobResponse> getBookmarkJob(
            @Query("can_id") String canId
    );

    //Post BookMark

    @FormUrlEncoded
    @POST("post_bookmark.php")
    Call <MakeBookmarkResponse> makeBookmark(
            @Field("job_id") String jobId,
            @Field("can_id") String canId

    );



    //Get User Details
    @GET("get_can_details.php")
    Call<GetUserDetailResponse> getUserDetails(
            @Query("can_id") String canId
    );

    //Get Candidate Subscription Pack
    @GET("get_candidate_payment.php")
    Call<SubscriptionResponse> getCandidateSubscription();

}
