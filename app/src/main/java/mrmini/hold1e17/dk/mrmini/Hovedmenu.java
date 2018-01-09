package mrmini.hold1e17.dk.mrmini;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.media.MediaPlayer;

public class Hovedmenu extends AppCompatActivity implements OnClickListener {
Button info, scanner, spil, ambulance, sygeplejeske, hoved;
String hospital, brugernavn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hovedmenu);

        brugernavn = PreferenceManager.getDefaultSharedPreferences(this).getString("login", "");

        hospital = PreferenceManager.getDefaultSharedPreferences(this).getString("pref_key_hospital", "");


        info = (Button) findViewById(R.id.info);
        scanner = (Button) findViewById(R.id.scanner);
        spil = (Button) findViewById(R.id.spil);
        ambulance = (Button) findViewById(R.id.ambulance);
        sygeplejeske = (Button)findViewById(R.id.sygeplejeske);
        hoved = (Button)findViewById(R.id.sygeplejeske);

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
            visHoved();
        } else if(v == hoved) {
           visSygeplejseske();
        }

    }

    private void visSygeplejseske() {
        System.out.println("SYGEPLEJSERKE OP");
        hoved.setVisibility(View.INVISIBLE);
        sygeplejeske.setVisibility(View.VISIBLE);
    }

    private void visHoved() {
        hoved.setVisibility(View.VISIBLE);
        sygeplejeske.setVisibility(View.INVISIBLE);
        System.out.println("HHOVED OP");
    }



    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.xml.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_name) {

            Intent i = new Intent(this, Indstillinger.class);
            startActivityForResult(i,0);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
