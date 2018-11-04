package com.example.jinhui.learnbasequickadapter.base;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


import com.example.jinhui.learnbasequickadapter.base.netstate.NetWorkUtil;
import com.example.jinhui.learnbasequickadapter.base.netstate.NetworkStateReceiver;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;


/**
 * Tip ：使用AutoLayoutActivity（适配开源库）时 需要在被使用的项目中添加设计草稿的尺寸
 * <p>
 * <meta-data android:name="design_width" android:value="768" />
 * <meta-data android:name="design_height" android:value="1280" />
 * <p>
 * Created by zsr on 2016/4/26.
 */
public abstract class BaseActivity extends AutoLayoutActivity {

    InputMethodManager inputMethodManager;//软键盘
    protected BaseApplication baseApplication;
    private boolean translucentStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(provideContentViewId());
        ButterKnife.bind(this);

        AppManager.getAppManager().addActivity(this);

        baseApplication = (BaseApplication) this.getApplication();

        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        NetworkStateReceiver.registerNetworkStateReceiver(this);//注册网络监听

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {//获取焦点
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);//隐藏软键盘
            }
        }
        return super.onTouchEvent(event);
    }

    //配置当前布局文件
    protected abstract int provideContentViewId();

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkStateReceiver.unRegisterNetworkStateReceiver(this);
        AppManager.getAppManager().finishActivity(this);
    }

    public void onConnect(NetWorkUtil.NetType type) {
    }


    public void onDisConnect() {
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
