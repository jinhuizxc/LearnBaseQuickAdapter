package com.example.jinhui.learnbasequickadapter.base;

import android.app.Activity;
import android.app.Application;
import android.os.Build;

import com.example.jinhui.learnbasequickadapter.base.netstate.NetChangeObserver;
import com.example.jinhui.learnbasequickadapter.base.netstate.NetWorkUtil;
import com.example.jinhui.learnbasequickadapter.base.netstate.NetworkStateReceiver;
import com.example.jinhui.learnbasequickadapter.base.utils.LogUtil;


/**
 * Tip : 此类使用时 需要添加到添加依赖的项目的清单文件中去
 * <p>
 * Created by zhusr on 2016/10/11.
 */

public class BaseApplication extends Application {

    public Activity currentActivity;

    ThreadManager.ThreadPool threadPool = ThreadManager.getThreadPool();//线程池
    /**
     * 初始化TAG
     */
    private static String TAG = BaseApplication.class.getName();

    private NetChangeObserver netChangeObServer;

    @Override
    public void onCreate() {
        super.onCreate();

        printAppParameter();//打印APP参数

        registerNetWorkStateListener();//注册网络状态监测器

    }

    private void registerNetWorkStateListener() {
        netChangeObServer = new NetChangeObserver() {
            @Override
            public void onConnect(NetWorkUtil.NetType type) {
                super.onConnect(type);
                try {
                    BaseApplication.this.onConnect(type);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

            @Override
            public void onDisConnect() {
                super.onDisConnect();
                try {
                    BaseApplication.this.onDisConnect();
                } catch (Exception e) {
                    // TODO: handle exception
                }

            }
        };
        NetworkStateReceiver.registerObserver(netChangeObServer);
    }


    /**
     * 当前没有网络连接通知
     */
    public void onDisConnect() {
        currentActivity = AppManager.getAppManager().currentActivity();
        if (currentActivity != null) {
            if (currentActivity instanceof BaseActivity) {
                ((BaseActivity) currentActivity).onDisConnect();
            }
        }
    }

    /**
     * 网络连接连接时通知
     */
    protected void onConnect(NetWorkUtil.NetType type) {
        currentActivity = AppManager.getAppManager().currentActivity();
        if (currentActivity != null) {
            if (currentActivity instanceof BaseActivity) {
                ((BaseActivity) currentActivity).onConnect(type);
            }
        }
    }

    /**
     * 打印出一些APP的参数
     */
    private void printAppParameter() {
        LogUtil.d(TAG, "OS:" + Build.VERSION.RELEASE + "(" + Build.VERSION.SDK_INT + ")");
        //获取屏幕尺寸···
//        打印...
    }

}
