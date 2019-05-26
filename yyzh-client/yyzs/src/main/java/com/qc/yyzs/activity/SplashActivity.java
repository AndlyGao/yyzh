package com.qc.yyzs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.qc.yyzs.R;

public class SplashActivity extends AppCompatActivity {
    private final static int FOWORD=12;
    private TextView splash_text;
    private TextView splash_next;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case FOWORD:
                    String text = (String) splash_text.getText();
                    int text1=Integer.parseInt(text);
                    text1--;
                    splash_text.setText(text1+"");
                    if(text1==0){
                        finish();
                        Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                    handler.removeMessages(FOWORD);
                    handler.sendEmptyMessageDelayed(FOWORD,1000);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        splash_text = (TextView)findViewById(R.id.splash_text);
        handler.sendEmptyMessageDelayed(FOWORD,1000);
        splash_next = (TextView)findViewById(R.id.splash_next);
        splash_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

}
