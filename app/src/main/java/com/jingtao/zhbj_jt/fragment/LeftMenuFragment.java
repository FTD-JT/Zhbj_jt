package com.jingtao.zhbj_jt.fragment;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jingtao.zhbj_jt.MainActivity;
import com.jingtao.zhbj_jt.R;
import com.jingtao.zhbj_jt.basePagerImpl.NewsCenter;
import com.jingtao.zhbj_jt.bean.NewsData;

import java.util.ArrayList;

/**
 * Created by jingtao on 16/4/15.
 */
public class LeftMenuFragment extends BaseFragment {

    private ListView lv_list;

    private ArrayList<NewsData.NewsMenuData> myMenuData;

    private int mCurrentPos;

    MenuAdapter madapter;


    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
        lv_list = (ListView) view.findViewById(R.id.lv_list);
        return view;
    }

    @Override
    public void initData() {
          lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  mCurrentPos=position;
                  madapter.notifyDataSetChanged();

                  setCurrentMenuDetailPager(position);


                  toggleSlidingMenu();
              }
          });
    }

    //当选中侧边栏里面的菜单项后,再把侧边栏隐藏起来
    private void toggleSlidingMenu() {
        MainActivity mainUi= (MainActivity) mActivity;
         SlidingMenu slidingMenu= mainUi.getSlidingMenu();//获取侧边栏
        slidingMenu.toggle();//隐藏侧边栏
    }

    //设置当前菜单详情页
    private void setCurrentMenuDetailPager(int position) {
        MainActivity mainUi= (MainActivity) mActivity;
       ContextFragment contextFragment= mainUi.getContentFragment();//获取主页面
       NewsCenter pager= contextFragment.getNewsCenter();
        pager.setCurrentMenuDetailPager(position);//设置当前菜单详情页
    }

    //设置网络数据
    public void setMenuData(NewsData data) {
//        Log.i("menu", data.toString());
        myMenuData = data.data;
        madapter = new MenuAdapter();
        lv_list.setAdapter(madapter);

    }

       //侧边栏适配器
    class MenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return myMenuData.size();
        }

        @Override
        public Object getItem(int position) {
            return myMenuData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mActivity, R.layout.list_menu_item, null);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);

            NewsData.NewsMenuData newsMenuData = (NewsData.NewsMenuData) getItem(position);
            tv_title.setText(newsMenuData.title);

            if (mCurrentPos==position){//判断当前绘制的view是否被选中
                //侧边栏显示红色
                   tv_title.setEnabled(true);
            }else{
                //侧边栏显示白色
                tv_title.setEnabled(false);
            }
            return view;
        }
    }
}
