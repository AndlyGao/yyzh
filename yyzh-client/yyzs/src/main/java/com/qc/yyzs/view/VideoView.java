package com.qc.yyzs.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class VideoView extends android.widget.VideoView {
    public VideoView(Context context) {
        super(context);
    }

    public VideoView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public VideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }
    public  void setVideoSize(int width,int height){
        ViewGroup.LayoutParams params=getLayoutParams();
        params.width=width;
        params.height=height;

        setLayoutParams(params);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case  MotionEvent.ACTION_DOWN:
//                startY = event.getY();
//            case  MotionEvent.ACTION_MOVE:
//                break;
//            case  MotionEvent.ACTION_UP:
//                break;
//        }
//        return true;
//    }
    private OnTouchVoiceChangeListener o;
    public  interface  OnTouchVoiceChangeListener{
        void OnTouchVoiceChangeListener(int voice);

    }
    private OnTouchVoiceChangeListener onTouchVoiceChangeListener;
    public void setonTouchVoiceChangeListener(OnTouchVoiceChangeListener l){
        o=l;
    }



}
