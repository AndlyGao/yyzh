package com.qc.yyzs.service;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.qc.yyzs.IMusicPlayerService;
import com.qc.yyzs.R;
import com.qc.yyzs.activity.CustomAudioPlayer;
import com.qc.yyzs.entity.MessageEvent;
import com.qc.yyzs.entity.Music;
import com.qc.yyzs.utils.CacheUtils;
import com.qc.yyzs.utils.Constants;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 */
public class MusicPlayerService extends Service {
    private ArrayList<Music> musics;
    private int position;

    /**
     * 播放音乐
     */
    private MediaPlayer mediaPlayer;
    /**
     * 当前播放的音频文件对象
     */
    private Music music;

    /**
     * 顺序播放
     */
    public static final int REPEAT_NORMAL = 1;
    /**
     * 单曲循环
     */
    public static final int REPEAT_SINGLE = 2;
    /**
     * 全部循环
     */
    public static final int REPEAT_ALL = 3;

    /**
     * 播放模式
     */
    private int playmode = REPEAT_NORMAL;
    int type;
    @Override
    public void onCreate() {
        super.onCreate();
        playmode = CacheUtils.getPlaymode(this, "playmode");
        type= Integer.parseInt(CacheUtils.getString(this, "type"));
        getDataFromLocal();
        //加载音乐列表

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        type=intent.getIntExtra("type",0);
//        CountDownLatch countDownLatch=new CountDownLatch(1);
//        ThreadSleep s=new ThreadSleep(countDownLatch);
//        new Thread(s).start();
//        try {
//            countDownLatch.await();
//        } catch (InterruptedException e) {
//
//        }

        return super.onStartCommand(intent, flags, startId);
    }

//    class ThreadSleep implements  Runnable{
//        //闭锁
//
//        private CountDownLatch countDownLatch;
//        public ThreadSleep(CountDownLatch countDownLatch) {
//            this.countDownLatch=countDownLatch;
//        }
//        @Override
//        public void run() {
//
//            countDownLatch.countDown();
//        }
//    }
    private void getDataFromLocal() {



        if(type==12){

            musics = new ArrayList<>();

            musics.add(new Music(212,"DJ天宝-全粤语Club音乐VS韶关",2,"2019-03-03",100,R.raw.main03+"","http://192.168.137.9:8080/kbapp/img/movie02.PNG",3000));
            musics.add(new Music(21321,"Dj龙仔-国粤语Club音乐不再回头了",2,"2019-03-03",100,R.raw.main04+"",null,3000));
        }else {

            musics=new ArrayList<>();
            RequestParams params = new RequestParams(Constants.QURRYMUSICFORTYPE);
            Log.e("--",type+"");
            params.addQueryStringParameter("type", type+"");
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.e("成功==", "" + result);
                    musics=parseJson(result);



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
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return stub;
    }


    private IMusicPlayerService.Stub stub = new IMusicPlayerService.Stub() {
        MusicPlayerService service = MusicPlayerService.this;

        @Override
        public void openAudio(int position) throws RemoteException {
            service.openAudio(position);
        }

        @Override
        public void start() throws RemoteException {
            service.start();

        }

        @Override
        public void pause() throws RemoteException {
            service.pause();
        }

        @Override
        public void stop() throws RemoteException {
            service.stop();
        }

        @Override
        public int getCurrentPosition() throws RemoteException {
            return service.getCurrentPosition();
        }

        @Override
        public int getDuration() throws RemoteException {
            return service.getDuration();
        }

        @Override
        public String getArtist() throws RemoteException {
            return service.getArtist();
        }

        @Override
        public String getName() throws RemoteException {
            return service.getName();
        }

        @Override
        public String getAudioPath() throws RemoteException {
            return service.getAudioPath();
        }

        @Override
        public void next() throws RemoteException {
            service.next();
        }

        @Override
        public void pre() throws RemoteException {
            service.pre();
        }

        @Override
        public void setPlayMode(int playmode) throws RemoteException {
            service.setPlayMode(playmode);
        }

        @Override
        public int getPlayMode() throws RemoteException {
            return service.getPlayMode();
        }

        @Override
        public boolean isPlaying() throws RemoteException {
            return service.isPlaying();
        }

        @Override
        public void seekTo(int progress) throws RemoteException {
            mediaPlayer.seekTo(progress);
        }
    };


    /**
     * 根据位置打开对应的音频文件,并且播放
     *
     * @param position
     */

    private void openAudio(int position) {
        this.position = position;



        if (musics != null && musics.size() > 0) {
            music = musics.get(position-1);

            if (mediaPlayer != null) {
//                mediaPlayer.release();
                mediaPlayer.reset();
            }

            try {
                mediaPlayer = new MediaPlayer();
                //设置监听：播放出错，播放完成，准备好
                Uri uri;
                uri = Uri.parse(music.getUrl());

                //
                mediaPlayer.setDataSource(this, uri);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MyOnPreparedListener());
                mediaPlayer.setOnCompletionListener(new MyOnCompletionListener());
                mediaPlayer.setOnErrorListener(new MyOnErrorListener());

                if(playmode==MusicPlayerService.REPEAT_SINGLE){
                    //单曲循环播放-不会触发播放完成的回调
                    mediaPlayer.setLooping(true);
                }else{
                    //不循环播放
                    mediaPlayer.setLooping(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            Toast.makeText(MusicPlayerService.this, "还没有数据", Toast.LENGTH_SHORT).show();
        }

    }

    class MyOnErrorListener implements MediaPlayer.OnErrorListener {

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
//            next();
            Log.e("----","cuowu");
            EventBus.getDefault().post(new MessageEvent("hideProgress"));
            return true;
        }
    }

    class  MyOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {

            next();
        }
    }

    class MyOnPreparedListener implements MediaPlayer.OnPreparedListener {

        @Override
        public void onPrepared(MediaPlayer mp) {
            start();
            Log.e("----", "zhunbei");
//            notifyChange("com.qc.mobileplayer.pullprechange");
            EventBus.getDefault().post(music);
            EventBus.getDefault().post(new MessageEvent("hideProgress"));
            notifyChange("com.qc.yyzh.chongbochange");
        }
    }

    private void notifyChange(String s) {
        Intent intent=new Intent(s);
        sendBroadcast(intent);
    }

    private NotificationManager manager;

    /**
     * 播放音乐
     */
    private void start() {

        mediaPlayer.start();
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //最主要
        Intent intent = new Intent(this, CustomAudioPlayer.class);
        intent.putExtra("notification",true);//标识来自状态拦
        intent.putExtra("po",position);//标识来自状
        PendingIntent pendingIntent = PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("音乐之声")
                .setContentText("正在播放:" + getName())
                .setContentIntent(pendingIntent)
                .build();
        manager.notify(1, notification);
    }

    /**
     * 播暂停音乐
     */
    private void pause() {
        mediaPlayer.pause();
    }

    /**
     * 停止
     */
    private void stop() {
        mediaPlayer.stop();
    }

    /**
     * 得到当前的播放进度
     *
     * @return
     */
    private int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }


