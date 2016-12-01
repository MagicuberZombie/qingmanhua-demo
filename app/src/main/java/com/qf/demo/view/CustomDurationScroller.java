package com.qf.demo.view;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by Administrator on 2016/11/4.
 */
public class CustomDurationScroller extends Scroller {
    private double scrollFactor = 1.0;

    public CustomDurationScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public void setScrollDurationFactor(double factor) {
        this.scrollFactor = factor;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, (int) (duration * this.scrollFactor));
    }
}
