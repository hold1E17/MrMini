package mrmini.hold1e17.dk.mrmini;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class Spil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MagnetView(this));
    }

    class Thing {
        RectF rectF = new RectF();

        String str;

        Thing(String string, int x, int y) {
            str = string;
            rectF = new RectF(x + 2, y + 2, x + 38, y + 38);
        }
        public String toString() {
            return str;
        }
    }

    class MagnetView extends View {
        PointF finger = new PointF();
        ArrayList<Thing> nonMagnetic = new ArrayList<>();
        ArrayList<Thing> magnetic = new ArrayList<>();
        ArrayList<Thing> caught = new ArrayList<>();
        Thing magnetObj = null;
        Paint brikStregtype = new Paint();
        Paint magnet;

        // programmatisk konstruktør
        public MagnetView(Context context) {
            super(context);

            brikStregtype.setColor(Color.GRAY);
            brikStregtype.setStyle(Paint.Style.FILL);
            brikStregtype.setAntiAlias(true);
            brikStregtype.setStrokeWidth(2);
            magnet = new Paint(brikStregtype);
            magnet.setStyle(Paint.Style.STROKE);
            magnet.setColor(Color.RED);

            nonMagnetic.add(new Thing("6", 30, 30));
            nonMagnetic.add(new Thing("+", 80, 80));
            magnetic.add(new Thing("1", 140, 40));
            magnetic.add(new Thing("2", 130, 90));
            magnetic.add(new Thing("3", 170, 130));

        }

        @Override
        protected void onDraw(Canvas c) {
        //    Resources res = getResources();
        //    Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.magnet);
            // Spillet er beregnet til en skærm der er 480 punkter bred...
            float skærmSkala = getWidth() / 480f; // ... så skalér derefter
            c.scale(skærmSkala, skærmSkala);

            // Tegn først alle brikker, undtagen den valgte
            for (Thing thing : nonMagnetic) {
                if (thing == magnetObj) continue;
                c.drawRoundRect(thing.rectF, 10, 10, brikStregtype);
            }
            for (Thing thing : magnetic) {
                if (thing == magnetObj) continue;
                c.drawRoundRect(thing.rectF, 10, 10, brikStregtype);
            }

            // Tegn den valgte brik til sidst, på fingerens plads
            if (magnetObj != null) {
                RectF rectF = new RectF(magnetObj.rectF);
                rectF.offsetTo(finger.x - rectF.width() / 2, finger.y - rectF.height() / 2);
                c.drawRoundRect(rectF, 10, 10, brikStregtype);
                //fixerTilBane(rectF);
                c.drawRoundRect(rectF, 12, 12, magnet);
            } else {
                // Ingen brik valgt - tegn finger
                c.drawCircle(finger.x, finger.y, 15, magnet);
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent e) {
            //System.out.println(e);
            // Spillet er beregnet til en skærm der er 480 punkter bred...
            float skærmSkala = getWidth() / 480f; // ... så skalér derefter
            float ex = e.getX() / skærmSkala;
            float ey = e.getY() / skærmSkala;
            finger.x = ex;
            finger.y = ey;

            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                for (Thing s : nonMagnetic) {
                    if (s.rectF.contains(ex, ey)) {
                        if (!(caught.contains(s))) {
                            caught.add(s);
                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            v.vibrate(400);
                        }
                        magnetObj = s;
                        break;
                    }
                }
            }
            if (e.getAction() == MotionEvent.ACTION_MOVE) {
                for (Thing s : magnetic) {
                    if (s.rectF.contains(ex, ey)) {
                        if (!(caught.contains(s))) {
                            caught.add(s);
                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            v.vibrate(400);
                        }
                        magnetObj = s;
                        break;
                    }
                }
                for (Thing c : caught) {
                    c.rectF.offsetTo(finger.x, finger.y);
                }
            }
            if (e.getAction() == MotionEvent.ACTION_UP && magnetObj != null) {
                RectF rectF = magnetObj.rectF;
                rectF.offsetTo(finger.x - rectF.width() / 2, finger.y - rectF.height() / 2);
          //      fixerTilBane(rectF);
                System.out.println("magnetObj.rectF=" + magnetObj.rectF);
       //         magnetObj = null;
      //          boolean korrekt = true;
                for (int i = 0; i < magnetic.size(); i++) {
                    Thing s1 = magnetic.get(i);
             //       float afstandTilKorrekt = Math.abs(s1.rectF.top - s2.rectF.top) + Math.abs(s1.rectF.left + 40 - s2.rectF.left);
                //    Log.d("Braetspil", s1.tekst + " til " + s2.tekst + " afstandTilKorrekt = " + afstandTilKorrekt);
           //         if (afstandTilKorrekt > 1) korrekt = false;
                }
        //        if (korrekt) MediaPlayer.create(getContext(), R.raw.dyt).start();
          //      else this.playSoundEffect(SoundEffectConstants.CLICK);

            }
            invalidate();
            return true;
        }

        private void fixerTilBane(RectF rectF) {
            int left = Math.round(rectF.left / 40) * 40 + 2;
            int top = Math.round(rectF.top / 40) * 40 + 2;
            rectF.offsetTo(left, top);

        }
    }
}
