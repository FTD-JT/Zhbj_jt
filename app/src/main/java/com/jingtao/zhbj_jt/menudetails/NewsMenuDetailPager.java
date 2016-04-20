package com.jingtao.zhbj_jt.menudetails;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jingtao.zhbj_jt.BaseMenuDetailPager;
import com.jingtao.zhbj_jt.MainActivity;
import com.jingtao.zhbj_jt.R;
import com.jingtao.zhbj_jt.TabDetailPager;
import com.jingtao.zhbj_jt.bean.NewsData;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;



/**
 * 菜单详情页-新闻
 * Created by jingtao on 16/4/18.
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager implements ViewPager
        .OnPageChangeListener {

    private ViewPager mViewPager;

    private ArrayList<TabDetailPager> mPagerList;

    private ArrayList<NewsData.NewsTabData> mNewsTabData;

    private TabPageIndicator indicator;

    public NewsMenuDetailPager(Activity mActivity, ArrayList<NewsData.NewsTabData> children) {
        super(mActivity);
        mNewsTabData=children;
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.news_menu_detail, null);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_menu_detail);

       //初始化自定义控件
       indicator= (TabPageIndicator) view.findViewById(R.id.indicator);
         //给indicator设置滑动事件,只有第一个标签时候才能把侧边栏滑出来,其他都不能把侧边栏拉出来
        indicator.setOnPageChangeListener(this);

        return view;
    }

    public void initData(){

        mPagerList=new ArrayList<TabDetailPager>();

        for (int i = 0; i <mNewsTabData.size() ; i++) {
            TabDetailPager tabDetailPager = new TabDetailPager(mActivity,mNewsTabData.get(i));
            mPagerList.add(tabDetailPager);
        }

        mViewPager.setAdapter(new MenuDetailAdapter());
        indicator.setViewPager(mViewPager);//必须在viewPager设置完适配器之后才能调用
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //indicator设置滑动监听
        MainActivity mainUi= (MainActivity) mActivity;
        SlidingMenu slidingMenu=mainUi.getSlidingMenu();
           if(position==0){//只有在北京这个页面,侧边栏才可以出来
              slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
           }else{
               slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
           }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    class MenuDetailAdapter extends PagerAdapter{

      //设置标题
        @Override
        public CharSequence getPageTitle(int position) {
            return mNewsTabData.get(position).title;
        }

        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPager pager = mPagerList.get(position);
            container.addView(pager.mRootView);
            pager.initData();
            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
           container.removeView((View) object);

        }
    }
}
