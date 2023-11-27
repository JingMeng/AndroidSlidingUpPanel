package com.sothree.slidinguppanel.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

public class MainActivity extends AppCompatActivity {

    // 将dp转换为px
    public int dpToPx(float dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = findViewById(R.id.content_view);
        View viewRoot = findViewById(R.id.viewRoot);

        /**
         * 创建上下左右渐渐变大的动画
         *
         * 会变形
         */
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, 0.7f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.9f, 1f);
        ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 0f, 200f);
        ObjectAnimator translationYAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0f, 200f);
        ObjectAnimator translationZAnimator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            translationZAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Z, 0f, 200f);
        }

        // 设置动画持续时间
        int duration = 100000;
        scaleXAnimator.setDuration(duration);
        scaleYAnimator.setDuration(duration);
        translationXAnimator.setDuration(duration);
        translationYAnimator.setDuration(duration);
        translationZAnimator.setDuration(duration);

        // 设置动画插值器，这里使用加速减速插值器
        AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        scaleXAnimator.setInterpolator(interpolator);
        scaleYAnimator.setInterpolator(interpolator);
        translationXAnimator.setInterpolator(interpolator);
        translationYAnimator.setInterpolator(interpolator);
        translationZAnimator.setInterpolator(interpolator);

        /**
         *     extracted(view, viewRoot); 的代码
         */
//        view.post(() -> {
//            final int targetWidth = viewRoot.getWidth();
//            ViewGroup.LayoutParams params = view.getLayoutParams();
//
//            params.width = targetWidth - dpToPx(80);
//        });

        findViewById(R.id.start).setOnClickListener(v -> {
//            extracted(view);
            //设置的数值合理了，就没有之前的拉伸的效果了
//            code(view);
//            scale(view);



            // 创建 ObjectAnimator 实例，view
            ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0f, -view.getHeight());

            // 设置动画的持续时间
            translateAnimator.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));

            // 设置动画的插值器
            translateAnimator.setInterpolator(new android.view.animation.AccelerateInterpolator());

            // 启动动画
            translateAnimator.start();


            //这个感觉也不对
//            extracted(view, viewRoot);
        });

//        translationXAnimator.start();
//        translationYAnimator.start();
    }

    private void code(View view) {
        int duration2 = 8000;
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 0.85f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.9f, 1f);
        scaleX.setDuration(duration2);
        scaleY.setDuration(duration2);

        scaleX.start();
        scaleY.start();
    }

    /**
     * 这个动画效果就可以的 但是可控性没有那么好
     *
     * @param view
     */
    private void scale(View view) {
        // 创建缩放动画
        Animation scaleAnimation = new ScaleAnimation(
                0.85f, // fromXScale
                1.0f, // toXScale
                0.9f, // fromYScale
                1.0f, // toYScale
                Animation.RELATIVE_TO_SELF, 0.5f, // pivotXType, pivotXValue (50% of width)
                Animation.RELATIVE_TO_SELF, 0.5f  // pivotYType, pivotYValue (50% of height)
        );

        // 设置动画持续时间
        scaleAnimation.setDuration(1000);

        // 启动动画
        view.startAnimation(scaleAnimation);
    }

    private void extracted(View view, View viewRoot) {
        // 计算目标宽度（假设为原始宽度的1.5倍）
        final int targetWidth = viewRoot.getWidth();

        // 创建 ValueAnimator 实例，设置目标值
        ValueAnimator valueAnimator = ValueAnimator.ofInt(view.getWidth(), targetWidth);

        // 设置动画插值器
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        // 设置动画时长
        valueAnimator.setDuration(1000);

        // 添加动画更新监听器
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 获取当前动画值，并设置为视图的新宽度
                int animatedValue = (int) animation.getAnimatedValue();
                view.getLayoutParams().width = animatedValue;
                view.requestLayout(); // 通知视图重新布局
            }
        });
        // 启动动画
        valueAnimator.start();
    }

    private void extracted(View view) {
        // 启动动画
//            scaleXAnimator.start();
//            scaleYAnimator.start();
//            translationZAnimator.start();

        // 使用 ViewPropertyAnimator 实现渐渐变大的动画
        view.animate()
                .scaleX(0.5f)
//                    .scaleY(1.5f)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(1000)
                .start();
    }
}