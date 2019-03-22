package com.example.flagapplication.Pager;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flagapplication.R;
import com.example.flagapplication.activity.ScheduleDetailActivity;
import com.example.flagapplication.adapter.RecycleAdapter;
import com.example.flagapplication.database.AlarmDBSupport;
import com.example.flagapplication.utils.BusProvider;
import com.example.flagapplication.utils.Events;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DayPager extends BasePager{
    RecyclerView recycle_view;
    TextView day;
    TextView week;
    TextView haveOrNot;
    private List<Object> list;
    public DayPager(Activity mActivity) {
        super(mActivity);

    }
    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.daypager_view, null);
        recycle_view =view.findViewById(R.id.recycle_view);
        day =view.findViewById(R.id.day);
        week =view.findViewById(R.id.week);
        haveOrNot =view.findViewById(R.id.haveOrNot);
        return view;
    }
    public void initData() {

        chooseDayFromClick();

        RecyclerView.LayoutManager manager = new LinearLayoutManager(mActivity);
        recycle_view.setLayoutManager(manager);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        list= new AlarmDBSupport(mActivity).getDataByDay(calendar);

        setDateToShow(calendar,list);

        recycle_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                BusProvider.getInstance().send(new Events.AgendaListViewTouchedEvent());
            }
        });

    }
    private void chooseDayFromClick(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        list= new AlarmDBSupport(mActivity).getDataByDay(calendar);
        setDateToShow(calendar,list);
    }
    /**
     * 设置显示的日期
     */
    private void setDateToShow(Calendar calendar ,List list){
        day.setText(calendar.get(Calendar.DAY_OF_MONTH)+"");

        //设置星期格式
        SimpleDateFormat dayWeekFormatter = new SimpleDateFormat("E");
        String weekStr = dayWeekFormatter.format(calendar.getTimeInMillis());
        week.setText(weekStr);

        if(list.size()==0){
            haveOrNot.setText("There is no flag informatio and click the "+" to add it");
        }else {
            haveOrNot.setText("This is what you need todo today:");
        }

        RecycleAdapter dayPagerAdapter = new RecycleAdapter("dayPager", list,mActivity);
        recycle_view.setAdapter(dayPagerAdapter);
        dayPagerAdapter.notifyDataSetChanged();

        //Item 点击事件
        itemClick(dayPagerAdapter);
    }
    /**
     * listView 的Item点击事件
     */
    private void itemClick(RecycleAdapter adapter){
        //Item 点击事件
        adapter.setOnMyItemListener(new RecycleAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView alarm_id = (TextView) view.findViewById(R.id.Alarm_id);
                Toast.makeText(mActivity,position+"-"+alarm_id.getText().toString(),Toast.LENGTH_SHORT).show();
                if(!alarm_id.getText().toString().equals("0")){
                    Intent intent = new Intent(mActivity, ScheduleDetailActivity.class);
                    intent.putExtra("id",alarm_id.getText().toString());
                    mActivity.startActivity(intent);
//                    mActivity.finish();
                }
            }
        });
    }


}
