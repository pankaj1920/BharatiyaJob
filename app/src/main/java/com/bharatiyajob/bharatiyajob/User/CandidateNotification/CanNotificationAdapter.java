package com.bharatiyajob.bharatiyajob.User.CandidateNotification;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bharatiyajob.bharatiyajob.User.CandidateNotification.alert_list.CandidateAlertListFragment;
import com.bharatiyajob.bharatiyajob.User.CandidateNotification.notification_list.CandidateNotificationFragment;

public class CanNotificationAdapter extends FragmentStatePagerAdapter {

    final int TabCount;

    public CanNotificationAdapter(@NonNull FragmentManager fm, int tabcount) {
        super(fm, tabcount);

        TabCount = tabcount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case (0):
                return new CandidateNotificationFragment();

            case (1):
                return new CandidateAlertListFragment();

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return TabCount;
    }
}
