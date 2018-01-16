package mrmini.hold1e17.dk.mrmini;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import mrmini.hold1e17.dk.mrmini.Logic.GameLogic;

public class Spil extends AppCompatActivity {

    GameLogic gl = new GameLogic();
    float width;
    float height;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        super.onCreate(savedInstanceState);
        setContentView(new MagnetView(this));
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    class MagnetView extends View {

        public MagnetView(Context context) {
            super(context);

            gl.createObjects();
            gl.mapObjects(getResources());

        }

        @Override
        protected void onDraw(Canvas c) {

            width = getWidth() / 480f;
            height = getHeight() / 480f;

            System.out.println(width);
            System.out.println(height);

            c.scale(width, height);

            gl.createNonMagnetic(c);
            gl.createMagnetic(c);
            gl.createMagnet(c);

        }

        @Override
        public boolean onTouchEvent(MotionEvent e) {
            float ex = e.getX() / width;
            float ey = e.getY() / height;

            if (e.getAction() == MotionEvent.ACTION_DOWN) {

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
                builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog);
            } else {
                builder = new AlertDialog.Builder(getContext());
            }
            builder.setTitle("Spil igen")
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
