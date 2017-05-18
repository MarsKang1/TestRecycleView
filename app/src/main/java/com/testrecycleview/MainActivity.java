package com.testrecycleview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.testrecycleview.my.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //testAnim();
        /*rv_main= (RecyclerView) findViewById(R.id.recycler_view);
        //设置布局管理器
        rv_main.setLayoutManager(new GridLayoutManager(this,1));
        rv_main.scheduleLayoutAnimation();
        List<String> items=new ArrayList<>();
        for (int i=0;i<100;i++){
            items.add("哈哈"+i);
        }
        MyAdapter adapter=new MyAdapter(this,items,rv_main);
        rv_main.setAdapter(adapter);*/

        testDot();
    }

    private void testDot() {
         final LinearLayout mPoint_1;
         final LinearLayout mPoint_2;
         final LinearLayout mPoint_3;
         final LinearLayout mPoint_4;
        mPoint_1 = (LinearLayout) findViewById(R.id.ll_point_circle_1);
        mPoint_2 = (LinearLayout) findViewById(R.id.ll_point_circle_2);
        mPoint_3 = (LinearLayout) findViewById(R.id.ll_point_circle_3);
        mPoint_4 = (LinearLayout) findViewById(R.id.ll_point_circle_4);

        Button startAni = (Button) findViewById(R.id.start_ani_2);
        startAni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator_1 = ObjectAnimator.ofFloat(mPoint_1, "rotation", 0, 360);
                animator_1.setDuration(2000);
                animator_1.setInterpolator(new AccelerateDecelerateInterpolator());
                animator_1.start();

                /*ObjectAnimator animator_2 = ObjectAnimator.ofFloat(
                        mPoint_2,
                        "rotation",
                        0,
                        360);
                animator_2.setStartDelay(150);
                animator_2.setDuration(2000 + 150);
                animator_2.setInterpolator(new AccelerateDecelerateInterpolator());

                ObjectAnimator animator_3 = ObjectAnimator.ofFloat(
                        mPoint_3,
                        "rotation",
                        0,
                        360);
                animator_3.setStartDelay(2 * 150);
                animator_3.setDuration(2000 + 2 * 150);
                animator_3.setInterpolator(new AccelerateDecelerateInterpolator());

                ObjectAnimator animator_4 = ObjectAnimator.ofFloat(
                        mPoint_4,
                        "rotation",
                        0,
                        360);
                animator_4.setStartDelay(3 * 150);
                animator_4.setDuration(2000 + 3 * 150);
                animator_4.setInterpolator(new AccelerateDecelerateInterpolator());

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(animator_1).with(animator_2).with(animator_3).with(animator_4);
                animatorSet.start();*/
            }
        });
    }










       /* final ImageView dot= (ImageView) findViewById(R.id.dot);
        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator_1 = ObjectAnimator.ofFloat(dot, "rotation", 0, 360);
                animator_1.setDuration(2000);
                animator_1.setInterpolator(new AccelerateDecelerateInterpolator());
                animator_1.start();
            }
        });


    }*/

    private void testAnim() {
        /*final ImageView iv_main= (ImageView) findViewById(R.id.iv_main);
        iv_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DouAnim anim=new DouAnim();
                anim.setDuration(2000);
                anim.setRepeatCount(2);
                iv_main.startAnimation(anim);
            }
        });*/

        /*AlphaAnimation alpha=new AlphaAnimation(0,1);//透明度变化动画
        alpha.setDuration(2000);// 透明度动画

        RotateAnimation rotate=new RotateAnimation(0,360,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);// 旋转动画
        rotate.setDuration(2000);

        ScaleAnimation scale=new ScaleAnimation(1.5f,1f,1.5f,1f);
        scale.setDuration(2000);

        TranslateAnimation translate = new TranslateAnimation(0, 160, 0, 240); // 位移动画
        translate.setDuration(2000);

        AnimationSet set=new AnimationSet(false);//是否共享插值器
        set.addAnimation(alpha);
        set.addAnimation(rotate);
        set.addAnimation(scale);
        set.addAnimation(translate);
        iv_main.startAnimation(set);*/



    }


    class DouAnim extends Animation {







        //interpolatedTime 表示当前动画进行的时间与动画总时间 从0逐渐增大到1
        //t 传递当前动画对象
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            Log.e("--------->",String.valueOf( (float) Math.sin(interpolatedTime * 50) * 8));
            t.getMatrix().setTranslate(
                    (float) Math.sin(interpolatedTime * 50) * 8,
                    (float) Math.sin(interpolatedTime * 50) * 8
            );// 50越大频率越高，8越小振幅越小

            super.applyTransformation(interpolatedTime, t);
        }
    }





}























