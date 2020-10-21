package com.bharatiyajob.bharatiyajob.Company.HomePage.PostJob;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;


import com.agrawalsuneet.dotsloader.loaders.CircularDotsLoader;
import com.bharatiyajob.bharatiyajob.Company.HomePage.CompanyHomePageActivity;
import com.bharatiyajob.bharatiyajob.Json.BaseClient;
import com.bharatiyajob.bharatiyajob.Json.Candidate.Login.LoginOtpResponse;
import com.bharatiyajob.bharatiyajob.Json.Company.CompanyJobPosted.CompanyJobPostedResponse;
import com.bharatiyajob.bharatiyajob.Json.JobApi;
import com.bharatiyajob.bharatiyajob.R;
import com.bharatiyajob.bharatiyajob.SharePrefeManger.LoginDetailSharePref;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static android.app.Activity.RESULT_OK;

public class PostJobFragment extends Fragment {

    ImageView formCameraPick;
    CircleImageView PcompanyLogo;
    EditText JPCompanyName, JPJobTitle, JPJobLocation, JPSkill, JPExprencieRequried, JPSalary, JPLanguageKnown,
            JPVacancy, JPJobRole, JPIndustryType, JPFunctionalArea, JPInterviewVenue, JPEmail, JPMobile,
            JPJobDescription;
    String Job_type = "";
    Context context;
    int IMAGGE_PICK = 100;
    RadioButton JpExperience, JpFresher;
    View v;
    Uri imageUri;
    Bitmap imagebitmap;
    String filepath,companyId;
    Button submitPostBtn;
    CircularDotsLoader jobPostProgress;
    ConstraintLayout cJobPostLayout,comPostLayout;

