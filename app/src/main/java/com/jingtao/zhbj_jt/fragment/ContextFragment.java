package com.jingtao.zhbj_jt.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import com.jingtao.zhbj_jt.BasePager;
import com.jingtao.zhbj_jt.R;
import com.jingtao.zhbj_jt.basePagerImpl.GovAffairs;
import com.jingtao.zhbj_jt.basePagerImpl.HomePager;
import com.jingtao.zhbj_jt.basePagerImpl.NewsCenter;
import com.jingtao.zhbj_jt.basePagerImpl.Settings;
import com.jingtao.zhbj_jt.basePagerImpl.SmartService;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingtao on 16/4/15.
 */
public class ContextFragment extends BaseFragment {


    private RadioGroup rg_group;


    private ViewPager mViewPager;

    private List<BasePager> lists;



    @Override
    public View initViews() {
       View view= View.inflate(mActivity, R.layout.fragment_context, null);
//        ViewUtils.inject(mActivity);//注入view和事件
        rg_group = (RadioGroup) view.findViewById(R.id.rg_group);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_context);
        initData();
        return view;
    }

    @Override
    public void initData() {
        //让主页开启时radiogroup默认勾选首页
        rg_group.check(R.id.rb_home);

        //初始化5个子页面
        lists=new ArrayList<BasePager>();
//        for (int i = 0; i <5 ; i++) {
//            BasePager pager=new BasePager(mActivity);
//            lists.add(pager);
//        }
        lists.add(new HomePager(mActivity));
        lists.add(new NewsCenter(mActivity));
        lists.add(new SmartService(mActivity));
        lists.add(new GovAffairs(mActivity));
        lists.add(new Settings(mActivity));

        //viewPager设置适配器
        mViewPager.setAdapter(new ContextAdapter());

        //点击RadioGroup下面的5个按钮,跳转到对应的pager
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home:
//                        mViewPager.setCurrentItem(0);
                        mViewPager.setCurrentItem(0,false);//去掉滑动动画和上面的方法有点不同

                        break;
                    case R.id.rb_newscenter:
//                        mViewPager.setCurrentItem(1);
                        mViewPager.setCurrentItem(1,false);
                        break;
                    case R.id.rb_smartservice:
//                        mViewPager.setCurrentItem(2);
                        mViewPager.setCurrentItem(2,false);
                        break;
                    case R.id.rb_govaffairs:
//                        mViewPager.setCurrentItem(3);
                        mViewPager.setCurrentItem(3,false);
                        break;
                    case R.id.rb_settings:
//                        mViewPager.setCurrentItem(4);
                        mViewPager.setCurrentItem(4,false);
                        break;
                }
            }
        });

        //设置viewPager监听事件加载数据
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                  lists.get(position).initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        lists.get(0).initData();//手动初始化首页数据
    }


    class ContextAdapter extends PagerAdapter{

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
            BasePager pager = lists.get(position);
            container.addView(pager.mRootView);
            //加载每个pager里面的数据
//            pager.initData();//viewPager默认加载3个页面
            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(lists.get(position).mRootView);
        }
    }

    /**
     * 获取新闻中心页面
     */
    public NewsCenter getNewsCenter(){
        return (NewsCenter) lists.get(1);
    }
}
