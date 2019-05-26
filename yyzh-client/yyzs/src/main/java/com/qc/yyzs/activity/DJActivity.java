package com.qc.yyzs.activity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qc.yyzs.R;
import com.qc.yyzs.entity.EntityVideo;
import com.qc.yyzs.entity.MessageEvent;
import com.qc.yyzs.utils.Constants;
import com.qc.yyzs.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;

public class DJActivity extends AppCompatActivity {
    private SoundPool soundpool;	//声明一个SoundPool对象
    private HashMap<Integer,Integer> soundmap=new HashMap<Integer,Integer>();//创建一个HashMap对象
    @ViewInject(R.id.qinggu)
    private Button qinggu;
    @ViewInject(R.id.zhonggu)
    private  Button zhonggu;
    @ViewInject(R.id.three)
    private  Button three;
    @ViewInject(R.id.four)
    private  Button four;
    @ViewInject(R.id.five)
    private  Button five;
    @ViewInject(R.id.start)
    private  Button start;

    @ViewInject(R.id.back)
    private ImageView back;

    @ViewInject(R.id.tianjia)
    private ImageView tianjia;
@ViewInject(R.id.musicdesc)
    private TextView musicdesc;
    private int play1=0;
    private int play2=0;
    private int play3=0;
    private int play4=0;
    private int play5=0;
    private int play6=0;
    private int play7=0;
    private int play8=0;
    private int play9=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dj);
        x.view().inject(this);
        EventBus.getDefault().register(this);
        soundpool=new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundmap.put(1, soundpool.load(DJActivity.this, R.raw.qinggu, 1));
        soundmap.put(2, soundpool.load(DJActivity.this, R.raw.zhonggu, 1));
        soundmap.put(3, soundpool.load(DJActivity.this, R.raw.jing, 1));
        soundmap.put(4, soundpool.load(DJActivity.this, R.raw.four, 1));
        soundmap.put(5, soundpool.load(DJActivity.this, R.raw.five, 1));
        soundmap.put(8, soundpool.load(DJActivity.this, R.raw.main01, 1));
        soundmap.put(7, soundpool.load(DJActivity.this, R.raw.cuopan, 1));

        soundmap.put(6, soundpool.load(DJActivity.this, R.raw.main03, 1));
        soundmap.put(9, soundpool.load(DJActivity.this, R.raw.main04, 1));
        qinggu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (play1 == 0) {
                    play1 = soundpool.play(soundmap.get(1), 1, 1, 0, 0, 1);
                } else {
                    soundpool.stop(play1);
                    play1 = soundpool.play(soundmap.get(1), 1, 1, 0, 0, 1);
                }


            }
        });
        zhonggu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(play2==0){
                    play2 =    soundpool.play(soundmap.get(2),1,1,0,0,1);
                }else {
                    soundpool.stop(play2);
                    play2=     soundpool.play(soundmap.get(2),1,1,0,0,1);
                }

            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(play3==0){
                    play3 = soundpool.play(soundmap.get(3), 0.15f, 0.15f, 0, 0, 1);
                }else {
                    soundpool.stop(play3);
                    play3= soundpool.play(soundmap.get(3), 0.15f, 0.15f, 0, 0, 1);
                }
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(play4==0){
                    play4= soundpool.play(soundmap.get(4), 0.1f, 0.1f, 0, 0, 1);
                }else {
                    soundpool.stop(play4);
                    play4= soundpool.play(soundmap.get(4), 0.1f, 0.1f, 0, 0, 1);
                }

            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(play5==0){
                    play5= soundpool.play(soundmap.get(5), 1, 1, 0, 0, 1);
                }else {
                    soundpool.stop(play5);
                    play5= soundpool.play(soundmap.get(5), 1, 1, 0, 0, 1);
                }
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundpool.stop(play1);
                soundpool.stop(play2);
                soundpool.stop(play3);
                soundpool.stop(play4);
                soundpool.stop(play5);
                soundpool.stop(play8);
                play8=0;

                EventBus.getDefault().post(new MessageEvent("stoprotate"));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                RequestParams params = new RequestParams(Constants.CURRENT_LOGOUT);
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.e("联网成功==", "" + result);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        //                LogUtil.e("联网失败==" + ex.getMessage());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        //                LogUtil.e("onCancelled==" + cex.getMessage());
                    }

                    @Override
                    public void onFinished() {
                    }
                });
            }
        });
        tianjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DJActivity.this,DJListMusicActivity.class);
                startActivity(intent);
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toteach(EntityVideo entityVideo) {
        musicdesc.setText("DJtest");
        if(play8==0){
            play8 = soundpool.play(soundmap.get(8), 1, 1, 0, 0, 1);
        }else {
            soundpool.stop(play8);
            play8= soundpool.play(soundmap.get(8), 1, 1, 0, 0, 1);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cuopan(Utils util) {

        if(play7==0){
            play7 = soundpool.play(soundmap.get(7), 0.15f, 0.15f, 0, 0, 1);
        }else {
            soundpool.stop(play7);
            play7= soundpool.play(soundmap.get(7), 0.15f, 0.15f, 0, 0, 1);
        }
    }




    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onres","onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus

    }

    //    private void loadMusic() {
//         int  music = getIntent().getIntExtra("music",2);
//        if(music!=2){
//            play8 = soundpool.play(soundmap.get(8), 1, 1, 0, 0, 1);
//        }
//    }
}
