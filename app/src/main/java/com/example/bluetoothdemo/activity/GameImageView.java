package com.example.bluetoothdemo.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.bluetoothdemo.R;

import androidx.annotation.Nullable;

public class GameImageView extends View {

    private Rect mSrc;
    private RectF mDesc;
    private Bitmap bitmap;
    private ObjectAnimator mAnimator;
    private Paint mPaint;

    private float radio;

    private float itemWidth;
    private float add;

    public void setRadio(float radio) {
        this.radio = radio;
        add((int) (radio*add));
    }

    public GameImageView(Context context) {
        super(context);
    }

    public GameImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GameImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap,mSrc,mDesc,mPaint);
    }

    public void start(){
        mAnimator.start();
    }

    private float c ;
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.timg);
        c = getHeight()*1f/bitmap.getHeight();
        itemWidth = getWidth()*1f/c;
        Log.e("大小比例",getWidth()+"," + getHeight() + " 比较 " + bitmap.getWidth() + "," + bitmap.getHeight());
        mSrc = new Rect(0,0,(int)itemWidth,getHeight());
        mDesc = new RectF(0,0,getWidth(),getHeight());
//        mSrc = new Rect(0,0,)

        add = (bitmap.getWidth() - itemWidth)*1f/100;
;        mAnimator = ObjectAnimator.ofFloat(this, "radio", 0,100);
        mAnimator.setDuration(20*1000);
    }

    public void add(int add_wdith){
        if (add_wdith + itemWidth < bitmap.getWidth()) {
            mSrc = new Rect(0 + add_wdith, 0, (int) (add_wdith + itemWidth), getHeight());
            postInvalidate();
        }
    }

    public static RectF getViewRect(View view) {
        int vLeft, vTop;
        int location[] = new int[2];
        view.getLocationInWindow(location);
        vLeft = location[0];
        vTop = location[1];
        RectF mViewRect = new RectF(vLeft, vTop, vLeft + view.getWidth(), vTop + view.getHeight());
        return mViewRect;
    }
}
