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
         * 加强版本
         */
        setContentView(R.layout.drag_listener_grid_view);

        /**
         * 仿照微信的那个
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