package com.qc.yyzs.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qc.yyzs.R;
import com.qc.yyzs.utils.CacheUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class CheckBgActivity extends AppCompatActivity {

    @ViewInject(R.id.ck_back)
    private ImageView back;
    @ViewInject(R.id.bg01)
    private LinearLayout ll01;
    @ViewInject(R.id.gb02)
    private LinearLayout ll02;
    @ViewInject(R.id.gb03)
    private LinearLayout ll03;

    @ViewInject(R.id.bgyes01)
    private ImageView im01;
    @ViewInject(R.id.bgyes02)
    private ImageView im02;
    @ViewInject(R.id.bgyes03)
    private ImageView im03;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_bg);
        x.view().inject(this);
        im01.setVisibility(View.GONE);
        im02.setVisibility(View.GONE);
        checkTheme();
        String theme = CacheUtils.getString(this, "theme");
        Log.e("dadwwda", "main" + theme);
        if(theme.equals("bg01")){
            im01.setVisibility(View.VISIBLE);
        }else if(theme.equals("bg02")) {
            im02.setVisibility(View.VISIBLE);
        }else if(theme.equals("bg03")){
            im03.setVisibility(View.VISIBLE);
        }else{
            im01.setVisibility(View.GONE);
            im02.setVisibility(View.GONE);
            im03.setVisibility(View.GONE);
        }


        ll01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheUtils.putString(CheckBgActivity.this, "theme", "bg01");
                checkTheme();
            }
        });
        ll02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CacheUtils.putString(CheckBgActivity.this, "theme", "bg02");
                checkTheme();
            }
        });
        ll03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheUtils.putString(CheckBgActivity.this, "theme", "bg03");
                checkTheme();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public  void checkTheme(){
        String theme = CacheUtils.getString(this, "theme");

        if(theme.equals("bg01")){
            im01.setVisibility(View.VISIBLE);
            im02.setVisibility(View.GONE);
            im03.setVisibility(View.GONE);
        }else if(theme.equals("bg02")) {
            im02.setVisibility(View.VISIBLE);
            im01.setVisibility(View.GONE);
            im03.setVisibility(View.GONE);
        }else if(theme.equals("bg03")){
            im03.setVisibility(View.VISIBLE);
            im01.setVisibility(View.GONE);
            im02.setVisibility(View.GONE);
        }
        else{
            im01.setVisibility(View.GONE);
            im02.setVisibility(View.GONE);
            im03.setVisibility(View.GONE);
        }
    }
}
