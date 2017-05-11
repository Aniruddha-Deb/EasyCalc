package com.sensei.easycalc.ui;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ViewFlipper;

public class BottomViewFlipper extends ViewFlipper implements GestureDetector.OnGestureListener{

    private GestureDetectorCompat gestureDetector = null;

    float swipeX1, swipeX2;
    float distance = 150;

    public BottomViewFlipper(Context context) {
        super( context );
        gestureDetector = new GestureDetectorCompat( context, this );
    }

    public BottomViewFlipper(Context context, AttributeSet attrs) {
        super( context, attrs );
        gestureDetector = new GestureDetectorCompat( context, this );
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent( event );
        return super.onTouchEvent( event );
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        gestureDetector.onTouchEvent( ev );
        return super.onInterceptTouchEvent( ev );
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.d( "BottomViewFlipper", "LEFT or RIGHT swipe detected" );
        if( v <= v1 ) {
            setDisplayedChild( getDisplayedChild()+1 );
        }
        else {
            setDisplayedChild( getDisplayedChild()-1 );
        }
        return true;
    }
}
