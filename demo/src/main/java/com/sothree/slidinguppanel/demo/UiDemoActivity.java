package com.sothree.slidinguppanel.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
        setContentView(R.layout.drag_listener_grid_view);

        /**
         * 仿照微信的那个
         *
         * 至于换成view，那就是一个 url 的问题，至于原位置有显示的，那就看一下 上面的 drag_listener_grid_view 处理
         */
//        setContentView(R.layout.drag_to_collect);
        /**
         *
         * 这个效果其实就是不错的
         *
         */
//        setContentView(R.layout.drag_up_down);
    }
}