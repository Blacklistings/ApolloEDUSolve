package com.example.apolloedusolve.start;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.apolloedusolve.FileSystem;
import com.example.apolloedusolve.R;
import com.example.apolloedusolve.Server;
import com.example.apolloedusolve.account_activity.AccountActivity;

import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EmployeeRegisterActivity2 extends AppCompatActivity {
    private EditText password, password2;
    private TextView error;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_register2);
        password = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);
        error = findViewById(R.id.error);
        error.setVisibility(View.INVISIBLE);
    }

    public void next(View view){
        boolean filled = true;
        if(password.getText().toString().isEmpty()){
            password.setHintTextColor(Color.RED);
            filled = false;
        } else {
            password.setHintTextColor(Color.BLACK);
        }
        if(password2.getText().toString().isEmpty()){
            password2.setHintTextColor(Color.RED);
            filled = false;
        } else {
            password2.setHintTextColor(Color.BLACK);
        }
        if(filled){
            if(password.getText().toString().equals(password2.getText().toString())){
                final String password = this.password.getText().toString();
                final Context context = this;
                Server.register(getIntent().getExtras(), password, new Server.OnReplyReceived() {
                    @Override
                    public void onReplyReceived(CloseableHttpResponse response) {
                        if(response != null){
                            Bundle extras = getIntent().getExtras();
                            FileSystem.writeAccountInfo(extras.getString("name"), password, context);
                            Intent intent = new Intent(context, AccountActivity.class);
                            intent.putExtra("name", extras.getString("name"));
                            startActivity(intent);
                        } else {
                            //todo alert user server is down
                        }
                    }
                });
            } else {
                error.setVisibility(View.VISIBLE);
            }
        }
    }
}