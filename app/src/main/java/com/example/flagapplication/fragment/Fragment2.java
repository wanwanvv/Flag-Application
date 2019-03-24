package com.example.flagapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flagapplication.R;
import com.example.flagapplication.activity.CreateTaskActivity;
import com.example.flagapplication.utils.CalendarManager;
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


public class Fragment2 extends BaseFragment implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener{
    FrameLayout list_frame;
    RapidFloatingActionButton labelListSampleRfab;
    RapidFloatingActionLayout labelListSampleRfal;
    RapidFloatingActionButton fab_button_groups;
    private RapidFloatingActionHelper rfabHelper;
    RapidFloatingActionLayout fab_group_layout;
    private View view;
    public flagMainActivity mActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment2,container,false);
        initCalenInfo();
        initView();
        initFabButton();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    private void initCalenInfo() {
        // 设置日历显示的时间，最大为当前时间+1年，最小为当前时间-2月
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH, -10);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);
        //根据你传入的开始结束值，构建生成Calendar数据（各种Item，JavaBean）
        if(getActivity()==null)
        {
            Log.d("Calendar","mActivity为空！！！");
        }
        CalendarManager.getInstance(getActivity()).buildCal(minDate, maxDate, Locale.getDefault());
        System.out.print("本地Local为："+Locale.getDefault());

    }
    public void initView(){
        list_frame =view.findViewById(R.id.list_frame);
        fab_button_groups=view.findViewById(R.id.fab_button_groups);
        fab_group_layout=view.findViewById(R.id.fab_group_layout);
        //填充 Fragment

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        ContentFragment2 fragment = new ContentFragment2();

        transaction.replace(R.id.list_frame, fragment, "CONTENT_FRAGMENT");

        transaction.commit();


    }

    public void initFabButton(){
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(getActivity());
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel("FLAG")
                .setResId(R.drawable.flag_fab)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xfff58f98)
                .setLabelSizeSp(10)
                .setWrapper(0)
        );
        rfaContent
                .setItems(items)
                .setIconShadowRadius(5)
                .setIconShadowColor(0xffffd524)
                .setIconShadowDy(5)
        ;
        rfabHelper = new RapidFloatingActionHelper(
                mActivity,
                fab_group_layout,
                fab_button_groups,
                rfaContent
        ).build();
    }

    @Override
    public void onRFACItemLabelClick(int i, RFACLabelItem rfacLabelItem) {
        Toast.makeText(getActivity(), "Click Label !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRFACItemIconClick(int i, RFACLabelItem rfacLabelItem) {
        if (i == 0) {
            Intent intent = new Intent(getActivity(), CreateTaskActivity.class);
            intent.putExtra("type", "mainToAdd");
            startActivity(intent);
            //finish();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
