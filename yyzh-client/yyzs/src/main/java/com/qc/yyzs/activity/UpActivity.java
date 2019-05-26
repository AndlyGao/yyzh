package com.qc.yyzs.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.qc.yyzs.R;
import com.qc.yyzs.utils.Constants;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class UpActivity extends AppCompatActivity {

    @ViewInject(R.id.up_text)
    private EditText up_text;
    @ViewInject(R.id.up_back)
    private ImageView up_back;
    @ViewInject(R.id.up_button)
    private Button up_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up);
        x.view().inject(this);

        up_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String  text_ =
                        String.valueOf(up_text.getText());
                RequestParams params = new RequestParams(Constants.UP);
                params.addQueryStringParameter("text", text_);
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        up_text.setText("");
                        Toast toast = Toast.makeText(UpActivity.this, null, Toast.LENGTH_SHORT);
                        toast.setText("提交成功！");
                        toast.show();

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        //                LogUtil.e("联网失败==" + ex.getMessage());
                        Toast toast = Toast.makeText(UpActivity.this, null, Toast.LENGTH_SHORT);
                        toast.setText("提交失败！");
                        toast.show();
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
}
