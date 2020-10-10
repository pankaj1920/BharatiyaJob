package com.bharatiyajob.bharatiyajob.SharePrefeManger;

import android.content.Context;

public class CandiateSharePreference {

    private static CandiateSharePreference sharePrefMamager;
     private Context context;

    public CandiateSharePreference(Context context) {
        this.context = context;
    }

    //we will create Syncronized Method as we only want a single instance
    public  static  synchronized  CandiateSharePreference getInstance(Context context){
        if (sharePrefMamager == null){  //this mean the object is no yet created in this case we will make new SharedPrefrenceManager
            sharePrefMamager = new CandiateSharePreference (context);
        }
        return  sharePrefMamager;
    }
}
