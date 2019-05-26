package com.qc.yyzs.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class RegisterActivity extends AppCompatActivity {
    @ViewInject(R.id.register_back)
    private ImageView register_back;
    @ViewInject(R.id.register_button)
    private Button register_button;
    @ViewInject(R.id.name)
    private EditText name;
    @ViewInject(R.id.password)
    private EditText password;
    @ViewInject(R.id.email)
    private EditText email;
    @ViewInject(R.id.phone)
    private EditText phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        x.view().inject(this);
        setListener();
    }

    private void setListener(){
        register_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1= String.valueOf(name.getText());
                String password1= String.valueOf(password.getText());
                String eamil1= String.valueOf(email.getText());
                String phone1= String.valueOf(phone.getText());
                RequestParams params = new RequestParams(Constants.REGESTER);
                params.addQueryStringParameter("name", name1);
                params.addQueryStringParameter("password", password1);
                params.addQueryStringParameter("email", eamil1);
                params.addQueryStringParameter("phone", phone1);
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.e("成功==", "" + result);
                        //                        if(r)
                        if (result.equals("1")) {
                            Toast toast = Toast.makeText(RegisterActivity.this, null, Toast.LENGTH_LONG);
                            toast.setText("注册成功");
                            toast.show();
                            finish();
                        } else if (result.equals("0")) {
                            Toast toast = Toast.makeText(RegisterActivity.this, null, Toast.LENGTH_LONG);
                            toast.setText("注册失败");
                            toast.show();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Toast toast = Toast.makeText(RegisterActivity.this, null, Toast.LENGTH_LONG);
                        toast.setText("请检查网络");
                        toast.show();
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                    }

                    @Override
                    public void onFinished() {
                    }
                });
            }
        });
    }
}
