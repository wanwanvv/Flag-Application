package com.example.flagapplication.view;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flagapplication.R;
import com.example.flagapplication.activity.BaseFragmentActivity;
import com.example.flagapplication.activity.CreateTaskActivity;
import com.example.flagapplication.fragment.Fragment1;
import com.example.flagapplication.fragment.Fragment2;
import com.example.flagapplication.fragment.Fragment3;
import com.example.flagapplication.fragment.Fragment4;
import com.example.flagapplication.fragment.Fragment5;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flagmain);

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
                title_layout.setVisibility(View.GONE);
                IsTab = 3;
                title_bar_change.setVisibility(View.GONE);
                setSelected(txt_menu_bottom_trend);
                viewPager_content.setCurrentItem(TAB_TREND, false);
                setTitleName("Trend");
                break;
            case R.id.txt_menu_bottom_diary:
                title_layout.setVisibility(View.GONE);
                IsTab = 4;
                title_bar_change.setVisibility(View.GONE);
                setSelected(txt_menu_bottom_diary);
                viewPager_content.setCurrentItem(TAB_DIARY, false);
                setTitleName("Diary");
                break;
            case R.id.txt_menu_bottom_me:
                title_layout.setVisibility(View.VISIBLE);
                IsTab = 5;
                title_bar_change.setVisibility(View.GONE);
                setSelected(txt_menu_bottom_me);
                viewPager_content.setCurrentItem(TAB_ME, false);
                setTitleName("Me");
                break;
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
        setTitleName("Trend");
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

    /**
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharePreUtil.SetShareString(mContext, "userid", "");//Activity死亡清空id保存
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(dorkFragment instanceof IOnFocusListenable) {
            ((IOnFocusListenable) dorkFragment).onKeyDown(keyCode,event);
        }

        //监听返回键，如果当前界面不是首界面，或没切换过界面，切到首界面
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (IsTab != 1) {
                IsTab = 1;
                jumpTodayFragment();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Rect rect = new Rect();
        // /取得整个视图部分,注意，如果你要设置标题样式，这个必须出现在标题样式之后，否则会出错
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int top = rect.top;

        if(dorkFragment instanceof IOnFocusListenable) {
            ((IOnFocusListenable) dorkFragment).onWindowFocusChanged(top);
        }
    }
**/

}
