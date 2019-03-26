package com.example.flagapplication.playAudience;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.app.Service;
import com.example.flagapplication.R;
import com.example.flagapplication.view.flagMainActivity;


public class AudioService extends Service implements MediaPlayer.OnCompletionListener{
    public static MediaPlayer player;
    private final IBinder binder = new AudioBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    //当Audio播放完的时候触发该动作

    @Override
    public void onCompletion(MediaPlayer mp) {
        stopSelf(); //结束了，则结束service
    }

    public void onCreate(){
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.sea);
        player.setOnCompletionListener(this);
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        if(!player.isPlaying()){
            player.start();
            flagMainActivity.state = 1;
        }
        else flagMainActivity.state = 0;
        return START_STICKY;
    }

    public void onDestroy(){
        super.onDestroy();
        if(player.isPlaying()){
            flagMainActivity.state = 0;
        }
        player.release();
    }

    //为了和Activity交互，需要定义一个Binder对象
    public class AudioBinder extends Binder {

        //返回Service对象
        public AudioService getService(){
            return AudioService.this;
        }
    }
}
