package com.example.bluetoothdemo.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bluetoothdemo.R;
import com.example.bluetoothdemo.activity.GameImageView;
import com.example.bluetoothdemo.activity.RenderingView;
import com.example.bluetoothdemo.utils.MyLinearLayoutManager;
import com.example.bluetoothdemo.utils.TimeLineDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class LoginActivity extends Activity {


    ListView parentListView;
    RecyclerView childRecyclerView;
    List<ParentBean> parentsList = new ArrayList<>();
    List<ChildBean> childBeans = new ArrayList<>();
    RecyclerView imgRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imgRecyclerView = findViewById(R.id.img_recyclerview);


        imgRecyclerView.addItemDecoration(new TimeLineDecoration(this,50));

        ArrayList<Integer> imgRes = new ArrayList<>();
        imgRes.add(R.drawable.she);
        imgRes.add(R.drawable.fairy);
        imgRes.add(R.drawable.she);
        imgRes.add(R.drawable.fairy);
        imgRes.add(R.drawable.she);
        imgRes.add(R.drawable.fairy);
        imgRes.add(R.drawable.she);
        imgRes.add(R.drawable.fairy);
        imgRes.add(R.drawable.she);
        imgRes.add(R.drawable.fairy);
        imgRes.add(R.drawable.she);
        imgRes.add(R.drawable.fairy);
        imgRes.add(R.drawable.she);
//        imgRes.add(R.drawable.fairy);
//        imgRes.add(R.drawable.fairy);
//        imgRes.add(R.drawable.fairy);
//        imgRes.add(R.drawable.fairy);
//        imgRes.add(R.drawable.fairy);
//        imgRes.add(R.drawable.fairy);
//        imgRes.add(R.drawable.fairy);
//        imgRes.add(R.drawable.fairy);
//        imgRes.add(R.drawable.fairy);
//        imgRes.add(R.drawable.fairy);
//        imgRes.add(R.drawable.fairy);
        imgRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        imgRecyclerView.setAdapter(new ImageAdapter(this,imgRes));

//        imgRecyclerView.setAdapter(adapter);

//        initDatas();
//
//        parentListView = findViewById(R.id.first_menu);
//        childRecyclerView = findViewById(R.id.second_childs);
//
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                if (childBeans.get(position).isParnet()){
//                    return 3;
//                }else {
//                    return 1;
//                }
//            }
//        });
//        childRecyclerView.setLayoutManager(new MyLinearLayoutManager(this));
//        ChildAdapter childAdapter = new ChildAdapter(this,childBeans);
//        childRecyclerView.setAdapter(childAdapter);
//        childRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            private int oldParent = -1;
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                ChildBean childBean = childBeans.get(manager.findFirstVisibleItemPosition());
//                int parent = childBean.getParentId();
//                if (parent!=oldParent){
//                    oldParent = parent;
//                }
//
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });
//
//
//        ParentAdapter adapter = new ParentAdapter(this, parentsList);
//        parentListView.setAdapter(adapter);
//        parentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ParentBean bean = (ParentBean) parent.getItemAtPosition(position);
//                int childId = bean.getChildTypeId();
//                childRecyclerView.smoothScrollToPosition(childId);
//                LinearLayoutManager manager = (LinearLayoutManager) childRecyclerView.getLayoutManager();
//                manager.smooth
//            }
//        });
//
//        int color = Color.argb(0,0,0,0.3f);
//        Log.e("颜色",Integer.toHexString(color));
//
//        findViewById(R.id.scan).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setAction("com.google.zxing.client.android.scan");
//                intent.addCategory(Intent.CATEGORY_DEFAULT);
//                startActivity(intent);
//            }
//        });
//        final GameImageView renderingView = findViewById(R.id.render);
//        renderingView.post(new Runnable() {
//            @Override
//            public void run() {
//                renderingView.start();
//            }
//        });
//        renderingView.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.timg));
    }


    private void initDatas() {
        Random random = new Random();
        int parentCount = random.nextInt(10) + 20;
        int sum = 0;
        for (int i = 0; i < parentCount; i++) {
            ParentBean parentBean = new ParentBean(i, "A" + i);
            int childCount = random.nextInt(parentCount) + 10;
            for (int j = 0; j < childCount; j++) {
                ChildBean childBean = null;
                if (j == 0) {
                    childBean = new ChildBean(i, j, i + "B" + j, ChildBean.PARENT);
                    parentBean.setChildTypeId(sum);
                } else {
                    childBean = new ChildBean(i, j, i + "B" + j, ChildBean.PARENT);
                }
                childBeans.add(childBean);
            }
            sum = sum + childCount;
            parentsList.add(parentBean);
        }
    }

    public static final int TYPE_PARENT = 1;
    public static final int TYPE_CHILD = 2;

    public class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder>{

        private ArrayList<Integer> imgRes;
        private Context context;

        public ImageAdapter(Context context , ArrayList<Integer> imgRes) {
            this.imgRes = imgRes;
            this.context = context;
        }

        @NonNull
        @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_img,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            if (holder!=null){
                holder.itemImage.setImageResource(imgRes.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return imgRes.size();
        }
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder{

        ImageView itemImage;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_img);
        }
    }

    public class ChildAdapter extends RecyclerView.Adapter<ViewHolder> {
        private List<ChildBean> datas;
        private Context context;

        public ChildAdapter(Context context,List<ChildBean> datas) {
            this.datas = datas;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ViewHolder viewHolder = null;
            View view = LayoutInflater.from(context).inflate(R.layout.item_child_child,parent,false);
//            switch (viewType) {
//                case TYPE_CHILD:
                    viewHolder = new ViewHolder(view);
//                    break;
//                case TYPE_PARENT:
//                    viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_child_child,parent));
//                    break;
//            }
            return viewHolder;
        }

        @Override
        public int getItemViewType(int position) {
            if (datas.get(position).getType() == ChildBean.CHILD) {
                return TYPE_CHILD;
            } else {
                return TYPE_PARENT;
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (holder!=null){
                holder.bind(datas.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return datas == null ? 0 : datas.size();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
        }

        public void bind(ChildBean bean){
            title.setText(bean.getTitle());
        }
    }


    class ParentAdapter extends BaseAdapter {

        private List<ParentBean> datas;
        private Context context;

        public ParentAdapter(Context context, List<ParentBean> datas) {
            this.datas = datas;
            this.context = context;
        }

        @Override
        public int getCount() {
            return datas == null ? 0 : datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas != null && datas.size() > 0 ? datas.get(position) : null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_parent, parent, false);
            TextView tvParent = view.findViewById(R.id.tv_parent);
            ParentBean bean = (ParentBean) getItem(position);
            tvParent.setText(bean.getTitle());
            return view;
        }
    }
}
