package com.bharatiyajob.bharatiyajob.User.CandidateNotification;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bharatiyajob.bharatiyajob.R;


public class CandidateAlertListFragment extends Fragment {

    public CandidateAlertListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_candidate_alert_list, container, false);
    }
}