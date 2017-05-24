package com.testrecycleview.pulltorefresh;


import android.support.v4.util.SparseArrayCompat;

/**
 * Created by Administrator on 2017/5/23.
 * item 的代理管理类用于保存每条item的属性
 */
public class ItemViewDelegateManager<T> {
    SparseArrayCompat<ItemViewDelegate<T>> delegates = new SparseArrayCompat();

    //获取代理总数
    public int getItemViewDelegateCount() {
        return delegates.size();
    }

    //添加代理 默认添加
    public ItemViewDelegateManager<T> addDelegate(ItemViewDelegate<T> delegate) {
        if (delegate != null) delegates.put(delegates.size(), delegate);
        return this;
    }

    //添加代理 通过viewType确定位置
    public ItemViewDelegateManager<T> addDelegate(int viewType, ItemViewDelegate<T> delegate) {
        if (delegates.get(viewType) != null) throw new IllegalArgumentException("An ItemViewDelegate is already registered for the viewType = " + viewType + ". Already registered ItemViewDelegate is " + delegates.get(viewType));
        delegates.put(viewType, delegate);
        return this;
    }

    //移除代理通过对象
    public ItemViewDelegateManager<T> removeDelegate(ItemViewDelegate<T> delegate) {
        if (delegate == null) throw new NullPointerException("ItemViewDelegate is null");
        if (delegates.indexOfValue(delegate) >= 0) delegates.removeAt(delegates.indexOfValue(delegate));//indexOfValue可以确定添加的代理位置
        return this;
    }

    //移除代理通过位置
    public ItemViewDelegateManager<T> removeDelegate(int itemType) {
        if (delegates.indexOfKey(itemType) >= 0) delegates.removeAt(delegates.indexOfKey(itemType));
        return this;
    }

    //获取代理的view类型  delegates.keyAt返回的是key的内容
    public int getItemViewType(T item, int position) {
        int delegatesCount = delegates.size();
        for (int i = delegatesCount - 1; i >= 0; i--) {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(item, position)) {//判断标准在外部实现
                return delegates.keyAt(i);//返回代理位置key
            }
        }
        throw new IllegalArgumentException("No ItemViewDelegate added that matches position=" + position + " in data source");
    }

    public void convert(MyViewHolder holder, T item, int position) {
        for (int i = 0; i < delegates.size(); i++) {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(item, position)) {//判断标准在外部实现
                delegate.convert(holder, item, position);//convert内容在外部实现
                return;
            }
        }
        throw new IllegalArgumentException("No ItemViewDelegateManager added that matches position=" + position + " in data source");
    }

    //获取ItemViewDelegate通过viewType 跟addDelegate(int viewType, ItemViewDelegate<T> delegate) 是一对
    public ItemViewDelegate getItemViewDelegate(int viewType) {
        return delegates.get(viewType);
    }

    //获取item的布局Id
    public int getItemViewLayoutId(int viewType) {
        return getItemViewDelegate(viewType).getItemViewLayoutId();
    }

    //获取ItemViewDelegate的位置
    public int getItemViewType(ItemViewDelegate itemViewDelegate) {
        return delegates.indexOfValue(itemViewDelegate);
    }
}
