package com.example.flagapplication.view;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flagapplication.R;
import com.example.flagapplication.activity.BaseFragmentActivity;
import com.example.flagapplication.activity.CreateTaskActivity;
import com.example.flagapplication.alarmremind.SendAlarmBroadcast;
import com.example.flagapplication.fragment.Fragment1;
import com.example.flagapplication.fragment.Fragment2;
import com.example.flagapplication.fragment.Fragment3;
import com.example.flagapplication.fragment.Fragment4;
import com.example.flagapplication.fragment.Fragment5;
import com.example.flagapplication.playAudience.AudioService;
import com.example.flagapplication.utils.PrefUtils;

import java.util.Locale;

public class flagMainActivity extends BaseFragmentActivity implements View.OnClickListener{
    private ViewPager viewPager_content;
    private TextView txt_menu_bottom_today;
    private TextView txt_menu_bottom_task;
    private TextView txt_menu_bottom_trend;
    private TextView txt_menu_bottom_diary;
    private TextView txt_menu_bottom_me;
    private View title_layout;

    private final int TAB_TODAY = 0;
    private final int TAB_TASK = 1;
    private final int TAB_TREND = 2;
    private final int TAB_DIARY = 3;
    private final int TAB_ME = 4;
    private int IsTab;

    private Fragment2 todayFragment;
    private Fragment1 taskFragment;
    private Fragment3 trendFragment;
    private Fragment4 diaryFragment;
    private Fragment5 meFragment;

