package com.qc.yyzs.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.qc.yyzs.R;
import com.qc.yyzs.entity.MessageEvent;
import com.qc.yyzs.pager.BasePager;
import com.qc.yyzs.pager.DadiePager;
import com.qc.yyzs.pager.MusicPager;
import com.qc.yyzs.pager.TeachPager;
import com.qc.yyzs.utils.CacheUtils;
import com.qc.yyzs.view.MainLinearLayout;
import com.qc.yyzs.view.MyFragMent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @ViewInject(R.id.main_sousuo)
    private ImageView main_sousuo;
    @ViewInject(R.id.main_shezhi)
    private ImageView main_shehhi;

    private ImageView navheadim;

    private TextView navheadtv;
    private DrawerLayout drawer;
    private FrameLayout main_fl;
    private RadioGroup main_bottom_rg;
    private ArrayList<BasePager> basePagers;
    private int position;
    private void initView(){
        m1= (MainLinearLayout) findViewById(R.id.rrr);
//        text = (TextView)findViewById(R.id.text);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        main_fl = (FrameLayout)findViewById(R.id.main_fl);

        main_bottom_rg = (RadioGroup)findViewById(R.id.main_bottom_rg);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        View headerView = navigationView.getHeaderView(0);
        navheadim = (ImageView) headerView.findViewById(R.id.imageViewlogin);
        navheadtv = (TextView) headerView.findViewById(R.id.textViewlogin);
        navigationView.setItemIconTintList(null);
    }
    private void setListener(){
        main_shehhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        m1.setlinister(new MainLinearLayout.LLLisener() {
            @Override
            public void invoke() {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        main_sousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchMActivity.class);
                startActivity(intent);
            }
        });
        navheadim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = CacheUtils.getString(MainActivity.this, "username");
                if (username == null || username.equals("")) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
    private static String mBgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPermission();
        setContentView(R.layout.activity_main);
        x.view().inject(this);
        initView();
        setListener();
        showUserName();
        checkbg();
        EventBus.getDefault().register(this);
        //        fornet(Constants.CURRENT_LOGIN);
        //        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //        setSupportActionBar(toolbar);

        //        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //        fab.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        //                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                        .setAction("Action", null).show();
        //            }
        //        });
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
        basePagers = new ArrayList<>();
        basePagers.add(new MusicPager(MainActivity.this));
        basePagers.add(new TeachPager(MainActivity.this));
        basePagers.add(new DadiePager(MainActivity.this));
        main_bottom_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    default:
                        isGrantExternalRW(MainActivity.this);
                        position = 0;
                        break;
                    case R.id.mian_botton_music:
                        isGrantExternalRW(MainActivity.this);
                        position = 0;
                        break;
                    case R.id.mian_botton_teach:
                        position = 1;
                        break;
                    case R.id.mian_botton_dish:
                        position = 2;
                        break;
                }
                setFragment();
            }
        });
        main_bottom_rg.check(R.id.mian_botton_music);

    }




    private void checkbg() {
        String theme = CacheUtils.getString(this, "theme");

        if(theme.equals("bg01")){
            mBgId="mainbg";
            int id = getResources().getIdentifier(mBgId, "drawable", getPackageName());
            m1.setBackgroundDrawable(getResources().getDrawable(id));
        }else if(theme.equals("bg02")){
            mBgId="splash";
            int id = getResources().getIdentifier(mBgId, "drawable", getPackageName());
            m1.setBackgroundDrawable(getResources().getDrawable(id));
        }
        else if(theme.equals("bg03")){
            mBgId="bgwhite";
            int id = getResources().getIdentifier(mBgId, "drawable", getPackageName());
            m1.setBackgroundDrawable(getResources().getDrawable(id));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showUserName();
    }

    private void showUserName() {
        String username = CacheUtils.getString(MainActivity.this, "username");
        if(username==null||username.equals("")){
            navheadtv.setText("未登录");
        }else {

            navheadtv.setText(username);
        }
    }

    private MainLinearLayout m1;


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.check_bg) {
            // Handle the camera action
            Intent intent=new Intent(MainActivity.this,CheckBgActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.clear) {
            Toast toast = Toast.makeText(MainActivity.this, null, Toast.LENGTH_SHORT);
            toast.setText("清除成功！");
            toast.show();
        }
        else if (id == R.id.help) {
//            Intent intent=new Intent(MainActivity.this,HelpActivity.class);
//            startActivity(intent);
        } else if (id == R.id.check_version) {
            Snackbar.make(m1, "目前是最新版本！", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        } else if (id == R.id.about) {
            Intent intent=new Intent(MainActivity.this,AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.up) {
            Intent intent=new Intent(MainActivity.this,UpActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public static boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission
                (android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
            return false;
        }
        return true;
    }
    private void setFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fl, new MyFragMent(getBasePager()));
        fragmentTransaction.commit();
    }
    private BasePager getBasePager() {
        BasePager basePager = basePagers.get(position);
        if (basePager != null && !basePager.isInitData) {
            basePager.initData();
            basePager.isInitData = true;
        }
        return basePager;
    }
    private boolean likai = false;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    likai = false;
                    break;
            }
        }
    };
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (!likai) {
                    Toast toast = Toast.makeText(MainActivity.this, null, Toast.LENGTH_SHORT);
                    toast.setText("再点一次推出");
                    toast.show();
                    handler.sendEmptyMessageDelayed(1, 2000);
                    likai = true;
                } else {
                    finish();
                }


                break;
        }
        return true;
    }
    private void initPermission() {
        String permissions[] = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE
        };
        ArrayList<String> toApplyList = new ArrayList<String>();
        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm))
            {
                toApplyList.add(perm);             //进入到这里代表没有权限.
            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }  }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("TAG", "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("TAG", "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "destory");
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkbg();
    }

    public void fornet(String url){
        RequestParams params = new RequestParams(url);
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
                //                LogUtil.e("onFinished==");
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toteach(MessageEvent messageEvent) {
        if(messageEvent.getMessage()=="hello"){
            position=1;
            setFragment();
            main_bottom_rg.check(R.id.mian_botton_teach);

        }
    }
}
