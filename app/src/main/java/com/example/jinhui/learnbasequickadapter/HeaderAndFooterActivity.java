package com.example.jinhui.learnbasequickadapter;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.jinhui.learnbasequickadapter.adapter.HeaderAndFooterAdapter;
import com.example.jinhui.learnbasequickadapter.base.BaseActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HeaderAndFooterActivity extends BaseActivity {

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;

    private HeaderAndFooterAdapter headerAndFooterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        headerAndFooterAdapter = new HeaderAndFooterAdapter(R.layout.rv_item, getItemDatas());
        recyclerView.setAdapter(headerAndFooterAdapter  );
        headerAndFooterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                addHeaderView();
            }
        });

        headerAndFooterAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                addFooterView();
                return false;
            }
        });
    }

    private void addFooterView() {
        View footView = getLayoutInflater().inflate(R.layout.rv_footer, null);
        footView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        headerAndFooterAdapter.addFooterView(footView);
        footView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "点击了尾部", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void addHeaderView() {
        View headerView = getLayoutInflater().inflate(R.layout.rv_header, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        headerAndFooterAdapter.addHeaderView(headerView);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "点击了头部", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_header_and_footer;
    }

    private static List<String> getItemDatas() {
        List<String> mList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mList.add("单击增加Header,长按增加Footer");
        }
        return mList;
    }
}
