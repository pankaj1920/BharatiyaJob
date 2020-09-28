package com.bharatiyajob.bharatiyajob.Json;

import com.bharatiyajob.bharatiyajob.Json.Candidate.ForgetPassword.ChangePasswordResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.GetUserDetails.GetUserDetailResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.JobDetails.JobDetailsResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginEntrNumResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.MakeBookmark.MakeBookmarkResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.ProfileForm.CanProfileResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Register.MobileRegisterResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Register.RegVerifyOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.SavedJob.BookmarkJobResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.SearchJob.JobResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.BookmarkCandidate.BookmarkCandidateResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.CandidateApplied.CandidateAppliedResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.CompanyJobList.CompanyJobListResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.GetBookmarkedCanList.GetBookMarkedCandidateResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.GetCompanyDetails.GetCompanyDetailsResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.RemoveBookmarkedCan.RemoveBookMarkedCandidateResponse;
import com.bharatiyajob.bharatiyajob.Json.SubscriptionPackage.SubscriptionResponse;
import com.bharatiyajob.bharatiyajob.Json.UpdateCanImage.UpdateImageResponse;
import com.bharatiyajob.bharatiyajob.Json.UpdateUserName.UpdateUserName;
import com.bharatiyajob.bharatiyajob.Json.UpdateUserName.UpdateUserNameResponse;

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

    @FormUrlEncoded
    @POST("update_user_image.php")
    Call<UpdateImageResponse> updateCanImage(
            @Field("profile_pic") String profile_pic,
            @Field("profile_name") String profile_name,
            @Field("can_id") String can_id
    );

    @FormUrlEncoded
    @POST("update_user_name.php")
    Call<UpdateUserName>upDateUserName(
            @Field("can_id") String can_id,
            @Field("name") String name
    );

    @FormUrlEncoded
    @POST("update_user_password.php")
    Call<UpdateUserName>upDateUserPassword(
            @Field("can_id") String can_id,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("update_user_skills.php")
    Call<UpdateUserNameResponse>updateUserSkill(
            @Field("can_id") String can_id,
            @Field("skills") String skills
    );

    //Get Candidate Subscription Pack
    @GET("get_candidate_payment.php")
    Call<SubscriptionResponse> getCandidateSubscription();

    //Get Company Subscription Pack
    @GET("get_company_payment.php")
    Call<SubscriptionResponse> getCompanySubscription();

    @FormUrlEncoded
    @POST("save_can_details.php")
    Call<CanProfileResponse>SaveCanDetail(
            @Field("can_id") String can_id,
            @Field("profile_pic") String profile_pic,
            @Field("gender") String gender,
            @Field("heighest_qualification") String heighest_qualification,
            @Field("work_experience") String work_experience,
            @Field("state") String state,
            @Field("skills") String skills,
            @Field("address") String address,
            @Field("profile_name") String profile_name
    );

    @GET("get_company_job_posted.php")
    Call<CompanyJobListResponse> getappliedjob(
            @Query("company_id") String company_id
    );

  @GET("get_bookmark_candidate.php")
    Call<GetBookMarkedCandidateResponse> getbookMarkCandidate(
            @Query("company_id") String company_id
    );

    @FormUrlEncoded
    @POST("remove_candidate_bookmark.php")
    Call<RemoveBookMarkedCandidateResponse>removebookmark(
            @Field("company_id") String company_id,
            @Field("candidate_id") String candidate_id
    );

    //Get Candidate List
    @GET("get_job_applied_candidates.php")
    Call<CandidateAppliedResponse> getCandidateList(
            @Query("company_id") String company_id
    );

    //Bookmark Candidate

    @FormUrlEncoded
    @POST("save_candidate_to_company.php")
    Call<BookmarkCandidateResponse>bookmarkCandidate(
            @Field("company_id") String company_id,
            @Field("candidate_id") String candidate_id
    );

    //Get Company Details
    @GET("get_company_details.php")
    Call<GetCompanyDetailsResponse> getCompanyDetails(
      @Query("company_id") String companyId
    );
}
