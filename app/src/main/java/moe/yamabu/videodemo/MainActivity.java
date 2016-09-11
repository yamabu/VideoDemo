package moe.yamabu.videodemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        MainRvList mainRvList = new MainRvList();

        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setTitle("CCTV1");
        videoInfo.setUri("http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8");
        mainRvList.getVideoInfos().add(videoInfo);

        videoInfo = new VideoInfo();
        videoInfo.setTitle("Flower Dance");
        videoInfo.setUri("http://mc.yamabu.moe/fd.mp4");
        mainRvList.getVideoInfos().add(videoInfo);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MainRvAdapter mainRvAdapter = new MainRvAdapter(this);
        mainRvAdapter.setData(mainRvList);
        recyclerView.setAdapter(mainRvAdapter);
    }
}
