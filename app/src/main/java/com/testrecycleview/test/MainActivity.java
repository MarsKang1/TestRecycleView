package com.testrecycleview.test;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.testrecycleview.R;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    AutoLoadRecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;


    private FreshNewsAdapter mAdapter;
    private LoadFinishCallBack mLoadFinisCallBack;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    HttpUtils httpUtils = new HttpUtils();
    BitmapUtils bitmapUtils ;

    //是否是大图模式
    private static boolean isLargeMode = false;
    private int index =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bitmapUtils = new BitmapUtils(this);

        mRecyclerView = (AutoLoadRecyclerView) findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mLoadFinisCallBack = mRecyclerView;
        mRecyclerView.setLoadMoreListener(new AutoLoadRecyclerView.onLoadMoreListener() {
            @Override
            public void loadMore() {
                mAdapter.loadNextPage();
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.loadFirst();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        imageLoader = ImageLoader.getInstance();
//        mRecyclerView.setOnPauseListenerParams(imageLoader, false, true);

//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        isLargeMode = sp.getBoolean(SettingFragment.ENABLE_FRESH_BIG, true);

        int loadingResource;

        //大图模式
        if (isLargeMode) {
            loadingResource = R.drawable.ic_loading_large;
        } else {
            loadingResource = R.drawable.ic_loading_small;
        }

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .resetViewBeforeLoading(true)
                .showImageOnLoading(loadingResource)
                .build();


        mAdapter = new FreshNewsAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.loadFirst();

    }

    /**
     * 新鲜事适配器
     */
    public class FreshNewsAdapter extends RecyclerView.Adapter<ViewHolder> {

//        private int page;
        private ArrayList<Goods> freshNewses;
        private int lastPosition = -1;

        public FreshNewsAdapter(ArrayList<Goods> freshNewses) {
            this.freshNewses = freshNewses;
        }
        public FreshNewsAdapter() {
            freshNewses = new ArrayList<Goods>();
        }

        private void setAnimation(View viewToAnimate, int position) {
            if (position > lastPosition) {
                Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R
                        .anim.item_bottom_in);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }
        }

        @Override
        public void onViewDetachedFromWindow(ViewHolder holder) {
            super.onViewDetachedFromWindow(holder);

                holder.ll_content.clearAnimation();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
            int layoutId;
            Log.e("Tag", "onCreateViewHolder");
            if (isLargeMode) {
//                layoutId = R.layout.item_fresh_news;
            } else {

            }
            layoutId = R.layout.main_fragment_list_item;
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(layoutId, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            if(freshNewses != null && freshNewses.size() > 0){
                Goods freshNews = freshNewses.get(position);
//            imageLoader.displayImage(freshNews.getImageUrl(), holder.img, options);
                bitmapUtils.display( holder.img,freshNews.getImageUrl());
                holder.tv_title.setText(freshNews.getAddress());
                holder.tv_info.setText(freshNews.getPrice() + "@" );


                setAnimation(holder.ll_content, position);
            }
            }



        @Override
        public int getItemCount() {
            return freshNewses.size();
        }

        public void loadFirst() {
            index = 1;
            loadData();
        }

        public void loadNextPage() {
            index++;
            loadData();
        }



    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private TextView tv_info;
        private TextView tv_views;
        private TextView tv_share;
        private ImageView img;
//        private CardView card;

        private LinearLayout ll_content;

        public ViewHolder(View contentView) {
            super(contentView);
            tv_title = (TextView) contentView.findViewById(R.id.main_fragment_item_text1);
            tv_info = (TextView) contentView.findViewById(R.id.main_fragment_item_text2);
            tv_views = (TextView) contentView.findViewById(R.id.main_fragment_item_text3);
            img = (ImageView) contentView.findViewById(R.id.main_fragment_item_imageView);

            if (isLargeMode) {
//                tv_share = (TextView) contentView.findViewById(R.id.tv_share);
//                card = (CardView) contentView.findViewById(R.id.card);
            } else {
                ll_content = (LinearLayout) contentView.findViewById(R.id.main_fragment_item_layout);
            }

        }
    }

    private void loadData() {

        httpUtils.send(HttpRequest.HttpMethod.GET, Goods.URL_FRESH_NEWS + index, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ArrayList<Goods> freshNewses = Goods.parse(responseInfo.result);
                if (1 == index) {
                    mAdapter.freshNewses.clear();
                    mAdapter.freshNewses.addAll(freshNewses);
                } else {
                    mAdapter.freshNewses.addAll(freshNewses);
                }

                mAdapter.notifyDataSetChanged();

                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }

                mLoadFinisCallBack.loadFinish(null);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

    }


}
