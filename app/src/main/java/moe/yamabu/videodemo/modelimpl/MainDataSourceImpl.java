package moe.yamabu.videodemo.modelimpl;

import moe.yamabu.videodemo.beans.MainRvList;
import moe.yamabu.videodemo.beans.VideoInfo;
import moe.yamabu.videodemo.contract.model.MainDataSource;

public class MainDataSourceImpl implements MainDataSource {
    @Override
    public MainRvList getList() {
        MainRvList mainRvList = new MainRvList();

        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setTitle("CCTV1");
        videoInfo.setUri("http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8");
        mainRvList.getVideoInfos().add(videoInfo);

        videoInfo = new VideoInfo();
        videoInfo.setTitle("Flower Dance");
        videoInfo.setUri("http://mc.yamabu.moe/fd.mp4");
        mainRvList.getVideoInfos().add(videoInfo);
        return mainRvList;
    }
}
