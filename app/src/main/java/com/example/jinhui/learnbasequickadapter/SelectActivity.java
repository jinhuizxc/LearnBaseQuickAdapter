package com.example.jinhui.learnbasequickadapter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.jinhui.learnbasequickadapter.base.BaseActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SelectActivity extends BaseActivity {

    @BindView(R.id.recy)
    RecyclerView recyclerView;

    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;

    private SparseBooleanArray sparseBooleanArray;//存储boolean值

    private int mLastCheckedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.rv_item_cb, getDatas()) {
            @Override
            protected void convert(final BaseViewHolder helper, String item) {
                if (sparseBooleanArray.get(helper.getAdapterPosition())) {
                    helper.setBackgroundColor(R.id.tv, Color.parseColor("#FF4081"));
                } else {
                    helper.setBackgroundColor(R.id.tv, Color.parseColor("#FFFFFF"));
                }

                helper.setOnClickListener(R.id.tv, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setItemChecked(helper.getAdapterPosition());
                    }
                });
            }
        };
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * @param position
     */
    public void setItemChecked(int position) {
        if (mLastCheckedPosition == position)
            return;

        sparseBooleanArray.put(position, true);

        if (mLastCheckedPosition > -1) {
            sparseBooleanArray.put(mLastCheckedPosition, false);
            mAdapter.notifyItemChanged(mLastCheckedPosition);
        }

        mAdapter.notifyDataSetChanged();

        mLastCheckedPosition = position;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_select;
    }

    public List<String> getDatas() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas.add("" + i);
        }
        sparseBooleanArray = new SparseBooleanArray(datas.size());
        return datas;
    }
}
