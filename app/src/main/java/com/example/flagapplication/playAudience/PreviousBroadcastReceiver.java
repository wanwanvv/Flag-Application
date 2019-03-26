package com.example.flagapplication.playAudience;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.flagapplication.view.flagMainActivity;

public class PreviousBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "PreviousBroadcastReceiv";
    @Override
    public void onReceive(Context context, Intent intent){
        final flagMainActivity musicActivity = flagMainActivity.getInstance();
        musicActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Log.d("PreviousReceiver:","The audio service has previous");
                Log.e(TAG, "run: "+"Previous" );
                musicActivity.audioService.player.start();
            }
        });
    }
}
