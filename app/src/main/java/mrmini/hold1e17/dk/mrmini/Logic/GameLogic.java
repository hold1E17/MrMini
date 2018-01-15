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

    public void createObjects() {

        nonMagnetic.add(new Thing(rand.nextInt(450), rand.nextInt(550)));
        nonMagnetic.add(new Thing(rand.nextInt(450), rand.nextInt(550)));
        nonMagnetic.add(new Thing(rand.nextInt(450), rand.nextInt(550)));
        nonMagnetic.add(new Thing(rand.nextInt(450), rand.nextInt(550)));
        nonMagnetic.add(new Thing(rand.nextInt(450), rand.nextInt(550)));
        magnetic.add(new Thing(rand.nextInt(450), rand.nextInt(550)));
        magnetic.add(new Thing(rand.nextInt(450), rand.nextInt(550)));
        magnetic.add(new Thing(rand.nextInt(450), rand.nextInt(550)));
        magnetic.add(new Thing(rand.nextInt(450), rand.nextInt(550)));
        magnetic.add(new Thing(rand.nextInt(450), rand.nextInt(550)));

    }

    public void createNonMagnetic(Resources res, Canvas c) {
        for (int i = 0; i < nonMagnetic.size(); i++) {
            if (nonMagnetic.get(i) == magnetObj) continue;
            Bitmap bitmap = BitmapFactory.decodeResource(res, nonMagDrawables[i]);
            c.drawBitmap(bitmap, null, nonMagnetic.get(i).rectF, object);
        }
    }

    public void createMagnetic(Resources res, Canvas c) {
        for (int i = 0; i < magnetic.size(); i++) {
            if (magnetic.get(i) == magnetObj) continue;
            Bitmap bitmap = BitmapFactory.decodeResource(res, magDrawables[i]);
            BitmapFactory.decodeResource(res,magDrawables[i]);
            c.drawBitmap(bitmap, null, magnetic.get(i).rectF, object) ;
        }
    }

    public void createMagnet(Resources res, Canvas c) {
        if (magnetObj != null) {
            RectF rectF = new RectF(magnetObj.rectF);
            rectF.offsetTo(finger.x - rectF.width() / 2, finger.y - rectF.height() / 2);
            Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.magnet);
            BitmapFactory.decodeResource(res,R.drawable.magnet);
            c.drawBitmap(bitmap, null, rectF, magnet) ;
        } else {
            RectF rectF = new RectF(mag.rectF);
            Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.magnet);
            BitmapFactory.decodeResource(res,R.drawable.magnet);
            c.drawBitmap(bitmap, null, rectF, magnet) ;
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
