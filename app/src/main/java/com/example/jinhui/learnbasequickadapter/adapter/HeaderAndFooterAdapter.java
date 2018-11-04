package com.example.jinhui.learnbasequickadapter.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.jinhui.learnbasequickadapter.R;


import java.util.List;

/**
 * Created by zhusr on 2017/8/18.
 */

public class HeaderAndFooterAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public HeaderAndFooterAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_item_text, item);
    }
}
