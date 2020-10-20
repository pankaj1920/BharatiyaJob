package com.bharatiyajob.bharatiyajob.Json;

import com.bharatiyajob.bharatiyajob.Json.Candidate.ApplyJob.ApplyJobResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.CreateJobAlert.CreateJobAlertResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.ForgetPassword.ChangePasswordResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.GetUserDetails.GetUserDetailResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.JobAlertList.GetJobAlertDataResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.JobDetails.JobDetailsResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginEntrNumResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.MakeBookmark.MakeBookmarkResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.ProfileForm.CanProfileResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Register.MobileRegisterResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Register.RegVerifyOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.SavedJob.BookmarkJobResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.SearchJob.JobResponse;
import com.bharatiyajob.bharatiyajob.Json.Candidate.UnBookmarkJob.UnBookmarJobResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.BookmarkCandidate.BookmarkCandidateResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.CandidateApplied.CandidateAppliedResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.CompanyJobList.CompanyJobListResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.CompanyJobPosted.CompanyJobPostedResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.CompanyRegistrationResponse.CompanyRegistrationResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.EnableDisableJobPost.EnableDisableJobResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.GetBookmarkedCanList.GetBookMarkedCandidateResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.GetCandidateDetails.GetCandidateDetaiResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.GetCompanyDetails.GetCompanyDetailsResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.RemoveBookmarkedCan.RemoveBookMarkedCandidateResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.VerifyOtpResponse.ComVerifyOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.company_job_detail.CompanyJobDetailResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.remove_candidate.RemoveCandidateResponse;
import com.bharatiyajob.bharatiyajob.Json.CustomerCare.CustomerCareResponse;
import com.bharatiyajob.bharatiyajob.Json.SubscriptionPackage.PostPaymentDetailResponse;
import com.bharatiyajob.bharatiyajob.Json.SubscriptionPackage.SubscriptionResponse;
import com.bharatiyajob.bharatiyajob.Json.UpdateCanImage.UpdateImageResponse;
import com.bharatiyajob.bharatiyajob.Json.UpdateCandidateProfile.UpdateCandidateProfileResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JobApi {

    //MobileRegister
    @FormUrlEncoded
    @POST("SaveUserMobileNumber.php")
    Call<MobileRegisterResponse>mobileRegister(
            @Field("mobile") String mobile,
            @Field("regtype") String regtype
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
            @Field(("password")) String password,
            @Field(("token")) String token
    );

    //    Mobile Login
    @FormUrlEncoded
    @POST("Login.php")
    Call<LoginOtpResponse> mobilelLogin(
            @Field("mobile") String mobile,
            @Field(("otp")) String otp,
            @Field("token") String token
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
      @Query("location") String location,
      @Query("candidate_id") String canId
    );

    //Get Job Details
    @GET("job_description.php")
    Call<JobDetailsResponse> getJobDetail(
            @Query("job_id") String jobId,
            @Query("can_id") String canId
    );

//Apply Job
    @FormUrlEncoded
    @POST("apply_post_jobs.php")
    Call<ApplyJobResponse> applyJob(
            @Field("can_id") String canId,
            @Field(("job_id")) String jobId
    );

    //Get BOOKMARK Job
    @GET("get_bookmark_job.php")
    Call<BookmarkJobResponse> getBookmarkJob(
            @Query("can_id") String canId
    );

    //Post BookMark Jon
    @FormUrlEncoded
    @POST("post_bookmark.php")
    Call <MakeBookmarkResponse> makeBookmark(
            @Field("job_id") String jobId,
            @Field("can_id") String canId
    );

    //Remove Job Bookmark
    @FormUrlEncoded
    @POST("remove_job_bookmark.php")
    Call <UnBookmarJobResponse> unBookmarkJob(
            @Field("can_id") String canId,
            @Field("job_id") String jobId
    );


    //Get User Details
    @GET("get_can_details.php")
    Call<GetUserDetailResponse> getUserDetails(
            @Query("can_id") String canId
    );

    //Get User Details
    @GET("get_user_job_alert_list.php")
    Call<GetJobAlertDataResponse> getJobAlert(
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
    Call<UpdateCandidateProfileResponse>upDateUserName(
            @Field("can_id") String can_id,
            @Field("name") String name
    );

    @FormUrlEncoded
    @POST("update_user_password.php")
    Call<UpdateCandidateProfileResponse>upDateUserPassword(
            @Field("can_id") String can_id,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("update_user_skills.php")
    Call<UpdateCandidateProfileResponse>updateUserSkill(
            @Field("can_id") String can_id,
            @Field("skills") String skills
    );

    //Customer Care
    @FormUrlEncoded
    @POST("customercare.php")
    Call<CustomerCareResponse>customerCare(
            @Field("mobile") String mobile,
            @Field("name") String name,
            @Field("email") String email,
            @Field("message") String message,
            @Field("language") String language,
            @Field("registration_type") String registration_type,
            @Field("customer_id") String customer_id

    );

    //Get Candidate Subscription Pack
    @GET("get_candidate_payment.php")
    Call<SubscriptionResponse> getCandidateSubscription();

    //Get Company Subscription Pack
    @GET("get_company_payment.php")
    Call<SubscriptionResponse> getCompanySubscription();

//    Post Payment Details
    @FormUrlEncoded
    @POST("post_paymentdetails.php")
    Call<PostPaymentDetailResponse> postPaymentDetails(
            @Field("customer_id") String customerId,
            @Field("customer_name") String customerName,
            @Field("registration_type") String registrationType,
            @Field("package_amount") String packageAmount,
            @Field("transactionId") String transactionId,
            @Field("transaction_status") String transactionStatus,
            @Field("subscription_days") String subscriptionDays
    );

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
    Call<CompanyJobListResponse> getPostedJob(
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

    //Get Candidate Details
    @GET("get_can_details.php")
    Call<GetCandidateDetaiResponse> getCandidateDetail(
      @Query("can_id") String CanId
    );

//    Enable Job Post
    @FormUrlEncoded
    @POST("disable_job_post.php")
    Call<EnableDisableJobResponse> disableJobPost(
      @Field("company_id") String companyId,
      @Field("job_id") String jobId
    );

    //    Enable Job Post
    @FormUrlEncoded
    @POST("enable_job_post.php")
    Call<EnableDisableJobResponse> enableJobPost(
            @Field("company_id") String companyId,
            @Field("job_id") String jobId
    );

    @FormUrlEncoded
    @POST("SaveUserMobileNumber.php")
    Call<CompanyRegistrationResponse>saveCompanyMobilenumber(
            @Field("mobile") String mobile,
            @Field("regtype") String regtype
    );

    @FormUrlEncoded
    @POST("companyRegistration.php")
    Call<ComVerifyOtpResponse>RegisterCompany(
            @Field("mobile") String mobile,
            @Field("otp") String otp,
            @Field("email") String email,
            @Field("companyname") String companyName,
            @Field("password") String password,
            @Field("gst_reg") String typeOfRegs,
            @Field("gstno") String gstNo,
            @Field("aadhaarno") String aadharNo,
            @Field("country") String country,
            @Field("state") String state,
            @Field("city") String city,
            @Field("address") String address,
            @Field("profile_pic") String profilePic,
            @Field("profile_pic_name") String PicName


    );

    @FormUrlEncoded
    @POST("save_post_job_details.php")
    Call<CompanyJobPostedResponse>PostJob(
            @Field("company_id") String company_id,
            @Field("cname") String cname,
            @Field("jobtitle") String jobtitle,
            @Field("industry_type") String industry_type,
            @Field("functional_area") String functional_area,
            @Field("skill") String skill,
            @Field("experience") String experience,
            @Field("salary") String salary,
            @Field("location") String location,
            @Field("jobrole") String jobrole,
            @Field("lang") String lang,
            @Field("emp_type") String emp_type,
            @Field("description") String description,
            @Field("number_of_vacancy") String number_of_vacancy,
            @Field("company_logo") String company_logo,
            @Field("company_logo_name") String company_logo_name,
            @Field("walk_in_venue") String walk_in_venue,
            @Field("contact_number") String contact_number,
            @Field("contact_email") String contact_email
    );

    @FormUrlEncoded
    @POST("remove_candidate_bookmark.php")
    Call<CreateJobAlertResponse>createJobPost(
            @Field("can_id") String can_id,
            @Field("alert_name") String alert_name,
            @Field("keywords") String keywords,
            @Field("location") String location,
            @Field("experience") String experience,
            @Field("annual_salary") String annual_salary,
            @Field("functional_area") String functional_area,
            @Field("Industry") String Industry
    );

    @FormUrlEncoded
    @POST("post_alert_job.php")
    Call<CreateJobAlertResponse> createJobAlert(
            @Field("can_id") String can_id,
            @Field("alert_name") String alert_name,
            @Field("keywords") String keywords,
            @Field("loaction") String loaction,
            @Field("experience") String experience,
            @Field("annual_salary") String annual_salary,
            @Field("functional_area") String functional_area,
            @Field("industry") String industry
    );

//Update CompanyName
    @FormUrlEncoded
    @POST("update_company_name.php")
    Call<UpdateCandidateProfileResponse>upDateComName(
            @Field("com_id") String can_id,
            @Field("cname") String name
    );


    //Update Address
    @FormUrlEncoded
    @POST("update_company_address.php")
    Call<UpdateCandidateProfileResponse>upDateComAddress(
            @Field("com_id") String can_id,
            @Field("address") String adress,
            @Field("state") String state,
            @Field("city") String city
    );


    //Update Address
    @FormUrlEncoded
    @POST("remove_candidate.php")
    Call<RemoveCandidateResponse>removeCandidate(
            @Field("candidate_id") String candidateId,
            @Field("job_id") String jobId
    );

    //Get Company Details
    @GET("company_job_description.php")
    Call<CompanyJobDetailResponse> getCompanyJobDetails(
            @Query("job_id") String jobId,
            @Query("can_id") String companyId
    );


}
