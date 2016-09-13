package moe.yamabu.videodemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import moe.yamabu.videodemo.R;
import moe.yamabu.videodemo.adapter.MainRvAdapter;
import moe.yamabu.videodemo.beans.MainRvList;
import moe.yamabu.videodemo.contract.view.MainView;
import moe.yamabu.videodemo.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainView {
    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MainPresenter mainPresenter = new MainPresenter().addTaskListener(this);
        mainPresenter.getData();
    }

    @Override
    public void onGetList(MainRvList mainRvList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MainRvAdapter mainRvAdapter = new MainRvAdapter(this);
        mainRvAdapter.setData(mainRvList);
        recyclerView.setAdapter(mainRvAdapter);
    }
}
