package com.example.projectattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_choice);
        
            ImageView one = findViewById(R.id.ivOne);
            ImageView two = findViewById(R.id.ivTwo);
            ImageView three = findViewById(R.id.ivThree);
            ImageView four = findViewById(R.id.ivFour);
            ImageView five = findViewById(R.id.ivFive);
            ImageView six = findViewById(R.id.ivsix);

            one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                      Intent intent=new Intent(AdminChoiceActivity.this,AddRemoveTeacher.class);
                      startActivity(intent);
                }
            });

            two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(AdminChoiceActivity.this,AddRemoveStudentByAdmin.class);
                    startActivity(intent);
                }
            });

            three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(AdminChoiceActivity.this, MarkAttendanceByAdmin.class);
                    startActivity(intent);
                }
            });

            four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(AdminChoiceActivity.this, AttendanceRecordAdmin.class);
                    startActivity(intent);
                }
            });

            five.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(AdminChoiceActivity.this, AdminChangePassword.class);
                    startActivity(intent);
                }
            });

            six.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(AdminChoiceActivity.this, Defaulter.class);
                    startActivity(intent);
                }
            });


    }

}