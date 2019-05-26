package com.qc.yyzs.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.net.TrafficStats;
import android.net.Uri;
import android.provider.Settings;
import android.view.WindowManager;

import java.util.Formatter;
import java.util.Locale;

public class Utils {

    private StringBuilder mFormatBuilder;

    private Formatter mFormatter;

    public Utils() {
        // 转换成字符串的时间
        mFormatBuilder = new StringBuilder();

        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

    }

    /**
     * 把毫秒转换成：1:20:30这里形式
     *
     * @param timeMs
     * @return
     */
    public String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;

        int minutes = (totalSeconds / 60) % 60;

        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {

            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds)
                    .toString();
        } else {

            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }
//是否网路uri
    public boolean isNeturi(String uri) {
        boolean result = false;
        if (uri != null) {

            if (uri.toLowerCase().startsWith("http") | uri.toLowerCase().startsWith("mms") | uri.toLowerCase().startsWith("rtsp")) {

                result = true;
            }
        }
        return result;
    }

    private long lastTotalRxBytes = 0;

    private long lastTimeStamp = 0;

    //获取网速
    public String getNetSpeed(Context context) {
        long nowTotalRxBytes = TrafficStats.getUidRxBytes(context.getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);

        long nowTimeStamp = System.currentTimeMillis();

        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换

        lastTimeStamp = nowTimeStamp;

        lastTotalRxBytes = nowTotalRxBytes;

        return String.valueOf(speed) + " kb/s";
    }

    //判断是否开启了自动亮度调节
    public static boolean isAutoBrightness(Context context) {

        ContentResolver resolver = context.getContentResolver();

        boolean automicBrightness = false;

        try {

            automicBrightness = Settings.System.getInt(resolver,

                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;

        } catch (Settings.SettingNotFoundException e) {

            e.printStackTrace();

        }

        return automicBrightness;

    }
    //获取当前屏幕亮度
    public static int getScreenBrightness(Context context) {

        int nowBrightnessValue = 0;

        ContentResolver resolver = context.getContentResolver();

        try {

            nowBrightnessValue = Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return nowBrightnessValue;

    }
    //自动亮度调节

    public static boolean autoBrightness(Context activity, boolean flag) {

        int value = 0;

        if (flag) {

            value = Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC; //开启

        } else {

            value = Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL;//关闭

        }

        return Settings.System.putInt(activity.getContentResolver(),

                Settings.System.SCREEN_BRIGHTNESS_MODE,

                value);

    }

    //设置亮度，退出app也能保持该亮度值
    public static void saveBrightness(Context context, int brightness) {

        ContentResolver resolver = context.getContentResolver();

        Uri uri = Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);

        Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, brightness);

        resolver.notifyChange(uri, null);

    }
    //、设置当前activity显示的亮度

    public static void setBrightness(Activity activity, int brightness) {

        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();

        lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);

        activity.getWindow().setAttributes(lp);

    }
}
