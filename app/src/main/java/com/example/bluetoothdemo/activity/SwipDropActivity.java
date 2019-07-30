package com.example.bluetoothdemo.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bluetoothdemo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_IDLE;

/**
 * 拖拽RecyclerView
 */
public class SwipDropActivity extends Activity {

    RecyclerView recyclerView;
    ItemTouchHelper touchHelper;   //ItemTouchHelper 一个工具类 用来处理对RecyclerView中每一个item的拖拽事件
    private List results = new ArrayList();
    private MyAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        recyclerView = findViewById(R.id.img_recyclerview);

        for (int i = 1 ;i < 36 ;i ++){
            results.add(i);
        }
        adapter = new MyAdapter(results,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        initItemTouchHolder();
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        private List<Integer> datas;
        private Context context;

        public MyAdapter(List<Integer> datas, Context context) {
            this.datas = datas;
            this.context = context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_txt,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            if (holder!=null){
                holder.itemTxt.setText(datas.get(position) + "");
            }
        }

        @Override
        public int getItemCount() {
            return datas==null?0:datas.size();
        }
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView itemTxt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTxt = itemView.findViewById(R.id.item_txt);
        }
    }


    private void initItemTouchHolder(){
        touchHelper = new ItemTouchHelper(new DrapOrSwipCallback());
        touchHelper.attachToRecyclerView(recyclerView);
    }


    private class DrapOrSwipCallback extends ItemTouchHelper.Callback{
        /**
         * 用于设置是否处理拖拽和滑动事件，以及拖拽和滑动操作的方向    根据RecyclerView的类型（列表还是网格）有不同的方法 比如
         * 列表只有UP  DOWN（或者横向的LEFT RIGHT） 网格则四个都有
         * @param recyclerView
         * @param viewHolder
         * @return
         */
        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            int dragFlags = 0;  //拖拽标志
            int swipFlags = 0;  //滑动标志  为0 代表不处理
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager){//网格
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                swipFlags = 0;
            }
            else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager){  //列表   允许滑动处理
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                swipFlags = ItemTouchHelper.START;
            }
            return makeMovementFlags(dragFlags,swipFlags);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
            int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
            if (fromPosition < toPosition) {  //向后移动
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(results, i, i + 1);
                }
            } else {  //向前移动
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(results, i, i - 1);
                }
            }
            adapter.notifyItemMoved(fromPosition,toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            results.remove(viewHolder.getAdapterPosition());
            adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);



        }

        /**
         * 长按选中时调用
         * @param viewHolder
         * @param actionState
         *         当前Item的状态
         *         ACTION_STATE_IDLE   闲置
         *         ACTION_STATE_SWIPE  滑动中
         *         ACTION_STATE_DRAG   拖拽中
         */
        @Override
        public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ACTION_STATE_IDLE){
                viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        /**
         * 松开时调用
         * @param recyclerView
         * @param viewHolder
         */
        @Override
        public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setBackgroundColor(0);
        }
    }
}
