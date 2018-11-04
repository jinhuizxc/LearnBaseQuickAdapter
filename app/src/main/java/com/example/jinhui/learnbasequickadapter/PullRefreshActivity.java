package com.example.jinhui.learnbasequickadapter;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.example.jinhui.learnbasequickadapter.adapter.MainAdapter;
import com.example.jinhui.learnbasequickadapter.base.BaseActivity;
import com.example.jinhui.learnbasequickadapter.base.CustomLoadMoreView;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

public class PullRefreshActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.btn_load_type)
    Button button;

    private MainAdapter mainAdapter;

    private static final int DELAY_MILLIS = 1500;//延迟时间

    private int mShowType = 0;

    private MyHandler myHandler = new MyHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        swipeRefreshLayout.setColorSchemeColors(Color.BLUE);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainAdapter = new MainAdapter(R.layout.rv_item, getItemDatas());
        recyclerView.setAdapter(mainAdapter);
        mainAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mainAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mShowType++;
                if (mShowType == 2) {
                    myHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mainAdapter.loadMoreFail();
                        }
                    }, DELAY_MILLIS);
                } else if (mShowType >= 4) {
                    myHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mainAdapter.loadMoreEnd();
                        }
                    }, DELAY_MILLIS);
                } else {
                    myHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mainAdapter.addData(addDatas());
                            mainAdapter.loadMoreComplete();
                        }
                    }, DELAY_MILLIS);
                }
            }
        });
        addHeadView();
    }

    private void addHeadView() {
        View headerView = getLayoutInflater().inflate(R.layout.rv_header, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mainAdapter.addHeaderView(headerView);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "your click headerView", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_pull_refresh;
    }

    @Override
    public void onRefresh() {
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mShowType = 0;
                mainAdapter.setNewData(getItemDatas());
                swipeRefreshLayout.setRefreshing(false);
            }
        }, DELAY_MILLIS);
    }

    public static List<String> addDatas() {
        List<String> mList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mList.add("我是新增条目" + (i + 1));
        }
        return mList;
    }

    public static List<String> getItemDatas() {
        List<String> mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add("下拉刷新、上拉加载" + new Random().nextInt(100));
        }
        return mList;
    }

    private static class MyHandler extends Handler {

        private WeakReference<PullRefreshActivity> activityWeakReference;

        public MyHandler(PullRefreshActivity activity) {
            activityWeakReference = new WeakReference<PullRefreshActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            PullRefreshActivity activity = activityWeakReference.get();
            if (activity == null) {
                return;
            }
        }
    }

    @OnClick(R.id.btn_load_type)
    public void click() {
        showAnimationTypeDialog();
    }

    private void showAnimationTypeDialog() {
        final String[] stringItems = {"Simple", "Custome"};
        final ActionSheetDialog dialog = new ActionSheetDialog(this, stringItems, null);
//        title("choose animation type")//
//                .titleTextSize_SP(14.5f)//
        dialog.isTitleShow(false)
                .show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    default:
                    case 0:
                        mainAdapter.setLoadMoreView(new SimpleLoadMoreView());
                        break;
                    case 1:
                        mainAdapter.setLoadMoreView(new CustomLoadMoreView());
                        break;
                }
                button.setText(stringItems[position]);
                mainAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }

}
