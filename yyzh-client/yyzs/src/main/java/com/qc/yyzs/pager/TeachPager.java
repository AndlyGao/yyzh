package com.qc.yyzs.pager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qc.yyzs.R;
import com.qc.yyzs.activity.CustomVideoPlayer;
import com.qc.yyzs.entity.EntityVideo;
import com.qc.yyzs.utils.CacheUtils;
import com.qc.yyzs.utils.Constants;
import com.qc.yyzs.view.XListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TeachPager extends BasePager {
    @ViewInject(R.id.netvideo_pager_listView)
    private XListView listView;
    @ViewInject(R.id.netvideo_pager_tv)
    private TextView textView;
    @ViewInject(R.id.netvideo_pager_tv_pb)
    private ProgressBar progressBar;
    private MyTeachAdapter adapter;


    private boolean isLoadMore = false;
    private ArrayList<EntityVideo> entityVideos;
    public TeachPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view=View.inflate(context, R.layout.teach_pager, null);
        x.view().inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        textView.setVisibility(View.GONE);
        String saveJson = CacheUtils.getString(context, Constants.MOVIE_URL);
        if(!TextUtils.isEmpty(saveJson)){
            progressData(saveJson);
        }
        getDataForNet();

    }
    private void getDataForNet() {
        RequestParams params = new RequestParams(Constants.MOVIE_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("onSuccess", result);
                progressBar.setVisibility(View.GONE);
                CacheUtils.putString(context, Constants.MOVIE_URL, result);
                progressData(result);
            }


            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("onError", "onError");
                showData();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("onCancelled", "onCancelled");
            }

            @Override
            public void onFinished() {
                Log.e("onSuccess", "onFinished");
            }
        });
    }
    private void showData() {
        //设置适配器
        if(entityVideos != null && entityVideos.size() >0){
            adapter = new MyTeachAdapter();
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new MyOnItemClickListener());
            listView.setPullLoadEnable(true);
            onLoad();
            textView.setVisibility(View.GONE);
            listView.setXListViewListener(new XListView.IXListViewListener() {
                @Override
                public void onRefresh() {
                    getDataForNet();
                }

                @Override
                public void onLoadMore() {
                    getMoreDataFromNet();
                }
            });

        }else{
            textView.setVisibility(View.VISIBLE);
            textView.setText("无数据");
        }
    }
    private void progressData(String result) {



        if(!isLoadMore){
            entityVideos = parseJson(result);
            showData();


        }else{
            //加载更多
            //要把得到更多的数据，添加到原来的集合中
            //            ArrayList<MediaItem> moreDatas = parseJson(json);
            isLoadMore = false;
            entityVideos.addAll(parseJson(result));
            //刷新适配器
            adapter.notifyDataSetChanged();
            onLoad();


        }
    }
    private void onLoad() {
        listView.stopRefresh();
        listView.stopLoadMore();
        listView.setRefreshTime("更新时间:"+getSysteTime());
    }
    public String getSysteTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date());
    }

    private void getMoreDataFromNet() {
        //联网
        //视频内容
        RequestParams params = new RequestParams(Constants.MOVIE_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("联网成功==","" + result);
                isLoadMore = true;
                //主线程
                progressData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //                LogUtil.e("联网失败==" + ex.getMessage());
                isLoadMore = false;
            }

            @Override
            public void onCancelled(CancelledException cex) {
                //                LogUtil.e("onCancelled==" + cex.getMessage());
                isLoadMore = false;
            }

            @Override
            public void onFinished() {
                //                LogUtil.e("onFinished==");
                isLoadMore = false;
            }
        });
    }
    private ArrayList<EntityVideo> parseJson(String json) {
        ArrayList<EntityVideo> es=new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.optJSONArray("movies");
            if(jsonArray!= null && jsonArray.length() >0){

                for (int i=0;i<jsonArray.length();i++){

                    JSONObject jsonObjectItem = (JSONObject) jsonArray.get(i);

                    if(jsonObjectItem != null){

                        EntityVideo mediaItem = new EntityVideo();


                        String movieName = jsonObjectItem.optString("moviename");//name
                        mediaItem.setName(movieName);

                        String videoTitle = jsonObjectItem.optString("videotitle");//desc
                        mediaItem.setDesc(videoTitle);

                        String imageUrl = jsonObjectItem.optString("coverImg");//imageUrl
                        mediaItem.setImageUrl(imageUrl);

                        String hightUrl = jsonObjectItem.optString("url");//data
                        mediaItem.setData(hightUrl);

                        //把数据添加到集合
                        es.add(mediaItem);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return es;
    }



    class MyTeachAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return  entityVideos.size();
        }

        @Override
        public Object getItem(int position) {
            return entityVideos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            EntityVideo entityVideo = entityVideos.get(position);
            if(convertView==null){
                viewHolder=new ViewHolder();
                convertView=View.inflate(context,R.layout.netvideo_pager_item,null);
                viewHolder.iv_icon= (ImageView) convertView.findViewById(R.id.iv_icon);
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                viewHolder. tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            EntityVideo e= entityVideos.get(position);
            viewHolder.tv_name.setText(e.getName());
            viewHolder.tv_desc.setText(e.getDesc());
            //            x.image().bind(viewHolder.iv_icon,e.getImageUrl());
            //            使用Glide请求图片
            Glide.with(context).load(e.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.video_default)
                    .error(R.drawable.video_default)
                    .into(viewHolder.iv_icon);

            //3.使用Picasso 请求图片
            //            Picasso.with(context).load(mediaItem.getImageUrl())
            //                    //                .diskCacheStrategy(DiskCacheStrategy.ALL)
            //                    .placeholder(R.drawable.video_default)
            //                    .error(R.drawable.video_default)
            //                    .into(viewHoder.iv_icon);
            return convertView;
        }

    }
    static   class ViewHolder{
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_desc;
    }
    class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent =new Intent(context,CustomVideoPlayer.class);
            //                intent.setDataAndType(Uri.parse(entityVideos.get(position).getData()),"video/*");
            //                intent.putExtra("name",entityVideos.get(position).getName());
            Bundle bundle=new Bundle();
            bundle.putSerializable("entityVideos",entityVideos);
            intent.putExtras(bundle);
            intent.putExtra("position",position-1);
            context.startActivity(intent);
        }
    }
}
