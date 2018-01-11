package mrmini.hold1e17.dk.mrmini;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.renderscript.Sampler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

// Gøres hurtigere
// Flere forskellige magnetisk/ikke magnetisk
// Starter random steder
// Magnet i stedet for ring
// Ikke grå kasser inden i ring


// Done = lyd og billeder

public class Spil extends AppCompatActivity {

    int magCount = 0;
    int nonMagCount = 0;
    int[] magDrawables = new int[]{R.drawable.magnetic1, R.drawable.magnetic2, R.drawable.magnetic3, R.drawable.magnetic4, R.drawable.magnetic5};
    int[] nonMagDrawables = new int[]{R.drawable.nonmagnetic1, R.drawable.nonmagnetic2, R.drawable.nonmagnetic3, R.drawable.nonmagnetic4, R.drawable.nonmagnetic5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MagnetView(this));
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    class Thing {
        RectF rectF = new RectF();

        String str;

        Thing(String string, int x, int y) {
            str = string;
            rectF = new RectF(x + -40, y + -40, x + 20, y + 20);
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
        Thing mag = new Thing("x", 0, 0);
        Paint brikStregtype = new Paint();
        Paint magnet;

        public MagnetView(Context context) {
            super(context);

            brikStregtype.setColor(Color.GRAY);
            brikStregtype.setStyle(Paint.Style.FILL);
            brikStregtype.setAntiAlias(true);
            brikStregtype.setStrokeWidth(2);
            magnet = new Paint(brikStregtype);
            magnet.setStyle(Paint.Style.STROKE);
            magnet.setColor(Color.RED);

            Random rand = new Random();

            nonMagnetic.add(new Thing("6", rand.nextInt(450), rand.nextInt(550)));
            nonMagnetic.add(new Thing("+", rand.nextInt(450), rand.nextInt(550)));
            nonMagnetic.add(new Thing("+", rand.nextInt(450), rand.nextInt(550)));
            nonMagnetic.add(new Thing("+", rand.nextInt(450), rand.nextInt(550)));
            nonMagnetic.add(new Thing("+", rand.nextInt(450), rand.nextInt(550)));
            magnetic.add(new Thing("1", rand.nextInt(450), rand.nextInt(550)));
            magnetic.add(new Thing("2", rand.nextInt(450), rand.nextInt(550)));
            magnetic.add(new Thing("3", rand.nextInt(450), rand.nextInt(550)));
            magnetic.add(new Thing("3", rand.nextInt(450), rand.nextInt(550)));
            magnetic.add(new Thing("3", rand.nextInt(450), rand.nextInt(550)));

        }


        @Override
        protected void onDraw(Canvas c) {

            float skærmSkala = getWidth() / 480f;
            c.scale(skærmSkala, skærmSkala);

            for (int i = 0; i < nonMagnetic.size(); i++) {
                if (nonMagnetic.get(i) == magnetObj) continue;
                Resources res = getResources();
                Bitmap bitmap = BitmapFactory.decodeResource(res, nonMagDrawables[i]);
               c.drawBitmap(bitmap, null, nonMagnetic.get(i).rectF, brikStregtype);
            }
            for (int i = 0; i < magnetic.size(); i++) {
                if (magnetic.get(i) == magnetObj) continue;
                Resources res = getResources();
                Bitmap bitmap = BitmapFactory.decodeResource(res, magDrawables[i]);
                BitmapFactory.decodeResource(res,magDrawables[i]);
                c.drawBitmap(bitmap, null, magnetic.get(i).rectF, brikStregtype) ;
            }

            if (magnetObj != null) {
                RectF rectF = new RectF(magnetObj.rectF);
                rectF.offsetTo(finger.x - rectF.width() / 2, finger.y - rectF.height() / 2);
                Resources res = getResources();
                Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.magnet);
                BitmapFactory.decodeResource(res,R.drawable.magnet);
                c.drawBitmap(bitmap, null, rectF, magnet) ;
            } else {
                RectF rectF = new RectF(mag.rectF);
                Resources res = getResources();
                Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.magnet);
                BitmapFactory.decodeResource(res,R.drawable.magnet);
                c.drawBitmap(bitmap, null, rectF, magnet) ;
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent e) {
            float screen = getWidth() / 480f;
            float ex = e.getX() / screen;
            float ey = e.getY() / screen;
            finger.x = ex;
            finger.y = ey;

            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                for (Thing s : magnetic) {
                    if (s.rectF.contains(ex, ey)) {
                        if (!(caught.contains(s))) {
                            caught.add(s);

                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            v.vibrate(400);
                            // Spiller kling lyd
                            MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.chime_bell_ding);
                            mediaPlayer.start();


                            if (caught.size() == magnetic.size()) {

                                endGame();

                            }
                        }
                        magnetObj = s;
                        break;

                    } else {
                        mag.rectF.offsetTo(finger.x, finger.y);
                    }

                }
                for (int i = 0; i < caught.size(); i++) {
                    caught.get(i).rectF.offsetTo(finger.x, finger.y);
                }
            }
            if (e.getAction() == MotionEvent.ACTION_MOVE) {
                for (Thing s : magnetic) {
                    if (s.rectF.contains(ex, ey)) {
                        if (!(caught.contains(s))) {
                            caught.add(s);

                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            v.vibrate(400);
                           // Spiller kling lyd
                            MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.chime_bell_ding);
                            mediaPlayer.start();


                            if (caught.size() == magnetic.size()) {

                                endGame();

                            }
                        }
                        magnetObj = s;
                        break;
                    } else {
                        mag.rectF.offsetTo(finger.x-10, finger.y-70);
                    }
                }
                for (int i = 0; i < caught.size(); i++) {
                    caught.get(i).rectF.offsetTo(finger.x, finger.y);
                }
            }
            if (e.getAction() == MotionEvent.ACTION_UP && magnetObj != null) {
                RectF rectF = magnetObj.rectF;
                rectF.offsetTo(finger.x - rectF.width() / 2, finger.y - rectF.height() / 2);

            }
            invalidate();
            return true;

        }

        private void endGame() {

            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(getContext());
            }
            builder.setTitle("Play again")
                    .setMessage(R.string.play_again)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            startActivity(getIntent());
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }
    }


}
