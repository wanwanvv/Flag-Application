package com.example.flagapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;

import com.example.flagapplication.R;
import com.example.flagapplication.userView.CircleTimerView;
import com.example.flagapplication.userView.SetItemView;
import com.example.flagapplication.view.flagMainActivity;


public class Fragment4 extends BaseFragment implements CircleTimerView.CircleTimerListener{
    private View cView;
    private static final String TAG = flagMainActivity.class.getSimpleName();

    private CircleTimerView mTimer;
    private EditText mTimerSet;
    private EditText mHintSet;
    private Button start_timer;
    private Button pause_timer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //加载布局
        cView = inflater.inflate(R.layout.fragment5, container, false);
        initView();

        return cView;
    }
    /**
     * 初始化控件信息
     */
    private void initView() {
        mTimer = cView.findViewById(R.id.ctv);
        mTimer.setCircleTimerListener(this);
        mTimerSet = cView.findViewById(R.id.time_set_et);
        mHintSet = cView.findViewById(R.id.hint_set_et);
        start_timer = cView.findViewById(R.id.start_timer);
        pause_timer = cView.findViewById(R.id.pause_timer);
        start_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    mTimer.setCurrentTime(Integer.parseInt(mTimerSet.getText().toString()));
                }
                catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                mTimer.startTimer();
                mTimer.setHintText(mHintSet.getText().toString());

            }
        });
        pause_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimer.pauseTimer();

            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getActivity().getMenuInflater();
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

/**
    public void start(View v)
    {
        try
        {
            mTimer.setCurrentTime(Integer.parseInt(mTimerSet.getText().toString()));
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }
        mTimer.startTimer();
        mTimer.setHintText(mHintSet.getText().toString());
    }

    public void pause(View v)
    {
        mTimer.pauseTimer();
    }
 **/
    @Override
    public void onTimerStop()
    {
        Toast.makeText(getActivity(), "You stop the focus timer!", Toast.LENGTH_LONG).show();

    }
    @Override
    public void onTimerStart(int currentTime)
    {
        Toast.makeText(getActivity(), "You start the focus timer!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTimerPause(int currentTime)
    {
        Toast.makeText(getActivity(), "You pause the focus timer!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTimerTimingValueChanged(int time)
    {
        Log.d(TAG, "onTimerTimingValueChanged");
    }

    @Override
    public void onTimerSetValueChanged(int time)
    {
        Log.d(TAG, "onTimerSetValueChanged");
    }

    @Override
    public void onTimerSetValueChange(int time)
    {
        Log.d(TAG, "onTimerSetValueChange");
    }



}
