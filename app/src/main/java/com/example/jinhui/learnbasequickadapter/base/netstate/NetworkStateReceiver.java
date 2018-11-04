package com.example.jinhui.learnbasequickadapter.base.netstate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;


import com.example.jinhui.learnbasequickadapter.base.utils.LogUtil;
import com.example.jinhui.learnbasequickadapter.base.utils.ToastUtil;

import java.util.ArrayList;


/**
 * @Title NetworkStateReceiver
 * @Package com.android.base.netstate
 * @Description 是一个检测网络状态改变的，需要配置
 *          <receiver
 *              android:name="com.android.base.netstate.NetworkStateReceiver" >
 *              <intent-filter>
 *                  <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
 *                  <action android:name="android.gzcpc.conn.CONNECTIVITY_CHANGE" />
 *              </intent-filter>
 *          </receiver>
 * <p/>
 * 需要开启权限
 *  <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
 *  <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
 *  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 *  <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
 */
public class NetworkStateReceiver extends BroadcastReceiver {
    private static Boolean networkAvailable = false;
    private static NetWorkUtil.NetType netType;
    private static ArrayList<NetChangeObserver> netChangeObserverArrayList = new ArrayList<NetChangeObserver>();
    private final static String ANDROID_NET_CHANGE_ACTION = ConnectivityManager.CONNECTIVITY_ACTION;//"android.net.conn.CONNECTIVITY_CHANGE";
    private static BroadcastReceiver receiver;

    private static BroadcastReceiver getReceiver() {
        if (receiver == null) {
            receiver = new NetworkStateReceiver();
        }
        return receiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        receiver = NetworkStateReceiver.this;
        if (intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION)) {
            // TALogger.i(NetworkStateReceiver.this, "网络状态改变.");
            if (!NetWorkUtil.isNetworkAvailable(context)) {
                // TALogger.i(NetworkStateReceiver.this, "没有网络连接.");
                networkAvailable = false;
                ToastUtil.showToast(context, "没有网络连接");

            } else {
                // TALogger.i(NetworkStateReceiver.this, "网络连接成功.");
                netType = NetWorkUtil.getAPNType(context);
                networkAvailable = true;
                LogUtil.d("network----->", "网络连接成功");
            }
            notifyObserver();
        }
    }

    /**
     * 注册网络状态广播
     */
    public static void registerNetworkStateReceiver(Context mContext) {
        IntentFilter filter = new IntentFilter();
        //filter.addAction(TA_ANDROID_NET_CHANGE_ACTION);
        filter.addAction(ANDROID_NET_CHANGE_ACTION);
        mContext.getApplicationContext()
                .registerReceiver(getReceiver(), filter);
    }

    /**
     * 检查网络状态
     */
    public static void checkNetworkState(Context mContext) {
        Intent intent = new Intent();
        //intent.setAction(TA_ANDROID_NET_CHANGE_ACTION);
        mContext.sendBroadcast(intent);
    }

    /**
     * 注销网络状态广播
     */
    public static void unRegisterNetworkStateReceiver(Context mContext) {
        if (receiver != null) {
            try {
                mContext.getApplicationContext().unregisterReceiver(receiver);
            } catch (Exception e) {
                // TODO: handle exception
                //TALogger.d("TANetworkStateReceiver", e.getMessage());
            }
        }

    }

    /**
     * 获取当前网络状态，true为网络连接成功，否则网络连接失败
     */
    public static Boolean isNetworkAvailable() {
        return networkAvailable;
    }

    public static NetWorkUtil.NetType getAPNType() {
        return netType;
    }

    private void notifyObserver() {

        for (int i = 0; i < netChangeObserverArrayList.size(); i++) {
            NetChangeObserver observer = netChangeObserverArrayList.get(i);
            if (observer != null) {
                if (isNetworkAvailable()) {
                    observer.onConnect(netType);
                } else {
                    observer.onDisConnect();
                }
            }
        }

    }

    /**
     * 注册网络连接观察者
     */
    public static void registerObserver(NetChangeObserver observer) {
        if (netChangeObserverArrayList == null) {
            netChangeObserverArrayList = new ArrayList<NetChangeObserver>();
        }
        netChangeObserverArrayList.add(observer);
    }

    /**
     * 注销网络连接观察者
     */
    public static void removeRegisterObserver(NetChangeObserver observer) {
        if (netChangeObserverArrayList != null) {
            netChangeObserverArrayList.remove(observer);
        }
    }

}