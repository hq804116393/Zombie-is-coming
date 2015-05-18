package com.HuangQi.Game.model;

import android.view.MotionEvent;

/**
 * Created by Qi on 2015/5/3.
 */
public interface Touchable {
    boolean onTouch(MotionEvent event);
//    boolean onInterceptTouchEvent(MotionEvent event);
}
