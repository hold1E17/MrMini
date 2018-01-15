package mrmini.hold1e17.dk.mrmini;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Scanner extends AppCompatActivity implements View.OnClickListener {

    Button scanBut1, scanBut2, sygeplejeske1, hoved1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        scanBut1 = findViewById(R.id.scanBut1);
        scanBut2 = findViewById(R.id.scanBut2);
        sygeplejeske1 = findViewById(R.id.krop1);  // Skal ændres
        hoved1 = findViewById(R.id.hoved1);

        scanBut1.setOnClickListener(this);
        scanBut2.setOnClickListener(this);
        sygeplejeske1.setOnClickListener(this);
        hoved1.setOnClickListener(this);

        hoved1.setVisibility(View.INVISIBLE);

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
