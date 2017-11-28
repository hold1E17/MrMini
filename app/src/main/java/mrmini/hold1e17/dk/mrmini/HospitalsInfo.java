package mrmini.hold1e17.dk.mrmini;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.VideoView;


public class HospitalsInfo extends AppCompatActivity {
    String vidAddress;
    String webAddress;


    private int position = 0;
    public VideoView vidView;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_video);

        // Init
        vidView = (VideoView)findViewById(R.id.videoView);
        WebView web = (WebView)findViewById(R.id.webView);
        web.getSettings().setJavaScriptEnabled(true);

        if (mediaController == null) {
            mediaController = new MediaController(HospitalsInfo.this);

            // Set the videoView that acts as the anchor for the MediaController.
            mediaController.setAnchorView(vidView);

            // Set MediaController for VideoView
            vidView.setMediaController(mediaController);
        }


        //Set local video name (no extension, from raw folder)
        String localVideoName = "testvideo";
        vidAddress = "android.resource://"+getPackageName()+"/raw/"+localVideoName;
        webAddress = "file:///android_asset/webview/index.html";

        if(isNetworkAvailable()){
            vidAddress = "http://www.html5videoplayer.net/videos/toystory.mp4";
            webAddress = "https://www.w3schools.com/html/tryhtml_responsive_media_query3.htm";
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
    //TODO()
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
