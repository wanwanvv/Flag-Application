package com.example.flagapplication.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.LogUtil;
import com.example.flagapplication.R;
import com.example.flagapplication.activity.CreateTaskActivity;
import com.example.flagapplication.alarmremind.SendAlarmBroadcast;
import com.example.flagapplication.start.MainActivity;
import com.example.flagapplication.userView.CalendarView;
import com.example.flagapplication.utils.BusProvider;
import com.example.flagapplication.utils.CalendarManager;
import com.example.flagapplication.utils.Events;
import com.example.flagapplication.utils.PrefUtils;
import com.example.flagapplication.view.flagMainActivity;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;



public class Fragment1 extends BaseFragment{

    TextView title_day;
    ImageView title_arrow;
    LinearLayout calendar_open_close;
    //打开关闭CalendarView
    private boolean isOpen = true;//日历视图是否显示

    FrameLayout main_frame;
    DrawerLayout main_draw_layout;
    public CalendarView calendar_view;
    private View tView;
    public flagMainActivity mActivity;
    //CalendarView自定义控件的属性
    private int mCalendarHeaderColor, mCalendarDayTextColor, mCalendarPastDayTextColor, mCalendarCurrentDayColor;

    /**
    public void click(View v){
        if (!isOpen) {
            calendar_view.setVisibility(View.VISIBLE);
            arrowRotateAnim(isOpen);
            isOpen = true;
        } else {
            calendar_view.setVisibility(View.GONE);
            arrowRotateAnim(isOpen);
            isOpen = false;
        }
    }
     **/

    private boolean isAllowAlert = false;
    private boolean showPeimission = true;
    /**
     @OnClick(R.id.go_today) void goToday() {
     //        homePager.agenda_view.getAgendaListView().scrollToCurrentDate(CalendarManager.getInstance().getToday());
     BusProvider.getInstance().send(new CalendarContract.Events.GoBackToDay());
     calendar_view.scrollToDate(CalendarManager.getInstance().getToday(), CalendarManager.getInstance().getWeeks());
     }
     **/

    /**
     * private TextView textView1;
     *
     * @Nullable
     * @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     * View view=inflater.inflate(R.layout.fragment1,container,false);
     * return view;
     * }
     * @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
     * super.onActivityCreated(savedInstanceState);
     * }
     **/



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * onCreateView是创建该fragment对应的视图，其中需要创建自己的视图并返回给调用者
     * onCreate类似于Activity.onCreate，在其中可初始化除了view之外的一切,onCreate先执行
     **/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //加载布局
        tView = inflater.inflate(R.layout.fragment1, container, false);
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        if(getActivity()==null)
        {
            Log.d("onCreate","mActivity为空！！！");
        }

        initCalendarInfo();
        initLayoutView();
        setMonthTitle();


        //弹窗权限验证
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            isAllowAlert = PrefUtils.getBoolean(getActivity(),"isAllowAlert",false);
            if(!isAllowAlert){
                showPermissionDialog();
            }
        }else {
            SendAlarmBroadcast.startAlarmService(getActivity());
        }



        return tView;
    }

    /**
     * title_arrow 旋转动画
     */
    private void arrowRotateAnim(Boolean isOpen) {
        RotateAnimation anim;
        if (isOpen) {
            anim = new RotateAnimation(0, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else {
            anim = new RotateAnimation(180f, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        anim.setDuration(150);
        anim.setFillAfter(true);
        title_arrow.startAnimation(anim);
    }


    //权限申请相关方法
    private static final int REQUEST_CODE = 1;

    @TargetApi(Build.VERSION_CODES.M)
    private void requestAlertWindowPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
        startActivityForResult(intent, REQUEST_CODE);
        //使用startActivityForResult启动授权界面来完成
    }




    /**
     * 权限申请弹窗
     */

    private void showPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Do you allow to open the popup?")
                .setPositiveButton("Start", (dialog, which) -> {
                    requestAlertWindowPermission();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {

                });
        builder.create().show();
    }


    private void initLayoutView() {
        //初始化控件

        calendar_view =tView.findViewById(R.id.calendar_view);
        title_day=tView.findViewById(R.id.title_day);
        main_frame=tView.findViewById(R.id.main_frame);

        title_arrow=tView.findViewById(R.id.title_arrow);
        calendar_open_close=tView.findViewById(R.id.calendar_open_close);
        calendar_open_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    calendar_view.setVisibility(View.VISIBLE);
                    arrowRotateAnim(isOpen);
                    isOpen = false;
                } else {
                    calendar_view.setVisibility(View.GONE);
                    arrowRotateAnim(isOpen);
                    isOpen = true;
                }

            }
        });
        //设置显示日历的颜色

        mCalendarHeaderColor = R.color.calendar_header_day_background;
        mCalendarDayTextColor = R.color.calendar_text_day;
        mCalendarCurrentDayColor = R.color.calendar_text_current_day;
        mCalendarPastDayTextColor = R.color.calendar_text_past_day;
        calendar_view.findViewById(R.id.cal_day_names).setBackgroundColor(getResources().getColor(mCalendarHeaderColor));
        // CalendarView 初始化,完成布局的初始化，数据的绑定,日期的显示
        calendar_view.init(CalendarManager.getInstance(getActivity()), getResources().getColor(mCalendarDayTextColor), getResources().getColor(mCalendarCurrentDayColor), getResources().getColor(mCalendarPastDayTextColor));

        //填充 Fragment

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        ContentFragment fragment = new ContentFragment();

        transaction.replace(R.id.main_frame, fragment, "CONTENT_FRAGMENT");

        transaction.commit();

    }

    private void initCalendarInfo() {
        // 设置日历显示的时间，最大为当前时间+1年，最小为当前时间-2月
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH, -10);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);
        //根据你传入的开始结束值，构建生成Calendar数据（各种Item，JavaBean）
        if(getActivity()==null)
        {
            Log.d("Calendar","mActivity is null！！！");
        }
        CalendarManager.getInstance(getActivity()).buildCal(minDate, maxDate, Locale.getDefault());
        System.out.print("Local is："+Locale.getDefault());

    }

    /**
     * 初始化 Fab
     */



    /**
     * 设置月份
     */
    private void setMonthTitle() {
        BusProvider.getInstance().toObserverable().subscribe(event -> {
            if (event instanceof Events.CalendarMonthEvent) {
                Events.CalendarMonthEvent monthEvent = (Events.CalendarMonthEvent) event;
                title_day.setText(monthEvent.getMonth());
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (Settings.canDrawOverlays(getActivity())) {
                Toast.makeText(getActivity(),"The alarm permission has been opened!",Toast.LENGTH_SHORT).show();
                PrefUtils.setBoolean(getActivity(), "isAllowAlert", true);
            }else {
                System.out.print("The alart_window_request fail!");
                PrefUtils.setBoolean(getActivity(), "isAllowAlert", false);
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }
}
