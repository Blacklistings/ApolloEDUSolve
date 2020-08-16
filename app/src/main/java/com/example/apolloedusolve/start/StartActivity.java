package com.example.apolloedusolve.start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.apolloedusolve.LoginActivity;
import com.example.apolloedusolve.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void employeeRegister(View view){
        Intent intent = new Intent(this, EmployeeRegisterActivity.class);
        startActivity(intent);
    }

    public void businessRegister(View view){

    }

    public void login(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}