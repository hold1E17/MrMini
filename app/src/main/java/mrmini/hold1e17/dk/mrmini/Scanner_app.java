package mrmini.hold1e17.dk.mrmini;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;



/**
 * Created by sofie on 26-11-2017.
 */

public class Scanner_app extends Activity implements View.OnClickListener {
    Button button, startScan;
    ImageView imageView;
    static final int CAM_REQUEST = 1;

    protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scanner_app);

    button = (Button) findViewById(R.id.button);
    startScan = (Button) findViewById(R.id.startScan);
    imageView = (ImageView) findViewById(R.id.imageView);


        button.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,0);
        }
    });
        button.setText("Tag billede");

        startScan.setOnClickListener(this);
        startScan.setText("Start scan");
}
    public void onClick(View v) {
        if(v == startScan){
            Intent i = new Intent(this, Scanner_app_execute.class);
            startActivity(i);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
    }
}
