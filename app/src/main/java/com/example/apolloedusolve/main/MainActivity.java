package com.example.apolloedusolve.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.apolloedusolve.R;
import com.example.apolloedusolve.Server;
import com.example.apolloedusolve.Threads;
import com.example.apolloedusolve.start.StartActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private VideoView video;
    private MediaController controller;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Server.init(this);

        video = findViewById(R.id.video);
        controller = new MediaController(this);
        String path = "android.resource://com.example.apolloedusolve/"+R.raw.video;

        Uri u = Uri.parse(path);
        video.setVideoURI(u);
        video.start();
        final Intent intent;
        if(Server.loggedin()){
            //intent = new Intent(this, AccountScreen.class);
            intent = null;
        } else {
            intent = new Intent(this, StartActivity.class);
        }
        Threads.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }
}
