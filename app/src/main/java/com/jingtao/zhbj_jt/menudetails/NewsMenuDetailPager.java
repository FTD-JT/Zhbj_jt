package com.jingtao.zhbj_jt.menudetails;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jingtao.zhbj_jt.BaseMenuDetailPager;
import com.jingtao.zhbj_jt.R;
import com.jingtao.zhbj_jt.TabDetailPager;
import com.jingtao.zhbj_jt.bean.NewsData;

import java.util.ArrayList;

/**
 * 菜单详情页-新闻
 * Created by jingtao on 16/4/18.
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager {

    private ViewPager mViewPager;

    private ArrayList<TabDetailPager> mPagerList;

    private ArrayList<NewsData.NewsTabData> mNewsTabData;

    public NewsMenuDetailPager(Activity mActivity, ArrayList<NewsData.NewsTabData> children) {
        super(mActivity);
        mNewsTabData=children;
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.news_menu_detail, null);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_menu_detail);
        return view;
    }

    public void initData(){

        mPagerList=new ArrayList<TabDetailPager>();

        for (int i = 0; i <mNewsTabData.size() ; i++) {
            TabDetailPager tabDetailPager = new TabDetailPager(mActivity,mNewsTabData.get(i));
            mPagerList.add(tabDetailPager);
        }

        mViewPager.setAdapter(new MenuDetailAdapter());
    }


    class MenuDetailAdapter extends PagerAdapter{

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
