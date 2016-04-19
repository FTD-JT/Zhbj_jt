package com.jingtao.zhbj_jt.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by jingtao on 16/4/19.
 */
public class TopNewsViewPager extends ViewPager {
    public TopNewsViewPager(Context context) {
        super(context);
    }

    public TopNewsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 事件分发,请求父控件及祖宗控件是否拦截时间
     */

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){

            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);//用getparent去请求


            case MotionEvent.ACTION_MOVE:

                break;
        }


        return super.dispatchTouchEvent(ev);
    }


}
