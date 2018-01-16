package mrmini.hold1e17.dk.mrmini;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;


/**
 * Created by sofie on 26-11-2017.
 */

public class Scanner_app extends AppCompatActivity implements View.OnClickListener {
    Button button, startScan, dreng, skildpadde;
    ImageButton custom;
    ImageView scanner, customScanner;
    static final int CAM_REQUEST = 1;

    protected void onCreate(Bundle savedInstanceState) {

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_app);

        button = findViewById(R.id.button);
        startScan = findViewById(R.id.startScan);
        dreng = findViewById(R.id.hoveddreng);
        skildpadde = findViewById(R.id.hovedskildpadde);
        custom = findViewById(R.id.customBody);
        customScanner = findViewById(R.id.customScan);
        scanner = findViewById(R.id.scanner_app);
        scanner.setImageResource(R.drawable.scanner);
        customScanner.setVisibility(View.INVISIBLE);

        button.setOnClickListener(this);
        startScan.setOnClickListener(this);
        dreng.setOnClickListener(this);
        custom.setOnClickListener(this);
        skildpadde.setOnClickListener(this);

    }


    public void onClick(View v) {
        if (v == startScan) {
            Intent i = new Intent(this, Scanner_app_execute.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        } else if (v == dreng) {
            scanner.setImageResource(R.drawable.scanner_mand);
            startScan.setVisibility(View.VISIBLE);
        } else if (v == skildpadde) {
            scanner.setImageResource(R.drawable.mrscanner_skildpadde);
            startScan.setVisibility(View.VISIBLE);
        } else if (v == custom) {
            scanner.setImageResource(R.drawable.scanner_mand);
            customScanner.setVisibility(View.VISIBLE);
            startScan.setVisibility(View.VISIBLE);
        }
        else if (v == button) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        custom.setImageBitmap(bitmap);
        customScanner.setImageBitmap(bitmap);

        } 

    }

}

