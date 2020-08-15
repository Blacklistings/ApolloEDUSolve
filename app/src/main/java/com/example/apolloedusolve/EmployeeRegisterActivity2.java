package com.example.apolloedusolve;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

            } else {
                error.setVisibility(View.VISIBLE);
            }
        }
    }
}
