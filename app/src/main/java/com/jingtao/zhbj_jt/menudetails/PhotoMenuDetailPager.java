package com.jingtao.zhbj_jt.menudetails;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.jingtao.zhbj_jt.BaseMenuDetailPager;

/**
 *菜单详情页-组图
 * Created by jingtao on 16/4/18.
 */
public class PhotoMenuDetailPager extends BaseMenuDetailPager {
    public PhotoMenuDetailPager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public View initView() {

        TextView textView = new TextView(mActivity);
        textView.setText("组图");
        textView.setTextColor(Color.RED);
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
