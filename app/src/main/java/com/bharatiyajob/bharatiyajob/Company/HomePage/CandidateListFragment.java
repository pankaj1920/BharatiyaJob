package com.bharatiyajob.bharatiyajob.Company.HomePage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bharatiyajob.bharatiyajob.R;

public class CandidateListFragment extends Fragment {


    public CandidateListFragment() {
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
        return inflater.inflate(R.layout.candidate_list_item, container, false);
//        return inflater.inflate(R.layout.fragment_candidate_list, container, false);
    }
}