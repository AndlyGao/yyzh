package com.qc.yyzs.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.qc.yyzs.entity.MessageEvent;
import com.qc.yyzs.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class XCRoundImageView extends AppCompatImageView {
    private float width;
    private float height;
    private float radius;
    private Paint paint;
    private Matrix matrix;

    public XCRoundImageView(Context context) {
        this(context, null);

    }

    public XCRoundImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XCRoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);   //设置抗锯齿
        matrix = new Matrix();
            //初始化缩放矩阵
        EventBus.getDefault().register(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toteach(MessageEvent messageEvent) {
        if(messageEvent.getMessage()=="rotate"){
            RotateAnimation rotate = getRotateAnimation();this.setAnimation(rotate);
        }
        if( messageEvent.getMessage()==  "stoprotate"){
            this.clearAnimation();
        }
    }


    /**
     * 测量控件的宽高，并获取其内切圆的半径
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        radius = Math.min(width, height) / 2;
    }
    Canvas canvas;
    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas=canvas;
        Drawable drawable = getDrawable();
        if (drawable == null) {
            super.onDraw(canvas);
            return;
        }
        if (drawable instanceof BitmapDrawable) {
            paint.setShader(initBitmapShader((BitmapDrawable) drawable));//将着色器设置给画笔
            canvas.drawCircle(width / 2, height / 2, radius, paint);//使用画笔在画布上画圆
            return;
        }

//                           this.clearAnimation();
        super.onDraw(canvas);
    }

    /**
     * 获取ImageView中资源图片的Bitmap，利用Bitmap初始化图片着色器,通过缩放矩阵将原资源图片缩放到铺满整个绘制区域，避免边界填充
     */
    private BitmapShader initBitmapShader(BitmapDrawable drawable) {
        Bitmap bitmap = drawable.getBitmap();
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = Math.max(width / bitmap.getWidth(), height / bitmap.getHeight());
        matrix.setScale(scale, scale);//将图片宽高等比例缩放，避免拉伸
        bitmapShader.setLocalMatrix(matrix);


        return bitmapShader;
    }
    float startX;
    float startY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //        super.onTouchEvent(event);
        switch (event.getAction()) {
            case  MotionEvent.ACTION_DOWN:
                startX=event.getX();
                startY=event.getY();
                Log.e("startX","startX"+startX);
                break;
            case  MotionEvent.ACTION_MOVE:
                float endX=event.getX();
                float endY=event.getY();
                float distanceY = startY - endY;
                float distanceX = startX - endX;
                if(Math.max(distanceX,distanceY) > 10) {
                    Log.e("endX", "endX" + endX);
                    this.clearAnimation();
                    setRotation(Math.max(distanceX,distanceY)/1);
//                    this.scrollTo(5,5);
//                    Log.e("dis",distance+"");
//                    RotateAnimation rotate  = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//                    LinearInterpolator lin = new LinearInterpolator();
//                    rotate.setInterpolator(lin);
//                    rotate.setDuration(500);//设置动画持续周期
//                    rotate.setRepeatCount(0);//设置重复次数
//                    rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
//                    rotate.setStartOffset(10);//执行前的等待时间
//                    this.setAnimation(rotate);
//                    this.clearAnimation();

//                    setRotation(Math.max(distanceX,distanceY)/2);
                    EventBus.getDefault().post(new Utils());
                    startX=endX;
                    startY=endY;
                    invalidate();
                }

                break;
            case  MotionEvent.ACTION_UP:

                RotateAnimation rotate = getRotateAnimation();
                this.setAnimation(rotate);


                break;
        }


        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        RotateAnimation rotate = getRotateAnimation();
//        this.setAnimation(rotate);
    }

    @NonNull
    private RotateAnimation getRotateAnimation() {
        RotateAnimation rotate  = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        LinearInterpolator lin = new LinearInterpolator();
        rotate.setInterpolator(lin);
        rotate.setDuration(5000);//设置动画持续周期
        rotate.setRepeatCount(-1);//设置重复次数
        rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        rotate.setStartOffset(10);//执行前的等待时间
        return rotate;
    }
}
