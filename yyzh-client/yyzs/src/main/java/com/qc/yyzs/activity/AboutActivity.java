package com.qc.yyzs.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qc.yyzs.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class AboutActivity extends AppCompatActivity {
    @ViewInject(R.id.back)
    private ImageView back;
    @ViewInject(R.id.call)
    private Button call;
    @ViewInject(R.id.callnumber)
    private TextView number;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        x.view().inject(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = number.getText();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + text);
                intent.setData(data);
                startActivity(intent);
            }
        });
    }
}
