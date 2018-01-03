package mrmini.hold1e17.dk.mrmini;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Sofie on 02-01-2018.
 */

public class Scanner_app_execute extends Activity {
private ImageView lmg;
  /*  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_app_execute);
        img = (ImageView) findViewById(R.id.imageView);
    }*/
    private ImageView img, patient,patientBody;
    private ViewStub visibleStub;
    private ViewGroup rootLayout;
    private int xD;
    private int yD;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_app_execute);
        rootLayout = (ViewGroup) findViewById(R.id.view_root);
        img = (ImageView) rootLayout.findViewById(R.id.imageView);

        patient = (ImageView) findViewById(R.id.patient);
        patient.setImageDrawable(getResources().getDrawable(R.drawable.man0));
        patientBody = (ImageView) findViewById(R.id.patientBody);
        patientBody.setImageDrawable(getResources().getDrawable(R.drawable.man1));

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150,150);
        img.setLayoutParams(layoutParams);
        img.setOnTouchListener(new TouchListener());
        patient.setVisibility(View.INVISIBLE);

    }

    private final class TouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            final int x = (int) motionEvent.getRawX();
            final int y = (int) motionEvent.getRawY();

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    xD = x - lParams.leftMargin;
                    yD = y - lParams.topMargin;
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.leftMargin = x - xD;
                    layoutParams.topMargin = y - yD;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    view.setLayoutParams(layoutParams);
                    break;
            }
            rootLayout.invalidate();
            return true;

        }
    }
}
