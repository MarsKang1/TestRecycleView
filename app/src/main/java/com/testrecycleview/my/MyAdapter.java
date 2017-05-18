package com.testrecycleview.my;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testrecycleview.R;
import com.testrecycleview.my.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */

public class MyAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private List<String> dates;
    private MyRecycleViewAnim mReboundAnimator;
    private int mColumn = 1;

    public MyAdapter(Context context, List<String> dates, RecyclerView recyclerView) {
        this.context = context;
        mReboundAnimator = new MyRecycleViewAnim(recyclerView.getResources().getDisplayMetrics().widthPixels);
        this.dates = dates;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv, parent, false);
        mReboundAnimator.onCreateViewHolder(viewGroup, mColumn);
        return new BaseViewHolder(context, viewGroup);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        //绑定数据
        holder.setText(R.id.tv_content, dates.get(position));
        mReboundAnimator.onBindViewHolder(holder.itemView, position);
       /* //编辑模式
        holder.views.get(R.id.delete).setVisibility(isEdit ? View.VISIBLE : View.GONE);
        //点击事件
        holder.views.get(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                remove(position);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }
}
