package com.bharatiyajob.bharatiyajob.HomePage.FindJob;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bharatiyajob.bharatiyajob.R;

public class SearchJobFragment extends Fragment {

    Button searchJobBtn;
    EditText SjBySkill,SjByLocation;
    String skill,location;

    public SearchJobFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_job, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchJobBtn = view.findViewById(R.id.searchJobBtn);
        SjBySkill = view.findViewById(R.id.SjBySkill);
        SjByLocation = view.findViewById(R.id.SjByLocation);

        final NavController navController = Navigation.findNavController(view);

        searchJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                skill = SjBySkill.getText().toString();
                location = SjByLocation.getText().toString();

                SearchJobArguments arguments = new SearchJobArguments(skill,location);
                SearchJobFragmentDirections.ActionSearchJobFragment2ToJobByTypeFragment3 action = SearchJobFragmentDirections.actionSearchJobFragment2ToJobByTypeFragment3(arguments);
                navController.navigate(action);
            }
        });
    }
}