package moe.yamabu.videodemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import moe.yamabu.videodemo.ijk.AndroidMediaController;
import moe.yamabu.videodemo.ijk.IjkVideoView;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class PlayerActivity extends AppCompatActivity {
    @BindView(R.id.sb)
    SeekBar seekBar;
    @BindView(R.id.sb2)
    SeekBar seekBar2;
    @BindView(R.id.video_view)
    IjkVideoView videoView;
    @BindView(R.id.hud_view)
    TableLayout tableLayout;

    int state = 1;
    private AndroidMediaController androidMediaController;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);
        initFullScreen();

        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
       /* androidMediaController = new AndroidMediaController(this, false);
        videoView.setMediaController(androidMediaController);*/
        videoView.setHudView(tableLayout);
        videoView.setVideoURI(Uri.parse("http://mc.yamabu.moe/fd.mp4"));

        videoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                videoView.start();
            }
        });

        new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        seekBar.setProgress((int) (((float) videoView.getCurrentPosition()) /
                                videoView
                                .getDuration() * 100));
                        seekBar2.setProgress(videoView.getBufferPercentage());
                        sleep(50);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    @SuppressLint("InlinedApi")
    private void initFullScreen() {
        getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE | View
                        .SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View
                        .SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View
                        .SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @OnClick({R.id.bt, R.id.bt_sound_up, R.id.bt_sound_down, R.id.bt_bright_up, R.id
            .bt_bright_down})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:
                doAction();
                break;
            case R.id.bt_sound_up:
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, 1, AudioManager
                        .FLAG_PLAY_SOUND);
                break;
            case R.id.bt_sound_down:
                break;
            case R.id.bt_bright_up:
                break;
            case R.id.bt_bright_down:
                break;
        }
    }

    private void doAction() {
        if (state == 0) {
            state = 1;
            videoView.pause();
        } else {
            state = 0;
            videoView.start();
        }

    }
}
