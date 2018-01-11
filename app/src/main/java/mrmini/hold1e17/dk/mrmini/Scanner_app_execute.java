package mrmini.hold1e17.dk.mrmini;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;


/**
 * Created by Sofie on 02-01-2018.
 */

public class Scanner_app_execute extends Activity {
    private ImageView img, img2;
    private ViewGroup rootLayout;
    private int xD, yD;
    private MediaPlayer scanningSound;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_scanner_app_execute);
        rootLayout = (ViewGroup) findViewById(R.id.view_root);
        img2 = (ImageView) rootLayout.findViewById(R.id.dragObj);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 150);
        img2.setLayoutParams(layoutParams);
        img2.setOnTouchListener(this);*/
        CustomView cV = new CustomView(this);
        cV.setOnTouchListener(cV);
        setContentView(cV);
        scanningSound = MediaPlayer.create(Scanner_app_execute.this, R.raw.mri_sound);

        scanningSound.setLooping(true);
        scanningSound.start();
    }

    public void onPause() {
        super.onPause();
        scanningSound.stop();
    }

    public class CustomView extends View implements View.OnTouchListener {
        private Bitmap maskPatientScanned = BitmapFactory.decodeResource(getResources(), R.drawable.man1);
        private Bitmap maskPatientDressed = BitmapFactory.decodeResource(getResources(), R.drawable.man0);
        private Bitmap maskFigure = BitmapFactory.decodeResource(getResources(), R.drawable.rectangle);
        private final Paint imagePaint;
        private final Paint maskPaint;
        private final Paint overlayPaint;
        private int maskX = 0;
        private int maskY = 0;
        private Canvas canvas2;

        public CustomView(final Context context) {
            super(context);
            System.out.println("Kald til CustomView");
            maskPaint = new Paint();
            maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

            overlayPaint = new Paint();
            overlayPaint.setXfermode((new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN)));

            imagePaint = new Paint();
            imagePaint.setXfermode((new PorterDuffXfermode(PorterDuff.Mode.DST_OVER)));

        }

        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            this.canvas2 = canvas;
            canvas.save();
            Double dWidthTemp = Double.valueOf(maskPatientDressed.getWidth());
            Double dHeightTemp = Double.valueOf(maskPatientDressed.getHeight());
            int dWidth = (int) (dWidthTemp / 1.3);
            int dHeight = (int) (dHeightTemp / 1.3);

            canvas.drawBitmap(maskPatientDressed, new Rect(0, 0, maskPatientDressed.getWidth(), maskPatientDressed.getHeight())
                    , new Rect(0, 0, dWidth, dHeight), overlayPaint);
            // canvas.drawBitmap(maskPatientDressed, 0, 0, overlayPaint);
            canvas.drawBitmap(maskFigure, maskX, maskY, maskPaint);
            canvas.drawBitmap(maskPatientScanned, new Rect(0, 0, (maskPatientScanned.getWidth()), maskPatientScanned.getHeight()),
                    new Rect(0, 0, dWidth, dHeight), imagePaint);
            System.out.println("WIDTH = " + canvas.getWidth());
            System.out.println("HEIGHT = " + canvas.getHeight());
            // canvas.drawBitmap(maskPatientScanned, maskX, maskY, imagePaint);
            canvas.restore();


        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            final int musX = (int) motionEvent.getRawX();
            final int musY = (int) motionEvent.getRawY();
            System.out.println("onTouch");
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    System.out.println("Entering case 1");
                    break;
                case MotionEvent.ACTION_UP:
                    System.out.println("Entering case 2");
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    System.out.println("Entering case 3");
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    System.out.println("Entering case 4");
                    break;
                case MotionEvent.ACTION_MOVE:
                    maskX = musX;
                    maskY = musY - 100;
                    System.out.println(motionEvent.getRawX());
                    if (motionEvent.getRawX() < 0) {
                        maskX = 0;
                    } else if (motionEvent.getRawY() < 0) {
                        maskY = 0;
                    } else if ((motionEvent.getRawX() + maskFigure.getWidth() > canvas2.getWidth())) {
                        maskX = canvas2.getWidth() - maskFigure.getWidth();
                    } else if ((motionEvent.getRawY() + maskFigure.getHeight() > canvas2.getHeight())) {
                        maskY = canvas2.getHeight() - maskFigure.getHeight();
                    }
                    this.invalidate();
                    System.out.println("Entering case 5");
                    break;
            }
            return true;
        }
    }



/*
    private final class ChoiceTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            final int xA = (int) motionEvent.getRawX();
            final int yA = (int) motionEvent.getRawY();

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    xD = xA - lParams.leftMargin;
                    yD = yA - lParams.topMargin;
                    System.out.println("hello");
                    break;
                case MotionEvent.ACTION_UP:
                    System.out.println("there");
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    System.out.println("nice");
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    System.out.println("to");
                    break;
                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.leftMargin = xA - xD;
                    layoutParams.topMargin = yA - yD;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    view.setLayoutParams(layoutParams);
                    //view.performClick();
                    System.out.println("meet");
                    break;

            }

            rootLayout.invalidate();
            return true;
        }
    }*/
        /*
        rootLayout = (ViewGroup) findViewById(R.id.view_root);
        img2 = (ImageView) rootLayout.findViewById(R.id.dragObj);
        img = (ImageView) findViewById(R.id.patient);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 150);
        img2.setLayoutParams(layoutParams);
        img2.setOnTouchListener(new ChoiceTouchListener());

       if (img != null) {
            img.setOnClickListener(this);
           //img.setOnTouchListener(this);

        }*/


