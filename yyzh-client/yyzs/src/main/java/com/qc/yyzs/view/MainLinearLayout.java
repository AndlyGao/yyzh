package com.qc.yyzs.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class MainLinearLayout extends LinearLayout{
    public MainLinearLayout(Context context) {
        this(context, null);
    }

    public MainLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private float startX;
    private float startY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //        super.onTouchEvent(event);
        Log.e("start", "start");
        switch (event.getAction()) {
            case  MotionEvent.ACTION_DOWN:
                startX=event.getX();
                Log.e("startX","startX"+startX);
                break;
            case  MotionEvent.ACTION_MOVE:
                float endX=event.getX();
                if((endX-startX)>30){
                    Log.e("endX", "endX" + endX);
//                    drawer.openDrawer(GravityCompat.START);
                    if(lisener!=null){
                        lisener.invoke();
                    }
                }

                break;
            case  MotionEvent.ACTION_UP:

                break;
        }


        return true;
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                Log.e("startX", "startX" + startX);
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = event.getX();
                if ((endX - startX) > 30) {
                    Log.e("endX", "endX" + endX);
                    //                    drawer.openDrawer(GravityCompat.START);
                    if (lisener != null) {
                        lisener.invoke();
                    }
                    return true;
                }

                break;
            case MotionEvent.ACTION_UP:

                break;


        }
            return false;
    }

    public interface LLLisener{
        void invoke();
    }
    private LLLisener lisener;
    public void setlinister(LLLisener lisener){
        this.lisener=lisener;
    }
}