    /**
     * 得到当前音频的总时长
     *
     * @return
     */
    private int getDuration() {
        return mediaPlayer.getDuration();
    }

    /**
     * 得到艺术家
     *
     * @return
     */
    private String getArtist() {
        return "";
    }

    /**
     * 得到歌曲名字
     *
     * @return
     */
    private String getName() {
        return music.getName();
    }


    /**
     * 得到歌曲播放的路径
     *
     * @return
     */
    private String getAudioPath() {
        return music.getUrl();
    }

    /**
     * 播放下一个视频
     */
    private void next() {
        //1.根据当前的播放模式，设置下一个的位置
        setNextPosition();
        //2.根据当前的播放模式和下标位置去播放音频
        openNextAudio();

    }
    private void setNextPosition() {
        int playmode = getPlayMode();
        if(playmode==MusicPlayerService.REPEAT_NORMAL){
            position++;
        }else if(playmode == MusicPlayerService.REPEAT_SINGLE){
            position++;
            if(position >=musics.size()){
                position = 0;
            }
        }else if(playmode ==MusicPlayerService.REPEAT_ALL){
            position++;
            if(position >=musics.size()){
                position = 0;
            }
        }else{
            position++;
        }
    }
    private void openNextAudio() {
        int playmode = getPlayMode();
        if(playmode==MusicPlayerService.REPEAT_NORMAL){
            if(position < musics.size()){
                //正常范围
                openAudio(position);
            }else{
                position = musics.size()-1;
            }
        }else if(playmode == MusicPlayerService.REPEAT_SINGLE){
            openAudio(position);
        }else if(playmode ==MusicPlayerService.REPEAT_ALL){
            openAudio(position);
        }else{
            if(position < musics.size()){
                //正常范围
                openAudio(position);
            }else{
                position = musics.size()-1;
            }
        }
    }
    /**
     * 播放上一个视频
     */
    private void pre() {
        //1.根据当前的播放模式，设置上一个的位置
        setPrePosition();
        //2.根据当前的播放模式和下标位置去播放音频
        openPreAudio();
    }

    private void openPreAudio() {
        int playmode = getPlayMode();
        if(playmode==MusicPlayerService.REPEAT_NORMAL){
            if(position >= 0){
                //正常范围
                openAudio(position);
            }else{
                position = 0;
            }
        }else if(playmode == MusicPlayerService.REPEAT_SINGLE){
            openAudio(position);
        }else if(playmode ==MusicPlayerService.REPEAT_ALL){
            openAudio(position);
        }else{
            if(position >= 0){
                //正常范围
                openAudio(position);
            }else{
                position = 0;
            }
        }
    }

    private void setPrePosition() {
        int playmode = getPlayMode();
        if(playmode==MusicPlayerService.REPEAT_NORMAL){
            position--;
        }else if(playmode == MusicPlayerService.REPEAT_SINGLE){
            position--;
            if(position < 0){
                position = musics.size()-1;
            }
        }else if(playmode ==MusicPlayerService.REPEAT_ALL){
            position--;
            if(position < 0){
                position = musics.size()-1;
            }
        }else{
            position--;
        }
    }


    private void setPlayMode(int playmode) {
        this.playmode = playmode;
        CacheUtils.putPlaymode(this, "playmode", playmode);

        if(playmode==MusicPlayerService.REPEAT_SINGLE){
            //单曲循环播放-不会触发播放完成的回调
            mediaPlayer.setLooping(true);
        }else{
            //不循环播放
            mediaPlayer.setLooping(false);
        }
    }

    /**
     * 得到播放模式
     *
     * @return
     */
    private int getPlayMode() {
        return playmode;
    }


    /**
     * 是否在播放音频
     * @return
     */
    private boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }


}
