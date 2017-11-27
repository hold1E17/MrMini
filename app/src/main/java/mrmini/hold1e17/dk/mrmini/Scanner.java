package mrmini.hold1e17.dk.mrmini;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Scanner extends AppCompatActivity implements View.OnClickListener {

    Button scanBut1, scanBut2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        scanBut1 = (Button) findViewById(R.id.scanBut1);
        scanBut2 = (Button) findViewById(R.id.scanBut2);

        scanBut1.setOnClickListener(this);
        scanBut2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == scanBut1) {
            Intent i = new Intent(this, Scanner_Toy.class);
            startActivity(i);
        } else if (v == scanBut2) {
            Intent j = new Intent(this, Scanner_app.class);
            startActivity(j);
        }
    }
}
