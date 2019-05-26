package com.qc.yyzs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qc.yyzs.R;
import com.qc.yyzs.utils.CacheUtils;
import com.qc.yyzs.utils.Constants;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class LoginActivity extends AppCompatActivity {
    @ViewInject(R.id.login_back)
    private ImageView back;
    @ViewInject(R.id.login_zhuce)
    private TextView login_zhuce;
    @ViewInject(R.id.name)
    private EditText name;
    @ViewInject(R.id.password)
    private EditText password;
    @ViewInject(R.id.login_button)
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        x.view().inject(this);
        setListener();
    }

    private void setListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name1= String.valueOf(name.getText());
                String password1= String.valueOf(password.getText());
                if(name1==""||password1==""||name1==null|password1==null){
                    Snackbar.make(back, "请输入用户名或者密码！", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }else{


                RequestParams params = new RequestParams(Constants.LOGIN);
                params.addQueryStringParameter("name", name1);
                params.addQueryStringParameter("password", password1);
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.e("成功==", "" + result);
//                        if(r)
                        if(result.equals("1111111111")){
                            CacheUtils.putString(LoginActivity.this,"username",name1);
                            finish();
                        }else if(result.equals("0")){
                            Toast toast = Toast.makeText(LoginActivity.this, null, Toast.LENGTH_LONG);
                            toast.setText("密码错误或账号不存在");
                            toast.show();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Toast toast = Toast.makeText(LoginActivity.this, null, Toast.LENGTH_LONG);
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
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        login_zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
