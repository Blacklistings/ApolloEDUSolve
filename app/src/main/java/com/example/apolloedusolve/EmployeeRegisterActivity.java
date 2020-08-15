package com.example.apolloedusolve;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EmployeeRegisterActivity extends AppCompatActivity {
    private EditText name, birthday, specialty, linkedin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_register);
        name = findViewById(R.id.name);
        birthday = findViewById(R.id.birthday);
        specialty = findViewById(R.id.specialty);
        linkedin = findViewById(R.id.linkedin);
    }

    public void next(View view){
        boolean filled = true;
        if(name.getText().toString().isEmpty()){
            name.setHintTextColor(Color.RED);
            filled = false;
        } else {
            name.setHintTextColor(Color.BLACK);
        }
        if(birthday.getText().toString().isEmpty()){
            birthday.setHintTextColor(Color.RED);
            filled = false;
        } else {
            birthday.setHintTextColor(Color.BLACK);
        }
        if(specialty.getText().toString().isEmpty()){
            specialty.setHintTextColor(Color.RED);
            filled = false;
        } else {
            specialty.setHintTextColor(Color.BLACK);
        }
        if(linkedin.getText().toString().isEmpty()){
            linkedin.setHintTextColor(Color.RED);
            filled = false;
        } else {
            linkedin.setHintTextColor(Color.BLACK);
        }
        if(filled){
            AccountRegisterData.birthday = birthday.getText().toString();
            AccountRegisterData.linkedin = linkedin.getText().toString();
            AccountRegisterData.name = name.getText().toString();
            AccountRegisterData.specialty = name.getText().toString();

            Intent intent = new Intent(this, EmployeeRegisterActivity2.class);
            startActivity(intent);
        }
    }
}