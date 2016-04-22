package com.jingtao.zhbj_jt.menudetails;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jingtao.zhbj_jt.BaseMenuDetailPager;
import com.jingtao.zhbj_jt.R;
import com.jingtao.zhbj_jt.bean.NewsData;
import com.jingtao.zhbj_jt.bean.PhotosData;
import com.jingtao.zhbj_jt.common.CacheUtils;
import com.jingtao.zhbj_jt.global.GlobalContext;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

import feign.Util;

/**
 * 菜单详情页-组图
 * Created by jingtao on 16/4/18.
 */
public class PhotoMenuDetailPager extends BaseMenuDetailPager {

    private  BitmapUtils Utils;
    private ArrayList<PhotosData.PhotoInfo> mphotoList;
    private PhotoAdapter mphotoAdapter;

    private ListView lv_photo;
    private GridView gv_photo;

    private ImageView btn_photo;

    public PhotoMenuDetailPager(Activity mActivity, ImageButton ib_photo) {

        super(mActivity);
        btn_photo=ib_photo;

        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDisplay();
            }
        });
    }

    private boolean isListDisplay=true;//是否是列表显示
    //切换展现方式
    private void changeDisplay() {
        if (isListDisplay){
            isListDisplay=false;
            lv_photo.setVisibility(View.GONE);
            gv_photo.setVisibility(View.VISIBLE);
            btn_photo.setImageResource(R.drawable.icon_pic_list_type);
        }else{
            isListDisplay=true;
            lv_photo.setVisibility(View.VISIBLE);
            gv_photo.setVisibility(View.GONE);

            btn_photo.setImageResource(R.drawable.icon_pic_grid_type);
        }
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.menu_photo_pager, null);

        lv_photo = (ListView) view.findViewById(R.id.lv_photo);
        gv_photo = (GridView) view.findViewById(R.id.gv_photo);
        return view;
    }

    @Override
    public void initData() {
        String cache = CacheUtils.getCache(GlobalContext.PHOTO_URL, mActivity);

        if (!TextUtils.isEmpty(cache)) {

        }

        getDataFromServer();
    }


    private void getDataFromServer() {

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalContext.PHOTO_URL, new
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
        Gson gson = new Gson();
        PhotosData data = gson.fromJson(result, PhotosData.class);

        mphotoList = data.data.news;//获得组图列表集合

        mphotoAdapter = new PhotoAdapter();
        lv_photo.setAdapter(mphotoAdapter);
        gv_photo.setAdapter(mphotoAdapter);
    }

    class PhotoAdapter extends BaseAdapter {

        public PhotoAdapter(){
            Utils=new BitmapUtils(mActivity);
            Utils.configDefaultLoadingImage(R.drawable.news_pic_default);
        }

        @Override
        public int getCount() {
            return mphotoList.size();
        }

        @Override
        public Object getItem(int position) {
            return mphotoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           ViewHolder viewHolder=null;
            if (convertView==null){
                convertView = View.inflate(mActivity, R.layout.list_photo_item, null);
                viewHolder=new ViewHolder();
                viewHolder.iv_photo = (ImageView) convertView.findViewById(R.id.iv_pic);
                viewHolder.tv_photo = (TextView) convertView.findViewById(R.id.tv_title);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }

            PhotosData.PhotoInfo item = (PhotosData.PhotoInfo) getItem(position);

            viewHolder.tv_photo.setText(item.title);

            Utils.display(viewHolder.iv_photo,item.listimage);

            return convertView;
        }
    }

    static class ViewHolder{
        ImageView iv_photo;
        TextView tv_photo;
    }
}
