package com.example.bluetoothdemo.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.bluetoothdemo.R;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class TimeLineDecoration extends RecyclerView.ItemDecoration {

    private Context context;
    private int distance;

    private Drawable verticalLine;
    private Drawable horizontaLine;
    private Drawable drawable;

    public TimeLineDecoration(Context context, int distance) {
        this.context = context;
        this.distance = distance;
        verticalLine = ContextCompat.getDrawable(context, R.drawable.gray_line);
        drawable = ContextCompat.getDrawable(context, R.drawable.time);
        horizontaLine = ContextCompat.getDrawable(context, R.drawable.horizontal_line);
    }

    /**
     * 设置item的偏移量
     * @param outRect   偏移量保存的矩阵
     * @param view  子View（也就是每个item）
     * @param parent 父View对应的RecyclerView
     * @param state
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = distance;
        outRect.right = distance;
        outRect.bottom = 3 * distance;
        if (parent.getChildAdapterPosition(view) == 0){  //第一个边距距离顶部为 distance
            outRect.top = distance;
        }else if (parent.getChildAdapterPosition(view) == 1){  //第二个 4*distance （这里针对GridLayout  且一行两个）
            outRect.top = 4 * distance;
        }
    }


    /**
     * 绘制偏移量范围内的内容   (比如时间轴效果的话   比如drawable 里的time_axis效果图
     * 绘制每个item中的时钟与线条(在绘制每一个itemView之前执行)
     * @param canvas  画布
     * @param parent  对应的RecyclerView
     * @param state
     */
    @Override
    public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(canvas, parent, state);
        canvas.save();
        int top;
        int bottom;
        int left;
        int right;
        if (parent.getClipToPadding()){  //判断是否有边距

            top = parent.getPaddingTop();  //顶部坐标
            bottom = parent.getHeight() - parent.getPaddingBottom(); //底部坐标
            left = parent.getPaddingLeft();  //左边距
            right = parent.getWidth() - parent.getPaddingRight();  //右边距
            canvas.clipRect(left,top,right,bottom); //切割一个Rect  //设置画布区域

        }else {

            top = 0;
            bottom = parent.getHeight();

        }

        /**
         * 绘制一整条时间轴  （整个itemView的中央）
         */
        int parentWidth = parent.getWidth() - parent.getPaddingLeft() - parent.getPaddingRight();//view的宽度
        verticalLine.setBounds(parentWidth/2 -1, top , parentWidth / 2 + 1, bottom);  //设置drawable 将被绘制在哪个区域内
        verticalLine.draw(canvas);

        int childCount = parent.getChildCount();
        Rect mBounds = new Rect();
        Rect decorationRect = new Rect(0,0,0,0);
        for (int i = 0 ; i < childCount ; i ++){
            View child = parent.getChildAt(i);
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child,mBounds);  //得到此itemView对应的(位置矩阵)
            /**
             * 计算对应位置
             */
            //根据所处item计算出要绘制横线与时钟的位置
            if (parent.getChildAdapterPosition(child) %2 == 0){ //左边
                decorationRect.left = child.getRight();
                decorationRect.top = child.getTop() + child.getHeight() / 3;  //左边处于三分之一位置
                decorationRect.right = mBounds.right;   //itemView 的右位置
                decorationRect.bottom = decorationRect.top + 1;
            }else if (parent.getChildAdapterPosition(child) %2 == 1){ //→_→
                decorationRect.left = parentWidth/2;
                decorationRect.top = child.getTop() + child.getHeight() / 3;
                decorationRect.right  = child.getLeft();
                decorationRect.bottom = decorationRect.top + 1;
            }
            horizontaLine.setBounds(decorationRect);
            horizontaLine.draw(canvas);

            /**
             * 绘制时钟
             */
            int w = drawable.getIntrinsicWidth();
            int h = drawable.getIntrinsicHeight();
            decorationRect.left = parentWidth / 2 - w / 2;
            decorationRect.right = decorationRect.left + w;
            decorationRect.top = child.getTop() + child.getHeight() / 3 - h / 2;
            decorationRect.bottom = decorationRect.top + h;
            drawable.setBounds(decorationRect);
            drawable.draw(canvas);
        }
        canvas.restore();
    }


    /**
     * 再整个RecyclerView上绘制内容 会覆盖其他所有内容
     * 在绘制完整个view后执行
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
