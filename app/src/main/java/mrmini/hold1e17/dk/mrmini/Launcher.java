package com.brugerinteraktion.gruppe01.mrscanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;

/**
 * Created by Simon on 30-10-2017.
 */

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        } else {
            Intent i = new Intent(this, Hovedmenu.class);
            startActivity(i);
        }

        setTitle("MR Scanner");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
