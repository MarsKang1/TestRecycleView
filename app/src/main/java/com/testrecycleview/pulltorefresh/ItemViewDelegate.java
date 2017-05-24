package com.testrecycleview.pulltorefresh;

/**
 * Created by Administrator on 2017/5/23.
 * 用于保存获取单条itemview的属性方法
 */
public interface ItemViewDelegate<T> {
    int getItemViewLayoutId();//获取view的id

    boolean isForViewType(T item, int position);//判断所给的数据是不是所给位置的type

    void convert(MyViewHolder holder, T t, int position);//组装view
}
