package mrmini.hold1e17.dk.mrmini;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mrmini.hold1e17.dk.mrmini.Logic.PreferenceLogic;

public class Scanner extends AppCompatActivity implements View.OnClickListener {

    Button scanBut1, scanBut2, sygeplejeske1, hoved1;

    String nurseStatus;

    PreferenceLogic pl = new PreferenceLogic();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        scanBut1 = (Button) findViewById(R.id.scanBut1);
        scanBut2 = (Button) findViewById(R.id.scanBut2);
        sygeplejeske1 = (Button) findViewById(R.id.krop1);
        hoved1 = (Button) findViewById(R.id.hoved1);

        nurseStatus = PreferenceManager.getDefaultSharedPreferences(this).getString("pref_key_nurse_scan", "");

        if (nurseStatus.equals("true")) {

            sygeplejeske1.setVisibility(View.GONE);
            hoved1.setVisibility(View.VISIBLE);

        } else {

            sygeplejeske1.setVisibility(View.VISIBLE);
            hoved1.setVisibility(View.GONE);

        }

        scanBut1.setOnClickListener(this);
        scanBut2.setOnClickListener(this);
        sygeplejeske1.setOnClickListener(this);
        hoved1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == scanBut1) {
            Intent i = new Intent(this, Scanner_Toy.class);
            startActivity(i);
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.toy);
            mediaPlayer.start();
        } else if (v == scanBut2) {
            Intent j = new Intent(this, Scanner_app.class);
            startActivity(j);
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.buttonclick);
            mediaPlayer.start();
        } else if(v == sygeplejeske1){
            pl.saveNurse(this);
            hoved1.setVisibility(View.VISIBLE);
            sygeplejeske1.setVisibility(View.INVISIBLE);
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.close);
            mediaPlayer.start();
        } else if(v == hoved1) {
            hoved1.setVisibility(View.INVISIBLE);
            sygeplejeske1.setVisibility(View.VISIBLE);
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.open);
            mediaPlayer.start();
    }
    }
}
