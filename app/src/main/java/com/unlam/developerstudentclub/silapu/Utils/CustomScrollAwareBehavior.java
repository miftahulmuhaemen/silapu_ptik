package com.unlam.developerstudentclub.silapu.Utils;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

public class CustomScrollAwareBehavior extends FloatingActionButton.Behavior{

    private Handler handler = new Handler();
    private FloatingActionButton fab;

    public CustomScrollAwareBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        fab = child;
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    Runnable showRunnable = new Runnable() {
        @Override
        public void run() {
            fab.show();
        }
    };

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
            handler.removeCallbacks(showRunnable);
            handler.postDelayed(showRunnable,1000);
            child.hide();
        }
    }
}

