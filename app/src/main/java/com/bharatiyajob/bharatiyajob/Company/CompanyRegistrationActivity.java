package com.bharatiyajob.bharatiyajob.Company;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bharatiyajob.bharatiyajob.R;
import com.google.android.material.textfield.TextInputLayout;

public class CompanyRegistrationActivity extends AppCompatActivity {

    TextInputLayout GstTextLayout,AdhaarTxtLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_registration);

        GstTextLayout = findViewById(R.id.GstTextLayout);
        AdhaarTxtLayout = findViewById(R.id.AdhaarTxtLayout);
    }

    public void selectGst(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.GST:
                if (checked)
                    GstTextLayout.setVisibility(View.VISIBLE);
                    AdhaarTxtLayout.setVisibility(View.GONE);
//                    Toast.makeText(this, "Gst is selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.NonGst:
                if (checked)
                    GstTextLayout.setVisibility(View.GONE);
                    AdhaarTxtLayout.setVisibility(View.VISIBLE);
//                    Toast.makeText(this, "Non Gst is selected", Toast.LENGTH_SHORT).show();
                break;

        }

    }
}