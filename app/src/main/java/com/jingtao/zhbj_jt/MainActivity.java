package com.jingtao.zhbj_jt;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;


import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.jingtao.zhbj_jt.fragment.ContextFragment;
import com.jingtao.zhbj_jt.fragment.LeftMenuFragment;

/**
 * 主页面
 * Created by jingtao on 16/4/15.
 */
public class MainActivity extends SlidingFragmentActivity {
    private static final String FRAGMENT_LEFT_MENU = "fragment_left_menu";
    private static final String FRAGMENT_CONTEXT = "fragment_context";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.left_menu);//设置侧边栏
        SlidingMenu slidingMenu = getSlidingMenu();//获取侧边栏对象
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//设置全屏触摸
        slidingMenu.setBehindOffset(500);//设置偏移量
        //加载Fragment
        initFragment();
    }


    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_left_menu, new LeftMenuFragment(), FRAGMENT_LEFT_MENU);
        transaction.replace(R.id.fl_context, new ContextFragment(), FRAGMENT_CONTEXT);
        transaction.commit();

    }

    //获取侧边栏fragment
    public LeftMenuFragment getLeftFragment(){
        FragmentManager fm = getSupportFragmentManager();
        LeftMenuFragment fragment = (LeftMenuFragment) fm.findFragmentByTag(FRAGMENT_LEFT_MENU);
        return fragment;
    }

     //获取主页栏fragment
    public ContextFragment getContentFragment(){
        FragmentManager fm = getSupportFragmentManager();
        ContextFragment fragment= (ContextFragment) fm.findFragmentByTag(FRAGMENT_CONTEXT);
           return fragment;
    }
}
