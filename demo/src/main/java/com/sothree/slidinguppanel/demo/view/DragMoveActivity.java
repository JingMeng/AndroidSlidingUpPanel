package com.sothree.slidinguppanel.demo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.sothree.slidinguppanel.demo.R;

/**
 * 这个布局使用 Linerlayout 会出现问题，但是使用 FrameLayout 是没有问题的
 */
public class DragMoveActivity extends AppCompatActivity {
    private int lastX, lastY;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_move);


        FollowFingerView view1 = findViewById(R.id.drag_move1);
        FollowFingerView view2 = findViewById(R.id.drag_move2);


        view1.setMyOnTouchListener((v, event) -> {
            /**
             * 这个方法最起码是起作用的
             * 但是只要橡胶就发现拖拽不开了
             *
             * 因为这里是根绝view来判断的，event没有参加计算
             */
            Rect rect1 = new Rect(view1.getLeft(), view1.getTop(), view1.getRight(), view1.getBottom());
            Rect rect2 = new Rect(view2.getLeft(), view2.getTop(), view2.getRight(), view2.getBottom());
            boolean intersect = rect1.intersect(rect2);
//            Log.i("DragMoveActivity", "----------两个视图重叠--------" + intersect);


            //计算中心点距离：
            int centerXView1 = (view1.getLeft() + view1.getRight()) / 2;
            int centerYView1 = (view1.getTop() + view1.getBottom()) / 2;

            int centerXView2 = (view2.getLeft() + view2.getRight()) / 2;
            int centerYView2 = (view2.getTop() + view2.getBottom()) / 2;

            double distance = Math.sqrt(Math.pow(centerXView1 - centerXView2, 2) + Math.pow(centerYView1 - centerYView2, 2));

            Log.i("DragMoveActivity", "----------两个视图重叠---distance-----" + distance);

            // 设定阈值
            double threshold = 100.0;
            boolean isClose = distance < threshold;


            //目前返回参数无效
            return intersect;
        });


        /**
         * 项目上暴差动画，圆形，就计算两个圆的距离以及半径的关系
         *
         * fixme 2023年12月12日13:58:01
         *
         * 就是预先判断的问题，但是如何解决快速滑动越界的问题
         * 这个不应该存在
         * 因为得到的 offsetX 和 offsetY 太大导致的吗？颗粒度的问题?
         */

        view2.setMyOnTouchListener((v, event) -> {

//            int x = (int) event.getRawX();
//            int y = (int) event.getRawY();
            int x = (int) event.getX();
            int y = (int) event.getY();

            Rect rect1 = new Rect(view1.getLeft(), view1.getTop(), view1.getRight(), view1.getBottom());
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

                    // 保存当前位置
                    lastX = x;
                    lastY = y;
                    Rect rect2 = new Rect(view2.getLeft() + offsetX, view2.getTop() + offsetY, view2.getRight() + offsetX, view2.getBottom() + offsetY);
                    return rect2.intersect(rect1);
            }
            //默认不拦截
            return false;
        });

    }


    public void test(FollowFingerView view1, FollowFingerView view2) {
        /**
         * 项目上暴差动画，圆形，就计算两个圆的距离以及半径的关系
         */
        view2.setMyOnTouchListener((v, event) -> {

            int x = (int) event.getRawX();
            int y = (int) event.getRawY();
            if (false) {

//                //计算中心点距离：
//                int centerXView1 = (view1.getLeft() + view1.getRight()) / 2;
//                int centerYView1 = (view1.getTop() + view1.getBottom()) / 2;
//
//                //之前如果是接触的，现在给一次机会操作一下
//                //这个是这次的距离
//                double distance = Math.sqrt(Math.pow(centerXView1 - x, 2) + Math.pow(centerYView1 - y, 2));
//
//
//                int centerXView2 = (view2.getLeft() + view2.getRight()) / 2;
//                int centerYView2 = (view2.getTop() + view2.getBottom()) / 2;
//                //因为目前相交，所以就是两个中心点的位置
//                double distanceOld = Math.sqrt(Math.pow(centerXView1 - centerXView2, 2) + Math.pow(centerYView1 - centerYView2, 2));
//
//
//                //距离在变化，并且是变远离
//                boolean canMove = distance - distanceOld > 0;
//
//                lastX = x;
//                lastY = y;
//
//                if (canMove) {
//                    intersect = false;
//                    return false;
//                }

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    return false;
                }

                Log.i("DragMoveActivity", lastX + "----------------" + lastY);

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
                    // 保存当前位置
                    lastX = x;
                    lastY = y;
                    break;
            }
            /**
             * 这个的判断条件和上面是一致的
             */
            boolean b = view1.getRight() > view2.getLeft()
                    && view1.getLeft() < view2.getRight()
                    && view1.getBottom() > view2.getTop()
                    && view1.getTop() < view2.getBottom();

            return b;
        });
    }
}