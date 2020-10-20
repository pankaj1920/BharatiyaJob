package com.bharatiyajob.bharatiyajob.User.CandidateNotification;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bharatiyajob.bharatiyajob.User.CandidateNotification.alert_list.CandidateAlertListFragment;
import com.bharatiyajob.bharatiyajob.User.CandidateNotification.notification_list.CandidateNotificationFragment;

public class CanNotificationAdapter extends FragmentStatePagerAdapter {

    int TabCount;

    public CanNotificationAdapter(@NonNull FragmentManager fm, int tabcount) {
        super(fm, tabcount);

        TabCount = tabcount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case (0):
                CandidateNotificationFragment canNotificationFragment = new CandidateNotificationFragment();
                return canNotificationFragment;

            case (1):
                CandidateAlertListFragment candidateAlertListFragment = new CandidateAlertListFragment();
                return candidateAlertListFragment;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return TabCount;
    }
}
