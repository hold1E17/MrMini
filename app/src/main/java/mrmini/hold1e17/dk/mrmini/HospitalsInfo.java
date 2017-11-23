package mrmini.hold1e17.dk.mrmini;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;
import android.widget.VideoView;

public class HospitalsInfo extends AppCompatActivity {
    String vidAddress = "http://www.html5videoplayer.net/videos/toystory.mp4";
    String webAddress = "http://www.dr.dk";

    private int position = 0;
    public VideoView vidView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_video);

        vidView = (VideoView)findViewById(R.id.videoView);
        WebView web = (WebView)findViewById(R.id.webView);

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

        // we also set an setOnPreparedListener in order to know when the video
        // file is ready for playback

        vidView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {

            public void onPrepared(MediaPlayer mediaPlayer)
            {
                // if we have a position on savedInstanceState, the video
                // playback should start from here
                vidView.seekTo(position);

                System.out.println("video is ready for playing");

                if (position == 0)
                {
                    vidView.start();
                } else
                {
                    // if we come from a resumed activity, video playback will
                    // be paused
                    vidView.pause();
                }
            }
        });
    }
}
