package com.jingtao.zhbj_jt.basePagerImpl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.jingtao.zhbj_jt.BasePager;

/**
 * Created by jingtao on 16/4/16.
 */
public class Settings extends BasePager {

    public Settings(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public void initData() {
        //隐藏小图标
        imageButton.setVisibility(View.GONE);
        setSlidingMenuEnable(false);
        tv_title.setText("设置");

        TextView textView = new TextView(mActivity);
        textView.setText("设置中心");
        textView.setTextColor(Color.RED);
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);

        //将textview放到FrameLayout中
        fl_context.addView(textView);
    }
}
