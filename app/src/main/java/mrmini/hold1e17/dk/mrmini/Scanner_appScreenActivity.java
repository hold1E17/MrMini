package mrmini.hold1e17.dk.mrmini;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class Scanner_appScreenActivity extends AppCompatActivity implements View.OnTouchListener {

    private Drawable man0, man1;
    private long t0;
    private GrafikView1 grafikView;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println(event);
        System.out.println(event.getAction());

        System.out.println(event);
        Point punktet = new Point((int) event.getX(), (int) event.getY());
        grafikView.touchPoints.add(punktet);
        //System.out.println(berøringspunkter);
        grafikView.invalidate(); // forårsager kald til onDraw()
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        man0 = getResources().getDrawable(R.drawable.man0);
        man1 = getResources().getDrawable(R.drawable.man1);

        grafikView = new GrafikView1(this);
        setContentView(grafikView);

        grafikView.setOnTouchListener(this);

    }


    void tegnGrafik(Canvas c) {

        man0.setBounds(50, 50, 249 * 2, 732 * 2);
        man0.draw(c);

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    class GrafikView1 extends View {
        ArrayList<Point> touchPoints = new ArrayList<Point>();
        Paint tekstStregtype = new Paint();

        // programmatisk konstruktør
        public GrafikView1(Context a) {
            super(a);
        }

        @Override
        protected void onDraw(Canvas c) {
            super.onDraw(c);
            c.drawText("Tryk og træk over skærmen", 0, 20, tekstStregtype);
            for (Point p : touchPoints) {
                c.drawCircle(p.x, p.y, 3, tekstStregtype);
                tegnGrafik(c);
            }
        }


    }
}
// Arbejd videre med at tegne stor grøn cirkel  under finger uden at gemme punkter.
// søg efter Android canvas draw transparent circle on image
