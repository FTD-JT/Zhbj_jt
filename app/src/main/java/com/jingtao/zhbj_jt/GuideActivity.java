package com.jingtao.zhbj_jt;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jingtao.zhbj_jt.common.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 新手引导页
 * 设置开启引导项
 * Created by jingtao on 16/4/15.
 */
public class GuideActivity extends Activity {
    private ViewPager vp_guide;
    private int[] mimages = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable
            .guide_3};
    private List<ImageView> lists;
    private GuideViewPager adapter;
    private LinearLayout ll_point_group;
    private int mPointWidth;
    private Button btn_guide;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        vp_guide = (ViewPager) findViewById(R.id.vp_guide);
        ll_point_group = (LinearLayout) findViewById(R.id.ll_point_group);
        btn_guide = (Button) findViewById(R.id.btn_guide);
//        initImageView();
        //初始化适配器
        adapter = new GuideViewPager(this, initImageView());
        //加载适配器到引导页上
        vp_guide.setAdapter(adapter);
        //viewpager设置监听
        vp_guide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //选择的界面
            @Override
            public void onPageSelected(int position) {
                //第三个pager时候显示按钮
                if (position == mimages.length-1) {
                    btn_guide.setVisibility(View.VISIBLE);
                }else{
                    btn_guide.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        /**
         * 给按钮设置监听跳到下一个界面
         */
        btn_guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtils.setBoolean(GuideActivity.this,"is show",true);
                //跳到下一个界面
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                //关闭当前界面
                finish();
            }
        });
    }

    //初始化3个加载页
    private List<ImageView> initImageView() {
        lists = new ArrayList<>();
        for (int i = 0; i < mimages.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(mimages[i]);
            lists.add(imageView);
        }

        //初始化加载页的小圆点
        for (int i = 0; i < mimages.length; i++) {
            View point = new View(this);
            point.setBackgroundResource(R.drawable.shape_point_gray);//设置引导页默认原点

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);

            if (i > 0) {
                //设置原点间边距
                params.leftMargin = 15;
            }

            point.setLayoutParams(params);
            //把小原点添加到布局里面
            ll_point_group.addView(point);
        }

        //获取视图树
        ll_point_group.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {
            //当layout结束时调用该方法
            @Override
            public void onGlobalLayout() {
                Log.i("info", "layout结束");
                //用完了把这个监视去掉
                ll_point_group.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //获取两个圆点间的距离
                mPointWidth = ll_point_group.getChildAt(1).getLeft() - ll_point_group
                        .getChildAt(0).getLeft();
                Log.i("info",mPointWidth+"");
            }
        });

        return lists;
    }
}
