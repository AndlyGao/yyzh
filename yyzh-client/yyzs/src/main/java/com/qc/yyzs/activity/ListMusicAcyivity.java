package com.qc.yyzs.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qc.yyzs.R;
import com.qc.yyzs.entity.Music;
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
import java.util.List;

public class ListMusicAcyivity extends AppCompatActivity {

    @ViewInject(R.id.search_title)
    TextView search_title;
    @ViewInject(R.id.search_back)
    ImageView imageView;
    @ViewInject(R.id.xlistview)
    XListView xListView;

//    private  String[] name={"DJ天宝-全粤语Club音乐VS韶关","Dj龙仔-国粤语Club音乐不再回头了"};
//    private String[] content={"2019-04-22 ID:5211231 人气：2","2019-04-23 ID:5248523 人气：3"};
//    private int[] path={R.raw.main03,R.raw.main04};

    private List<Music> musics=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_music_acyivity);
        x.view().inject(this);

        receiver = new MyAudioReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.qc.yyzh.chongbochange");
        registerReceiver(receiver, intentFilter);
        String title = getIntent().getStringExtra("title");
        search_title.setText(title);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Log.e("title", title);
        if(title.equals("APP自带方便测试")){
            musics.add(new Music(212,"DJ天宝-全粤语Club音乐VS韶关",2,"2019-03-03",100,R.raw.main03+"","http://192.168.137.9:8080/kbapp/img/movie02.PNG",3000));
            musics.add(new Music(21321,"Dj龙仔-国粤语Club音乐不再回头了",2,"2019-03-03",100,R.raw.main04+"",null,3000));
            xListView.setAdapter(new MyAdapter());
            xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(position!=preposi){
                        chongbo=false;
                    }
                    if (chongbo) {

                        Intent intent = new Intent(ListMusicAcyivity.this, CustomAudioPlayer.class);
                        //                Bundle bundle=new Bundle();
                        //                bundle.putSerializable("entityVideos",entityVideos);
                        //                intent.putExtras(bundle);
                        intent.putExtra("position", position - 1);
                        intent.putExtra("chongbo", true);

                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(ListMusicAcyivity.this, CustomAudioPlayer.class);
                        //                Bundle bundle=new Bundle();
                        //                bundle.putSerializable("entityVideos",entityVideos);
                        //                intent.putExtras(bundle);
                        intent.putExtra("position", position - 1);
                        startActivity(intent);
                    }

                    preposi=position;
                }

            });
        }else{
            musics=new ArrayList<>();
            final int type = getIntent().getIntExtra("type", 0);
            Log.e("dwdad", type + "");
            CacheUtils.putString(this,"type",type+"");
            final MyAdapter myAdapter=new MyAdapter();
            xListView.setAdapter(myAdapter);
            RequestParams params = new RequestParams(Constants.QURRYMUSICFORTYPE);
            params.addQueryStringParameter("type", type+"");
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.e("成功==", "" + result);

                    musics = parseJson(result);

                    myAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                }

                @Override
                public void onCancelled(CancelledException cex) {
                }

                @Override
                public void onFinished() {
                }
            });
            xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position != preposi) {
                        chongbo = false;
                    }
                    if (chongbo) {

                        Intent intent = new Intent(ListMusicAcyivity.this, CustomAudioPlayer.class);
                        //                Bundle bundle=new Bundle();
                        //                bundle.putSerializable("entityVideos",entityVideos);
                        //                intent.putExtras(bundle);
                        intent.putExtra("position", position - 1);
                        intent.putExtra("chongbo", true);
                        intent.putExtra("type",type);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(ListMusicAcyivity.this, CustomAudioPlayer.class);
                        //                Bundle bundle=new Bundle();
                        //                bundle.putSerializable("entityVideos",entityVideos);
                        //                intent.putExtras(bundle);
                        intent.putExtra("position", position - 1);
                        intent.putExtra("type",type);
                        CacheUtils.putString(ListMusicAcyivity.this,"type",type+"");
                        startActivity(intent);
                    }

                    preposi = position;
                }

            });





        }
        xListView.setPullLoadEnable(true);
        onLoad();
        xListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                new Thread() {
                    @Override
                    public void run() {
                        try {

                            sleep(1500);
                        } catch (InterruptedException e) {
                        }

                        xListView.stopRefresh();
                    }
                }.start();
            }

            @Override
            public void onLoadMore() {
                new Thread() {
                    @Override
                    public void run() {
                        try {

                            sleep(1500);
                        } catch (InterruptedException e) {
                        }

                        xListView.stopLoadMore();
                    }
                }.start();
            }
        });



    }

    private ArrayList<Music> parseJson(String json) {
        ArrayList<Music> es=new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.optJSONArray("music");
            if(jsonArray!= null && jsonArray.length() >0){

                for (int i=0;i<jsonArray.length();i++){

                    JSONObject jsonObjectItem = (JSONObject) jsonArray.get(i);

                    if(jsonObjectItem != null){

                        Music music = new Music();

                        int id = jsonObjectItem.optInt("id");
                        music.setId(id);


                        String name = jsonObjectItem.optString("name");//name
                        music.setName(name);

                        int type = jsonObjectItem.optInt("type");
                        music.setType(type);
                        int renqi = jsonObjectItem.optInt("renqi");
                        music.setRenqi(renqi);
                        String time = jsonObjectItem.optString("time");//desc
                        music.setTime(time);

                        String url = jsonObjectItem.optString("url");//desc
                        music.setUrl(url);


                        es.add(music);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return es;
    }

    private int preposi;
    private boolean chongbo;
    private MyAudioReceiver receiver;


    private void onLoad() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime("更新时间:"+getSysteTime());
    }
    public String getSysteTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date());
    }


     class MyAdapter  extends BaseAdapter{

         @Override
         public int getCount() {
             return musics.size();
         }

         @Override
         public Object getItem(int position) {
        return  null;
         }

         @Override
         public long getItemId(int position) {
            return 0;
         }

         @Override
         public View getView(int position, View convertView, ViewGroup parent) {
             ViewHolder viewHolder;
             if(convertView==null){
                 viewHolder=new ViewHolder();
                 convertView=View.inflate(ListMusicAcyivity.this, R.layout.music_main_search_item,null);
                 viewHolder.xuhao= (TextView) convertView.findViewById(R.id.xuhao);
                 viewHolder.name= (TextView) convertView.findViewById(R.id.name);
                 viewHolder.content= (TextView) convertView.findViewById(R.id.content);
                 viewHolder.path= (TextView) convertView.findViewById(R.id.path);
                 convertView.setTag(viewHolder);
             }else{
                 viewHolder= (ViewHolder) convertView.getTag();
             }
//              musics.add(new Music("DJ天宝-全粤语Club音乐VS韶关",2,"2019-03-03",100,R.raw.main03+"",null,3000));
             viewHolder.xuhao.setText(position+1+"");
             viewHolder.name.setText(musics.get(position).getName());
             viewHolder.content.setText(musics.get(position).getTime()+" "+"ID: "+musics.get(position).getId()
             +" "+"人气： "+musics.get(position).getRenqi()
             );
             viewHolder.path.setText(musics.get(position).getUrl());
             return convertView;
         }
     }
         static   class ViewHolder{
             TextView xuhao;
             TextView name;
             TextView content;
             TextView path;
         }
    class MyAudioReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        chongbo = true;

    }
}
}
