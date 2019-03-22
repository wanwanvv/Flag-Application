package com.example.flagapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;

import com.example.flagapplication.R;

public class SetAlarmTimeActivity extends AppCompatActivity implements View.OnClickListener{
    private CheckBox no_remind,min10_remind,hour1_remind,day1_remind;
    ImageButton left_alarm_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm_time);
        left_alarm_back= findViewById(R.id.left_alarm_back);

        no_remind = (CheckBox) findViewById(R.id.no_remind);
        min10_remind = (CheckBox) findViewById(R.id.min10_remind);
        hour1_remind = (CheckBox) findViewById(R.id.hour1_remind);
        day1_remind = (CheckBox) findViewById(R.id.day1_remind);

        no_remind.setChecked(true);

        no_remind.setOnClickListener(this);
        min10_remind.setOnClickListener(this);
        hour1_remind.setOnClickListener(this);
        day1_remind.setOnClickListener(this);
        left_alarm_back.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()){
            case R.id.no_remind:
                no_remind.setChecked(true);
                min10_remind.setChecked(false);
                hour1_remind.setChecked(false);
                day1_remind.setChecked(false);
                intent.putExtra("remind", "Null");
                setResult(1, intent);
                finish();
                break;
            case R.id.min10_remind:
                min10_remind.setChecked(true);
                no_remind.setChecked(false);
                hour1_remind.setChecked(false);
                day1_remind.setChecked(false);
                intent.putExtra("remind", "Remind 10 minutes ahead");
                setResult(1, intent);
                finish();
                break;
            case R.id.hour1_remind:
                hour1_remind.setChecked(true);
                no_remind.setChecked(false);
                min10_remind.setChecked(false);
                day1_remind.setChecked(false);
                intent.putExtra("remind", "Remind an hour ahead");
                setResult(1, intent);
                finish();
                break;
            case R.id.day1_remind:
                day1_remind.setChecked(true);
                no_remind.setChecked(false);
                min10_remind.setChecked(false);
                hour1_remind.setChecked(false);
                intent.putExtra("remind", "Remind a day ahead");
                setResult(1, intent);
                finish();
                break;
            case R.id.left_alarm_back:
                finish();
                break;
        }
    }
}
