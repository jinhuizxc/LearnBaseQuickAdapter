package com.example.jinhui.learnbasequickadapter;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.jinhui.learnbasequickadapter.adapter.NavigationAdapter;
import com.example.jinhui.learnbasequickadapter.base.BaseActivity;
import com.example.jinhui.learnbasequickadapter.bean.City;
import com.example.jinhui.learnbasequickadapter.comparator.PinYinComparator;
import com.example.jinhui.learnbasequickadapter.util.StringUtils;
import com.example.jinhui.learnbasequickadapter.view.LetterNavigationView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

import static com.example.jinhui.learnbasequickadapter.adapter.NavigationAdapter.HEADER_VISIBLE_VIEW;


public class NavigationActivity extends BaseActivity {

    @BindView(R.id.tv_letter_hide)
    TextView tvCenter;

    @BindView(R.id.navigation)
    LetterNavigationView navigationView;

    @BindView(R.id.tv_letter_header)
    TextView tvLetterHeader;

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;

    private List<City> mDatas;

    private List<String> mLetterDatas;

    private boolean move;

    private int selectPosition = 0;

    private NavigationAdapter navigationAdapter;

    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDatas();
        initView();
        navigationView.setData(mLetterDatas = getNavigationDatas());
    }

    private List<String> getNavigationDatas() {
        List<String> datas = new ArrayList<>();
        for (City city : mDatas) {
            if (!datas.contains(city.firstPinYin)) {
                datas.add(city.firstPinYin);
            }
        }
        return datas;
    }


    /**
     * @param cityName
     * @return
     */
    public City getCity(String cityName) {
        City city = new City();
        city.cityName = cityName;
        city.hideEnable = false;
        city.cityPinYin = StringUtils.transformPinYin(city.cityName);
        if (city.cityPinYin != null && city.cityPinYin.length() >= 1) {
            city.firstPinYin = city.cityPinYin.substring(0, 1);
        }
        return city;
    }

    private void getDatas() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < stringCitys.length; i++) {
            mDatas.add(getCity(stringCitys[i]));
        }

        Collections.sort(mDatas, new PinYinComparator());
    }

    public static String[] stringCitys = new String[]{
            "安定", "张家界", "黄山", "淮北", "阜阳", "蚌埠", "淮南", "滁州",
            "洛阳", "芜湖", "铜陵", "安庆", "安阳", "黄山", "六安", "巢湖",
            "池州", "宣城", "亳州", "明光", "天长", "桐城", "宁国",
            "徐州", "连云港", "宿迁", "淮安", "盐城", "扬州", "长安",
            "南通", "镇江", "常州", "无锡", "苏州", "江阴", "广安",
            "邳州", "新沂", "金坛", "溧阳", "常熟", "张家港", "太仓",
            "昆山", "吴江", "如皋", "通州", "海门", "晋江", "大丰",
            "东台", "高邮", "仪征", "江都", "扬中", "句容", "丹阳",
            "兴化", "姜堰", "泰兴", "靖江", "福州", "南平", "三明",
            "邻水", "上海", "深圳", "香港", "乐山", "文淑", "重庆"
    };

    private void initView() {
        recyclerView.setLayoutManager(linearLayoutManager = new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        navigationAdapter = new NavigationAdapter(R.layout.rv_item_city, mDatas);
        recyclerView.setAdapter(navigationAdapter);

        navigationAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_city:
                        Snackbar.make(view, "" + mDatas.get(position).cityName, Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.tv_letter_header:
                        for (City city : mDatas) {
                            if (city.firstPinYin.equals(mDatas.get(position).firstPinYin)) {
                                city.hideEnable = !city.hideEnable;
                            }
                        }
                        navigationAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                View transView = recyclerView.findChildViewUnder(
                        tvLetterHeader.getMeasuredWidth(), tvLetterHeader.getMeasuredHeight() - 1);
                if (transView != null) {
                    TextView tvLetter = (TextView) transView.findViewById(R.id.tv_letter_header);
                    if (tvLetter != null) {
                        String tvLetterStr = tvLetter.getText().toString().trim();
                        String tvHeaderStr = tvLetterHeader.getText().toString().trim();
                        if (!tvHeaderStr.equals(tvLetterStr)) {
                            for (int i = 0; i < mLetterDatas.size(); i++) {
                                if (tvLetterStr.equals(mLetterDatas.get(i))) {
                                    navigationView.setSelectorPosition(i);
                                    break;
                                }
                            }
                        }
                        tvLetterHeader.setText(tvLetterStr);
                        if (transView.getTag() != null) {
                            int headerMoveY = transView.getTop() - tvLetterHeader.getMeasuredHeight();
                            int tag = (int) transView.getTag();
                            if (tag == HEADER_VISIBLE_VIEW) {
                                if (transView.getTop() > 0) {
                                    tvLetterHeader.setTranslationY(headerMoveY);
                                } else {
                                    tvLetterHeader.setTranslationY(0);
                                }
                            } else {
                                tvLetterHeader.setTranslationY(0);
                            }
                        }
                    }
                }

                if (move) {
                    move = false;
                    //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                    int n = selectPosition - linearLayoutManager.findFirstVisibleItemPosition();
                    if (0 <= n && n < recyclerView.getChildCount()) {
                        //获取要置顶的项顶部离RecyclerView顶部的距离
                        int top = recyclerView.getChildAt(n).getTop();
                        //最后的移动
                        recyclerView.scrollBy(0, top);
                    }
                }
            }
        });

        navigationView.setOnTouchListener(new LetterNavigationView.OnTouchListener() {
            @Override
            public void onTouchListener(String str, boolean hideEnable) {
                tvCenter.setText(str);
                if (hideEnable) {
                    tvCenter.setVisibility(View.GONE);
                } else {
                    tvCenter.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < mDatas.size(); i++) {
                    if (mDatas.get(i).firstPinYin.equals(str)) {
                        selectPosition = i;
                        break;
                    }
                }
                moveToPosition(selectPosition);
            }
        });
    }

    /**
     * @param n
     */
    private void moveToPosition(int n) {
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = linearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = linearLayoutManager.findLastVisibleItemPosition();
        //然后区分情况
        if (n <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            recyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            //当要置顶的项已经在屏幕上显示时
            int top = recyclerView.getChildAt(n - firstItem).getTop();
            recyclerView.scrollBy(0, top);
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            recyclerView.scrollToPosition(n);
            //这里这个变量是用在RecyclerView滚动监听里面的
            move = true;
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_navigation;
    }
}
