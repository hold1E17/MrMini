package mrmini.hold1e17.dk.mrmini;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class HospitalsInfo extends AppCompatActivity {
    String vidAddress = "http://www.html5videoplayer.net/videos/toystory.mp4";
    String webAddress = "https://responsivedesign.is/examples/";

    private int position = 0;
    public VideoView vidView;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_video);

        vidView = (VideoView)findViewById(R.id.videoView);
        WebView web = (WebView)findViewById(R.id.webView);

        if (mediaController == null) {
            mediaController = new MediaController(HospitalsInfo.this);

            // Set the videoView that acts as the anchor for the MediaController.
            mediaController.setAnchorView(vidView);

            // Set MediaController for VideoView
            vidView.setMediaController(mediaController);
        }

        try{
            web.loadUrl(webAddress);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        try{
            vidView.setVideoURI(Uri.parse(vidAddress));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Fejl: Video kunne ikke loades", Toast.LENGTH_SHORT).show();
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        vidView.requestFocus();

        vidView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {

            public void onPrepared(MediaPlayer mediaPlayer)
            {
                // if we have a position on savedInstanceState, the video
                // playback should start from here
                vidView.seekTo(position);

                vidView.start();
            }
        });
    }
    // When you change direction of phone, this method will be called.
    // It store the state of video (Current position)
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Store current position.
        savedInstanceState.putInt("CurrentPosition", vidView.getCurrentPosition());
        vidView.pause();

    }
    // After rotating the phone. This method is called.
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Get saved position.
        position = savedInstanceState.getInt("CurrentPosition");
        vidView.seekTo(position);
    }
}
