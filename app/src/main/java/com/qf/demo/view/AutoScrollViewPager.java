package com.qf.demo.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/11/4.
 */
public class AutoScrollViewPager extends ViewPager {

    private Handler mHandler;
    private int interval = 2000;
    private int currentIndex = 0;
    private Runnable loopRun = new Runnable() {
        @Override
        public void run() {
            AutoScrollViewPager.this.setCurrentItem(currentIndex++);
            mHandler.postDelayed(loopRun, interval);
        }
    };

    public AutoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mHandler = new Handler();
    }

    public void startScroll() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        mHandler.post(loopRun);
                    }
                }
        ).start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mHandler.removeCallbacks(loopRun);
                break;
            case MotionEvent.ACTION_UP:
                startScroll();
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(
                    0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) {
                height = h;
            }
        }
        if (height != 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
