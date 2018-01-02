package mrmini.hold1e17.dk.mrmini;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;

/**
 * Created by Simon on 30-10-2017.
 */

public class Launcher extends AppCompatActivity {

    String login = "";
    String hospital = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        login = PreferenceManager.getDefaultSharedPreferences(this).getString("login", "");

        hospital = PreferenceManager.getDefaultSharedPreferences(this).getString("pref_key_hospital", "");

        if (!(PreferenceManager.getDefaultSharedPreferences(this).getString("pref_key_save", "").equals("true"))) {

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();

            editor.clear().apply();

            Intent i = new Intent(this, Login.class);
            startActivity(i);
            finish();
        } else {
            Intent i = new Intent(this, Hovedmenu.class);
            startActivity(i);
            finish();
        }

        setTitle("MR Scanner");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
