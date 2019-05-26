package com.qc.yyzs.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qc.yyzs.R;
import com.qc.yyzs.entity.Music;
import com.qc.yyzs.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class SearchMActivity extends AppCompatActivity {

    @ViewInject(R.id.search_search)
    private TextView search;

    private List<Music> musics=new ArrayList<>();
    @ViewInject(R.id.search_edit)
    private EditText search_edit;

    @ViewInject(R.id.search_listview)
    private ListView search_listview;

    @ViewInject(R.id.search_back)
    private ImageView search_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search_m);
        x.view().inject(this);
        initData();
        setListener();

    }

    private void initData() {


    }
    int type1;
    private void setListener() {
        search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyAdapter myAdapter=new MyAdapter();
                search_listview.setAdapter(myAdapter);
                RequestParams params = new RequestParams(Constants.QURRYMUSICFORNAME);
                  String text = String.valueOf(search_edit.getText());
                params.addQueryStringParameter("name", text);
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
            }
        });
        search_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != preposi) {
                    chongbo = false;
                }
                if (chongbo) {

                    Intent intent = new Intent(SearchMActivity.this, CustomAudioPlayer.class);
                    //                Bundle bundle=new Bundle();
                    //                bundle.putSerializable("entityVideos",entityVideos);
                    //                intent.putExtras(bundle);
                    intent.putExtra("position", position - 1);
                    intent.putExtra("chongbo", true);
                    intent.putExtra("type",type1);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SearchMActivity.this, CustomAudioPlayer.class);
                    //                Bundle bundle=new Bundle();
                    //                bundle.putSerializable("entityVideos",entityVideos);
                    //                intent.putExtras(bundle);
                    intent.putExtra("position", position - 1);
                    intent.putExtra("type",type1);
                    startActivity(intent);
                }

                preposi = position;
            }
        });


    }


    class MyAdapter  extends BaseAdapter {

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
                convertView=View.inflate(SearchMActivity.this, R.layout.music_main_search_item,null);
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
                        type1=type;
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
    class MyAudioReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            chongbo = true;

        }
    }
}
