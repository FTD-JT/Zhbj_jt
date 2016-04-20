package com.jingtao.zhbj_jt.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by jingtao on 16/4/19.
 */
public class TopNewsViewPager extends ViewPager {
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public TopNewsViewPager(Context context) {
        super(context);
    }

    public TopNewsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 事件分发,请求父控件及祖宗控件是否拦截时间
     * 1.右滑,而且是第一个页面,需要父控件拦截
     * 2.左滑,而且是最后一个页面,需要父控件拦截
     * 3.上下滑动,需要父控件拦截
     */

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){

            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);//不要拦截,不然action move都会失效

                startX= (int) ev.getRawX();
                startY= (int) ev.getRawY();
                 break;
            case MotionEvent.ACTION_MOVE:

                endX= (int) ev.getRawX();
                endY= (int) ev.getRawY();

                //表示左右滑动
                if (Math.abs(endX-startX)> Math.abs(endY-startY)){
                    if (endX>startX){
                        //右滑
                        if (getCurrentItem()==0){//第一个页面,需要父控件拦截
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }else {
                        //左滑
                        if (getCurrentItem()==getAdapter().getCount()-1){//最后一个需要拦截
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                }else{
                    //表示上下滑
                    getParent().requestDisallowInterceptTouchEvent(false);//父控件拦截事件
                }

                break;
        }


        return super.dispatchTouchEvent(ev);
    }


}
