package com.jingtao.zhbj_jt;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * 设置引导页适配器
 * Created by jingtao on 16/4/15.
 */
public class GuideViewPager extends PagerAdapter {
    private Context context;
    private List<ImageView> lists;

    public GuideViewPager(Context context, List<ImageView> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(lists.get(position));
        return lists.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(lists.get(position));
    }
}
