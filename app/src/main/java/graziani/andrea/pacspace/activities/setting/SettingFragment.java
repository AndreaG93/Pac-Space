package graziani.andrea.pacspace.activities.setting;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import graziani.andrea.pacspace.R;


public class SettingFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preference);
    }
}


