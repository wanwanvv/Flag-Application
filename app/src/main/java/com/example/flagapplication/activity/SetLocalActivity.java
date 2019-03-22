package com.example.flagapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.flagapplication.R;

public class SetLocalActivity extends AppCompatActivity implements View.OnClickListener{
    EditText ed_local;
    TextView tv_save;
    ImageButton left_local_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_local);
        ed_local =findViewById(R.id.ed_local);
        tv_save = findViewById(R.id.tv_save);
        left_local_back = findViewById(R.id.left_local_back);

        tv_save.setOnClickListener(this);
        left_local_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()) {
            case R.id.tv_save:
                if(ed_local.getText().toString().equals("")){
                    intent.putExtra("local", "Null");
                    setResult(2, intent);
                    finish();
                }else {
                    intent.putExtra("local", ed_local.getText().toString());
                    setResult(2, intent);
                    finish();
                }
                break;
            case R.id.left_local_back:
                finish();
                break;
        }
    }


}
