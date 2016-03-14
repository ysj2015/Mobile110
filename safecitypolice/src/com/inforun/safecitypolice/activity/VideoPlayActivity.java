package com.inforun.safecitypolice.activity;

import com.inforun.safecitypolice.MyApplication;
import com.inforun.safecitypolice.R;
import com.inforun.safecitypolice.finals.Constants;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;


public class VideoPlayActivity extends Activity {

	private VideoView vv;
	private MediaController mmc;
    /**
     * 
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        ((MyApplication)getApplication()).addActivityToList(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video);
        vv = (VideoView)findViewById(R.id.video);
		String video = getIntent().getStringExtra("video");
		Uri u2 = Uri.parse(Constants.BASE_URL + video);
		vv.setVideoURI(u2);
		

		mmc = new MediaController(this);
		mmc.setVisibility(View.VISIBLE);
		vv.setMediaController(new MediaController(this));
		vv.requestFocus();
		vv.start();
        
    }


}