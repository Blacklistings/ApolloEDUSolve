package com.example.apolloedusolve;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.apolloedusolve.account_activity.AccountActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {
    private TextView name, age, specialty, bio;
    private String linkedin;
    private Button contactButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        boolean personal = extras.getBoolean("personal");

        contactButton = findViewById(R.id.contactButton);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        specialty = findViewById(R.id.specialty);
        bio = findViewById(R.id.bio);
        JSONObject user = null;

        try {
            user = new JSONObject(extras.getString("user"));
            name.setText(user.getString("name"));

            String birthday = user.getString("birthday");
            LocalDate birthdate = LocalDate.of(Integer.parseInt(birthday.substring(4, 8)), Integer.parseInt(birthday.substring(0, 2)), Integer.parseInt(birthday.substring(2, 4)));
            LocalDate now = LocalDate.now();
            Period diff = Period.between(birthdate, now);
            String age = diff.getYears() + "";
            this.age.setText(age);

            specialty.setText(user.getString("specialty"));
            linkedin = user.getString("linkedin");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(personal){
            contactButton.setBackground(getDrawable(R.drawable.rounded_button_grey));
        } else {
            contactButton.setBackground(getDrawable(R.drawable.rounded_button));
        }
    }

    public void back(View view){
        Intent intent = null;
        if(getIntent().getExtras().getBoolean("personal")){
            intent = new Intent(this, AccountActivity.class);
        }
        startActivity(intent);
    }

    public void linkedin(View view){
        Uri uri = Uri.parse(linkedin);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void contact(View view){
        System.out.println("contact");
    }

}
