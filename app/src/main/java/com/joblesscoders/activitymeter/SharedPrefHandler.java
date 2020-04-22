package com.joblesscoders.activitymeter;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefHandler {
    public static void saveData(Context context,ActivityPojo activityPojo)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Info",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("activity", activityPojo.getActivity());
        editor.putString("user", activityPojo.getUser());
        editor.putInt("delay",activityPojo.getDelay());
        editor.commit();

    }
    public static ActivityPojo getData(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Info",Context.MODE_PRIVATE);
        if(sharedPreferences.getString("activity",null)==null)
            return null;
        ActivityPojo activityPojo = new ActivityPojo(sharedPreferences.getString("activity",null),sharedPreferences.getInt("delay",0),sharedPreferences.getString("user",null));
        return activityPojo;
    }

}
