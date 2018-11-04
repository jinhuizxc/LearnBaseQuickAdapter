package com.example.jinhui.learnbasequickadapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.jinhui.learnbasequickadapter.adapter.MainAdapter;
import com.example.jinhui.learnbasequickadapter.base.BaseActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *学习使用BaseQuickAdapter,玩转RecyclerView通用适配器
 *
 * 框架传送门：CymChad/BaseRecyclerViewAdapterHelper
 *
 * 本项目只作为自己测试备份使用
 *
 * 学习、实现功能
 * RecyclerView 头部、尾部添加Header
 * RecyclerView 动画效果
 * 上拉加载、下拉刷新
 * 空布局展示
 * 多布局-聊天
 * 字母导航
 * 单选
 *
 * 遇到的问题:
 * NavigationActivity 中点击上面字母隐藏城市名字，但是下个字母没有与上方字母收缩接合，是个问题。
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<String> mData = new ArrayList<>();

    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        initView();
    }

    private void initData() {
        mData.add("头部Header + 尾部Footer");
        mData.add("动画展示");
        mData.add("上拉加载下拉刷新");
        mData.add("空布局");
        mData.add("多布局-聊天");
        mData.add("字母导航");
        mData.add("单选");
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainAdapter = new MainAdapter(R.layout.rv_item, mData);
        recyclerView.setAdapter(mainAdapter);
        mainAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        intent.setClass(MainActivity.this, HeaderAndFooterActivity.class);
                        break;
                    case 1:
                        intent.setClass(MainActivity.this, AnimationActivity.class);
                        break;
                    case 2:
                        intent.setClass(MainActivity.this, PullRefreshActivity.class);
                        break;
                    case 3:
                        intent.setClass(MainActivity.this, EmptyActivity.class);
                        break;
                    case 4:
                        intent.setClass(MainActivity.this, MultiActivity.class);
                        break;
                    case 5:
                        intent.setClass(MainActivity.this, NavigationActivity.class);
                        break;
                    case 6:
                        intent.setClass(MainActivity.this, SelectActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }
}
