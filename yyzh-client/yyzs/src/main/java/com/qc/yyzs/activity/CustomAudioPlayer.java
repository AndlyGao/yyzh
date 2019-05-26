package com.qc.yyzs.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.qc.yyzs.IMusicPlayerService;
import com.qc.yyzs.R;
import com.qc.yyzs.entity.MessageEvent;
import com.qc.yyzs.entity.Music;
import com.qc.yyzs.service.MusicPlayerService;
import com.qc.yyzs.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CustomAudioPlayer extends Activity implements View.OnClickListener {
    private ImageView iv_icon;
    private boolean  notification;
    private int position;
    private int position1;
    private TextView tvArtist;
    private TextView tvName;
    private TextView tvTime;
    private SeekBar seekbarAudio;
    private Button btnAudioPlaymode;
    private Button btnAudioPre;
    private Button btnAudioStartPause;
    private Button btnAudioNext;
    private Button btnLyrc;
    private Utils utils;
    private RelativeLayout rela;
    private static String mBgId;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customaudioplayer);
        EventBus.getDefault().register(this);
        initData();
        findViews();
        getData();
        bindAndStartService();
        checkbg();

    }
    private void checkbg() {
//        String theme = CacheUtils.getString(this, "theme");
//
//        if(theme.equals("bg01")){
//            mBgId="mainbg";
//            int id = getResources().getIdentifier(mBgId, "drawable", getPackageName());
//            rela.setBackgroundDrawable(getResources().getDrawable(id));
//        }else if(theme.equals("bg02")){
//            mBgId="splash";
//            int id = getResources().getIdentifier(mBgId, "drawable", getPackageName());
//            rela.setBackgroundDrawable(getResources().getDrawable(id));
//        }
//        else if(theme.equals("bg03")){
//            mBgId="bgwhite";
//            int id = getResources().getIdentifier(mBgId, "drawable", getPackageName());
//            rela.setBackgroundDrawable(getResources().getDrawable(id));
//        }
    }
    private AnimationDrawable animationDrawable;

    private void findViews() {
        iv_icon = (ImageView)findViewById(R.id.iv_icon);
        iv_icon.setBackgroundResource(R.drawable.animation_list);
         animationDrawable= (AnimationDrawable) iv_icon.getBackground();
        animationDrawable.start();
        rela = (RelativeLayout)findViewById(R.id.rela);
        tvArtist = (TextView)findViewById( R.id.tv_artist);
        tvName = (TextView)findViewById( R.id.tv_name );
        tvTime = (TextView)findViewById( R.id.tv_time );
        seekbarAudio = (SeekBar)findViewById( R.id.seekbar_audio );
        btnAudioPlaymode = (Button)findViewById( R.id.btn_audio_playmode );
        btnAudioPre = (Button)findViewById( R.id.btn_audio_pre );
        btnAudioStartPause = (Button)findViewById( R.id.btn_audio_start_pause );
        btnAudioNext = (Button)findViewById( R.id.btn_audio_next );
        btnLyrc = (Button)findViewById( R.id.btn_lyrc );
        btnAudioPlaymode.setOnClickListener( this );
        btnAudioPre.setOnClickListener( this );
        btnAudioStartPause.setOnClickListener( this );
        btnAudioNext.setOnClickListener( this );
        btnLyrc.setOnClickListener( this );
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
        seekbarAudio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    try {
                        service.seekTo(progress);
                    } catch (RemoteException e) {

                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }
    int type ;
    boolean chongbo;
    private boolean isT;
    private void getData() {
        notification= getIntent().getBooleanExtra("notification",false);
        int po = getIntent().getIntExtra("po", 0);
        type=getIntent().getIntExtra("type",0);
         chongbo=getIntent().getBooleanExtra("chongbo", false);
        Log.e("----", chongbo + "");
        Log.e("--",po+"");
        if(!notification&&!chongbo){
            position = getIntent().getIntExtra("position",0);
            position++;

        }else{

        }
    }
    private void bindAndStartService() {
        Intent intent = new Intent(this, MusicPlayerService.class);
        intent.setAction("com.qc.yyzh.service.MUSICPLAYERSERVICE");

        intent.putExtra("type",type);
//        intent.putExtra("type", getIntent().getStringExtra("type"));
        bindService(intent, con, Context.BIND_AUTO_CREATE);
        startService(intent);//不至于实例化多个服务


    }
    private IMusicPlayerService service;
    private ServiceConnection con = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            service = IMusicPlayerService.Stub.asInterface(binder);
            if(service != null){
                try {
                    //不是状态兰
                    if(!notification){
                        if(chongbo){
                            showData();
                        }else {
                            service.openAudio(position);
                        }
                    }else{
                        showData();
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            try {
                if(service != null){
                    service.stop();
                    service = null;
                }

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        if(service!=null&&con!=null){
            unbindService(con);
            con=null;
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        if ( v == btnAudioPlaymode ) {
            // Handle clicks for btnAudioPlaymode
            setPlaymode();
        } else if ( v == btnAudioPre ) {
            // Handle clicks for btnAudioPre
            if(service!=null){
                try {
                    service.pre();
                } catch (RemoteException e) {

                }
            }


        } else if ( v == btnAudioStartPause ) {
            if(service != null){
                try {
                    if(service.isPlaying()){
                        //暂停
                        service.pause();
                        animationDrawable.stop();
                        //按钮-播放
                        btnAudioStartPause.setBackgroundResource(R.drawable.btn_audio_start_selector);
                    }else{
                        //播放
                        service.start();
                        //按钮-暂停
                        animationDrawable.start();
                        btnAudioStartPause.setBackgroundResource(R.drawable.btn_audio_pause_selector);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            // Handle clicks for btnAudioStartPause
        } else if ( v == btnAudioNext ) {
            // Handle clicks for btnAudioNext
           if(service!=null){
               try {
                   service.next();
               } catch (RemoteException e) {

               }
           }



        } else if ( v == btnLyrc ) {
            // Handle clicks for btnLyrc
        }
    }
    private void setPlaymode() {
        try {
            int playmode = service.getPlayMode();
            if(playmode==MusicPlayerService.REPEAT_NORMAL){
                playmode = MusicPlayerService.REPEAT_SINGLE;
            }else if(playmode == MusicPlayerService.REPEAT_SINGLE){
                playmode = MusicPlayerService.REPEAT_ALL;
            }else if(playmode ==MusicPlayerService.REPEAT_ALL){
                playmode = MusicPlayerService.REPEAT_NORMAL;
            }else{
                playmode = MusicPlayerService.REPEAT_NORMAL;
            }

            //保持
            service.setPlayMode(playmode);

            //设置图片
            showPlaymode();

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void showPlaymode() {
        try {
            int playmode = service.getPlayMode();

            if(playmode==MusicPlayerService.REPEAT_NORMAL){
                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_normal_selector);
                Toast toast = Toast.makeText(CustomAudioPlayer.this, null, Toast.LENGTH_SHORT);
                toast.setText("顺序播放");
                toast.show();
            }else if(playmode == MusicPlayerService.REPEAT_SINGLE){
                Toast toast = Toast.makeText(CustomAudioPlayer.this, null, Toast.LENGTH_SHORT);
                toast.setText("单曲循环");
                toast.show();
                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_single_selector);
            }else if(playmode ==MusicPlayerService.REPEAT_ALL){
                Toast toast = Toast.makeText(CustomAudioPlayer.this, null, Toast.LENGTH_SHORT);
                toast.setText("全部循环");
                toast.show();
                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_all_selector);
            }else{
                Toast toast = Toast.makeText(CustomAudioPlayer.this, null, Toast.LENGTH_SHORT);
                toast.setText("顺序播放");
                toast.show();
                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_normal_selector);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    private void checkPlaymode() {
        try {
            int playmode = service.getPlayMode();

            if(playmode==MusicPlayerService.REPEAT_NORMAL){
                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_normal_selector);
            }else if(playmode == MusicPlayerService.REPEAT_SINGLE){
                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_single_selector);
            }else if(playmode ==MusicPlayerService.REPEAT_ALL){
                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_all_selector);
            }else{
                btnAudioPlaymode.setBackgroundResource(R.drawable.btn_audio_playmode_normal_selector);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }



    private void initData() {
        utils=new Utils();
    }

    @Subscribe(threadMode = ThreadMode.MAIN,priority = 0,sticky = false)
    public void evb3(Music music){
        checkPlaymode();
        showData();
    }


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case  1:
                    try{
                        //1.得到当前进度
                        int currentPosition = service.getCurrentPosition();


                        //2.设置SeekBar.setProgress(进度)
                        seekbarAudio.setProgress(currentPosition);

                        //3.时间进度跟新
                        tvTime.setText(utils.stringForTime(currentPosition)+"/"+utils.stringForTime(service.getDuration()));


                        //4.每秒更新一次
                        handler.removeMessages(1);
                        handler.sendEmptyMessageDelayed(1,1000);

                    }catch (Exception e){

                    }

                    break;

            }
        }
    };

    private  void showData(){
        try {
            tvArtist.setText(service.getArtist());
            tvName.setText(service.getName());
            //设置进度条的最大值
            seekbarAudio.setMax(service.getDuration());
            progressBar.setVisibility(View.GONE);
            //发消息
            handler.sendEmptyMessage(1);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toteach(MessageEvent messageEvent) {
        if(messageEvent.getMessage()=="hideProgress"){
            progressBar.setVisibility(View.GONE);
        }
    }

}
