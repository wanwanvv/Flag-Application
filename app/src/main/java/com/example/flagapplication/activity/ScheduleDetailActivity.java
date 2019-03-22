package com.example.flagapplication.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.flagapplication.R;
import com.example.flagapplication.alarmremind.AlarmBean;
import com.example.flagapplication.alarmremind.SendAlarmBroadcast;
import com.example.flagapplication.database.AlarmDBSupport;
import com.example.flagapplication.utils.ColorUtils;
import com.example.flagapplication.view.flagMainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ScheduleDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private AlarmDBSupport support;
    private int id;
    private AlarmBean bean;
    TextView detail_alarm_title;
    TextView detail_alarm_date;
    TextView detail_alarm_start_end_time;
    TextView detail_alarm_remind;
    TextView detail_alarm_local;
    TextView detail_alarm_description;
    RelativeLayout detail_layout;
    ImageButton tv_delete;
    FloatingActionButton update_fab;
    ImageButton left_alarm_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_detail);
        initLayout();

        id = Integer.parseInt(getIntent().getStringExtra("id"));
        System.out.println("---id=" + id);

        support = new AlarmDBSupport(getApplicationContext());

        bean = getDataById(id);
        Calendar calendar = Calendar.getInstance();
        calendar.set(bean.getYear(), bean.getMonth(), bean.getDay());
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/  EE");
        //匹配数据
        detail_alarm_title.setText(bean.getTitle());
        detail_alarm_date.setText(df.format(calendar.getTime())+"  The"+calendar.get(Calendar.WEEK_OF_MONTH)+"week");
        buildStartEndTime(bean.getStartTimeHour(), bean.getStartTimeMinute(), bean.getEndTimeHour(), bean.getEndTimeMinute());
        detail_alarm_remind.setText(bean.getAlarmTime());
        detail_alarm_local.setText(bean.getLocal());
        detail_alarm_description.setText(bean.getDescription());

        int colorId = ColorUtils.getColorFromStr(bean.getAlarmColor());
        detail_layout.setBackgroundColor(getResources().getColor(colorId));
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(colorId));
        }

    }
    public void initLayout(){
        detail_alarm_title = findViewById(R.id.detail_alarm_title);
        detail_alarm_date = findViewById(R.id.detail_alarm_date);
        detail_alarm_start_end_time = findViewById(R.id.detail_alarm_start_end_time);
        detail_alarm_remind = findViewById(R.id.detail_alarm_remind);
        detail_alarm_local = findViewById(R.id.detail_alarm_local);
        detail_alarm_description = findViewById(R.id.detail_alarm_description);
        detail_layout = findViewById(R.id.detail_layout);
        tv_delete = findViewById(R.id.tv_delete);
        update_fab = findViewById(R.id.update_fab);
        left_alarm_back= findViewById(R.id.left_alarm_back);
        tv_delete.setOnClickListener(this);
        update_fab.setOnClickListener(this);
        left_alarm_back.setOnClickListener(this);


    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_delete:
                dialog();
                break;
            case R.id.update_fab:
                Intent intent = new Intent(this,CreateTaskActivity.class);
                intent.putExtra("AlarmBean",bean);
                intent.putExtra("type","DetailToAdd");
                startActivity(intent);
                finish();
                break;
            case R.id.left_alarm_back:
                startActivity(new Intent(this, flagMainActivity.class));
                finish();
                break;

        }

    }
    private void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("Are you sure to delete?"); //设置标题
        builder.setIcon(R.mipmap.smalllogo);//设置图标，图片id即可
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deteleDataById(id);
                startActivity(new Intent(ScheduleDetailActivity.this, flagMainActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        //参数都设置完成了，创建并显示出来
        builder.create().show();
    }
    /**
     * 根据ID删除数据
     */
    private void deteleDataById(int id) {
        support.deleteDataById(id);

        SendAlarmBroadcast.startAlarmService(this);

    }
    /**
     * 根据ID查询一条数据
     */
    private AlarmBean getDataById(int id) {
        AlarmBean alarmBean = support.getDataById(id);
        return alarmBean;
    }
    /**
     * 构建开始结束时间
     */
    private void buildStartEndTime(int startHour, int startMinute, int endHour, int endMinute) {
        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, startHour);
        startTime.set(Calendar.MINUTE, startMinute);

        Calendar endTime = Calendar.getInstance();
        endTime.set(Calendar.HOUR_OF_DAY, endHour);
        endTime.set(Calendar.MINUTE, endMinute);

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String strStartTime = df.format(startTime.getTime());
        String strEndTime = df.format(endTime.getTime());

        detail_alarm_start_end_time.setText(strStartTime + "-" + strEndTime);
    }

}
