package com.example.jinhui.learnbasequickadapter.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by zhusr on 2017/8/22.
 */

public class MultipleItem implements MultiItemEntity {

    public static final int FROM = 1;

    public static final int TO = 2;

    public String message;

    public int itemType;

    @Override
    public int getItemType() {
        return itemType;
    }
}
