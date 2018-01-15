package mrmini.hold1e17.dk.mrmini;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.VideoView;

import mrmini.hold1e17.dk.mrmini.Logic.InfoLogic;

public class HospitalsInfo extends AppCompatActivity {
    String vidAddress;
    String webAddress;

    private int position = 0;
    public VideoView vidView;
    public WebView web;
    private MediaController mediaController;
    private String hospital;

    InfoLogic il = new InfoLogic();

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_video);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);


        // Init
        vidView = findViewById(R.id.videoView);
        web = findViewById(R.id.webView);
        web.getSettings().setJavaScriptEnabled(true);
        hospital = PreferenceManager.getDefaultSharedPreferences(this).getString("pref_key_hospital", "");

        //Set local video name (no extension, located in raw folder)
        String localVideoName = "testvideo";
        vidAddress = "android.resource://"+getPackageName()+"/raw/"+localVideoName;
        webAddress = "file:///android_asset/webview/index.html";

        // Online video and web resources
        if(il.isNetworkAvailable(this)){
            switch (hospital) {
                case "Rigshospitalet":
                    vidAddress = "https://region-hovedstaden-ekstern.23video.com/9826383/10357633/a5865685bedff3d06215fdf40b4c41e6/video_medium/mr-skanning-video.mp4";
                    webAddress = "https://www.rigshospitalet.dk/afdelinger-og-klinikker/diagnostisk/radiologisk-klinik/undersoegelse-og-behandling/Sider/mr-skanning.aspx";
                    break;
                case "Gentofte":
                    vidAddress = "https://region-hovedstaden-ekstern.23video.com/9826383/10357633/a5865685bedff3d06215fdf40b4c41e6/video_medium/mr-skanning-video.mp4";
                    webAddress = "https://www.gentoftehospital.dk/undersoegelse-og-behandling/undersoegelser/Sider/MR-skanning.aspx";
                    break;
                case "Herlev":
                    vidAddress = "https://region-hovedstaden-ekstern.23video.com/9826383/10357633/a5865685bedff3d06215fdf40b4c41e6/video_medium/mr-skanning-video.mp4";
                    webAddress = "https://www.herlevhospital.dk/afdelinger-og-klinikker/radiologisk/undersoegelse-og-behandling/Sider/MR-skanning.aspx";
                    break;
            }
        }

        //Video playback controls
        if (mediaController == null) {
            mediaController = new MediaController(HospitalsInfo.this);
            mediaController.setAnchorView(vidView);
            vidView.setMediaController(mediaController);
        }

        // Loads the vidAddress and webAddress variables into the view

        il.loadWeb(web, vidView, webAddress, vidAddress, position);

    }

/*
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
    */

}
