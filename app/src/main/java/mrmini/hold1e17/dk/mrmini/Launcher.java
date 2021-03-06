package mrmini.hold1e17.dk.mrmini;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Simon on 30-10-2017.
 */

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        super.onCreate(savedInstanceState);
        boolean EMULATOR = Build.PRODUCT.contains("sdk") || Build.MODEL.contains("Emulator");
        if (!EMULATOR) {
            Fabric.with(this, new Crashlytics());
        }

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
