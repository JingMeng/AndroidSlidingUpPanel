package com.sothree.slidinguppanel.demo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class FollowFingerView extends View {

    private int lastX, lastY;

    public FollowFingerView(Context context) {
        super(context);
        init();
    }

    public FollowFingerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private OnTouchListener onTouchListener;

    public void setMyOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }


    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        setOnTouchListener((v, event) -> {
            int x = (int) event.getRawX();
            int y = (int) event.getRawY();

//            int x = (int) event.getX();
//            int y = (int) event.getY();


            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // 记录手指按下时的位置
                    lastX = x;
                    lastY = y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (null != onTouchListener) {
                        boolean b = onTouchListener.onTouch(v, event);
                        if (b) {
                            //不允许重叠的操作，如果重叠了，啥也不做，就是消耗事件
                            return b;
                        }
                    }
                    // 计算手指移动的距离
                    int offsetX = x - lastX;
                    int offsetY = y - lastY;

                    // 更新View的位置
//                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
//                    layoutParams.leftMargin = getLeft() + offsetX;
//                    layoutParams.topMargin = getTop() + offsetY;
//                    setLayoutParams(layoutParams);
                    offsetLeftAndRight(offsetX);
                    offsetTopAndBottom(offsetY);
                    // 保存当前位置
                    lastX = x;
                    lastY = y;
                    break;
            }
            return true;
        });
    }
}
