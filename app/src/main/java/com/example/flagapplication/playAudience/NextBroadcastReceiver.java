package com.example.flagapplication.playAudience;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.flagapplication.view.flagMainActivity;

public class NextBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "NextBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent){
        final flagMainActivity musicActivity = flagMainActivity.getInstance();
        musicActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Log.e(TAG, "run: "+"Next");
                Log.d("NextReceiver:","The audio service has nextedd");
                musicActivity.audioService.onCreate();
            }
        });

    }
}
