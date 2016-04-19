package com.jingtao.zhbj_jt;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import com.jingtao.zhbj_jt.common.SharedPreferencesUtils;

public class SplashActivity extends Activity {
    private LinearLayout ll_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        startAnim();

    }


    /**
     * 闪屏动画
     */

    private void startAnim() {
        //动画集合
        AnimationSet animationSet = new AnimationSet(false);

        //旋转动画
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation
                .RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(3000);
        rotateAnimation.setFillAfter(true);
        animationSet.addAnimation(rotateAnimation);

        //缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation
                .RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(3000);
        scaleAnimation.setFillAfter(true);
        animationSet.addAnimation(scaleAnimation);

        //渐变动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setFillAfter(true);
        animationSet.addAnimation(alphaAnimation);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                jumpNextActivity();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //设置动画
        ll_splash.startAnimation(animationSet);

    }


    //利用sharedprefences判断下一次进入是否要进入新手引导界面
    private void jumpNextActivity() {

        boolean flag = SharedPreferencesUtils.getBoolean(this, "is show", false);

        if (!flag){
            Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
            startActivity(intent);
            //结束当前页面
            finish();
        }else{
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            //结束当前页面
            finish();
        }
    }

    private void initView() {
        ll_splash = (LinearLayout) findViewById(R.id.ll_splash);
    }


}
