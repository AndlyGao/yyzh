package com.qc.yyzs;

import android.app.Application;
import android.util.Log;

import org.xutils.x;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
        Log.e("application ","启动成功");
    }
}
