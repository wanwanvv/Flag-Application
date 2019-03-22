package com.example.flagapplication.fragment;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;


import com.example.flagapplication.R;
import com.example.flagapplication.view.flagMainActivity;

public class BaseFragment extends Fragment {
    protected final String TAG = "时间管理：";
    protected Activity mActivity;
    protected Context mContext;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (flagMainActivity) context;//保存Context引用
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mActivity = getActivity();

/**
 返回一个和此fragment绑定的FragmentActivity或者其子类的实例。相反，如果此fragment绑定的是一个context的话，怎可能会返回null。
 因为getActivity()大部分都是在fragment中使用到，而fragment需要依赖于activity，所有我们在fragment里头需要做一些动作，比如启动一个activity，就需要拿到activity对象才可以启动，而fragment对象是没有startActivity()方法的。
 */

    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(R.anim.activity_in, 0);
        /**
         overridePendingTransition换动画指的是从一个activity跳转到另外一个activity时的动画
         必需紧挨着startActivity()或者finish()函数之后调用
         */
    }

}
