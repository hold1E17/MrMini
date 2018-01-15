package mrmini.hold1e17.dk.mrmini;

import android.app.Activity;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by Sofie on 15/01/2018.
 */

public class CustomCamera extends Activity {
        Camera camera;
        FrameLayout myLayout;
        ShowCamera showCamera;
        ImageView im;


        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_camera);
            camera = switch_on_camera();
            im = (ImageView) findViewById(R.id.face);
            im.setRotation(90);
            myLayout = (FrameLayout)findViewById(R.id.camera_preview);
            showCamera = new ShowCamera(getApplicationContext(),camera);
            myLayout.addView(showCamera);
        }
    public Camera switch_on_camera()
    {
        Camera cam_obj = null;
        cam_obj = Camera.open();
        Camera.Parameters parameters = cam_obj.getParameters();
        if(this.getResources().getConfiguration().orientation!=Configuration.ORIENTATION_LANDSCAPE)
        {
            parameters.set("orientation","portrait");
            cam_obj.setDisplayOrientation(90);
            parameters.setRotation(90);}
        else
        {
            parameters.set("orientation","landscape");
            cam_obj.setDisplayOrientation(0);
            parameters.setRotation(0);
        }
        return cam_obj;
    }
}
