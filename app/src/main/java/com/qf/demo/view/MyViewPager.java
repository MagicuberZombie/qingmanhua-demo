package com.qf.demo.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.qf.demo.fragment.BookShelfFragment;

/**
 * Created by Administrator on 2016/11/3.
 */
public class MyViewPager extends ViewPager {

    private boolean result = true;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (result) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (result) {
            return super.onTouchEvent(ev);
        }
        return false;
    }

    public void setScrollable(boolean scrollable) {
        result = scrollable;
    }


}