/*
    @Override
    public void onClick(View view) {
        ImageView patientBody = (ImageView) findViewById(R.id.patientBody);

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean handled = false;
        final int action = motionEvent.getAction();
        final int x = (int) motionEvent.getX();
        final int y = (int) motionEvent.getY();
        int nextImage = -1;

        ImageView back = (ImageView) view.findViewById(R.id.patient);
        if (back == null) return false;

        Integer tagNum = (Integer) back.getTag();
        int currentResource = (tagNum == null) ? R.drawable.man0 : tagNum.intValue();

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:

                if (currentResource == R.drawable.man0) {
                    nextImage = R.drawable.man1;
                    handled = true;
                } else handled = true;
                break;
            case MotionEvent.ACTION_UP:
                int touchColor = getHotspotColor(R.id.patientBody, x, y);

                colorTool ct = new colorTool();
                int tolerance = 25;
                nextImage = R.drawable.man0;

                if (ct.closeMatch(Color.RED, touchColor, tolerance)) nextImage = R.drawable.magnet1;
                else if (ct.closeMatch(Color.BLUE, touchColor, tolerance))
                    nextImage = R.drawable.magnet2;
                else if (ct.closeMatch(Color.YELLOW, touchColor, tolerance))
                    nextImage = R.drawable.magnet3;
                else if (ct.closeMatch(Color.WHITE, touchColor, tolerance))
                    nextImage = R.drawable.magnet4;

                if (currentResource == nextImage) {
                    nextImage = R.drawable.man0;
                }
                handled = true;
                break;
            default:
                handled = false;
        }

        if (handled) {
            if (nextImage > 0) {
                back.setImageResource(nextImage);
                back.setTag(nextImage);
            }
        }

        return handled;
    }*/

  /*   public boolean onTouch(View view, MotionEvent motionEvent) {
         boolean handled = false;
         final int action = motionEvent.getAction();
         final int x = (int) motionEvent.getX();
         final int y = (int) motionEvent.getY();
         int nextImage = -1;

         ImageView back = (ImageView) view.findViewById(R.id.patient);
         if (back == null) return false;

         Integer tagNum = (Integer) back.getTag();
         int currentResource = (tagNum == null) ? R.drawable.man0 : tagNum.intValue();

         switch (action & MotionEvent.ACTION_MASK) {
             case MotionEvent.ACTION_DOWN:

                 if (currentResource == R.drawable.man0) {
                     nextImage = R.drawable.man1;
                     handled = true;
                 } else handled = true;
                 System.out.println("Don't");
                 break;
             case MotionEvent.ACTION_UP:
                 int touchColor = getHotspotColor(R.id.patientBody, x, y);

                 colorTool ct = new colorTool();
                 int tolerance = 25;
                 nextImage = R.drawable.man0;

                 if (ct.closeMatch(Color.RED, touchColor, tolerance)) nextImage = R.drawable.magnet1;
                 else if (ct.closeMatch(Color.BLUE, touchColor, tolerance))
                     nextImage = R.drawable.magnet2;
                 else if (ct.closeMatch(Color.YELLOW, touchColor, tolerance))
                     nextImage = R.drawable.magnet3;
                 else if (ct.closeMatch(Color.WHITE, touchColor, tolerance))
                     nextImage = R.drawable.magnet4;

                 if (currentResource == nextImage) {
                     nextImage = R.drawable.man0;
                 }
                 handled = true;
                 System.out.println("Touch");
                 break;
             default:
                 handled = false;
                 System.out.println("Me");
         }

         if (handled) {
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
         if (v != null) {
             Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
             v.startAnimation(anim);
         }
     }

     public void onClickWglxy(View v) {
         Intent viewIntent = new Intent("android.intent.action.VIEW");
         startActivity(viewIntent);
     }

     public int getHotspotColor(int hpId, int x, int y) {
         ImageView change = (ImageView) findViewById(hpId);
         if (change == null) {
             Log.d("ImageAreasAcitivity", "Hot Spot image not found");
             return 0;
         } else {
             change.setDrawingCacheEnabled(true);
             Bitmap hotSpots = Bitmap.createBitmap(change.getDrawingCache());
             if (hotSpots == null) {
                 Log.d("ImageAreasActivity", "Hot spot bitmap was not created");
                 return 0;
             } else {
                 change.setDrawingCacheEnabled(false);
                 return hotSpots.getPixel(x, y);
             }
         }

     }

    private final class ChoiceTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            final int xA = (int) motionEvent.getRawX();
            final int yA = (int) motionEvent.getRawY();

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    xD = xA - lParams.leftMargin;
                    yD = yA - lParams.topMargin;
                    System.out.println("hello");
                    break;
                case MotionEvent.ACTION_UP:
                    System.out.println("there");
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    System.out.println("nice");
                    break;s
                case MotionEvent.ACTION_POINTER_UP:
                    System.out.println("to");
                    break;
                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.leftMargin = xA - xD;
                    layoutParams.topMargin = yA - yD;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    view.setLayoutParams(layoutParams);
                    //view.performClick();
                    System.out.println("meet");
                    break;

            }

            rootLayout.invalidate();
            return true;
        }


    }*/
}
