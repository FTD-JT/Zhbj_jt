package com.jingtao.zhbj_jt.basePagerImpl;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jingtao.zhbj_jt.BaseMenuDetailPager;
import com.jingtao.zhbj_jt.BasePager;
import com.jingtao.zhbj_jt.MainActivity;
import com.jingtao.zhbj_jt.bean.NewsData;
import com.jingtao.zhbj_jt.common.CacheUtils;
import com.jingtao.zhbj_jt.fragment.LeftMenuFragment;
import com.jingtao.zhbj_jt.global.GlobalContext;
import com.jingtao.zhbj_jt.menudetails.InteractMenuDetailPager;
import com.jingtao.zhbj_jt.menudetails.NewsMenuDetailPager;
import com.jingtao.zhbj_jt.menudetails.PhotoMenuDetailPager;
import com.jingtao.zhbj_jt.menudetails.TopicMenuDetailPager;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;


/**
 * Created by jingtao on 16/4/16.
 */
public class NewsCenter extends BasePager {

   public NewsData mNewsData;//设置数据

    private ArrayList<BaseMenuDetailPager> mPagers;//初始化4个菜单详情页集合

    public NewsCenter(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public void initData() {

        tv_title.setText("新闻");
        setSlidingMenuEnable(true);

//        TextView textView = new TextView(mActivity);
//        textView.setText("新闻中心");
//        textView.setTextColor(Color.RED);
//        textView.setTextSize(30);
//        textView.setGravity(Gravity.CENTER);
//
//        //将textview放到FrameLayout中
//        fl_context.addView(textView);

        String cache = CacheUtils.getCache(GlobalContext.CATEGORIES_URL, mActivity);

        if (!TextUtils.isEmpty(cache)){//如果缓存存在,直接解析数据,无需访问网络
            parseData(cache);
        }else {
            getDataFromServer();
        }
    }

    //向服务器请求数据
    private void getDataFromServer() {

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalContext.CATEGORIES_URL, new
                RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
//                        Log.i("news", responseInfo.result);
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
        mNewsData = gson.fromJson(result, NewsData.class);
//        Log.i("news",data.toString());

        //刷新侧边栏数据
        MainActivity mainUi = (MainActivity) mActivity;
        LeftMenuFragment leftMenuFragment=mainUi.getLeftFragment();
        leftMenuFragment.setMenuData(mNewsData);


         // 准备4个菜单详情页
        mPagers=new ArrayList<BaseMenuDetailPager>();
        mPagers.add(new NewsMenuDetailPager(mActivity,mNewsData.data.get(0).children));
        mPagers.add(new TopicMenuDetailPager(mActivity));
        mPagers.add(new PhotoMenuDetailPager(mActivity,ib_photo));
        mPagers.add(new InteractMenuDetailPager(mActivity));

        setCurrentMenuDetailPager(0);//设置一开始侧边栏显示的是新闻第一个菜单详情页

    }

    /**
     * 设置当前详情页
     */
     public void setCurrentMenuDetailPager(int position) {
         BaseMenuDetailPager pager = mPagers.get(position);
         fl_context.removeAllViews();//删除当前view
         fl_context.addView(pager.mRootView);//将布局文件设置给帧布局

        NewsData.NewsMenuData newsMenuData= mNewsData.data.get(position);
         tv_title.setText(newsMenuData.title);

         pager.initData();

         //只有在组图页面,切换按钮才会显示出来
         if (pager instanceof PhotoMenuDetailPager){
             ib_photo.setVisibility(View.VISIBLE);
         }else{
             ib_photo.setVisibility(View.GONE);
         }
     }

}



