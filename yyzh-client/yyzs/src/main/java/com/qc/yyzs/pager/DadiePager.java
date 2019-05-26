package com.qc.yyzs.pager;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qc.yyzs.R;
import com.qc.yyzs.activity.DJActivity;
import com.qc.yyzs.entity.MessageEvent;
import com.qc.yyzs.utils.Constants;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class DadiePager extends  BasePager {
    private  final static int  GETCURR=2131;

    public DadiePager(Context context) {
        super(context);
        handler.sendEmptyMessageDelayed(GETCURR, 2000);
    }

    @ViewInject(R.id.toteach)
    private Button toTeach;
    @ViewInject(R.id.todadie)
    private Button toDadie;
    @ViewInject(R.id.dadie_user)
    private TextView dadie_user;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case  GETCURR:
                    showCurrentUser();
                    handler.removeMessages(GETCURR);
                    handler.sendEmptyMessageDelayed(GETCURR,2000);
                    break;
            }
        }
    };

    @Override
    public View initView() {
        View view=View.inflate(super.context, R.layout.dadie_pager,null);
        x.view().inject(this, view);


        return view;
    }

    @Override
    public void initData() {
        toTeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent("hello"));
            }
        });
        toDadie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DJActivity.class);
                context.startActivity(intent);
                RequestParams params = new RequestParams(Constants.CURRENT_LOGIN);
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
    }


     public   void showCurrentUser(){
         RequestParams params = new RequestParams(Constants.GETCURRENTUSER);
         x.http().get(params, new Callback.CommonCallback<String>() {
             @Override
             public void onSuccess(String result) {
                 Log.e("联网成功==", "" + result);
                 dadie_user.setText(result);
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
}
