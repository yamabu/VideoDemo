package moe.yamabu.videodemo.presenter;

import moe.yamabu.videodemo.contract.view.MainView;
import moe.yamabu.videodemo.modelimpl.MainDataSourceImpl;

public class MainPresenter {
    private MainDataSourceImpl mainDataSource;
    private MainView mainView;

    public MainPresenter() {
        this.mainDataSource = new MainDataSourceImpl();
    }

    public MainPresenter addTaskListener(MainView mainView) {
        this.mainView = mainView;
        return this;
    }

    public void getData() {
        mainView.onGetList(mainDataSource.getList());
    }
}
