package com.jingtao.zhbj_jt.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自定义ViewPager让Viewpager不能滑动
 * Created by jingtao on 16/4/16.
 */
public class NoScrollViewPager extends ViewPager {

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //重写onTouchEvent方法让viewpager不能滑动,返回false
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }


    //父控件不拦截事件,给里面的子控件响应事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
