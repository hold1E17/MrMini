package mrmini.hold1e17.dk.mrmini;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class Hovedmenu extends AppCompatActivity implements OnClickListener {
Button info, scanner, spil, ambulance, sygeplejeske, hoved, indstillinger;
String hospital, brugernavn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hovedmenu);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        brugernavn = PreferenceManager.getDefaultSharedPreferences(this).getString("login", "");

        hospital = PreferenceManager.getDefaultSharedPreferences(this).getString("pref_key_hospital", "");

        info = findViewById(R.id.info);
        scanner = findViewById(R.id.scanner);
        spil = findViewById(R.id.spil);
        ambulance = findViewById(R.id.ambulance);
        sygeplejeske = findViewById(R.id.sygeplejeske);
        hoved = findViewById(R.id.hoved);
        indstillinger = findViewById(R.id.indstillinger);

        indstillinger.setOnClickListener(this);
        hoved.setOnClickListener(this);
        info.setOnClickListener(this);
        scanner.setOnClickListener(this);
        spil.setOnClickListener(this);
        ambulance.setOnClickListener(this);
        sygeplejeske.setOnClickListener(this);

        sygeplejeske.setVisibility(View.VISIBLE);
        hoved.setVisibility(View.GONE);

    }

    public void onClick(View v) {
        if(v == info){
            Intent i = new Intent(this, HospitalsInfo.class);
            startActivity(i);
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.buttonclick);
            mediaPlayer.start();
        } else if(v == scanner){
            Intent i = new Intent(this, Scanner.class);
            startActivity(i);
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.buttonclick);
            mediaPlayer.start();
        } else if(v == spil){
            Intent i = new Intent(this, Spil.class);
            startActivity(i);
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.buttonclick);
            mediaPlayer.start();
        } else if(v == ambulance){
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.truck);
            mediaPlayer.start();
            ambulance.startAnimation(AnimationUtils.makeOutAnimation(this, false));
        } else if(v == sygeplejeske){
            hoved.setVisibility(View.VISIBLE);
            sygeplejeske.setVisibility(View.INVISIBLE);
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.close);
            mediaPlayer.start();
        } else if(v == hoved) {
            hoved.setVisibility(View.INVISIBLE);
            sygeplejeske.setVisibility(View.VISIBLE);
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.open);
            mediaPlayer.start();
        } else if(v == indstillinger) {
            Intent i = new Intent(this, Indstillinger.class);
            startActivityForResult(i,0);
        }

    }

}
