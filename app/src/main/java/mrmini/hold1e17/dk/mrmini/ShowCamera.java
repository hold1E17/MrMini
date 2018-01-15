package mrmini.hold1e17.dk.mrmini;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by Sofie on 15/01/2018.
 */

public class ShowCamera extends SurfaceView implements SurfaceHolder.Callback{
    private Camera camera;
    private SurfaceHolder holdMe;

    public ShowCamera(Context context, Camera mycam){
        super(context);
        camera = mycam;
        holdMe = getHolder();
        holdMe.addCallback(this);
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch(IOException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();

    }
}
