package com.example.jinhui.learnbasequickadapter.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.jinhui.learnbasequickadapter.R;
import com.example.jinhui.learnbasequickadapter.bean.MultipleItem;


import java.util.List;

/**
 * Created by zhusr on 2017/8/22.
 */

public class OrderMultipleAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public OrderMultipleAdapter(List<MultipleItem> data) {
        super(data);

        addItemType(MultipleItem.TO, R.layout.multiple_to);
        addItemType(MultipleItem.FROM, R.layout.multiple_from);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (item.itemType) {
            case MultipleItem.FROM:
                helper.setText(R.id.item_from_name, item.message);
                break;
            case MultipleItem.TO:
                helper.setText(R.id.item_to_name, item.message);
                break;
        }
    }
}
