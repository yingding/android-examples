package com.example.yingding.ui.main;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * set ViewPager no swip
 * Reference:
 * https://stackoverflow.com/questions/28503599/disable-swiping-between-tabs
 *
 * Author: Yingding Wang
 * Created: 29.April.2019
 */
public class NoSwipViewPager extends ViewPager {

    private boolean swipEnabled;

    public NoSwipViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.swipEnabled = false; // deactivate swip touch event by default
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.swipEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.swipEnabled && super.onInterceptTouchEvent(event);
    }

    public void setSwipEnabled(boolean enabled) {
        this.swipEnabled = enabled;
    }

    public boolean isSwipEnabled() {
        return this.swipEnabled;
    }
}