    private FragmentAdapter adapter;
    private ImageView title_bar_change;
    private boolean isAllowAlert = false;
    //音乐播放所需变量
    private static final int NOTIFICATION_ID = 1; // 如果id设置为0,会导致不能设置为前台service
    public static NotificationManager manager;
    RemoteViews remoteViews;
    Notification notification;
    private Button btn;
    private TextView tv;
    static flagMainActivity appCompatActivity;
    public static int state = 0;
    //白噪音播放
    public static AudioService audioService;
    NotificationChannel channel;
    String id;
    String description;
    int importance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flagmain);
        if(appCompatActivity == null){
            appCompatActivity = this;
        }

        initID();//初始化绑定组件id
        initView();//初始化视图

    }


    /**
     * 初始化控件加载
     */
    public void initID() {
        TextView title_bar_back = (TextView) findViewById(R.id.title_bar_back);
        title_bar_back.setVisibility(View.GONE);

        viewPager_content = (ViewPager) findViewById(R.id.viewPager_content);
        txt_menu_bottom_today = (TextView) findViewById(R.id.txt_menu_bottom_today);
        txt_menu_bottom_task = (TextView) findViewById(R.id.txt_menu_bottom_task);
        txt_menu_bottom_trend = (TextView) findViewById(R.id.txt_menu_bottom_trend);
        txt_menu_bottom_diary = (TextView) findViewById(R.id.txt_menu_bottom_diary);
        txt_menu_bottom_me = (TextView) findViewById(R.id.txt_menu_bottom_me);

        title_layout=findViewById(R.id.title_up_layout);
        /**
        title_bar_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(flagMainActivity.this, CreateTaskActivity.class));
            }
        });
        **/
        title_bar_change = (ImageView) findViewById(R.id.title_bar_change);
        txt_menu_bottom_today.setOnClickListener(this);
        txt_menu_bottom_task.setOnClickListener(this);
        txt_menu_bottom_trend.setOnClickListener(this);
        txt_menu_bottom_diary.setOnClickListener(this);
        txt_menu_bottom_me.setOnClickListener(this);
        title_bar_change.setOnClickListener(this);

        //ViewPager滑动监听,切换界面
        viewPager_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("main_viewpager", "position--" + position);
                switch (position) {
                    case TAB_TODAY:
                        IsTab = 1;
                        jumpTodayFragment();
                        break;
                    case TAB_TASK:
                        IsTab = 2;
                        jumpTaskFragment();
                        break;
                    case TAB_TREND:
                        IsTab = 3;
                        jumpTrendFragment();
                        break;
                    case TAB_DIARY:
                        IsTab = 4;
                        jumpDiaryFragment();
                        break;
                    case TAB_ME:
                        IsTab = 5;
                        jumpMeFragment();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * 初始化视图,默认显示首界面
     */
    public void initView() {
        //isLoad = false;
        taskFragment = new Fragment1();
        todayFragment = new Fragment2();
        trendFragment = new Fragment3();
        diaryFragment = new Fragment4();
        meFragment = new Fragment5();
        adapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager_content.setAdapter(adapter);
        setSelected(txt_menu_bottom_today);
        viewPager_content.setCurrentItem(TAB_TODAY, false);
        //主要在viewPager设置当前页时，页面跳转时使用。false：代表快速切换 true：表示切换速度慢
        setTitleName("Flag");
        viewPager_content.setOffscreenPageLimit(2);
        //setOffscreenPageLimit设置预加载页面数，且决定超过多少页fragment就会被destroy

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.txt_menu_bottom_today:
                title_layout.setVisibility(View.VISIBLE);
                IsTab = 1;
                title_bar_change.setVisibility(View.GONE);
                setSelected(txt_menu_bottom_today);
                viewPager_content.setCurrentItem(TAB_TODAY, false);
                setTitleName("Flag");

                break;
            case R.id.txt_menu_bottom_task:
                title_layout.setVisibility(View.GONE);
                IsTab = 2;
                title_bar_change.setVisibility(View.GONE);
                setSelected(txt_menu_bottom_task);
                viewPager_content.setCurrentItem(TAB_TASK, false);
                setTitleName("Task");
                break;
            case R.id.txt_menu_bottom_trend:
                title_layout.setVisibility(View.VISIBLE);
                IsTab = 3;
                title_bar_change.setVisibility(View.GONE);
                setSelected(txt_menu_bottom_trend);
                viewPager_content.setCurrentItem(TAB_TREND, false);
                setTitleName("Statistic");
                break;
            case R.id.txt_menu_bottom_me:
                title_layout.setVisibility(View.VISIBLE);
                IsTab = 5;
                title_bar_change.setVisibility(View.GONE);
                setSelected(txt_menu_bottom_me);
                viewPager_content.setCurrentItem(TAB_ME, false);
                setTitleName("Me");
                break;
            case R.id.txt_menu_bottom_diary:
                title_layout.setVisibility(View.GONE);
                IsTab = 4;
                title_bar_change.setVisibility(View.GONE);
                setSelected(txt_menu_bottom_diary);
                viewPager_content.setCurrentItem(TAB_DIARY, false);
                setTitleName("Focus");
                break;
                /**
                if(state==0){
                    //打开白噪音
                    android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(flagMainActivity.this);
                    builder.setIcon(R.drawable.diary);
                    builder.setTitle("Notice");
                    builder.setMessage("Do you want to play the white noise?");
                    //确定按钮
                    builder.setPositiveButton("Play", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            createNotifcation();
                            Log.d("createNotification:","CreateNotification successufully!");
                            Intent intent = new Intent();
                            intent.setClass(flagMainActivity.this, AudioService.class);
                            startService(intent);
                            Log.d("SatrtService","Service started successufully!");
                            bindService(intent,conn,Context.BIND_AUTO_CREATE);
                            Log.d("BindService:","BindService successfully!");
                            Toast.makeText(flagMainActivity.this, "You have started the White noise. Please control it in the notification bar!", Toast.LENGTH_LONG).show();
                        }

                    });
                    //Toast.makeText(flagMainActivity.this, "You have started the White noise. Please control it in the notification!", Toast.LENGTH_LONG).show();

                    //取消按钮
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    //显示出对话框
                    builder.show();

                }
                 **/
                //设置通知栏

            default:
                break;
        }
    }



    /**
     * 显示主界面TodayFragemnt
     */
    private void jumpTodayFragment() {
        title_bar_change.setVisibility(View.GONE);
        setSelected(txt_menu_bottom_today);
        viewPager_content.setCurrentItem(TAB_TODAY, false);
        setTitleName("Flag");
    }

    /**
     * 显示TaskFragment
     */
    public void jumpTaskFragment() {//
        title_bar_change.setVisibility(View.GONE);
        setSelected(txt_menu_bottom_task);
        viewPager_content.setCurrentItem(TAB_TASK, false);
        setTitleName("Task");
    }

    /**
     * 显示DorkFragment
     */
    public void jumpTrendFragment() {
        title_bar_change.setVisibility(View.GONE);
        setSelected(txt_menu_bottom_trend);
        viewPager_content.setCurrentItem(TAB_TREND, false);
        setTitleName("Statistic");
    }

    /**
     * 显示StatisticFragment,提供给新建拜访完成后调用
     */
    public void jumpDiaryFragment() {
        title_bar_change.setVisibility(View.GONE);
        setSelected(txt_menu_bottom_diary);
        viewPager_content.setCurrentItem(TAB_DIARY, false);
        setTitleName("Diary");
    }

    /**
     * 显示MeFragment
     */
    public void jumpMeFragment() {
        title_bar_change.setVisibility(View.GONE);
        setSelected(txt_menu_bottom_me);
        viewPager_content.setCurrentItem(TAB_ME, false);
        setTitleName("Me");
    }

    //当选中的时候变色,改变底部文字颜色
    public void setSelected(TextView textView) {
        txt_menu_bottom_today.setSelected(false);
        txt_menu_bottom_task.setSelected(false);
        txt_menu_bottom_trend.setSelected(false);
        txt_menu_bottom_diary.setSelected(false);
        txt_menu_bottom_me.setSelected(false);
        textView.setSelected(true);
    }

    /*
     * 模块Fragment适配器
     */
    public class FragmentAdapter extends FragmentPagerAdapter {
        private final int TAB_COUNT = 5;

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int id) {
            switch (id) {
                case TAB_TODAY:
                    return todayFragment;
                case TAB_TASK:
                    return taskFragment;
                case TAB_TREND:
                    return trendFragment;
                case TAB_DIARY:
                    return diaryFragment;
                case TAB_ME:
                    return meFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }
    }
    //使用ServiceConnection来监听Service状态的变化
    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
            audioService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            //这里我们实例化audioService,通过binder来实现
            audioService = ((AudioService.AudioBinder)binder).getService();

        }
    };
    public static flagMainActivity getInstance(){
        return appCompatActivity;
    }
    //设置通知栏
    public void createNotifcation(){
        remoteViews = new RemoteViews(getPackageName(),
                R.layout.widget);
        remoteViews.setTextViewText(R.id.wt_title, "White noise");
        remoteViews.setImageViewResource(R.id.icon1, R.drawable.add_music);
        if(state == 0){
            Log.d("createNotification","state==0");
            System.out.print("createNotification.state(0)="+state);
            remoteViews.setImageViewResource(R.id.wt_play,R.drawable.pause);
        }else {
            Log.d("createNotification","state==1");
            System.out.print("createNotification.state(1)="+state);
            remoteViews.setImageViewResource(R.id.wt_play,R.drawable.play);
        }

        Intent previous=new Intent("com.examle.flagapplication.broadcasttest.PREVIOUS");
        PendingIntent pi_previous = PendingIntent.getBroadcast(flagMainActivity.this,0,
                previous,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.wt_previous,pi_previous);


        Intent play=new Intent("com.example.flagapplication.broadcasttest.PLAY");
        PendingIntent pi_play = PendingIntent.getBroadcast(flagMainActivity.this,1,
                play,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.wt_play,pi_play);

        Intent next=new Intent("com.example.flagapplication.broadcasttest.NEXT");
        PendingIntent pi_next = PendingIntent.getBroadcast(flagMainActivity.this,2,
                next,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.wt_next,pi_next);

        Intent clear=new Intent("com.example.flagapplication.broadcasttest.CLEAR");
        PendingIntent pi_clear = PendingIntent.getBroadcast(flagMainActivity.this,3,
                clear,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.wt_clear,pi_clear);
        manager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= 26){
            id = "channel_1";
            description = "143";
            importance = NotificationManager.IMPORTANCE_LOW;
            channel = new NotificationChannel(id, description, importance);
            manager.createNotificationChannel(channel);
            Log.d("CreateChannel","Channel has created!");
            notification = new Notification.Builder(flagMainActivity.this, id)
                    .setContentTitle("white noise")
                    .setTicker("White noise")
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setCustomBigContentView(remoteViews)
                    .setSmallIcon(R.drawable.add_music)
                    .build();

        }else{
            notification.icon =R.drawable.add_music;
            notification.tickerText = "White noise";
            notification.when = System.currentTimeMillis();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            notification.contentView = remoteViews;

        }


        manager.notify(NOTIFICATION_ID,notification);

    }


}
