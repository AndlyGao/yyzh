package com.qc.yyzs.activity;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.qc.yyzs.R;
import com.qc.yyzs.entity.EntityVideo;
import com.qc.yyzs.utils.Utils;
import com.qc.yyzs.view.VideoView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomVideoPlayer extends Activity implements View.OnClickListener {
    private VideoView custom_video;
    private Uri uri;

    private LinearLayout cvMediaCtrlbottom;
    private RelativeLayout customvideoplayer_media_controller;
    private LinearLayout cvMediaCtrlTop;
    private TextView cvMediaCtrlTopName;
    private ImageView cvMediaCtrlTopBattery;
    private TextView cvMediaCtrlTopSystemTime;
    private LinearLayout cvMediaCtrlStatus;
    private Button cvMediaCtrlStatusBtnVoice;
    private SeekBar cvMediaCtrlStatusSeekbarVoice;
    private Button cvMediaCtrlStatusBtnSwichPlayer;
    private TextView cvMediaCtrlBottomCurrentTime;
    private SeekBar cvMediaCtrlBottomSeekbarVideo;
    private TextView cvMediaCtrlBottomDuration;
    private Button cvMediaCtrlBottomBtnExit;
    private Button cvMediaCtrlBottomBtnVideoPre;
    private Button cvMediaCtrlBottomBtnVideoStartPause;
    private Button cvMediaCtrlBottomBtnVideoNext;
    private Button cvMediaCtrlBottomBtnVideoSiwchScreen;
    private Utils utils;
    private String videoName;
    private MyBatteryBroadcastReceiver myBatteryBroadcastReceiver;
    private ArrayList<EntityVideo> entityVideos;
    private GestureDetector detector;
    private final static int HIDE_MEDIACONTROLL = 277;
    private boolean isFullScreen = false;
    private static final int DEFALUT = 1;
    private static final int FULLSCREEN = 0;
    private int screenWidth;
    private int screenHeight;
    private int mVideoWidth;
    private int mVideoHeight;
    private AudioManager audioManger;
    private int maxVoice;
    private int currentVoice;
    private boolean isJinyin = false;
    private TextView customvideoplayer_voice_change;
    private boolean isnet=false;
    private final static int  HIDE_VIOCE_SHANGE=111;
    private RelativeLayout customvideoplayer_speed;
    private boolean isUseSystem=false;
    private RelativeLayout customvideoplayer_loadding;
    private TextView customvideoplayer_loadding_tv;
    private TextView customvideoplayer_speed_tv;
    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2019-03-25 12:51:06 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        setContentView(R.layout.customvideoplayer);
        customvideoplayer_speed_tv = (TextView)findViewById(R.id.customvideoplayer_speed_tv);
        customvideoplayer_loadding_tv = (TextView)findViewById(R.id.customvideoplayer_loadding_tv);
        customvideoplayer_loadding = (RelativeLayout)findViewById(R.id.customvideoplayer_loadding);
        customvideoplayer_speed = (RelativeLayout)findViewById(R.id.customvideoplayer_speed);
        cvMediaCtrlbottom= (LinearLayout) findViewById(R.id.cv_media_ctrl_bottom);
        customvideoplayer_media_controller = (RelativeLayout) findViewById(R.id.customvideoplayer_media_controller);
        cvMediaCtrlTop = (LinearLayout) findViewById(R.id.cv_media_ctrl_top);
        cvMediaCtrlTopName = (TextView) findViewById(R.id.cv_media_ctrl_top_name);
        cvMediaCtrlTopBattery = (ImageView) findViewById(R.id.cv_media_ctrl_top_battery);
        cvMediaCtrlTopSystemTime = (TextView) findViewById(R.id.cv_media_ctrl_top_system_time);
        cvMediaCtrlStatus = (LinearLayout) findViewById(R.id.cv_media_ctrl_status);
        cvMediaCtrlStatusBtnVoice = (Button) findViewById(R.id.cv_media_ctrl_status_btn_voice);
        cvMediaCtrlStatusSeekbarVoice = (SeekBar) findViewById(R.id.cv_media_ctrl_status_seekbar_voice);
        cvMediaCtrlStatusBtnSwichPlayer = (Button) findViewById(R.id.cv_media_ctrl_status_btn_swich_player);
        cvMediaCtrlBottomCurrentTime = (TextView) findViewById(R.id.cv_media_ctrl_bottom_current_time);
        cvMediaCtrlBottomSeekbarVideo = (SeekBar) findViewById(R.id.cv_media_ctrl_bottom_seekbar_video);
        cvMediaCtrlBottomDuration = (TextView) findViewById(R.id.cv_media_ctrl_bottom_duration);
        cvMediaCtrlBottomBtnExit = (Button) findViewById(R.id.cv_media_ctrl_bottom_btn_exit);
        cvMediaCtrlBottomBtnVideoPre = (Button) findViewById(R.id.cv_media_ctrl_bottom_btn_video_pre);
        cvMediaCtrlBottomBtnVideoStartPause = (Button) findViewById(R.id.cv_media_ctrl_bottom_btn_video_start_pause);
        cvMediaCtrlBottomBtnVideoNext = (Button) findViewById(R.id.cv_media_ctrl_bottom_btn_video_next);
        cvMediaCtrlBottomBtnVideoSiwchScreen = (Button) findViewById(R.id.cv_media_ctrl_bottom_btn_video_siwch_screen);
        customvideoplayer_voice_change = (TextView) findViewById(R.id.customvideoplayer_voice_change);
        custom_video = (VideoView) findViewById(R.id.custom_video);
        cvMediaCtrlStatusBtnVoice.setOnClickListener(this);
        cvMediaCtrlStatusBtnSwichPlayer.setOnClickListener(this);
        cvMediaCtrlBottomBtnExit.setOnClickListener(this);
        cvMediaCtrlBottomBtnVideoPre.setOnClickListener(this);
        cvMediaCtrlBottomBtnVideoStartPause.setOnClickListener(this);
        cvMediaCtrlBottomBtnVideoNext.setOnClickListener(this);
        cvMediaCtrlBottomBtnVideoSiwchScreen.setOnClickListener(this);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2019-03-25 12:51:06 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == cvMediaCtrlStatusBtnVoice) {
            // Handle clicks for cvMediaCtrlStatusBtnVoice
            if (isJinyin) {
                updateVicoe(currentVoice, false);
                isJinyin = false;

            } else {
                updateVicoe(currentVoice, true);
                isJinyin = true;

            }
        } else if (v == cvMediaCtrlStatusBtnSwichPlayer) {
            // Handle clicks for cvMediaCtrlStatusBtnSwichPlayer
        } else if (v == cvMediaCtrlBottomBtnExit) {
            custom_video.pause();
         finish();
            // Handle clicks for cvMediaCtrlBottomBtnExit
        } else if (v == cvMediaCtrlBottomBtnVideoPre) {
            playPreVideo();
            // Handle clicks for cvMediaCtrlBottomBtnVideoPre
        } else if (v == cvMediaCtrlBottomBtnVideoStartPause) {
            startAndPause();
            // Handle clicks for cvMediaCtrlBottomBtnVideoStartPause
        } else if (v == cvMediaCtrlBottomBtnVideoNext) {
            playNextVideo();
            // Handle clicks for cvMediaCtrlBottomBtnVideoNext
        } else if (v == cvMediaCtrlBottomBtnVideoSiwchScreen) {
            if (isFullScreen) {
                setVideoType(DEFALUT);
            } else {
                setVideoType(FULLSCREEN);
            }
        }
        handler.removeMessages(HIDE_MEDIACONTROLL);
        handler.sendEmptyMessageDelayed(HIDE_MEDIACONTROLL, 4000);
    }

    private void startAndPause() {
        if (custom_video.isPlaying()) {
            custom_video.pause();
            handler.removeMessages(100);
            cvMediaCtrlBottomBtnVideoStartPause.setBackgroundResource(R.drawable.btn_video_start_selector);
        } else {
            custom_video.start();
            handler.sendEmptyMessage(100);
            cvMediaCtrlBottomBtnVideoStartPause.setBackgroundResource(R.drawable.btn_video_pause_selector);
        }
    }

    private int position;
    private EntityVideo entityVideo;

    private void playNextVideo() {
        if(isnet){

            customvideoplayer_loadding.setVisibility(View.VISIBLE);
        }

        if (entityVideos != null && entityVideos.size() > 0) {
            position++;

            if (position < entityVideos.size()) {
                entityVideo = entityVideos.get(position);
                isnet= utils.isNeturi(entityVideo.getData());
                custom_video.setVideoPath(entityVideo.getData());
                cvMediaCtrlTopName.setText(entityVideo.getName());
            }
            setButtonStatus();
        } else if (uri != null) {
            setButtonStatus();
        }
    }

    private void setButtonStatus() {
        if (entityVideos != null && entityVideos.size() > 0) {
            if (entityVideos.size() == 1) {
                cvMediaCtrlBottomBtnVideoPre.setBackgroundResource(R.drawable.btn_pre_gray);
                cvMediaCtrlBottomBtnVideoPre.setEnabled(false);
                cvMediaCtrlBottomBtnVideoNext.setBackgroundResource(R.drawable.btn_next_gray);
                cvMediaCtrlBottomBtnVideoNext.setEnabled(false);
            } else if (entityVideos.size() == 2) {
                if (position == 0) {
                    cvMediaCtrlBottomBtnVideoPre.setBackgroundResource(R.drawable.btn_pre_gray);
                    cvMediaCtrlBottomBtnVideoPre.setEnabled(false);
                    cvMediaCtrlBottomBtnVideoNext.setBackgroundResource(R.drawable.btn_video_next_selector);
                    cvMediaCtrlBottomBtnVideoNext.setEnabled(true);
                } else if (position == entityVideos.size() - 1) {
                    cvMediaCtrlBottomBtnVideoNext.setBackgroundResource(R.drawable.btn_next_gray);
                    cvMediaCtrlBottomBtnVideoNext.setEnabled(false);
                    cvMediaCtrlBottomBtnVideoPre.setBackgroundResource(R.drawable.btn_video_pre_selector);
                    cvMediaCtrlBottomBtnVideoPre.setEnabled(true);
                }
            } else {
                if (position == 0) {
                    cvMediaCtrlBottomBtnVideoPre.setBackgroundResource(R.drawable.btn_pre_gray);
                    cvMediaCtrlBottomBtnVideoPre.setEnabled(false);
                } else if (position == entityVideos.size() - 1) {
                    cvMediaCtrlBottomBtnVideoNext.setBackgroundResource(R.drawable.btn_next_gray);
                    cvMediaCtrlBottomBtnVideoNext.setEnabled(false);
                } else {
                    cvMediaCtrlBottomBtnVideoPre.setBackgroundResource(R.drawable.btn_video_pre_selector);
                    cvMediaCtrlBottomBtnVideoPre.setEnabled(true);
                    cvMediaCtrlBottomBtnVideoNext.setBackgroundResource(R.drawable.btn_video_next_selector);
                    cvMediaCtrlBottomBtnVideoNext.setEnabled(true);
                }
            }
        } else if (uri != null) {
            cvMediaCtrlBottomBtnVideoPre.setBackgroundResource(R.drawable.btn_pre_gray);
            cvMediaCtrlBottomBtnVideoPre.setEnabled(false);
            cvMediaCtrlBottomBtnVideoNext.setBackgroundResource(R.drawable.btn_next_gray);
            cvMediaCtrlBottomBtnVideoNext.setEnabled(false);
        }
    }

    private void playPreVideo() {
        if(isnet){

            customvideoplayer_loadding.setVisibility(View.VISIBLE);
        }
        if (entityVideos != null && entityVideos.size() > 0) {
            position--;

            if (position >= 0) {
                entityVideo = entityVideos.get(position);
                isnet=  utils.isNeturi(entityVideo.getData());
                custom_video.setVideoPath(entityVideo.getData());
                cvMediaCtrlTopName.setText(entityVideo.getName());
            }
            setButtonStatus();
        } else if (uri != null) {
            setButtonStatus();
        }
    }

    private MyVolumeReceiver myVolumeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViews();
        initData();
        setLinister();
        hideMediaControll();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    private void initData() {
        //        videoName=getIntent().getStringExtra("name");
        customvideoplayer_speed.setVisibility(View.GONE);
        customvideoplayer_voice_change.setVisibility(View.GONE);
        utils = new Utils();
        screenBrightness = Utils.getScreenBrightness(this);
        appLight=screenBrightness;
        uri = getIntent().getData();
        entityVideos = (ArrayList<EntityVideo>) getIntent().getSerializableExtra("entityVideos");
        position = getIntent().getIntExtra("position", 0);

        if (entityVideos != null && entityVideos.size() > 0) {
            entityVideo = entityVideos.get(position);
            custom_video.setVideoPath(entityVideo.getData());
           isnet= utils.isNeturi(entityVideo.getData());
            cvMediaCtrlTopName.setText(entityVideo.getName());
        } else if (uri != null) {
            cvMediaCtrlTopName.setText(uri.toString());
            isnet=    utils.isNeturi(uri.toString());

            custom_video.setVideoURI(uri);
        } else {
            Toast.makeText(this, "No extra", Toast.LENGTH_SHORT).show();
        }
        setButtonStatus();
        //注册广播
        myBatteryBroadcastReceiver = new MyBatteryBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(myBatteryBroadcastReceiver, filter);
        //
        myVolumeReceiver = new MyVolumeReceiver();
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction("android.media.VOLUME_CHANGED_ACTION");
        registerReceiver(myVolumeReceiver, filter2);


        initGestureDetector();
        //获取屏幕宽高
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        audioManger = (AudioManager) getSystemService(AUDIO_SERVICE);
        currentVoice = audioManger.getStreamVolume(AudioManager.STREAM_MUSIC);
        maxVoice = audioManger.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        cvMediaCtrlStatusSeekbarVoice.setMax(maxVoice);
        cvMediaCtrlStatusSeekbarVoice.setProgress(currentVoice);
        if(isnet) {
        customvideoplayer_loadding.setVisibility(View.VISIBLE);
        handler.sendEmptyMessage(SHOW_SPEED);
        }
    }

    void initGestureDetector() {
        detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {


            @Override
            public void onLongPress(MotionEvent e) {
                startAndPause();
                super.onLongPress(e);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                //                Toast.makeText(CustomVideoPlayer.this,"onDoubleTap",Toast.LENGTH_SHORT).show();
                if (isFullScreen) {
                    setVideoType(DEFALUT);
                } else {
                    setVideoType(FULLSCREEN);
                }
                return super.onDoubleTap(e);
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (isShow) {
                    hideMediaControll();
                    handler.removeMessages(HIDE_MEDIACONTROLL);
                } else {
                    showMediaControll();
                    handler.sendEmptyMessageDelayed(HIDE_MEDIACONTROLL, 3000);
                }
                //                Toast.makeText(CustomVideoPlayer.this,"onSingleTapConfirmed",Toast.LENGTH_SHORT).show();
                return super.onSingleTapConfirmed(e);
            }
        });
    }

    private void setVideoType(int Type) {
        switch (Type) {
            case DEFALUT:
                isFullScreen = false;
                cvMediaCtrlBottomBtnVideoSiwchScreen.setBackgroundResource(R.drawable.btn_video_siwch_screen_default_selector);
                int height = screenHeight;
                int width = screenWidth;
                if (mVideoWidth * height < width * mVideoHeight) {
                    //Log.i("@@@", "image too wide, correcting");
                    width = height * mVideoWidth / mVideoHeight;
                } else if (mVideoWidth * height > width * mVideoHeight) {
                    //Log.i("@@@", "image too tall, correcting");
                    height = width * mVideoHeight / mVideoWidth;
                }
                custom_video.setVideoSize(width, height);

                break;
            case FULLSCREEN:
                isFullScreen = true;
                custom_video.setVideoSize(screenWidth, screenHeight);
                cvMediaCtrlBottomBtnVideoSiwchScreen.setBackgroundResource(R.drawable.btn_video_siwch_screen_full_selector);
                break;
        }
    }

    //状态栏的状态
    private boolean isShow = false;

    //展示状态栏
    private void showMediaControll() {
//        customvideoplayer_media_controller.setVisibility(View.VISIBLE);
        cvMediaCtrlTop.setVisibility(View.VISIBLE);
        cvMediaCtrlbottom.setVisibility(View.VISIBLE);
        isShow = true;
    }

    //隐藏状态栏
    private void hideMediaControll() {
//        customvideoplayer_media_controller.setVisibility(View.GONE);
        cvMediaCtrlTop.setVisibility(View.GONE);
        cvMediaCtrlbottom.setVisibility(View.GONE);
        isShow = false;
    }

    private int prepositon;
    private final static int SHOW_SPEED=12;
    //处理消息
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:

                    int currentPosition = custom_video.getCurrentPosition();
                    cvMediaCtrlBottomSeekbarVideo.setProgress(currentPosition);
                    cvMediaCtrlBottomCurrentTime.setText(utils.stringForTime(currentPosition));
                    currentPosition = custom_video.getCurrentPosition();
                    Log.e("currentPosition", "" + utils.stringForTime(currentPosition));
                    removeMessages(100);
                    //                   cvMediaCtrlTopName.setText(videoName);

                    if(isnet){
                        int buffer= custom_video.getBufferPercentage();
                        int totalBuffer=buffer*cvMediaCtrlBottomSeekbarVideo.getMax();
                        int pro=totalBuffer/100;
                        Log.e("TAg","----------"+pro);
                        cvMediaCtrlBottomSeekbarVideo.setSecondaryProgress(pro);

                    }else{
                        cvMediaCtrlBottomSeekbarVideo.setSecondaryProgress(0);
                    }

                    if(!isUseSystem&&custom_video.isPlaying()&&isnet){
                        int buffer=currentPosition-prepositon;
                        if(buffer<500){
                            customvideoplayer_speed.setVisibility(View.VISIBLE);
                        }else{
                            customvideoplayer_speed.setVisibility(View.GONE);
                        }

                    }else{
                        customvideoplayer_speed.setVisibility(View.GONE);
                    }
                    prepositon=currentPosition;
                    cvMediaCtrlTopSystemTime.setText(getSysTime());
                    sendEmptyMessageDelayed(100, 1000);


                    break;
                case HIDE_MEDIACONTROLL:
                    hideMediaControll();
                    break;
                case HIDE_VIOCE_SHANGE:
                    hideMediaVoiceControll();
                break ;


                case SHOW_SPEED:
                    String speed= utils.getNetSpeed(CustomVideoPlayer.this);

                    customvideoplayer_loadding_tv.setText("玩命加载中..."+speed);

                    customvideoplayer_speed_tv.setText(speed);
                    handler.removeMessages(SHOW_SPEED);
                    handler.sendEmptyMessageDelayed(SHOW_SPEED,2000);



                    break ;
            }
        }
    };

    private void hideMediaVoiceControll() {
        customvideoplayer_voice_change.setVisibility(View.GONE);
    }

    //获取系统时间
    private String getSysTime() {
        SimpleDateFormat format = new SimpleDateFormat("h:mm");
        return format.format(new Date());
    }

    //舰艇
    private void setLinister() {
        //        custom_video.setonTouchVoiceChangeListener(new VideoView.OnTouchVoiceChangeListener() {
        //            @Override
        //            public void OnTouchVoiceChangeListener(int voice) {
        //
        //            }
        //        });
        custom_video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideoWidth = mp.getVideoWidth();
                mVideoHeight = mp.getVideoHeight();
                custom_video.start();
                int duration = custom_video.getDuration();
                //                Log.e("duration", "" + utils.stringForTime(duration));
                cvMediaCtrlBottomSeekbarVideo.setMax(duration);
//                cvMediaCtrlTopName.setText(entityVideo.getName());
                cvMediaCtrlBottomDuration.setText(utils.stringForTime(duration));
                setVideoType(DEFALUT);
                //拖动完成
//                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//
//                    }
//                });
                customvideoplayer_loadding.setVisibility(View.GONE);
                handler.sendEmptyMessage(100);
            }
        });
        custom_video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {

            Toast.makeText(CustomVideoPlayer.this,"error",Toast.LENGTH_LONG).show();


                return true;
            }
        });
        custom_video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playNextVideo();
            }
        });
        //        custom_video.setMediaController(new MediaController(this));
        //seekbar拖动进度
        cvMediaCtrlBottomSeekbarVideo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    custom_video.seekTo(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeMessages(HIDE_MEDIACONTROLL);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                handler.sendEmptyMessageDelayed(HIDE_MEDIACONTROLL, 3000);
            }
        });
        cvMediaCtrlStatusSeekbarVoice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    if (progress > 0) {
                        isJinyin = false;

                    } else {
                        isJinyin = true;
                    }

                    updateVicoe(progress, isJinyin);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeMessages(HIDE_MEDIACONTROLL);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                handler.sendEmptyMessageDelayed(HIDE_MEDIACONTROLL, 4000);

            }
        });

        if(isUseSystem){
            custom_video.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    switch (what) {
                        case MediaPlayer.MEDIA_INFO_BUFFERING_START :
                            customvideoplayer_speed.setVisibility(View.VISIBLE);
                            break;
                        case MediaPlayer.MEDIA_INFO_BUFFERING_END :
                            customvideoplayer_speed.setVisibility(View.GONE);

                            break;
                    }
                    return true;
                }
            });
        }

    }


    private void updateVicoe(int progress, boolean isJinyin) {
        if (isJinyin) {

            audioManger.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
            cvMediaCtrlStatusSeekbarVoice.setProgress(0);

        } else {

            audioManger.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            cvMediaCtrlStatusSeekbarVoice.setProgress(progress);
            currentVoice = progress;
        }


    }


    @Override
    protected void onDestroy() {
        if (myBatteryBroadcastReceiver != null) {
            unregisterReceiver(myBatteryBroadcastReceiver);
            myBatteryBroadcastReceiver = null;
        }
        if (myVolumeReceiver != null) {
            unregisterReceiver(myVolumeReceiver);
            myVolumeReceiver = null;
        }
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        custom_video.pause();
        handler.removeMessages(100);
        Log.e("onPause", "onPause");
      super.onPause();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        custom_video.start();
//        handler.sendEmptyMessage(100);
//        Log.e("onResume", "onResume");
//    }

//
    @Override
    protected void onResume() {
        super.onResume();
        custom_video.seekTo(prepositon);
        custom_video.start();
        handler.sendEmptyMessage(100);
        Log.e("onResume", "onResume");
//        custom_video.getHolder().addCallback();
    }


    //设置电量图标
    private void setBattery(int level) {
        if (level <= 0) {
            cvMediaCtrlTopBattery.setImageResource(R.drawable.ic_battery_0);
        } else if (level <= 10) {
            cvMediaCtrlTopBattery.setImageResource(R.drawable.ic_battery_10);
        } else if (level <= 20) {
            cvMediaCtrlTopBattery.setImageResource(R.drawable.ic_battery_20);
        } else if (level <= 40) {
            cvMediaCtrlTopBattery.setImageResource(R.drawable.ic_battery_40);
        } else if (level <= 60) {
            cvMediaCtrlTopBattery.setImageResource(R.drawable.ic_battery_60);
        } else if (level <= 80) {
            cvMediaCtrlTopBattery.setImageResource(R.drawable.ic_battery_80);
        } else {
            cvMediaCtrlTopBattery.setImageResource(R.drawable.ic_battery_100);
        }


    }



    //监听点亮的广播
    class MyBatteryBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra("level", 0);
            setBattery(level);
        }

    }

    class MyVolumeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //如果音量发生变化则更改seekbar的位置
            if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
                AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                int currVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);// 当前的媒体音量
                cvMediaCtrlStatusSeekbarVoice.setProgress(currVolume);
                //                Log.e("-----------------------------------------","-----------------------------------");
            }
        }
    }

    private float startY;
    private float screen_height;
    private int touch_volice;
    private float startX;
    private float youbandeX;
    //触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getY();
                startX=event.getX();
