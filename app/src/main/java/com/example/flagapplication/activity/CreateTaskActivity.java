package com.example.flagapplication.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.flagapplication.R;
import com.example.flagapplication.alarmremind.AlarmBean;
import com.example.flagapplication.alarmremind.SendAlarmBroadcast;
import com.example.flagapplication.database.AlarmDBSupport;
import com.example.flagapplication.utils.ColorUtils;
import com.example.flagapplication.view.flagMainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CreateTaskActivity extends AppCompatActivity implements View.OnClickListener {
    private DatePickerDialog mDataPicker;
    private TimePickerDialog mStartTimePicker, mEndTimePicker;
    private boolean isAllDay = false;
    private boolean isVibrate = false;
    private AlarmBean alarmBean = new AlarmBean();
    private AlarmDBSupport support;
    private int id;
    EditText alarm_title;
    EditText alarm_description;
    TextView alarm_replay;
    TextView alarm_remind;
    TextView alarm_local;
    TextView alarm_color;
    TextView alarm_tone_Path;
    TextView alarm_date;
    TextView insert_update_title;
    LinearLayout action_bar;
    TextView alarm_start_time;
    TextView alarm_end_time;
    ImageButton left_clear;
    RelativeLayout layout_alarm_replay;
    RelativeLayout layout_alarm_remind;
    RelativeLayout layout_alarm_local;
    RelativeLayout layout_alarm_color;
    RelativeLayout layout_alarm_tone_Path;
    Switch sw_all_day;
    Switch sw_vibrate;
    TextView tv_save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_schedule_activity);
        initLayout();

        support = new AlarmDBSupport(getApplicationContext());
        if (getIntent().getStringExtra("type").equals("DetailToAdd")) {
            Intent intent = getIntent();
            AlarmBean bean = (AlarmBean) intent.getSerializableExtra("AlarmBean");
            id = bean.getId();
            alarm_title.setText(bean.getTitle());
            alarm_remind.setText(bean.getAlarmTime());
            alarm_color.setText(bean.getAlarmColor());
            alarm_description.setText(bean.getDescription());
            alarm_local.setText(bean.getLocal());
            alarm_replay.setText(bean.getReplay());
//            alarm_date.setText(DateHelper.getScheduleDate(bean));
//            alarm_start_time.setText(DateHelper.getStartTime(bean));
//            alarm_end_time.setText(DateHelper.getEndTime(bean));
            insert_update_title.setText("Modify the flag");
            //动态改变颜色
            int colorId = ColorUtils.getColorFromStr(bean.getAlarmColor());
            action_bar.setBackgroundColor(getResources().getColor(colorId));
            Window window = getWindow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(getResources().getColor(colorId));
            }
        } else {
            insert_update_title.setText("Add a flag");
        }

    }


    private void initLayout(){
        alarm_title = findViewById(R.id.alarm_title);
        alarm_replay = findViewById(R.id.alarm_replay);
        alarm_description = findViewById(R.id.alarm_description);
        alarm_remind = findViewById(R.id.alarm_remind);
        alarm_local = findViewById(R.id.alarm_local);
        alarm_color = findViewById(R.id.alarm_color);
        alarm_tone_Path= findViewById(R.id.alarm_tone_Path);
        alarm_date = findViewById(R.id.alarm_date);
        insert_update_title = findViewById(R.id.insert_update_title);
        action_bar= findViewById(R.id.action_bar);
        alarm_start_time = findViewById(R.id.alarm_start_time);
        alarm_end_time = findViewById(R.id.alarm_end_time);
        left_clear = findViewById(R.id.left_clear);
        layout_alarm_replay= findViewById(R.id.layout_alarm_replay);
        layout_alarm_remind= findViewById(R.id.layout_alarm_remind);
        layout_alarm_local= findViewById(R.id.layout_alarm_local);
        layout_alarm_color = findViewById(R.id.layout_alarm_color);
        layout_alarm_tone_Path= findViewById(R.id.layout_alarm_tone_Path);
        sw_all_day= findViewById(R.id.sw_all_day);
        sw_vibrate= findViewById(R.id.sw_vibrate);
        tv_save= findViewById(R.id.tv_save);
        alarm_date.setOnClickListener(this);
        alarm_start_time.setOnClickListener(this);
        alarm_end_time.setOnClickListener(this);
        left_clear.setOnClickListener(this);
        layout_alarm_replay.setOnClickListener(this);
        layout_alarm_remind.setOnClickListener(this);
        layout_alarm_local.setOnClickListener(this);
        layout_alarm_color.setOnClickListener(this);
        layout_alarm_tone_Path.setOnClickListener(this);
        sw_all_day.setOnClickListener(this);
        sw_vibrate.setOnClickListener(this);
        tv_save.setOnClickListener(this);


    }
    @Override
    public void onClick(View view) {
        //获得对象
        //myapplication = (Myapplication) getApplication();
        switch (view.getId()) {
            case R.id.alarm_date:
                getDatePickerDialog();
                mDataPicker.show();
                break;
            case R.id.alarm_start_time:
                getStartTimePickerDialog();
                mStartTimePicker.show();
                break;
            case R.id.alarm_end_time:
                getEndTimePickerDialog();
                mEndTimePicker.show();
                break;
            case R.id.left_clear:
                startActivity(new Intent(this, flagMainActivity.class));
                finish();
                break;
            case R.id.layout_alarm_replay:
                startActivityForResult(new Intent(CreateTaskActivity.this, SetRePlayActivity.class), 0);
                break;
            case R.id.layout_alarm_remind:
                startActivityForResult(new Intent(CreateTaskActivity.this, SetAlarmTimeActivity.class), 1);
                break;
            case R.id.layout_alarm_local:
                startActivityForResult(new Intent(CreateTaskActivity.this, SetLocalActivity.class), 2);
                break;
            case R.id.layout_alarm_color:
                startActivityForResult(new Intent(CreateTaskActivity.this, SetColorActivity.class), 3);
                break;
            case R.id.layout_alarm_tone_Path:
                startActivityForResult(new Intent(CreateTaskActivity.this, SetAlarmToneActivity.class), 4);
                break;
            case R.id.sw_all_day:
                if (!isAllDay) {
                    alarm_start_time.setVisibility(View.GONE);
                    alarm_end_time.setVisibility(View.GONE);
                    isAllDay = true;
                } else {
                    alarm_start_time.setVisibility(View.VISIBLE);
                    alarm_end_time.setVisibility(View.VISIBLE);
                    isAllDay = false;
                }
                break;
            case R.id.sw_vibrate:
                Vibrator vibrator;
                vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                if (!isVibrate) {
                    alarmBean.setIsVibrate(1);
                    vibrator.vibrate(500);
                    isVibrate = true;
                } else {
                    alarmBean.setIsVibrate(0);
                    vibrator.cancel();
                    isVibrate = false;
                }
                break;
            case R.id.tv_save:
                //设置标题
                if (alarm_title.getText().toString().equals("")) {
                    alarmBean.setTitle("Null");
                } else {
                    alarmBean.setTitle(alarm_title.getText().toString());
                }

                //设置描述
                if (alarm_description.getText().toString().equals("")) {
                    alarmBean.setDescription("There is no description");
                } else {
                    alarmBean.setDescription(alarm_description.getText().toString());
                }

                //设置地点
                if (alarm_local.getText().equals("Add the location")) {
                    alarmBean.setLocal("There is no location");
                }

                //设置是否全天
                if (isAllDay) {
                    alarmBean.setIsAllday(1);
                } else {
                    alarmBean.setIsAllday(0);
                }

                //设置年月日
                if (alarm_date.getText().toString().equals("Choose the activity date")) {
                    alarmBean.setYear(getToDay().get(Calendar.YEAR));
                    alarmBean.setMonth(getToDay().get(Calendar.MONTH));
                    alarmBean.setDay(getToDay().get(Calendar.DAY_OF_MONTH));
                }

                //设置开始时间
                if (alarm_start_time.getText().toString().equals("Choose the start time")) {
                    if(isAllDay){
                        alarmBean.setStartTimeHour(0);
                        alarmBean.setStartTimeMinute(0);
                    }else {
                        alarmBean.setStartTimeHour(getToDay().get(Calendar.HOUR_OF_DAY));
                        alarmBean.setStartTimeMinute(getToDay().get(Calendar.MINUTE));
                    }
                }

                //设置结束时间
                if (alarm_end_time.getText().toString().equals("Choose the end time")) {
                    if(isAllDay){
                        alarmBean.setEndTimeHour(23);
                        alarmBean.setEndTimeMinute(59);
                    }else {
                        alarmBean.setEndTimeHour(getToDay().get(Calendar.HOUR_OF_DAY) + 1);
                        alarmBean.setEndTimeMinute(getToDay().get(Calendar.MINUTE));
                    }
                }

                //设置提醒时间
                if (alarm_remind.getText().toString().equals("Choose the remind time")) {
                    alarmBean.setAlarmTime("Default time");
                } else {
                    alarmBean.setAlarmTime(alarm_remind.getText().toString());
                }

                //设置重复天
                if (alarm_replay.getText().toString().equals("No Replay")) {
                    alarmBean.setReplay("No Replay");
                } else {
                    alarmBean.setReplay(alarm_replay.getText().toString());
                }

                //设置铃声
                if (alarm_tone_Path.getText().toString().equals("Choose the alarm music")) {
                    Uri uri = RingtoneManager.getActualDefaultRingtoneUri(
                            CreateTaskActivity.this, RingtoneManager.TYPE_RINGTONE);
                    alarmBean.setAlarmTonePath(uri.toString());
                }

                //设置颜色
                if (alarm_color.getText().toString().equals("Default color")) {
                    alarmBean.setAlarmColor("Default color");
                } else {
                    alarmBean.setAlarmColor(alarm_color.getText().toString());
                }

                //设置地区
                if (alarm_local.getText().toString().equals("Null")) {
                    alarmBean.setLocal("Null");
                } else {
                    alarmBean.setLocal(alarm_local.getText().toString());
                }

                if (id == 0) {
                    System.out.println("Saved data:" + alarmBean.toString());
                    support.insertAlarmDate(alarmBean);

                    SendAlarmBroadcast.startAlarmService(this);

                    Toast.makeText(this, "Add successfully!！", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, flagMainActivity.class));
                    finish();
                } else {
                    System.out.println("Updated data:" + alarmBean.toString());
                    support.updateDataById(id, alarmBean);

                    SendAlarmBroadcast.startAlarmService(this);

                    Toast.makeText(this, "Update successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, flagMainActivity.class));
                    finish();
                }
                break;
        }
    }

    private Calendar getToDay() {
        Calendar today = Calendar.getInstance();
        today.setTimeInMillis(System.currentTimeMillis());
        return today;
    }

    /**
     * 获取日期选择器
     */
    private void getDatePickerDialog(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        mDataPicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/  EE");
                alarm_date.setText(df.format(calendar.getTime()));

                //设置选择的年、月、日
                alarmBean.setYear(year);
                alarmBean.setMonth(monthOfYear);
                alarmBean.setDay(dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

    }
    /**
     * 获取开始时间选择器
     */
    private void getStartTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        mStartTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");

                alarm_start_time.setText("Start time:  " + df.format(calendar.getTime()));

                //设置开始时间的小时、分钟
                alarmBean.setStartTimeHour(hourOfDay);
                alarmBean.setStartTimeMinute(minute);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
    }

    /**
     * 获取结束时间选择器
     */
    private void getEndTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        mEndTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");

                alarm_end_time.setText("End time:  " + df.format(calendar.getTime()));


                //设置结束时间的小时、分钟
                alarmBean.setEndTimeHour(hourOfDay);
                alarmBean.setEndTimeMinute(minute);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == 0) {
                if (data != null) {
                    alarm_replay.setText(data.getStringExtra("replay"));
                    alarmBean.setReplay(data.getStringExtra("replay"));
                }
            }
        } else if (requestCode == 1) {
            if (resultCode == 1) {
                if (data != null) {
                    alarm_remind.setText(data.getStringExtra("remind"));
                    alarmBean.setAlarmTime(data.getStringExtra("remind"));
                }
            }
        } else if (requestCode == 2) {
            if (resultCode == 2) {
                if (data != null) {
                    alarm_local.setText(data.getStringExtra("local"));
                    alarmBean.setLocal(data.getStringExtra("local"));
                }
            }
        } else if (requestCode == 3) {
            if (resultCode == 3) {
                if (data != null) {
                    alarm_color.setText(data.getStringExtra("color"));
                    alarmBean.setAlarmColor(data.getStringExtra("color"));
                    //动态改变颜色
                    int colorId = ColorUtils.getColorFromStr(data.getStringExtra("color"));
                    action_bar.setBackgroundColor(getResources().getColor(colorId));
                    Window window = getWindow();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.setStatusBarColor(getResources().getColor(colorId));
                    }

                }
            }
        } else if (requestCode == 4) {
            if (resultCode == 4) {
                if (data != null) {
                    alarm_tone_Path.setText(data.getStringExtra("tone"));
                    alarmBean.setAlarmTonePath(data.getStringExtra("tonePath"));
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,flagMainActivity.class));
        finish();
    }

}
