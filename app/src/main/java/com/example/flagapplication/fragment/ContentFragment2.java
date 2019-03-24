package com.example.flagapplication.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.example.flagapplication.Pager.BasePager;
import com.example.flagapplication.Pager.DayPager;
import com.example.flagapplication.Pager.HomePager;
import com.example.flagapplication.Pager.WeekPager;
import com.example.flagapplication.R;
import com.example.flagapplication.database.AlarmDBSupport;
import com.example.flagapplication.utils.BusProvider;
import com.example.flagapplication.utils.CalendarManager;
import com.example.flagapplication.utils.Events;

import java.util.ArrayList;
import java.util.List;

public class ContentFragment2 extends BasePagerFragment{
    ViewPager vpContent;
    private List<BasePager> mPageList;
    private AlarmDBSupport support;
    private HomePager homePager;
    private DayPager dayPager;
    private WeekPager weekPager;
    @Override
    public View initView() {

        View view=View.inflate(mActivity, R.layout.fragment_content,null);
        vpContent = view.findViewById(R.id.vp_content);

        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void initDate() {

        homePager = new HomePager(mActivity);
        dayPager = new DayPager(mActivity);
        weekPager = new WeekPager(mActivity);

        //主界面添加数据
        mPageList= new ArrayList<>();

        mPageList.add(homePager);
        mPageList.add(dayPager);


        vpContent.setAdapter(new VpContentAdapter());

        buildHomePager();
        vpContent.setCurrentItem(1,false);
        dayPager.initData();

    }

    /**
     * 主界面设置
     */
    private void buildHomePager(){
        homePager.initData();
        BusProvider.getInstance().toObserverable().subscribe(event ->{
            if(event instanceof Events.GoBackToDay){
                homePager.agenda_view.getAgendaListView().scrollToCurrentDate(CalendarManager.getInstance().getToday());
            }
        });
    }
    /**
     * viewPager数据适配器
     */
    class VpContentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager =mPageList.get(position);
            container.addView(pager.mRootView);
            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mPageList.get(position).mRootView);
        }
    }

}
