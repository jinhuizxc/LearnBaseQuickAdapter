package com.example.jinhui.learnbasequickadapter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;


import com.example.jinhui.learnbasequickadapter.adapter.OrderMultipleAdapter;
import com.example.jinhui.learnbasequickadapter.base.BaseActivity;
import com.example.jinhui.learnbasequickadapter.bean.MultipleItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MultiActivity extends BaseActivity {

    @BindView(R.id.et_chat)
    EditText etChat;

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;

    private OrderMultipleAdapter orderMultipleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        orderMultipleAdapter = new OrderMultipleAdapter(getMultipleItemData());
        recyclerView.setAdapter(orderMultipleAdapter);
        orderMultipleAdapter.openLoadAnimation();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_multi;
    }

    private static List<MultipleItem> getMultipleItemData() {
        List<MultipleItem> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            MultipleItem item = new MultipleItem();
            if (i % 2 == 0) {
                item.message = "你好呀~";
                item.itemType = MultipleItem.TO;
            } else {
                item.message = "叔叔，我们不约！";
                item.itemType = MultipleItem.FROM;
            }
            list.add(item);
        }
        return list;
    }

    @OnClick(R.id.btn_send)
    public void send() {
        MultipleItem item = new MultipleItem();
        item.itemType = MultipleItem.TO;
        item.message = etChat.getText().toString().trim();
        orderMultipleAdapter.addData(item);

        recyclerView.smoothScrollToPosition(orderMultipleAdapter.getItemCount());//只i的那个recyclerview滑动到最后一个Item的位置
    }
}
