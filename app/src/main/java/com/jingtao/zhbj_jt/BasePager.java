package com.jingtao.zhbj_jt;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * 加载5个页面
 * Created by jingtao on 16/4/16.
 */
public class BasePager {

    public Activity mActivity;

    public View mRootView;

    public TextView tv_title;

    public FrameLayout fl_context;


    //顶部的小菜单选项
    public ImageButton imageButton;

    public BasePager(Activity mActivity) {
        this.mActivity = mActivity;
        //初始化界面
        initView();
    }

    //初始化布局
    public void initView(){
       mRootView=View.inflate(mActivity,R.layout.base_pager,null);

        tv_title = (TextView) mRootView.findViewById(R.id.tv_title);
        fl_context = (FrameLayout) mRootView.findViewById(R.id.fl_context);
        imageButton = (ImageButton) mRootView.findViewById(R.id.ib_menu);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSlidingMenu();
            }
        });
    }

    //当选中小菜单后,再把侧边栏隐藏起来
    private void toggleSlidingMenu() {
        MainActivity mainUi= (MainActivity) mActivity;
        SlidingMenu slidingMenu= mainUi.getSlidingMenu();//获取侧边栏
        slidingMenu.toggle();//隐藏侧边栏
    }

    //初始化数据

    public void initData(){

    }

    /**
     * 设置侧边栏开启或关闭
     */
    public void setSlidingMenuEnable(Boolean enable){
        MainActivity mainUi= (MainActivity) mActivity;

       SlidingMenu slidingMenu= mainUi.getSlidingMenu();

        if (enable){
            //开启 全屏触摸侧边栏
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else{
            //关闭侧边栏
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }
}
