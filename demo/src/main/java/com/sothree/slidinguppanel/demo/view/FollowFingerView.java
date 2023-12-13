package com.sothree.slidinguppanel.demo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
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

    private int maxRight;
    private int maxBottom;

    public void setMaxRight(int maxRight) {
        this.maxRight = maxRight;
    }

    public void setMaxBottom(int maxBottom) {
        this.maxBottom = maxBottom;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        setOnTouchListener((v, event) -> {
            int x = (int) event.getRawX();
            int y = (int) event.getRawY();

//            int x = (int) event.getX();
//            int y = (int) event.getY();

            if (null != onTouchListener) {
                boolean b = onTouchListener.onTouch(v, event);
                if (b) {
                    //不允许重叠的操作，如果重叠了，啥也不做，就是消耗事件
                    return b;
                }
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // 记录手指按下时的位置
                    lastX = x;
                    lastY = y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    // 计算手指移动的距离
                    int offsetX = x - lastX;
                    int offsetY = y - lastY;

                    // 更新View的位置
//                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
//                    layoutParams.leftMargin = getLeft() + offsetX;
//                    layoutParams.topMargin = getTop() + offsetY;
//                    setLayoutParams(layoutParams);

                    int left = getLeft() + offsetX;
                    int right = getRight() + offsetX;

                    int top = getTop() + offsetY;
                    int bottom = getBottom() + offsetY;

                    /**
                     * 边界探测，探测是别超出屏幕的四周
                     */

                    if (false) {
                        left = Math.min(left, maxRight - getWidth());
                        left = Math.max(left, 0);
                        //最大为 maxRight-getWidth()
                        right = Math.min(right, maxRight);
                        right = Math.max(right, 0);

                        top = Math.max(0, top);
                        top = Math.min(top, maxBottom);

                        bottom = Math.min(bottom, maxBottom);
                        bottom = Math.max(bottom, 0);
                        //上面越界会导致界面的改动----就是大小的改变
                    }

                    if (true) {
                        //这个就可以预防上下
                        if (top < 0) {
                            top = 0;
                            bottom = getHeight();
                        }
                        if (bottom > maxBottom) {
                            bottom = maxBottom;
                            top = maxBottom - getHeight();
                        }

                        if (left < 0) {
                            left = 0;
                            right = getWidth();
                        }
                        if (right > maxRight) {
                            right = maxRight;
                            left = maxRight - getWidth();
                        }
                    }

                    layout(left, top, right, bottom);

//                    offsetLeftAndRight(offsetX);
//                    offsetTopAndBottom(offsetY);

                    // 保存当前位置
                    lastX = x;
                    lastY = y;
                    break;
            }
            return true;
        });
    }
}
