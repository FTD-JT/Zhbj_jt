package com.jingtao.zhbj_jt;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * 自定义下拉刷新ListView
 * Created by jingtao on 16/4/20.
 */
public class RefreshListView extends ListView {
    private RotateAnimation animUp;
    private RotateAnimation animDown;


    private static final int START_PULL_REFRESH = 0;//下拉刷新
    private static final int START_RELEASE_REFRESH = 1;//松开刷新
    private static final int START_REFRESH = 2;//正在刷新

    private int mCurrentState = START_PULL_REFRESH;//当前状态

    private int startY;

    private int endY;

    private int mHearViewHeight;

    private View mHeaderView;
    private TextView tvTitle;
    private TextView tvTime;
    private ImageView ivArrow;
    private ProgressBar pbProgress;


    public RefreshListView(Context context) {
        super(context);
        initHeaderView();
    }

    private void initHeaderView() {
        mHeaderView = View.inflate(getContext(), R.layout.refresh_header, null);
        this.addHeaderView(mHeaderView);

        tvTitle = (TextView) mHeaderView.findViewById(R.id.tv_title);
        tvTime = (TextView) mHeaderView.findViewById(R.id.tv_time);
        ivArrow = (ImageView) mHeaderView.findViewById(R.id.iv_arrow);
        pbProgress = (ProgressBar) mHeaderView.findViewById(R.id.pb_progress);


        //先测量meaderViewH的框高
        mHeaderView.measure(0, 0);
        mHearViewHeight = mHeaderView.getMeasuredHeight();
        //往上跑,隐藏头布局
        mHeaderView.setPadding(0, -mHearViewHeight, 0, 0);

    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {//确保start有效
                    startY = (int) ev.getRawY();

                }

                if (mCurrentState == START_REFRESH) {
                    break;
                }
                endY = (int) ev.getRawY();

                int dy = endY - startY;//移动偏移量

                if (dy > 0 && getFirstVisiblePosition() == 0) {//只有是第一个item时才能下拉
                    int padding = dy - mHearViewHeight;//计算现在padding的位置
                    mHeaderView.setPadding(0, padding, 0, 0);

                    if (padding > 0 && mCurrentState != START_PULL_REFRESH) {//状态改为松开刷新
                        mCurrentState = START_RELEASE_REFRESH;
                        refreshState();
                    } else if (padding < 0 && mCurrentState != START_PULL_REFRESH) {//状态改为下拉刷新
                        mCurrentState = START_PULL_REFRESH;
                    }

                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;//重置

                if (mCurrentState == START_RELEASE_REFRESH) {
                    mCurrentState = START_REFRESH;
                    mHeaderView.setPadding(0, -mHearViewHeight, 0, 0);
                    refreshState();
                } else if (mCurrentState == START_PULL_REFRESH) {
                    mHeaderView.setPadding(0, -mHearViewHeight, 0, 0);//隐藏
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void refreshState() {
        switch (mCurrentState) {
            case START_PULL_REFRESH:
                tvTitle.setText("下拉刷新");
                ivArrow.setVisibility(VISIBLE);
                pbProgress.setVisibility(INVISIBLE);
                break;
            case START_RELEASE_REFRESH:
                tvTitle.setText("松开刷新");

                break;
            case START_REFRESH:
                tvTitle.setText("正在刷新....");
                ivArrow.setVisibility(INVISIBLE);
                pbProgress.setVisibility(VISIBLE);


                if (mListener!=null){
                    mListener.onRefresh();
                }
                break;
            default:
                break;
        }
    }

    private void initArrowAnimation() {
        //箭头向上
       animUp = new RotateAnimation(0, -180, Animation
                .RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animUp.setDuration(200);
        animUp.setFillAfter(true);

        //箭头向下
        animDown = new RotateAnimation(0, 180, Animation
                .RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animDown.setDuration(200);
        animDown.setFillAfter(true);

    }

    OnRefreshListener mListener;

    public void setOnRefreshListener(OnRefreshListener listener){
        mListener=listener;
    }

    public interface OnRefreshListener{

        public void onRefresh();
    }
}

