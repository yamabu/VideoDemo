package moe.yamabu.videodemo.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import moe.yamabu.videodemo.R;
import moe.yamabu.videodemo.beans.VideoInfo;
import moe.yamabu.videodemo.ijk.AndroidMediaController;
import moe.yamabu.videodemo.ijk.IjkVideoView;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class PlayerActivity extends AppCompatActivity {
    @BindView(R.id.video_view)
    IjkVideoView videoView;
    @BindView(R.id.hud_view)
    TableLayout tableLayout;

    float currentBrightness = -1;
    private AudioManager audioManager;
    private WindowManager.LayoutParams lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);

        VideoInfo videoInfo = getIntent().getParcelableExtra("videoInfo");

        initFullScreen();

        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        IjkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_ERROR);
        AndroidMediaController androidMediaController = new AndroidMediaController(this, true);
        videoView.setMediaController(androidMediaController);
        videoView.setHudView(tableLayout);
        videoView.setVideoURI(Uri.parse(videoInfo.getUri()));

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        lp = getWindow().getAttributes();
        try {
            currentBrightness = Settings.System.getInt(getContentResolver(), Settings.System
                    .SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.stopPlayback();
        videoView.stopBackgroundPlay();
        videoView.release(true);
        IjkMediaPlayer.native_profileEnd();
    }

    @SuppressLint("InlinedApi")
    private void initFullScreen() {
        videoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @OnClick({R.id.bt_sound_up, R.id.bt_sound_down, R.id.bt_bright_up, R.id.bt_bright_down})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_sound_up:
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager
                        .ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
                break;
            case R.id.bt_sound_down:
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager
                        .ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
                break;
            case R.id.bt_bright_up:
                //4-255 Nexus6P
                System.out.println(currentBrightness);
                if (currentBrightness != -1 && currentBrightness < 240) {
                    currentBrightness = currentBrightness + 10;
                    lp.screenBrightness = currentBrightness / 255;
                    getWindow().setAttributes(lp);
                }
                break;
            case R.id.bt_bright_down:
                if (currentBrightness != -1 && currentBrightness > 20) {
                    currentBrightness = currentBrightness - 10;
                    lp.screenBrightness = currentBrightness / 255;
                    getWindow().setAttributes(lp);
                }
                break;
        }
    }

}
