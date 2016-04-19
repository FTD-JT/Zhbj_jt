package com.jingtao.zhbj_jt;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.jingtao.zhbj_jt.bean.NewsData;

/**
 * Created by jingtao on 16/4/19.
 */
public class TabDetailPager extends BaseMenuDetailPager {
    private TextView textView;

    private NewsData.NewsTabData mNewsTabData;

    public TabDetailPager(Activity mActivity, NewsData.NewsTabData newsTabData) {
        super(mActivity);
        mNewsTabData=newsTabData;
    }

    @Override
    public View initView() {
        textView = new TextView(mActivity);
        textView.setText("页签详情页");
        textView.setTextColor(Color.RED);
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    public void initData(){
        textView.setText(mNewsTabData.title);
    }
}
