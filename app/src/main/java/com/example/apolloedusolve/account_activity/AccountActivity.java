package com.example.apolloedusolve.account_activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.apolloedusolve.FileSystem;
import com.example.apolloedusolve.ProfileActivity;
import com.example.apolloedusolve.R;
import com.example.apolloedusolve.Server;
import com.example.apolloedusolve.start.StartActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class AccountActivity extends AppCompatActivity {
    private JSONObject user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(listener);

        if(getIntent().hasExtra("name")){
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment(getIntent().getStringExtra("name"))).commit();
        } else {
            Server.getUser(new Server.OnReplyReceived() {
                @Override
                public void onReplyReceived(CloseableHttpResponse response) {
                    if (response != null) {
                        try {
                            String in = new String(EntityUtils.toByteArray(response.getEntity()));
                            System.out.println(in);
                            if (in.equals("1")) {
                                System.out.println("couldn't access server");
                            } else {
                                user = new JSONObject(in);
                                System.out.println(user);
                                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment(user.getString("name"))).commit();
                            }
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    try {
                        if(user != null) {
                            fragment = new HomeFragment(user.getString("name"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.navigation_search:
                    fragment = new SearchFragment();
                    break;
                case R.id.navigation_resources:
                    fragment = new ResourcesFragment();
                    break;
                case R.id.navigation_edit:
                    fragment = new EditFragment();
                    break;
                default:
                    return false;
            }
            if(fragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
            }
            return true;
        }
    };

    public void viewProfile(View view){
        if(user==null){
            final Context context = this;
            Server.getUser(new Server.OnReplyReceived() {
                @Override
                public void onReplyReceived(CloseableHttpResponse response) {
                    if (response != null) {
                        try {
                            String in = new String(EntityUtils.toByteArray(response.getEntity()));
                            System.out.println(in);
                            if (in.equals("1")) {
                                System.out.println("couldn't access server");
                            } else {
                                user = new JSONObject(in);
                                Intent intent = new Intent(context, ProfileActivity.class);
                                intent.putExtra("personal", true);
                                intent.putExtra("user", user.toString());
                                startActivity(intent);
                            }
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("personal", true);
            intent.putExtra("user", user.toString());
            startActivity(intent);
        }
    }

    public void jobLook(View view){
        System.out.println("Job Look");
    }

    public void logout(View view){
        Server.logout();
        FileSystem.clearAccountInfo(this);
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}
