package com.bharatiyajob.bharatiyajob.HomePage.FindJob;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bharatiyajob.bharatiyajob.R;

public class FindJobFragment extends Fragment {

    public FindJobFragment() {
        // Required empty public constructor
    }

    Button searchJobBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // In this fragment we are inflaating one more fragment which will contain two fragments


        return inflater.inflate(R.layout.fragment_find_job, container, false);
    }
}