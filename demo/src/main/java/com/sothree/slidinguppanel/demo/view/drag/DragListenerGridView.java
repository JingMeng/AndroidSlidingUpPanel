package com.sothree.slidinguppanel.demo.view.drag;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class DragListenerGridView extends ViewGroup {
    private static final int COLUMNS = 2;
    private static final int ROWS = 3;

    ViewConfiguration viewConfiguration;
    OnDragListener dragListener = new HenDragListener();
    View draggedView;
    List<View> orderedChildren = new ArrayList<>();

    public DragListenerGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewConfiguration = ViewConfiguration.get(context);
        setChildrenDrawingOrderEnabled(true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            /**
             *  初始化位置
             *  默认顺序
             *
             */
            orderedChildren.add(child);
            child.setOnLongClickListener(v -> {
                //点击之后就知道 draggedView
                draggedView = v;
                v.startDrag(null, new DragShadowBuilder(v), v, 0);
                return false;
            });
            child.setOnDragListener(dragListener);
        }
    }

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

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int childLeft;
        int childTop;
        int childWidth = getWidth() / COLUMNS;
        int childHeight = getHeight() / ROWS;
        for (int index = 0; index < count; index++) {
            View child = getChildAt(index);
            childLeft = index % 2 * childWidth;
            childTop = index / 2 * childHeight;
            child.layout(0, 0, childWidth, childHeight);
            child.setTranslationX(childLeft);
            child.setTranslationY(childTop);
        }
    }

    /**
     * 因为这个有自己的区域，还有别人的区域使用的事一半做的标记
     */
    private class HenDragListener implements OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {

//            Log.i("DragListenerGridView", v+"-------------onDrag-----------------"+event);

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    if (event.getLocalState() == v) {
                        v.setVisibility(INVISIBLE);
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

//                    try {
//                        throw new RuntimeException("----------DragEvent.ACTION_DRAG_ENTERED--------------------");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    /**
                     * 进入监听的范围，这个范围写法不一样了，因为本身就在这个范围内 ------------- 这句话是错误的
                     * 这个v并不是当前view本身   是遮盖的那个view  ，可以看 DragToCollectLayout
                     *
                     * 官方文件解释：
                     * 拖动阴影刚刚进入拖动事件监听器的 View 的边界框。这是在拖动阴影进入边界框时，监听器收到的第一个事件操作类型。
                     *
                     *
                     */
                    Log.i("DragListenerGridView", event.getLocalState() + "--------------------" + v + "--------HenDragListener--------" + (event.getLocalState() != v));
                    //排除自己
                    if (event.getLocalState() != v) {
                        sort(v);
                    }
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setVisibility(VISIBLE);
                    break;
            }
            return true;
        }
    }

    private void sort(View targetView) {
        int draggedIndex = -1;
        int targetIndex = -1;
        for (int i = 0; i < getChildCount(); i++) {
            View child = orderedChildren.get(i);
            if (targetView == child) {
                targetIndex = i;
            } else if (draggedView == child) {
                draggedIndex = i;
            }
        }

        Log.i("DragListenerGridView", targetIndex + "--------sort--------" + draggedIndex);
//        if (targetIndex < draggedIndex) {
//            orderedChildren.remove(draggedIndex);
//            orderedChildren.add(targetIndex, draggedView);
//        } else if (targetIndex > draggedIndex) {
//            orderedChildren.remove(draggedIndex);
//            orderedChildren.add(targetIndex, draggedView);
//        }

        orderedChildren.remove(draggedIndex);
        orderedChildren.add(targetIndex, draggedView);

        int childLeft;
        int childTop;
        int childWidth = getWidth() / COLUMNS;
        int childHeight = getHeight() / ROWS;
        for (int index = 0; index < getChildCount(); index++) {
            View child = orderedChildren.get(index);
            childLeft = index % 2 * childWidth;
            childTop = index / 2 * childHeight;
            child.animate()
                    .translationX(childLeft)
                    .translationY(childTop)
                    .setDuration(150);
        }
    }
}
