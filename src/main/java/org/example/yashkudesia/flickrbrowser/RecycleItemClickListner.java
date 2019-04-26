package org.example.yashkudesia.flickrbrowser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

class RecycleItemClickListner extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "RecycleItemClickListner";
    private final OnRecycleClickListner listner;
    private final GestureDetectorCompat gestureDetector;

    public RecycleItemClickListner(Context context, final RecyclerView recyclerView, OnRecycleClickListner listner) {
        this.listner = listner;
        gestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                return super.onSingleTapUp(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent: starts");
        if (gestureDetector != null) {
            boolean result = gestureDetector.onTouchEvent(e);
            Log.d(TAG, "onInterceptTouchEvent: returned-:" + result);
            return result;
        } else {
            Log.d(TAG, "onInterceptTouchEvent: returned false");
            return false;
        }
    }

    interface OnRecycleClickListner {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }
}
