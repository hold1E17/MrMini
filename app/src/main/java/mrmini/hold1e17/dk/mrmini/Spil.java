package mrmini.hold1e17.dk.mrmini;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import mrmini.hold1e17.dk.mrmini.Logic.GameLogic;

public class Spil extends AppCompatActivity {

    GameLogic gl = new GameLogic();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MagnetView(this));
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    class MagnetView extends View {

        public MagnetView(Context context) {
            super(context);

            gl.createObjects();

        }

        @Override
        protected void onDraw(Canvas c) {

            float screen = getWidth() / 480f;
            c.scale(screen, screen);

            gl.createNonMagnetic(getResources(), c);
            gl.createMagnetic(getResources(), c);
            gl.createMagnet(getResources(), c);

        }

        @Override
        public boolean onTouchEvent(MotionEvent e) {
            float screen = getWidth() / 480f;
            float ex = e.getX() / screen;
            float ey = e.getY() / screen;

            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                //      gl.catchMagnetic(getContext(), ex, ey);
                if (!gl.catchMagnetic(getContext(), ex, ey)) {
                    endGame();
                }
            }
            if (e.getAction() == MotionEvent.ACTION_MOVE) {
                if (!gl.catchMagnetic(getContext(), ex, ey)) {
                    endGame();
                }
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
