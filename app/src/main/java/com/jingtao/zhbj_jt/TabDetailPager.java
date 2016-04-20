package com.jingtao.zhbj_jt;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jingtao.zhbj_jt.bean.NewsData;
import com.jingtao.zhbj_jt.bean.TabData;
import com.jingtao.zhbj_jt.global.GlobalContext;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * Created by jingtao on 16/4/19.
 */
public class TabDetailPager extends BaseMenuDetailPager implements ViewPager.OnPageChangeListener{
    private TextView tv_title;//头条标题

    private ArrayList<TabData.TopNewsData> mTopNewsList;//头条新闻集合

    private CirclePageIndicator mIndicator;//头条新闻位置指示器

    private RefreshListView lv_list;

    private TextView textView;

    private NewsData.NewsTabData mNewsTabData;

    private ArrayList<TabData.TabNewsData> mNewsList;

    private String mUrl;

    private TabData mTabDetailData;

    private ViewPager mViewPager;

    private BitmapUtils bitmapUtils;

    private NewsAdapter mNewsAdapter;

    public TabDetailPager(Activity mActivity, NewsData.NewsTabData newsTabData) {
        super(mActivity);
        mNewsTabData=newsTabData;
        //获取url
        mUrl= GlobalContext.SERVER_URL+mNewsTabData.url;
    }


    public void getDataFromServer(){
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET,mUrl, new
                RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                     parseData(responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Log.i("news", "错误信息");
                    }


                });
    }

    private void parseData(String result) {
        Gson gson=new Gson();
        mTabDetailData= gson.fromJson(result, TabData.class);
//        Log.i("info","页签详情页返回结果:"+mTabDetailData);

          mTopNewsList= mTabDetailData.data.topnews;

         mNewsList=mTabDetailData.data.news;//新闻数据集合

        if (mTopNewsList!=null){
            mViewPager.setAdapter(new TopNewsAdapter());

            mIndicator.setViewPager(mViewPager);
            mIndicator.setSnap(true);//快照显示

            mIndicator.setOnPageChangeListener(this);

            mIndicator.onPageSelected(0);//让指示剂定位到第一个点

            tv_title.setText(mTopNewsList.get(0).title);//第一次进入时设置头条信息
        }


        //填充新闻列表数据
        if (mNewsList!=null){
            mNewsAdapter=new NewsAdapter();
            lv_list.setAdapter(mNewsAdapter);
        }
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);

        lv_list = (RefreshListView) view.findViewById(R.id.lv_list);
        //加载头布局
        View headerView = View.inflate(mActivity, R.layout.list_header_topnews, null);

       //将头条新闻以头布局形式加载给listview
        lv_list.addHeaderView(headerView);

        tv_title = (TextView) headerView.findViewById(R.id.tv_title);

        mViewPager = (ViewPager) headerView.findViewById(R.id.vp_news);

        mIndicator = (CirclePageIndicator) headerView.findViewById(R.id.indicator);


        return view;
    }

    public void initData(){
//        textView.setText(mNewsTabData.title);

        getDataFromServer();

    }


    /**
     * 新闻列表适配器
     */

    class NewsAdapter extends BaseAdapter{

        private BitmapUtils util;

        public NewsAdapter() {
            util = new BitmapUtils(mActivity);
            bitmapUtils.configDefaultLoadingImage(R.drawable.topnews_item_default);
        }

        @Override
        public int getCount() {
            return mNewsList.size();
        }

        @Override
        public Object getItem(int position) {
            return mNewsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder  viewHolder;
            if (convertView==null){
                convertView=View.inflate(mActivity,R.layout.list_news_item,null);
                viewHolder=new ViewHolder();
                viewHolder.ivPic = (ImageView) convertView.findViewById(R.id.iv_pic);
                viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
                convertView.setTag(viewHolder);
            }
            else {
                viewHolder= (ViewHolder) convertView.getTag();
            }

            TabData.TabNewsData item= (TabData.TabNewsData) getItem(position);

            viewHolder.tvTitle.setText(item.title);
            viewHolder.tvDate.setText(item.pubdate);
            util.display(viewHolder.ivPic,item.listimage);
            return convertView;
        }
    }

    static class ViewHolder{
        public TextView tvTitle;
        public TextView tvDate;
        public ImageView ivPic;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tv_title.setText(mTopNewsList.get(position).title);//设置头条新闻
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class TopNewsAdapter extends PagerAdapter{

          public TopNewsAdapter() {
              bitmapUtils = new BitmapUtils(mActivity);
              bitmapUtils.configDefaultLoadingImage(R.drawable.topnews_item_default);
          }

          @Override
          public int getCount() {
              return mTabDetailData.data.topnews.size();
          }

          @Override
          public boolean isViewFromObject(View view, Object object) {
              return view==object;
          }

          @Override
          public Object instantiateItem(ViewGroup container, int position) {
              ImageView imageView = new ImageView(mActivity);
              imageView.setImageResource(R.drawable.topnews_item_default);
              imageView.setScaleType(ImageView.ScaleType.FIT_XY);//基于控件大小填充图片

             TabData.TopNewsData topNewsData= mTopNewsList.get(position);
              bitmapUtils.display(imageView,topNewsData.topimage);//传递imageView图片
              container.addView(imageView);
              return imageView;
          }

          @Override
          public void destroyItem(ViewGroup container, int position, Object object) {
             container.removeView((View) object);
          }
      }
}
