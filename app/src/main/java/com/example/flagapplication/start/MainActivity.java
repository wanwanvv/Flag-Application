package com.example.flagapplication.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.flagapplication.R;
import com.example.flagapplication.view.flagActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn_login,btn_register,btn_forget;
    EditText edit_name,edit_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login=findViewById(R.id.login);
        btn_register=findViewById(R.id.register);
        btn_forget=findViewById(R.id.forget);
        edit_name=findViewById(R.id.name);
        edit_pwd=findViewById(R.id.password);
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        btn_forget.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                AVUser.logInInBackground(edit_name.getText().toString(), edit_pwd.getText().toString(), new LogInCallback<AVUser>() {
                    @Override
                    public void done(AVUser avUser, AVException e) {
                        if(e==null){
                            startActivity(new Intent(MainActivity.this, flagActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"用户名或密码错误",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
            case R.id.register:
                Intent intent=new Intent(this, registerActivity.class);
                startActivity(intent);
                break;
            case R.id.forget:
                break;
        }
    }


}
