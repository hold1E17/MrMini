package mrmini.hold1e17.dk.mrmini;

import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import static android.view.View.X;
import static android.view.View.Y;

public class Spil extends AppCompatActivity {

// Skal samle ting op/eller de skal forsvinde (Sætte sig på magneten eller forsvinde),
// skal sige lyde, skal slutte når alle tingene er samlet op



    // At den er under fingeren.
    // Canvas?

    ImageView iv1, iv2, iv3, iv4, iv5, iv6;
    private int xd, yd;
    private ViewGroup rootLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spil);
        rootLayout = (ViewGroup) findViewById(R.id.view_root);

        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        iv4 = (ImageView) findViewById(R.id.iv4);
        iv5 = (ImageView) findViewById(R.id.iv5);
        iv6 = (ImageView) findViewById(R.id.iv6);


        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150,150);
        iv6.setLayoutParams(layoutParams);
        iv6.setOnTouchListener(new OnTouchListen());
    }


    private final class OnTouchListen implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            final int x = (int) motionEvent.getRawX();
            final int y = (int) motionEvent.getRawY();

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    xd = x - lParams.leftMargin;
                    yd= y - lParams.topMargin;
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    view.setX(x);
                    view.setY(y);
                    /*
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                            .getLayoutParams();
                    layoutParams.leftMargin = x - xd;
                    layoutParams.topMargin = y - yd;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    view.setLayoutParams(layoutParams);
                    */
                    break;
            }
            rootLayout.invalidate();
            return true;
        }


        }
    }


