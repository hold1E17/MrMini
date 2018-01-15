package mrmini.hold1e17.dk.mrmini.Logic;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import android.widget.VideoView;

/**
 * Created by Simon on 15-01-2018.
 */

public class InfoLogic {

    public boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

    public void loadWeb(WebView web, VideoView vidView, String webAddress, String vidAddress, int position) {

        final VideoView vv = vidView;
        final int pos = position;

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

        // requestFocus() viser vores playback controls midlertidigt i begyndelse af playback
        // og starter video, når mediaplayeren er klar
        vidView.requestFocus();
        vidView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {

            public void onPrepared(MediaPlayer mediaPlayer)
            {
                // if we have a position on savedInstanceState, the video
                // playback should start from here
                vv.seekTo(pos);
                vv.start();
            }
        });


    }


}