    public PostJobFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_post_job, container, false);

        JpExperience = v.findViewById(R.id.JpExperience);
        formCameraPick = v.findViewById(R.id.formCameraPick);
        JpFresher = v.findViewById(R.id.JpFresher);
        PcompanyLogo = v.findViewById(R.id.PcompanyLogo);
        JPCompanyName = v.findViewById(R.id.JPCompanyName);
        JPJobTitle = v.findViewById(R.id.JPJobTitle);
        JPJobLocation = v.findViewById(R.id.JPJobLocation);
        JPSkill = v.findViewById(R.id.JPSkill);
        JPExprencieRequried = v.findViewById(R.id.JPExprencieRequried);
        JPSalary = v.findViewById(R.id.JPSalary);
        JPJobRole = v.findViewById(R.id.JPJobRole);
        JPIndustryType = v.findViewById(R.id.JPIndustryType);
        JPFunctionalArea = v.findViewById(R.id.JPFunctionalArea);
        JPInterviewVenue = v.findViewById(R.id.JPInterviewVenue);
        JPEmail = v.findViewById(R.id.JPEmail);
        JPMobile = v.findViewById(R.id.JPMobile);
        JPJobDescription = v.findViewById(R.id.JPJobDescription);
        JPVacancy = v.findViewById(R.id.JPVacancy);
        submitPostBtn = v.findViewById(R.id.submitPostBtn);
        JPLanguageKnown = v.findViewById(R.id.JPLanguageKnown);
        jobPostProgress = v.findViewById(R.id.jobPostProgress);
        cJobPostLayout = v.findViewById(R.id.cJobPostLayout);
        comPostLayout = v.findViewById(R.id.comPostLayout);

        getCompanyDetail();

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "Camera permission granted", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 2);
        }
        submitPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                SavecaData();
            }
        });
        formCameraPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseLogo();
            }
        });
        JpExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Job_type = "Experienced";
                Log.d("GENDER", Job_type);
                Toast.makeText(getActivity(), "Exp", Toast.LENGTH_SHORT).show();

            }
        });
        JpFresher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Job_type = "Fresher";
                Log.d("GENDER", Job_type);
                Toast.makeText(getActivity(), "Fresher", Toast.LENGTH_SHORT).show();

            }
        });


        return v;
    }

    public void ChooseLogo() {

        Intent pick_image_intent = new Intent(Intent.ACTION_PICK);
        pick_image_intent.setType("image/*");
        startActivityForResult(pick_image_intent, 100);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data.getData() != null) {
            filepath = data.getData().toString();
            imageUri = data.getData();
            try {
                imagebitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                PcompanyLogo.setImageBitmap(imagebitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            Toast.makeText(getActivity(), "data empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void SavecaData() {

        cJobPostLayout.setVisibility(View.INVISIBLE);
        comPostLayout.setAlpha(0.5f);
        jobPostProgress.setVisibility(View.VISIBLE);

        CircularDotsLoader loader = new CircularDotsLoader(getActivity());
        loader.setDefaultColor(ContextCompat.getColor(getActivity(),R.color.blue_delfault));
        loader.setSelectedColor(ContextCompat.getColor(getActivity(),R.color.blue_selected));
        loader.setBigCircleRadius(80);
        loader.setRadius(24);
        loader.setAnimDur(300);
        loader.setShowRunningShadow(true);
        loader.setFirstShadowColor(ContextCompat.getColor(getActivity(), R.color.blue_selected));
        loader.setSecondShadowColor(ContextCompat.getColor(getActivity(), R.color.blue_delfault));

        if (JPCompanyName.getText().toString().isEmpty()
        ) {
            JPCompanyName.setError("require");
            JPCompanyName.setFocusable(true);
            Toast.makeText(getActivity(), "please fill the required fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Job_type.isEmpty()) {
            Toast.makeText(getActivity(), "please select Experience", Toast.LENGTH_SHORT).show();
            return;
        }
        if (JPJobTitle.getText().toString().isEmpty()
        ) {
            JPJobTitle.setError("require");
            JPJobTitle.setFocusable(true);
            return;
        }
        if (JPJobLocation.getText().toString().isEmpty()
        ) {
            JPJobLocation.setError("require");
            JPJobLocation.setFocusable(true);
            return;
        }


        if (JPSkill.getText().toString().isEmpty()
        ) {
            JPSkill.setError("require");
            JPSkill.setFocusable(true);
            return;
        }
        if (JPExprencieRequried.getText().toString().isEmpty()
        ) {
            JPExprencieRequried.setError("company name require");
            JPExprencieRequried.setFocusable(true);
            return;
        }
        if (JPJobLocation.getText().toString().isEmpty()
        ) {
            JPJobLocation.setError("require");
            JPJobLocation.setFocusable(true);
            return;
        }

        if (JPSalary.getText().toString().isEmpty()
        ) {
            JPSalary.setError("require");
            JPSalary.setFocusable(true);
            return;
        }

        if (JPLanguageKnown.getText().toString().isEmpty()
        ) {
            JPLanguageKnown.setError("require");
            JPLanguageKnown.setFocusable(true);
            return;
        }

        if (JPJobRole.getText().toString().isEmpty()
        ) {
            JPJobRole.setError("require");
            JPJobRole.setFocusable(true);
            return;
        }
        if (JPIndustryType.getText().toString().isEmpty()
        ) {
            JPIndustryType.setError("require");
            JPIndustryType.setFocusable(true);
            return;
        }

        if (JPFunctionalArea.getText().toString().isEmpty()
        ) {
            JPFunctionalArea.setError("require");
            JPFunctionalArea.setFocusable(true);
            return;
        }
        if (JPInterviewVenue.getText().toString().isEmpty()
        ) {
            JPInterviewVenue.setError("require");
            JPInterviewVenue.setFocusable(true);
            return;
        }
        if (JPEmail.getText().toString().isEmpty()
        ) {
            JPEmail.setError("require");
            JPEmail.setFocusable(true);
            return;
        }
        if (JPMobile.getText().toString().isEmpty()
        ) {
            JPMobile.setError("require");
            JPMobile.setFocusable(true);
            return;
        }
        if (JPJobDescription.getText().toString().isEmpty()
        ) {
            JPJobDescription.setError("require");
            JPJobDescription.setFocusable(true);
            return;
        }
        if (JPVacancy.getText().toString().isEmpty()
        ) {
            JPVacancy.setError("require");
            JPVacancy.setFocusable(true);
            return;
        }

        String cname=JPCompanyName.getText().toString();
        String Jobtitle=JPJobTitle.getText().toString();
        String industry_type=JPIndustryType.getText().toString();
        String functional_area=JPFunctionalArea.getText().toString();
        String skill=JPSkill.getText().toString();
        String experience=JPExprencieRequried.getText().toString();
        String salary=JPSalary.getText().toString();
        String location=JPJobLocation.getText().toString();
        String jobrole=JPJobRole.getText().toString();
        String lang=JPLanguageKnown.getText().toString();
        String description=JPJobDescription.getText().toString();
        String no_vacancy=JPVacancy.getText().toString();
        String venue=JPInterviewVenue.getText().toString();
        String contact_no=JPMobile.getText().toString();
        String contact_email=JPEmail.getText().toString();



        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if ( imagebitmap==null){
            Toast.makeText(getActivity(), "please select the company Logo", Toast.LENGTH_SHORT).show();
            return;
        }
        imagebitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageinbyte = byteArrayOutputStream.toByteArray();

        final String imagestring = Base64.encodeToString(imageinbyte, Base64.DEFAULT);
        File file=new File(filepath);
        Log.d("imagestring", imagestring);
        Toast.makeText(getActivity(), imagestring, Toast.LENGTH_SHORT).show();
        JobApi jobApi = BaseClient.getBaseClient().create(JobApi.class);

        Call<CompanyJobPostedResponse> call=jobApi.PostJob(companyId,cname,Jobtitle,industry_type,functional_area
                ,skill,experience,salary,location,jobrole
                ,lang,Job_type,description,no_vacancy
                ,imagestring,file.getName(),venue,contact_no,contact_email);


        call.enqueue(new Callback<CompanyJobPostedResponse>() {
            @Override
            public void onResponse(Call<CompanyJobPostedResponse> call, Response<CompanyJobPostedResponse> response) {
                CompanyJobPostedResponse getCompanyJobPostedResponse=response.body();
                if (response.isSuccessful() ){
                    Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), CompanyHomePageActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CompanyJobPostedResponse> call, Throwable t) {
                Toast.makeText(getContext(), "OnFailre", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getCompanyDetail() {
//        LoginOtpResponse loginOtpResponse = LoginDetailSharePref.getInstance(getActivity()).getDetail();

        LoginDetailSharePref loginDetailSharePref = new LoginDetailSharePref(getActivity());
        LoginOtpResponse loginOtpResponse = loginDetailSharePref.getDetail();
        companyId = loginOtpResponse.getId();
    }
}