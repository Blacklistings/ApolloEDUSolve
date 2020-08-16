package com.example.apolloedusolve;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.apolloedusolve.start.StartActivity;

import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.File;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    private EditText username, password;
    private TextView err;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        err = findViewById(R.id.err);
        err.setVisibility(View.INVISIBLE);
    }

    public void login(View view) {
        final String username = this.username.getText().toString(),
                password = this.password.getText().toString();
        boolean filled = true;
        if(username.isEmpty()){
            this.username.setTextColor(Color.RED);
            filled = false;
        } else {
            this.username.setTextColor(Color.BLACK);
        }

        if(password.isEmpty()){
            this.password.setTextColor(Color.RED);
            filled = false;
        } else {
            this.password.setTextColor(Color.BLACK);
        }

        if(filled){
            final Context context = this;
            Server.verifyLogin(username, password, new Server.OnReplyReceived() {
                String in = "";
                @Override
                public void onReplyReceived(CloseableHttpResponse response) {
                    try {
                        in = new String(EntityUtils.toByteArray(response.getEntity()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        err.setText("Can't Contact Server");
                        err.setVisibility(View.VISIBLE);
                    }
                    if(in.equals("0")){
                        FileSystem.writeAccountInfo(username, password, context);
                        Server.init(context);
                        System.out.println("gg");
                        //Intent intent = new Intent(context, AccountScreen.class);
                        //startActivity(intent);
                    } else if(in.equals("1")){
                        err.post(new Runnable() {
                            @Override
                            public void run() {
                                err.setVisibility(View.VISIBLE);
                                err.setText("Account doesn't exist");
                            }
                        });
                    } else if(in.equals("2")){
                        err.post(new Runnable() {
                            @Override
                            public void run() {
                                err.setVisibility(View.VISIBLE);
                                err.setText("Incorrect Password");
                            }
                        });
                    }
                }
            });
        }
    }

    public void back(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}
