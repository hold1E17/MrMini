package mrmini.hold1e17.dk.mrmini;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Sofie on 02-01-2018.
 */

public class Scanner_app_execute extends Activity implements View.OnTouchListener{
    private ImageView img;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_app_execute);

        img = (ImageView) findViewById(R.id.patient);

        if(img != null) {
            img.setOnTouchListener(this);
        }
        //toast ("Touch screen to discover where the regions are.");
    }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            boolean handled = false;
            final int action = motionEvent.getAction();
            final int x = (int) motionEvent.getX();
            final int y = (int) motionEvent.getY();
            int nextImage = -1;

            ImageView back = (ImageView) view.findViewById(R.id.patient);
            if(back == null) return false;

            Integer tagNum = (Integer) back.getTag();
            int currentResource = (tagNum == null) ? R.drawable.man0 : tagNum.intValue();

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    if(currentResource == R.drawable.man0) {
                    nextImage = R.drawable.man1;
                    handled = true;
                    } else handled = true;
                    break;
                case MotionEvent.ACTION_UP:
                    int touchColor = getHotspotColor(R.id.patientBody, x,y);

                    colorTool ct = new colorTool();
                    int tolerance = 25;
                    nextImage = R.drawable.man0;

                    if (ct.closeMatch (Color.RED, touchColor, tolerance)) nextImage = R.drawable.magnet1;
                    else if (ct.closeMatch (Color.BLUE, touchColor, tolerance)) nextImage = R.drawable.magnet2;
                    else if (ct.closeMatch (Color.YELLOW, touchColor, tolerance)) nextImage = R.drawable.magnet3;
                    else if (ct.closeMatch (Color.WHITE, touchColor, tolerance)) nextImage = R.drawable.magnet4;

                    if(currentResource == nextImage) {
                       nextImage = R.drawable.man0;
                   }
                   handled = true;
                   break;
                default:
                    handled = false;
            }
            if(handled) {
                if (nextImage > 0) {
                    back.setImageResource(nextImage);
                    back.setTag(nextImage);
                }
            }

            return handled;

    }
    protected void onResume() {
        super.onResume();
        View v = findViewById(R.id.wglxy_bar);
        if(v != null) {
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            v.startAnimation(anim);
        }
    }
    public void onClickWglxy (View v) {
        Intent viewIntent = new Intent("android.intent.action.VIEW");
        startActivity(viewIntent);
    }
    public int getHotspotColor(int hpId, int x, int y) {
    ImageView change = (ImageView) findViewById(hpId);
    if(change == null) {
        Log.d("ImageAreasAcitivity","Hot Spot image not found");
        return 0;
    } else {
        change.setDrawingCacheEnabled(true);
        Bitmap hotSpots = Bitmap.createBitmap(change.getDrawingCache());
        if(hotSpots == null) {
            Log.d("ImageAreasActivity","Hot spot bitmap was not created");
            return 0;
    } else {
            change.setDrawingCacheEnabled(false);
            return hotSpots.getPixel(x,y);
        }
    }

    }
}
