package com.example.flagapplication.playAudience;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.flagapplication.view.flagMainActivity;

public class ClearBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "ClearBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent){
        final flagMainActivity musicActivity = flagMainActivity.getInstance();
        musicActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("ClaerReceiver:","The audio service has closed");
                Log.e(TAG, "run: "+"Close");
                flagMainActivity.audioService.onDestroy();
            }
        });

    }
}
