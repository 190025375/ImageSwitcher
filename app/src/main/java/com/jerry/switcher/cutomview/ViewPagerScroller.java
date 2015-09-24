package com.jerry.switcher.cutomview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Created by jerry on 2015/9/23.
 */
public class ViewPagerScroller extends Scroller {
    private int mScrollDuration = 2000;             // 滑动速度，默认是2秒

    public ViewPagerScroller(Context context) {
        super(context);
    }
    
    public ViewPagerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }

    public void initViewPagerScroll(ViewPager viewPager, int duration) {
        this.mScrollDuration = duration;
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(viewPager, this);
        } catch(Exception e) {
            Log.e("ViewPagerScroller error", "ViewPagerScroller initViewPagerScroll error" + e.getMessage());
        }
    }
}