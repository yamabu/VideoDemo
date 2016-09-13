package moe.yamabu.videodemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import moe.yamabu.videodemo.R;
import moe.yamabu.videodemo.activity.PlayerActivity;
import moe.yamabu.videodemo.beans.MainRvList;
import moe.yamabu.videodemo.beans.VideoInfo;

public class MainRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    private MainRvList mainRvList;

    public MainRvAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_main, parent, false);
        return new MainViewHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MainViewHolder) holder).setData(mainRvList.getVideoInfos().get(position));
    }

    @Override
    public int getItemCount() {
        return mainRvList.getVideoInfos().size();
    }

    public void setData(MainRvList mainRvList) {
        this.mainRvList = mainRvList;
    }
}

class MainViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv)
    TextView textView;
    private Context context;
    private VideoInfo videoInfo;

    public MainViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    @OnClick({R.id.parent})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.parent:
                Intent intent = new Intent(context.getApplicationContext(), PlayerActivity.class);
                intent.putExtra("videoInfo", videoInfo);
                context.startActivity(intent);
                break;
        }
    }

    public void setData(VideoInfo videoInfo) {
        this.videoInfo = videoInfo;
        textView.setText(videoInfo.getTitle());
    }
}
