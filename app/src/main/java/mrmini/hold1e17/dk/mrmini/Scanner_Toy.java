package mrmini.hold1e17.dk.mrmini;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Simon on 02-11-2017.
 */

public class Scanner_Toy extends AppCompatActivity implements View.OnClickListener {

    Button startScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_toy);

        startScan = (Button) findViewById(R.id.startScan);

        startScan.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == startScan) {

        }

    }
}
