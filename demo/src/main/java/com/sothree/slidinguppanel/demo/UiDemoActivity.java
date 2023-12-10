package com.sothree.slidinguppanel.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class UiDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ui_demo);

        /**
         * 基本的demo
         */
//        setContentView(R.layout.drag_helper_grid_view);
        /**
         *
         * https://blog.csdn.net/qq_31339141/article/details/107597055
         *
         * 加强版本
         */
//        setContentView(R.layout.drag_listener_grid_view);

        /**
         * 仿照微信的那个
         *
         * 至于换成view，那就是一个 url 的问题，至于原位置有显示的，那就看一下 上面的 drag_listener_grid_view 处理
         *
         * 这个测试的时候，也几乎是一半才能起作用
         *
         * todo：但是微信的朋友圈发布图片是触碰了就会触发
         */
        setContentView(R.layout.drag_to_collect);
        /**
         *
         * 这个效果其实就是不错的
         *
         */
//        setContentView(R.layout.drag_up_down);

        basic1();

        basic();
    }

    private void basic() {
        View view1 = findViewById(R.id.avatarView);
        View view2 = findViewById(R.id.collectorLayout);
        if (view1.getRight() > view2.getLeft()
                && view1.getLeft() < view2.getRight()
                && view1.getBottom() > view2.getTop()
                && view1.getTop() < view2.getBottom()) {
            // 两个视图重叠
            Log.i("UiDemoActivity","-----basic-------------basic----------------");
        } else {
            // 两个视图不重叠
        }
    }

    private void basic1() {
        View view1 = new View(this);
        View view2 = new View(this);
        Rect rect1 = new Rect(view1.getLeft(), view1.getTop(), view1.getRight(), view1.getBottom());
        Rect rect2 = new Rect(view2.getLeft(), view2.getTop(), view2.getRight(), view2.getBottom());

        if (rect1.intersect(rect2)) {
            // 两个视图重叠
        } else {
            // 两个视图不重叠
        }
    }
}