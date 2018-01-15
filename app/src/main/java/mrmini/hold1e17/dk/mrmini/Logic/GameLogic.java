package mrmini.hold1e17.dk.mrmini.Logic;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.Vibrator;

import java.util.ArrayList;
import java.util.Random;

import mrmini.hold1e17.dk.mrmini.R;

/**
 * Created by Simon on 10-01-2018.
 */

public class GameLogic {

    private ArrayList<Thing> nonMagnetic = new ArrayList<>();
    private ArrayList<Thing> magnetic = new ArrayList<>();
    private ArrayList<Thing> caught = new ArrayList<>();
    private ArrayList<Bitmap> magMap = new ArrayList<>();
    private ArrayList<Bitmap> nonMagMap = new ArrayList<>();
    private Bitmap magBM;
    private Random rand = new Random();
    private int[] magDrawables = new int[]{R.drawable.magnetic1, R.drawable.magnetic2, R.drawable.magnetic3, R.drawable.magnetic4, R.drawable.magnetic5};
    private int[] nonMagDrawables = new int[]{R.drawable.nonmagnetic1, R.drawable.nonmagnetic2, R.drawable.nonmagnetic3, R.drawable.nonmagnetic4, R.drawable.nonmagnetic5};
    private Thing magnetObj = null;
    private Thing mag = new Thing(0, 0);
    private Paint object = new Paint();
    private Paint magnet = new Paint();
    PointF finger = new PointF();

    class Thing {
        RectF rectF = new RectF();

        Thing(int x, int y) {
            rectF = new RectF(x - 50, y - 50, x + 38, y + 38);
        }
    }

    public void mapObjects(Resources res) {

        for (int i = 0; i < nonMagnetic.size(); i++) {
            if (nonMagnetic.get(i) == magnetObj) continue;
            nonMagMap.add(BitmapFactory.decodeResource(res, nonMagDrawables[i]));
        }

        for (int i = 0; i < magnetic.size(); i++) {
            if (magnetic.get(i) == magnetObj) continue;
            magMap.add(BitmapFactory.decodeResource(res, magDrawables[i]));
        }

        magBM = BitmapFactory.decodeResource(res, R.drawable.magnet);
    }

    public void createObjects() {

        int w = 480;
        int h = 480;

        nonMagnetic.add(new Thing(rand.nextInt(w), rand.nextInt(h)));
        nonMagnetic.add(new Thing(rand.nextInt(w), rand.nextInt(h)));
        nonMagnetic.add(new Thing(rand.nextInt(w), rand.nextInt(h)));
        nonMagnetic.add(new Thing(rand.nextInt(w), rand.nextInt(h)));
        nonMagnetic.add(new Thing(rand.nextInt(w), rand.nextInt(h)));
        magnetic.add(new Thing(rand.nextInt(w), rand.nextInt(h)));
        magnetic.add(new Thing(rand.nextInt(w), rand.nextInt(h)));
        magnetic.add(new Thing(rand.nextInt(w), rand.nextInt(h)));
        magnetic.add(new Thing(rand.nextInt(w), rand.nextInt(h)));
        magnetic.add(new Thing(rand.nextInt(w), rand.nextInt(h)));

    }

    public void createNonMagnetic(Canvas c) {

        for (int i = 0; i < nonMagMap.size(); i++) {

            c.drawBitmap(nonMagMap.get(i), null, nonMagnetic.get(i).rectF, object);

        }
    }

    public void createMagnetic(Canvas c) {

        for(int i = 0; i < magMap.size(); i++) {

            c.drawBitmap(magMap.get(i), null, magnetic.get(i).rectF, object);

        }
    }

    public void createMagnet(Canvas c) {
        if (magnetObj != null) {
            RectF rectF = new RectF(magnetObj.rectF);
            rectF.offsetTo(finger.x - rectF.width() / 2, finger.y - rectF.height() / 2);
            c.drawBitmap(magBM, null, rectF, magnet) ;
        } else {
            RectF rectF = new RectF(mag.rectF);
            c.drawBitmap(magBM, null, rectF, magnet) ;
        }
    }

    public boolean catchMagnetic(Context ctx, float ex, float ey) {
        finger.x = ex;
        finger.y = ey;

        for (Thing s : magnetic) {
            if (s.rectF.contains(ex, ey)) {
                if (!(caught.contains(s))) {
                    caught.add(s);

                    Vibrator v = (Vibrator) ctx.getSystemService(ctx.VIBRATOR_SERVICE);
                    v.vibrate(400);
                    // Spiller kling lyd
                    MediaPlayer mediaPlayer = MediaPlayer.create(ctx, R.raw.chime_bell_ding);
                    mediaPlayer.start();


                    if (caught.size() == magnetic.size()) {

                        return false;

                    }
                }
                magnetObj = s;

            } else {
                mag.rectF.offsetTo(finger.x, finger.y);
            }

        }
        for (int i = 0; i < caught.size(); i++) {
            caught.get(i).rectF.offsetTo(finger.x, finger.y);
        }
        return true;
    }

}
