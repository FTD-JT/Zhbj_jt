package com.jingtao.zhbj_jt.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment基类
 * Created by jingtao on 16/4/15.
 */
public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;

    //创建Fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=getActivity();
    }

    //处理Frgment布局
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        return initViews();
    }


    //依附的Activity创建完成
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }


    //子类必须实现初始化布局的方法
    public abstract View initViews();

    //初始化数据,可以不实现
    public void initData() {

    }

}
