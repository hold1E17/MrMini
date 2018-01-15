package mrmini.hold1e17.dk.mrmini;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import mrmini.hold1e17.dk.mrmini.Logic.PreferenceLogic;

/**
 * Created by Simon on 30-10-2017.
 */

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button noLogBut, logBut, ambulance;
    EditText userName;
    Spinner hospitalChoice;
    CheckBox rememberMe;

    PreferenceLogic pl = new PreferenceLogic();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        logBut = findViewById(R.id.logBut);
        noLogBut = findViewById(R.id.noLogBut);
        userName = findViewById(R.id.userName);
        hospitalChoice = findViewById(R.id.hospitalChoice);
        rememberMe = findViewById(R.id.rememberMe);
        ambulance = findViewById(R.id.ambulance);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.hospitalList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hospitalChoice.setAdapter(adapter);

        logBut.setOnClickListener(this);
        noLogBut.setOnClickListener(this);
        ambulance.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == logBut) {
            Intent i = new Intent(this, Hovedmenu.class);

            pl.saveLogin(hospitalChoice.getSelectedItem().toString(), userName.getText().toString(), rememberMe.isChecked(), this);
            startActivity(i);
            finish();

            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.buttonclick);
            mediaPlayer.start();

        } else if (v == noLogBut) {
            Intent i = new Intent(this, Hovedmenu.class);
            startActivity(i);
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.buttonclick);
            mediaPlayer.start();
        } else if(v == ambulance){
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.truck);
            mediaPlayer.start();
            ambulance.startAnimation(AnimationUtils.makeOutAnimation(this, false));
        }


    }

}
