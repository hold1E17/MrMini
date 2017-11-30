package mrmini.hold1e17.dk.mrmini;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Hovedmenu extends AppCompatActivity implements OnClickListener {
Button info, scanner, spil;
String brugernavn, hospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hovedmenu);

        brugernavn = getIntent().getStringExtra("navn");
        hospital = getIntent().getStringExtra("hospital");

        info = (Button) findViewById(R.id.info);
        scanner = (Button) findViewById(R.id.scanner);
        spil = (Button) findViewById(R.id.spil);

        info.setOnClickListener(this);
        scanner.setOnClickListener(this);
        spil.setOnClickListener(this);
    }

    public void onClick(View v) {
        if(v == info){
            Intent i = new Intent(this, HospitalsInfo.class);
            i.putExtra("hospital", hospital);
            startActivity(i);
        } else if(v == scanner){
            Intent i = new Intent(this, Scanner.class);
            startActivity(i);
        } else if(v == spil){
            Intent i = new Intent(this, Spil.class);
            startActivity(i);
        }

    }
}
