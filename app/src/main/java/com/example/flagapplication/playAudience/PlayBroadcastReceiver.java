package com.example.flagapplication.playAudience;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.flagapplication.view.flagMainActivity;

public class PlayBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "PlayBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent){
        final flagMainActivity musicActivity = flagMainActivity.getInstance();

        Log.e(TAG, "run: "+"start or pause" );
        if(flagMainActivity.state == 0){
            flagMainActivity.state = 1;
            flagMainActivity.audioService.player.start();
            Log.d("PlayReceiver:","The music has started");
        }else {
            flagMainActivity.state = 0;
            flagMainActivity.audioService.player.pause();
            Log.d("PlayReceiver:","The music has paused");
            //MainActivity.audioService.player.seekTo(0);
        }
        musicActivity.createNotifcation();
    }
}
