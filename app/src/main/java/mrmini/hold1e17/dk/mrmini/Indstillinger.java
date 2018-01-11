package mrmini.hold1e17.dk.mrmini;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class Indstillinger extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();

    }

    public static class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.preferences);

            Preference pref = findPreference("pref_key_hospital");

            pref.setSummary(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("pref_key_hospital", ""));

            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

            Preference button = getPreferenceManager().findPreference("pref_key_logout");
            if (button != null) {
                button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference arg0) {
                        Intent i = new Intent(getActivity(), Login.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor editor = preferences.edit();

                        editor.clear().apply();

                        startActivity(i);
                        getActivity().finish();

                        return true;
                    }
                });

            }

        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            // TODO implement volume change on slider change

            if(key.equals("pref_key_hospital")) {
                Preference pref = findPreference(key);
                pref.setSummary(sharedPreferences.getString(key, ""));
            }

            if(key.equals("pref_key_sound")) {
               // TODO implement mute function
            }

        }
    }

}
