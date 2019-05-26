package com.qc.yyzs.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qc.yyzs.R;
import com.qc.yyzs.utils.CacheUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class UserInfoActivity extends AppCompatActivity {
    @ViewInject(R.id.user)
    private TextView user;
    @ViewInject(R.id.userinfo_back)
    private ImageView userinfo_back;
    @ViewInject(R.id.logout)
    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        x.view().inject(this);
        setListener();
        String username = CacheUtils.getString(UserInfoActivity.this, "username");
        user.setText("用户名:"+username);
    }

    private void setListener() {
        userinfo_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheUtils.putString(UserInfoActivity.this,"username","");
                finish();
            }
        });
    }

}
