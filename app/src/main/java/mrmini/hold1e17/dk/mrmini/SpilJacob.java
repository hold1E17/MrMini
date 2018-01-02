package mrmini.hold1e17.dk.mrmini;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class SpilJacob extends AppCompatActivity {

    /**
// skal sige lyde, skal slutte når alle tingene er samlet op



    // At den er under fingeren.
    // Så den starter i midten
    // Info skærm der forklarer hvad det går ud på
    // Canvas?

    ImageView iv1, iv2, iv3, iv4, iv5, iv6;
    private int xd, yd;
    private ViewGroup rootLayout;
    private ArrayList<Brik> objektliste;
    private ArrayList<Point> ivp;
    private Brik magneten;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spil);
        rootLayout = (ViewGroup) findViewById(R.id.view_root);

        objektliste = new ArrayList<Brik>(); // array af brikker
        int[] ividl = new int[]{R.id.iv1, R.id.iv2, R.id.iv3, R.id.iv4, R.id.iv5, R.id.iv6, R.id.iv6};
        // forloop der opretter de forskellige brikker
        for (int id : ividl) {
            Brik b = new Brik();
            b.view = (ImageView) findViewById(id);
            b.pos = new Point(50, 50);
            objektliste.add(b);
        }

       // magneten er det sidste objekt, derfor -1
        magneten = objektliste.get(objektliste.size()-1); //
        objektliste.remove(objektliste.size()-1); // Fjerne det fra listen

        rootLayout.setOnTouchListener(new OnTouchListen());

        handler = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.post(opdateringRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(opdateringRunnable);
    }
// skal få brikkerne til at bevæge sig mod magneten
    private Runnable opdateringRunnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(opdateringRunnable, 10000);
            for (Brik b : objektliste) {
                if (Math.abs(b.pos.x - magneten.pos.x) + Math.abs(b.pos.y - magneten.pos.y) <20 ) {
                    b.pos.x = (9*b.pos.x+ 1*magneten.pos.x + 5)/10;
                    b.pos.y = (9*b.pos.y+ 1*magneten.pos.y + 5)/10;
                    opdaterPosPåSkærm(b);
                }
            }
        }
    };

    // Magneten flytter sig onTouch
    private final class OnTouchListen implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            final int x = (int) motionEvent.getX();
            final int y = (int) motionEvent.getY();

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    magneten.pos.x = x;
                    magneten.pos.y = y;
                    opdaterPosPåSkærm(magneten);
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    magneten.pos.x = x;
                    magneten.pos.y = y;
                    opdaterPosPåSkærm(magneten);
                    /*
                    view.setX(x);
                    view.setY(y);
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                            .getLayoutParams();
                    layoutParams.leftMargin = x - xd;
                    layoutParams.topMargin = y - yd;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    view.setLayoutParams(layoutParams);

                    break;
            }
            rootLayout.invalidate();
            return true;
        }
    }

   // Positionen er baseret på brik center
    private void opdaterPosPåSkærm(Brik valgt) {
        valgt.view.setX( valgt.pos.x - valgt.view.getWidth()/2 );
        valgt.view.setY( valgt.pos.y - valgt.view.getHeight()/2 );
    }


     * Created by j on 27-11-17.


    static class Brik {
        public ImageView view;
        public Point pos;
    }

    // metode når alle er ramt, noget med distance 0 og en counter alt efter hvor mange objekter der er
    // Når slut skal den spørge om spil igen eller slut

*/
}


