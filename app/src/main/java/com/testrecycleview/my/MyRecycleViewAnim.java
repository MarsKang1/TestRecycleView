package com.testrecycleview.my;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/5/16.
 */

class MyRecycleViewAnim {
    private static final int INIT_DELAY = 500;
    int order = 1;
    private int mWidth;//recycleview的宽度
    private boolean mFirstViewInit = true;
    private int mLastPosition = -1;
    private int mStartDelay;

    MyRecycleViewAnim(int recyclerViewWidth) {
        mWidth = recyclerViewWidth;//mRecyclerView.getResources().getDisplayMetrics().widthPixels
        mStartDelay = INIT_DELAY;
    }

    void onCreateViewHolder(View item, int colum) {
        if (mFirstViewInit) {
            slideInBottom(item, mStartDelay);
            if (order % colum == 0) {
                mStartDelay += 50;
                order = 1;
            } else {
                order++;
            }
        }
    }

    void onBindViewHolder(View item, int position) {
        if (!mFirstViewInit && position > mLastPosition) {
            slideInBottom(item, 0);
            mLastPosition = position;
        }
    }

    private void slideInBottom(final View item, final int delay) {
        item.setTranslationX(mWidth);
//        item.setTranslationY(mWidth);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mWidth);
//        valueAnimator.setInterpolator(new SpringInterpolator());//控制动画运行速率不设置的话 系统默认是先加速后减速这样一种动画执行速率
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mFirstViewInit = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = (mWidth - (float) animation.getAnimatedValue());
                item.setTranslationX(val);
            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.setStartDelay(delay);
        valueAnimator.start();
    }
}
