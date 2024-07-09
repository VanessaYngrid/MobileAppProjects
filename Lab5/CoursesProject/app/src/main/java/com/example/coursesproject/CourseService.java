package com.example.coursesproject;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class CourseService extends Service {
    MediaPlayer myPlayer;
    public CourseService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"Service created", Toast.LENGTH_SHORT).show();
        myPlayer = MediaPlayer.create(this, R.raw.el_ritmo_de_mi_corazon);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"Service started", Toast.LENGTH_SHORT).show();
        myPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Service destroyed", Toast.LENGTH_SHORT).show();
        myPlayer.stop();
    }
}