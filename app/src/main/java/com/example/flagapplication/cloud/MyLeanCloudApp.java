package com.example.flagapplication.cloud;


import android.app.Application;
import com.avos.avoscloud.AVOSCloud;

public class MyLeanCloudApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"vVS3pbz00gl4xFV5g1apXst7-gzGzoHsz","Lmm8yUQjTI3UOUAM2nf3h4BK");
    }

}
