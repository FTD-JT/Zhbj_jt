package com.jingtao.zhbj_jt.basePagerImpl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.jingtao.zhbj_jt.BasePager;

/**
 * Created by jingtao on 16/4/16.
 */
public class SmartService extends BasePager {

    public SmartService(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public void initData() {

        tv_title.setText("生活");
        setSlidingMenuEnable(true);

        TextView textView = new TextView(mActivity);
        textView.setText("生活服务");
        textView.setTextColor(Color.RED);
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);

        //将textview放到FrameLayout中
        fl_context.addView(textView);
    }
}
