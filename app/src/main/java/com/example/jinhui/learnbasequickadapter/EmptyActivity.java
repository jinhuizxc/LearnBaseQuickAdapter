package com.example.jinhui.learnbasequickadapter;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import com.example.jinhui.learnbasequickadapter.adapter.MainAdapter;
import com.example.jinhui.learnbasequickadapter.base.BaseActivity;

import butterknife.BindView;

public class EmptyActivity extends BaseActivity {

    @BindView(R.id.recy)
    RecyclerView recyclerView;

    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        addEmptyView();
    }

    private void addEmptyView() {
        View view = getLayoutInflater().inflate(R.layout.rv_empty, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mainAdapter.setEmptyView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "点击了EmptyView", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainAdapter = new MainAdapter(R.layout.rv_item, null);
        recyclerView.setAdapter(mainAdapter);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_empty;
    }
}
