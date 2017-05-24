package com.testrecycleview.pulltorefresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/5/23.
 * 共通适配器
 */
public class MultyItemAdapter<T> extends RecyclerView.Adapter<MyViewHolder> {
    protected Context mContext;
    protected List<T> mDatas;
    protected ItemViewDelegateManager daiLiManager;//单条布局的管理类
    protected OnItemClickListener mOnItemClickListener;//点击或者长按事件的处理

    public MultyItemAdapter(Context mContext, List<T> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        daiLiManager = new ItemViewDelegateManager();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //跟其他不同的是view的id是从daiLiManager中获取的非共通的是直接写上R.layout.XXX
        MyViewHolder holder = MyViewHolder.createViewHolder(mContext, parent, daiLiManager.getItemViewDelegate(viewType).getItemViewLayoutId());
        setListener(holder, viewType);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //绑定数据到ItemViewDelegateManager中
        daiLiManager.convert(holder,  mDatas.get(position), holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @SuppressWarnings("unchecked")
    public MultyItemAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        daiLiManager.addDelegate(itemViewDelegate);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override//获取view的类型
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager()) return super.getItemViewType(position);
        return daiLiManager.getItemViewType(mDatas.get(position), position);
    }

    //如果设置了代理则调用设置代理返回的view的类型否则直接调用系统返回的类型
    private boolean useItemViewDelegateManager() {
        return daiLiManager.getItemViewDelegateCount() > 0;
    }

    private void setListener(final MyViewHolder viewHolder, int viewType) {
//        if (!isEnabled(viewType)) return; 如果不想设置点击事件和长按事件则直接return即可 viewType（数据部分 header,footer）
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener == null) return;
                mOnItemClickListener.onItemClick(v, viewHolder, viewHolder.getAdapterPosition());
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener == null) return false;
                return mOnItemClickListener.onItemLongClick(v, viewHolder, viewHolder.getAdapterPosition());
            }
        });
    }


    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