//                Log.e("startY",startY+"");
//                Log.e("startX",startX+"");
                View view= (View) custom_video.getParent();
                 youbandeX = (view.getRight())/2;
//                Log.e("youbandeX",youbandeX+"");
                touch_volice = audioManger.getStreamVolume(AudioManager.STREAM_MUSIC);
                screen_height = Math.min(screenHeight, screenWidth);
                handler.removeMessages(HIDE_MEDIACONTROLL);

                break;
            case MotionEvent.ACTION_MOVE:

                float endX = event.getX();
                float endY = event.getY();
                float distance = startY - endY;
                float distanceX = startX - endX;
//                Log.e("endX",endX+"");
//                Log.e("endY",endY+"");


                //                if(Math.abs(distance)>5||Math.abs(distanceX)>5){
//                    customvideoplayer_voice_change.setVisibility(View.VISIBLE);
////                    customvideoplayer_voice_change.setText(currentVoice);
//                }

                float delta = (distance / screen_height) * maxVoice;
                int touch_volice2 = (int) Math.min(Math.max(touch_volice + delta, 0), maxVoice);
                if (delta != 0&&Math.abs(distance)>65&&startX>youbandeX
                         ) {
                    isJinyin = false;
                    updateVicoe(touch_volice2, isJinyin);
//                    customvideoplayer_voice_change.setVisibility(View.VISIBLE);
                    float baifenbi=(float)touch_volice2/(float)maxVoice;
                    int zuizhong=(int)(baifenbi*100)/1;
                    customvideoplayer_voice_change.setText("音量:" + zuizhong + "%");
                    customvideoplayer_voice_change.setVisibility(View.VISIBLE);
//                    Toast.makeText(this,"ddad",Toast.LENGTH_LONG).show();
                }
                float liangdu=((distance / screen_height)*255)/10;
                if (delta != 0&&Math.abs(distance)>65&&startX<youbandeX) {
                    updateLight(liangdu);
            }
//                if(Math.abs(distance)>25){
//           h'h'h'h'h'h'h'h'h         Toast.makeText(this,"da",Toast.LENGTH_LONG).show();
//                }

                break;
            case MotionEvent.ACTION_UP:
                handler.sendEmptyMessageDelayed(HIDE_MEDIACONTROLL, 2000);
                handler.sendEmptyMessageDelayed(HIDE_VIOCE_SHANGE, 1000);
                break;
        }
        return super.onTouchEvent(event);

    }
    private int  currLight;
    private    int screenBrightness ;
    private int appLight;
    private void updateLight(float x) {

//        Log.e("screenBrightness", screenBrightness + "");
//        boolean autoBrightness = Utils.isAutoBrightness(this);
//        Log.e("autoBrightness", autoBrightness + "");
        int curr=appLight+(int)x;
        if(curr<5){
            curr=5;
        }else if(curr>244){
            curr=244;
        }

        Utils.setBrightness(this,curr);
        Log.e("autoBrightness", x + "");
        appLight=curr;

        //

    }




}
