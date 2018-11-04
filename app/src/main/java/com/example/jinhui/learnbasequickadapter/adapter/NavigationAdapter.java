package com.example.jinhui.learnbasequickadapter.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.jinhui.learnbasequickadapter.R;
import com.example.jinhui.learnbasequickadapter.bean.City;


import java.util.List;

/**
 * Created by zhusr on 2017/8/22.
 */

public class NavigationAdapter extends BaseQuickAdapter<City, BaseViewHolder> {

    public static final int HEADER_FIRST_VIEW = 0x00000111;
    public static final int HEADER_VISIBLE_VIEW = 0x00000222;
    public static final int HEADER_NONE_VIEW = 0x00000333;

    private List<City> mDatas;

    public NavigationAdapter(@LayoutRes int layoutResId, @Nullable List<City> data) {
        super(layoutResId, data);
        this.mDatas = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, City item) {
        if (helper.getAdapterPosition() == 0) {
            helper.setVisible(R.id.tv_letter_header, true);
            helper.itemView.setTag(HEADER_FIRST_VIEW);
        } else {
            if (item.firstPinYin.equals(mDatas.get(helper.getAdapterPosition() - 1).firstPinYin)) {
                helper.setVisible(R.id.tv_letter_header, false);
                helper.itemView.setTag(HEADER_NONE_VIEW);
            } else {
                helper.setVisible(R.id.tv_letter_header, true);
                helper.itemView.setTag(HEADER_VISIBLE_VIEW);
            }
        }

        if (item.hideEnable) {
            helper.setVisible(R.id.tv_city, false);
        } else {
            helper.setVisible(R.id.tv_city, true);
        }

        helper.setText(R.id.tv_letter_header, item.firstPinYin);
        helper.setText(R.id.tv_city, item.cityName);

        helper.addOnClickListener(R.id.tv_city);

        helper.addOnClickListener(R.id.tv_letter_header);
    }
}
