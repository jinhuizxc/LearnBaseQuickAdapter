package com.example.jinhui.learnbasequickadapter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.jinhui.learnbasequickadapter.adapter.MainAdapter;
import com.example.jinhui.learnbasequickadapter.base.BaseActivity;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AnimationActivity extends BaseActivity {

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;

    @BindView(R.id.tv_first_only)
    TextView tv_IsFirstOnly;

    @BindView(R.id.tv_animation_type)
    TextView tvAnimationType;

    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mainAdapter = new MainAdapter(R.layout.rv_item, getItemDatas());
        recyclerView.setAdapter(mainAdapter);
        mainAdapter.openLoadAnimation();
    }

    @OnClick({R.id.tv_first_only, R.id.tv_animation_type})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_first_only:
                showIsFirstOnlyDialog();
                break;
            case R.id.tv_animation_type:
                showAnimationTypeDialog();
                break;
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_animation;
    }

    public static List<String> getItemDatas() {
        List<String> mList = new ArrayList<>();
        for (int i = 0; i < 99; i++) {
            mList.add("展示动画效果");
        }
        return mList;
    }

    private void showAnimationTypeDialog() {
        final String[] stringItems = {"ALPHA", "SCALE", "SLIDE_BOTTOM",
                "SLIDE_LEFT", "SLIDE_RIGHT"};
        final ActionSheetDialog dialog = new ActionSheetDialog(this, stringItems, null);
//        title("choose animation type")
//                .titleTextSize_SP(14.5f);
        dialog.isTitleShow(false)
                .show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    default:
                    case 1:
                        mainAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
                        break;
                    case 2:
                        mainAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                        break;
                    case 3:
                        mainAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
                        break;
                    case 4:
                        mainAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
                        break;
                    case 5:
                        mainAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
                        break;
                }
                tvAnimationType.setText(stringItems[position]);
                mainAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }

    private void showIsFirstOnlyDialog() {
        final String[] stringItems = {"isFirstOnly(true)", "isFirstOnly(false)"};
        final ActionSheetDialog dialog = new ActionSheetDialog(this, stringItems, null);
        dialog.isTitleShow(false)
                .show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    default:
                    case 0:
                        mainAdapter.isFirstOnly(true);
                        break;
                    case 1:
                        mainAdapter.isFirstOnly(false);
                        break;
                }
                tv_IsFirstOnly.setText(stringItems[position]);
                mainAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }

}
