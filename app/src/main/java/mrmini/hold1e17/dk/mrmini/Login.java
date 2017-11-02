package com.brugerinteraktion.gruppe01.mrscanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Simon on 30-10-2017.
 */

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button noLogBut, logBut;
    EditText userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logBut = (Button) findViewById(R.id.logBut);
        noLogBut = (Button) findViewById(R.id.noLogBut);
        userName = (EditText) findViewById(R.id.userName);

        logBut.setOnClickListener(this);
        noLogBut.setOnClickListener(this);

}

public void onClick(View v) {

    if (v == logBut) {
        Intent i = new Intent(this, Hovedmenu.class);
        startActivity(i);
    } else if (v == noLogBut) {
        Intent i = new Intent(this, Hovedmenu.class);
        startActivity(i);
    }

}
}
