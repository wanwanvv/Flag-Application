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
import com.avos.avoscloud.SignUpCallback;
import com.example.flagapplication.R;

public class registerActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_cancel,btn_RE;
    EditText edit_rename,edit_repwd,edit_reemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);
        btn_cancel=findViewById(R.id.cancel);
        btn_RE=findViewById(R.id.Re);
        edit_reemail=findViewById(R.id.reemail);
        edit_rename=findViewById(R.id.rename);
        edit_repwd=findViewById(R.id.repwd);
        btn_RE.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.cancel:
                Intent intent=new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.Re:
                AVUser user=new AVUser();
                user.setUsername(edit_rename.getText().toString());
                user.setEmail(edit_reemail.getText().toString());
                user.setPassword(edit_repwd.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(AVException e) {
                        if(e==null){
                            startActivity(new Intent(registerActivity.this,MainActivity.class));
                            registerActivity.this.finish();
                        }
                        else{
                            Toast.makeText(registerActivity.this,"账号已经存在，请重新输入",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
        }
    }


}
