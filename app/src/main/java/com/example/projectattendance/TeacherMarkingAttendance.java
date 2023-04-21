package com.example.projectattendance;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.projectattendance.Adapters.TeacherChoiceAdapter;
import com.example.projectattendance.Model.SubjectModel;

import java.util.ArrayList;

public class TeacherMarkingAttendance extends AppCompatActivity {
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_marking_attendance);

        getSupportActionBar().hide();

        recyclerView=findViewById(R.id.recyclerView);
        ArrayList<SubjectModel> list=new ArrayList<>();

        list.add(new SubjectModel(R.drawable.bg, "View Attendance"));
        list.add(new SubjectModel(R.drawable.clg, "Mark Attendance"));

        TeacherChoiceAdapter adapter = new TeacherChoiceAdapter(list, this);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);





    }
}