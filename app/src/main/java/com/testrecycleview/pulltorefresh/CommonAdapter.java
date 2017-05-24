package com.testrecycleview.pulltorefresh;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.List;

/**
 * Created by Administrator on 2017/5/23.
 */

public abstract class CommonAdapter<T> extends MultyItemAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public CommonAdapter(Context mContext, int mLayoutId, List<T> mDatas) {
        super(mContext, mDatas);
        this.mContext = mContext;
        this.mLayoutId = mLayoutId;
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(mContext);
        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return CommonAdapter.this.mLayoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(MyViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(MyViewHolder holder, T t, int position);
}
