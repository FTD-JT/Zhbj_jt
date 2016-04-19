package com.jingtao.zhbj_jt;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

/**
 * Created by jingtao on 16/4/19.
 */
public class TabDetailPager extends BaseMenuDetailPager {
    private TextView textView;

    private NewsData.NewsTabData mNewsTabData;

    private String mUrl;

    private TabData mTabDetailData;

    private ViewPager mViewPager;

    private BitmapUtils bitmapUtils;

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
        Log.i("info","页签详情页返回结果:"+mTabDetailData);

        mViewPager.setAdapter(new TopNewsAdapter());

    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_news);
        return view;
    }

    public void initData(){
//        textView.setText(mNewsTabData.title);

        getDataFromServer();

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

             TabData.TopNewsData topNewsData= mTabDetailData.data.topnews.get(position);
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
