package com.qc.yyzs.pager;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qc.yyzs.R;
import com.qc.yyzs.activity.ListMusicAcyivity;
import com.qc.yyzs.view.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MusicPager extends BasePager {
    XListView viewById;
    MyAdapter myadapter;
    private ArrayList<String> titles;
    private Context context;
   public static final int[] image={
            R.drawable.nav1,
            R.drawable.nav2,
            R.drawable.nav3,
            R.drawable.nav4,
            R.drawable.nav5,
            R.drawable.nav6,
            R.drawable.nav7,
            R.drawable.nav8,
            R.drawable.nav9,
            R.drawable.nav10,
            R.drawable.nav11,
            R.drawable.nav8,

    };
    public MusicPager(Context context) {
        super(context);
        this.context=context;
    }


    @Override
    public View initView() {
       View view=View.inflate(super.context, R.layout.music_main,null);
        viewById = (XListView) view.findViewById(R.id.music_main);
//        viewById.getParent().requestDisallowInterceptTouchEvent(false);
        titles=new ArrayList<>();
        String[] title={
                "最新推荐舞曲",
                "热门点播舞曲",
                "的高串烧",
                "慢摇串烧",
                "中文CLUB",
                "外文CLUB",
                "电音HOUSE",
                "酒吧潮歌",
                "中文DISCO",
                "外文DISCO",
                "交谊舞曲","APP自带方便测试"
        };

        for(int i=0;i<title.length;i++){
            Log.e("wocao",title[i]);
            titles.add(title[i]);
        }
        myadapter=new MyAdapter();
        viewById.setAdapter(myadapter);
        viewById.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, ListMusicAcyivity.class);
                intent.putExtra("title", titles.get(position-1));
                intent.putExtra("type", position);
                context.startActivity(intent);
            }
        });
        viewById.setPullLoadEnable(true);
        onLoad();
        viewById.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                new Thread(){
                    @Override
                    public void run() {
                        try {

                            sleep(1500);
                        } catch (InterruptedException e) {
                        }


                        viewById.post(new Runnable() {
                            @Override
                            public void run() {
                                viewById.stopRefresh();
                            }
                        });

                    }
                }.start();
            }

            @Override
            public void onLoadMore() {
                new Thread(){
                    @Override
                    public void run() {
//                        try {
//
//                            sleep(500);
//                        } catch (InterruptedException e) {
//                        }
//
//                        viewById.stopLoadMore();
                    }
                }.start();
            }
        });
        return view;
    }

    @Override
    public void initData() {

    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public Object getItem(int position) {
            return  titles.get(position);
        }

        @Override
        public long getItemId(int position) {
              return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(context, R.layout.music_main_item, null);
                viewHolder.music_item_text= (TextView) convertView.findViewById(R.id.music_item_text);
                viewHolder.music_main_item_image= (ImageView) convertView.findViewById(R.id.music_main_item_image);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.music_item_text.setText(titles.get(position));
            viewHolder.music_main_item_image.setImageResource(image[position]);
            return convertView;
        }

    }
    static class ViewHolder {
        TextView music_item_text;
        ImageView music_main_item_image;
    }

    private void onLoad() {
        viewById.stopRefresh();
        viewById.stopLoadMore();
        viewById.setRefreshTime("更新时间:"+getSysteTime());
    }
    public String getSysteTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date());
    }
}
