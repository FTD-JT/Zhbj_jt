package com.jingtao.zhbj_jt;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * 自定义下拉刷新ListView
 * Created by jingtao on 16/4/20.
 */
public class RefreshListView extends ListView {

    public RefreshListView(Context context) {
        super(context);
        initHeaderView();
    }

    private void initHeaderView() {
        View mHeaderView = View.inflate(getContext(), R.layout.refresh_header, null);
        //先测量meaderViewH的框高
        mHeaderView.measure(0,0);
        int mHearViewHeight=mHeaderView.getMeasuredHeight();
        //往上跑,隐藏头布局
        mHeaderView.setPadding(0,-mHearViewHeight,0,0);
        this.addHeaderView(mHeaderView);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
    }


}

