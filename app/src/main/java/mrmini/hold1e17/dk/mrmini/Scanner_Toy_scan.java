package mrmini.hold1e17.dk.mrmini;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class Scanner_Toy_scan extends AppCompatActivity {

    private static Context context;
    private static TextView statusTextScan;
    private static ImageView scannerImg;

    private static String savedCase;
    private static boolean scanning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner__toy_scan);
        statusTextScan = (TextView) findViewById(R.id.textStatusScan);
        scannerImg = (ImageView) findViewById(R.id.scanner_empty);

        if(savedInstanceState != null){
            savedCase = savedInstanceState.getString("savedCase");
            if(savedCase != null){
                read(savedCase);
            }
        }

        context = this;
    }

    public static void read(String messageRead) {

        if(scanning == true){
            Scanner_app_execute.endActivity();
            scanning = false;
        }

        switch (messageRead) {
            case "1":
                statusTextScan.setText("Dukken er blevet fjernet, og derfor afbrydes scanningen");
                scannerImg.setImageResource(R.drawable.scanner);
                savedCase = "1";
                break;
            case "2":
                statusTextScan.setText("Ingen dukke");
                scannerImg.setImageResource(R.drawable.scanner);

                savedCase = "2";
                break;
            case "3":
                statusTextScan.setText("Dukken er placeret korrekt");
                scannerImg.setImageResource(R.drawable.scanner_mand);
                savedCase = "3";
                break;
            case "4":
                statusTextScan.setText("MR scanneren er ved at bevæge sig ind");
                scannerImg.setImageResource(R.drawable.scanner_mand);
                savedCase = "4";
                break;
            case "5":
                statusTextScan.setText("MR scanneren er ved at bevæge sig ud");
                scannerImg.setImageResource(R.drawable.scanner_mand);
                savedCase = "5";
                break;
            case "6":
                statusTextScan.setText("Scanningen er igang");
                savedCase = "6";
                scanning = true;
                Intent i = new Intent(context, Scanner_app_execute.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(i);
                break;
            case "7":
                statusTextScan.setText("Dukken der er blevet placeret er en dreng");
                scannerImg.setImageResource(R.drawable.scanner_mand);
                savedCase = "7";
                break;
            case "8":
                statusTextScan.setText("Dukken der er blevet placeret er en pige");
                savedCase = "8";
                break;
            case "9":
                statusTextScan.setText("Dukken der er blevet placeret er en skildpadde");
                savedCase = "9";
                scannerImg.setImageResource(R.drawable.mrscanner_skildpadde);
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("savedCase", savedCase);
        super.onSaveInstanceState(outState);
    }
}
