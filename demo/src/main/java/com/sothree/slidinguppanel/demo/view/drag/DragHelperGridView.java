package com.sothree.slidinguppanel.demo.view.drag;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

public class DragHelperGridView extends ViewGroup {
    private static final int COLUMNS = 2;
    private static final int ROWS = 3;

    ViewDragHelper dragHelper;

    public DragHelperGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        dragHelper = ViewDragHelper.create(this, new DragCallback());
    }

    /**
     * 这个是相当简单了，只要敢写就应该没有问题
     *
     * @param widthMeasureSpec  horizontal space requirements as imposed by the parent.
     *                          The requirements are encoded with
     *                          {@link android.view.View.MeasureSpec}.
     * @param heightMeasureSpec vertical space requirements as imposed by the parent.
     *                          The requirements are encoded with
     *                          {@link android.view.View.MeasureSpec}.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        int childWidth = specWidth / COLUMNS;
        int childHeight = specHeight / ROWS;

        measureChildren(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY));

        setMeasuredDimension(specWidth, specHeight);
    }

    /**
     * 这个是相当简单了，只要敢写就应该没有问题
     *
     * @param changed This is a new size or position for this view
     * @param l       Left position, relative to parent
     * @param t       Top position, relative to parent
     * @param r       Right position, relative to parent
     * @param b       Bottom position, relative to parent
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //xml里面写死的数量
        int count = getChildCount();
        int childLeft;
        int childTop;
        int childWidth = getWidth() / COLUMNS;
        int childHeight = getHeight() / ROWS;
        for (int index = 0; index < count; index++) {
            View child = getChildAt(index);
            //因为就只有两个，所以就这么计算了左侧的距离
            childLeft = index % 2 * childWidth;
            //这个高度使用的除数 不是取余
            childTop = index / 2 * childHeight;
            child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private class DragCallback extends ViewDragHelper.Callback {
        float capturedLeft;
        float capturedTop;

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return true;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            if (state == ViewDragHelper.STATE_IDLE) {
                View capturedView = dragHelper.getCapturedView();
                if (capturedView != null) {
                    //这个操作是为了突出显示
                    capturedView.setElevation(capturedView.getElevation() - 1);
                }
            }
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return top;
        }

        @Override
        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
            /**
             *  <uses-sdk
             *         android:minSdkVersion="21"
             *         android:targetSdkVersion="30"
             *         tools:overrideLibrary="android.support.v7.appcompat,android.support.graphics.drawavle" />
             *
             *     这里面有部分报错 受到上面的 AndroidManifest.xml 配置影响，之前的 minSdkVersion 是 14
             *
             *     build.gradle 里面的没有起到修改这部分的作用吗？
             *
             *     这个是打包之后的效果
             *         <uses-sdk
             *               android:minSdkVersion="21"
             *               android:targetSdkVersion="30" />
             *
             *     解决方案：1. 修改      AndroidManifest 里面的      uses-sdk
             *              2. 直接删掉，使用目前  android studio 自动添加的 （目前选择这个方案）
             *                我们使用androidx的方案不需要考虑 support 并且我们观察的，即使设置了，后期也会被gradle 修改
             */
            capturedChild.setElevation(getElevation() + 1);
            capturedLeft = capturedChild.getLeft();
            capturedTop = capturedChild.getTop();
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            //回归到原始的捕获坐标点
            dragHelper.settleCapturedViewAt((int) capturedLeft, (int) capturedTop);
            postInvalidateOnAnimation();
        }
    }
}
