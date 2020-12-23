package com.bharatiyajob.bharatiyajob.Company.HomePage.CandidateDetail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.R;
import com.github.barteksc.pdfviewer.PDFView;

public class ResumePdfActivity extends AppCompatActivity {

    String Resume;
    PDFView viewResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_pdf);

        viewResume = findViewById(R.id.viewResume);


        Bundle bundle = getIntent().getExtras();
//        Resume = bundle.getString("resume");


        Toast.makeText(this, Resume , Toast.LENGTH_SHORT).show();
    }

    public void downloadResume(View view) {


        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.tutorialspoint.com/python3/python_tutorial.pdf"));
        startActivity(intent);
    }

}