package com.example.apolloedusolve.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import com.example.apolloedusolve.R;
import com.example.apolloedusolve.Server;
import com.example.apolloedusolve.Threads;
import com.example.apolloedusolve.account_activity.AccountActivity;
import com.example.apolloedusolve.start.StartActivity;

import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private VideoView video;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Server.init(this);

        video = findViewById(R.id.video);
        String path = "android.resource://com.example.apolloedusolve/"+R.raw.video;

        Uri u = Uri.parse(path);
        video.setVideoURI(u);
        video.start();
        final long lastTime = System.currentTimeMillis();
        if(Server.loggedin()){
            final Context context = this;
            Server.verifyLogin(new Server.OnReplyReceived() {
                @Override
                public void onReplyReceived(CloseableHttpResponse response) {
                    long time = 1000-(System.currentTimeMillis()-lastTime);
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    try {
                        String in = new String(EntityUtils.toByteArray(response.getEntity()));
                        Intent intent;
                        if(in.equals("0")){
                            intent = new Intent(context, AccountActivity.class);
                        } else {
                            intent = new Intent(context, StartActivity.class);
                        }
                        startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("couldn't access server");
                        Intent intent = new Intent(context, StartActivity.class);
                        startActivity(intent);
                    }
                }
            });
        } else {
            final Intent intent = new Intent(this, StartActivity.class);
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
}
