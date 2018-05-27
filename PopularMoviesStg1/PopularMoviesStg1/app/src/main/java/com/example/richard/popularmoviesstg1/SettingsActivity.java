package com.example.richard.popularmoviesstg1;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;


public class SettingsActivity extends PreferenceActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.app.FragmentTransaction fManager = getFragmentManager().beginTransaction();
        fManager.replace(android.R.id.content, new SettingsFragment());
        fManager.commit();
        }

        public static class SettingsFragment extends PreferenceFragment{
        @Override
        public void onCreate(final Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);
        }
    }


}
