package com.qc.yyzs.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qc.yyzs.R;
import com.qc.yyzs.entity.EntityVideo;
import com.qc.yyzs.entity.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class DJListMusicActivity extends AppCompatActivity {
    @ViewInject(R.id.dj_extra)
    private LinearLayout dj_extra;
    @ViewInject(R.id.back)
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_djlist_music);
        x.view().inject(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dj_extra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(DJListMusicActivity.this,DJActivity.class);
//                intent.putExtra("music",5);
//                startActivity(intent);
                EventBus.getDefault().post(new EntityVideo());

                EventBus.getDefault().post(new MessageEvent("rotate"));
                finish();
            }
        });
    }
}
