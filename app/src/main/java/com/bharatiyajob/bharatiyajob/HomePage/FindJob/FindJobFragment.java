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

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_job, container, false);


        return view;
    }
}