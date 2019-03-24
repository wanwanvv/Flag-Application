package com.example.flagapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flagapplication.R;
import com.example.flagapplication.userView.SetItemView;

public class Fragment5 extends Fragment {
    private View mView;
    private SetItemView mMeItem;
    private SetItemView mAboutItem;
    private SetItemView mExitItem;
    private SetItemView mClearItem;
    private SetItemView mFeedItem;
    private SetItemView mVersionItem;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //加载布局
        mView = inflater.inflate(R.layout.fragment3, container, false);
        initView();

        return mView;
    }
    /**
     * 初始化控件信息
     */
    private void initView() {
        mExitItem=(SetItemView) mView.findViewById(R.id.rl_exit);
        mMeItem = (SetItemView) mView.findViewById(R.id.rl_me);
        mAboutItem = (SetItemView) mView.findViewById(R.id.rl_about);
        mClearItem = (SetItemView) mView.findViewById(R.id.rl_clear);
        mFeedItem = (SetItemView) mView.findViewById(R.id.rl_feedback);
        mVersionItem= (SetItemView) mView.findViewById(R.id.rl_version);

        mMeItem.setmOnSetItemClick(new SetItemView.OnSetItemClick() {
            @Override
            public void click() {
                Toast.makeText(getActivity(), "Clicked the Personal information!", Toast.LENGTH_SHORT).show();
            }
        });
        mAboutItem.setmOnSetItemClick(new SetItemView.OnSetItemClick() {
            @Override
            public void click() {
                Toast.makeText(getActivity(), "Clicked the About APP!", Toast.LENGTH_SHORT).show();
            }
        });
        mExitItem.setmOnSetItemClick(new SetItemView.OnSetItemClick() {
            @Override
            public void click() {
                Toast.makeText(getActivity(), "Clicked the Share!", Toast.LENGTH_SHORT).show();
            }
        });
        mClearItem.setmOnSetItemClick(new SetItemView.OnSetItemClick() {
            @Override
            public void click() {
                Toast.makeText(getActivity(), "Clicked the Clear cache!", Toast.LENGTH_SHORT).show();
            }
        });
        mFeedItem.setmOnSetItemClick(new SetItemView.OnSetItemClick() {
            @Override
            public void click() {
                Toast.makeText(getActivity(), "Clicked the Advice feedback!", Toast.LENGTH_SHORT).show();
            }
        });
        mVersionItem.setmOnSetItemClick(new SetItemView.OnSetItemClick() {
            @Override
            public void click() {
                Toast.makeText(getActivity(), "Clicked the New version!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
