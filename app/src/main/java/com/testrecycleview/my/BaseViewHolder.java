package com.testrecycleview.my;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;

import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/5/15.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    protected SparseArray<View> views;
    protected Context context;
    protected Resources resources;
    protected View convertView;

    public BaseViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.convertView = itemView;
        resources = context.getResources();
        this.views = new SparseArray<View>();
    }

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    /**
     * 设置TextView内容
     */
    public BaseViewHolder setText(int viewId, CharSequence value) {
        TextView view = getView(viewId);
        if (!TextUtils.isEmpty(value)) {
            view.setText(value);
        }
        return this;
    }

    /**
     * 设置开/关两种状态的按钮
     */
    public BaseViewHolder setCheck(int viewId, boolean checked) {
        View view = getView(viewId);
        if (view instanceof CompoundButton) {
            ((CompoundButton) view).setChecked(checked);
        } else if (view instanceof CheckedTextView) {
            ((CheckedTextView) view).setChecked(checked);
        }
        return this;
    }

    /**
     * 设置adapter(ListView/GridView都继承AdapterView)
     */
    public BaseViewHolder setAdapter(int viewId, Adapter adapter) {
        AdapterView view = getView(viewId);
        view.setAdapter(adapter);
        return this;
    }

    /**
     * 设置Rcv的adapter
     */
    public BaseViewHolder setRcvAdapter(int viewId, RecyclerView.Adapter adapter) {
        RecyclerView view = getView(viewId);
        view.setAdapter(adapter);
        return this;
    }

    /**
     * 得到相信的view
     *
     * @param viewId view的id
     * @param <T>    view本身
     */
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }
    //使用 SparseArray 做缓存
    //此处还能继续添加自己想要的控件的初始化赋值等，自行添加。注意：图片我用的是Fresco，可以自己修改
}
