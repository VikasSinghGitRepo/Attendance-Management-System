package com.example.projectattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.projectattendance.Adapters.ChooseSubjectAdapter;
import com.example.projectattendance.Model.SubjectModel;

import java.util.ArrayList;


public class ChooseSubject extends AppCompatActivity {
    RecyclerView recyclerView;
    String sem,branch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_subject);
        getSupportActionBar().hide();


        Intent i = getIntent();

        sem= i.getStringExtra("SEM");
        branch= i.getStringExtra("FIELD");

        recyclerView=findViewById(R.id.recyclerView);
        ArrayList<SubjectModel>list=new ArrayList<>();


        if (branch.equals("BSC(C.S)")){

            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            switch (sem) {
                case "First":
                    list.add(new SubjectModel(R.drawable.sub0, "D.E"));
                    list.add(new SubjectModel(R.drawable.sub1, "Mathematics"));
                    list.add(new SubjectModel(R.drawable.sub2, "Basics of C.S"));
                    list.add(new SubjectModel(R.drawable.sub3, "Windows,its Features,Application"));
                    list.add(new SubjectModel(R.drawable.sub4, "Environmental Sciences"));
                    list.add(new SubjectModel(R.drawable.sub5, "C.O.P"));
                    break;

                case "Second":
                    list.add(new SubjectModel(R.drawable.sub0, "D.E"));
                    list.add(new SubjectModel(R.drawable.sub1, "D.B.M.S"));
                    list.add(new SubjectModel(R.drawable.sub2, "LINUX"));
                    list.add(new SubjectModel(R.drawable.sub3, "Programming Concepts"));
                    list.add(new SubjectModel(R.drawable.sub4, "Discrete Mathematics"));
                    list.add(new SubjectModel(R.drawable.sub5, "F.O.M"));
                    break;

                case "Third":
                    list.add(new SubjectModel(R.drawable.sub0, "OOPS using C++"));
                    list.add(new SubjectModel(R.drawable.sub1, "S.D-I"));
                    list.add(new SubjectModel(R.drawable.sub2, "F.E-I"));
                    list.add(new SubjectModel(R.drawable.sub3, "PHP"));
                    list.add(new SubjectModel(R.drawable.sub4, "Technical Writing"));
                    list.add(new SubjectModel(R.drawable.sub5, "Embedded Systems"));
                    break;

                case "Forth":
                    list.add(new SubjectModel(R.drawable.sub0, "Value and Ethics"));
                    list.add(new SubjectModel(R.drawable.sub1, "M.F For C.S"));
                    list.add(new SubjectModel(R.drawable.sub2, "S.A & D"));
                    list.add(new SubjectModel(R.drawable.sub3, "Arrays"));
                    list.add(new SubjectModel(R.drawable.sub4, "Java Programming"));
                    list.add(new SubjectModel(R.drawable.sub5, "A.S.D-II"));
                    break;


                case "Fifth":
                    list.add(new SubjectModel(R.drawable.sub0, "O.S"));
                    list.add(new SubjectModel(R.drawable.sub1, "Python"));
                    list.add(new SubjectModel(R.drawable.sub2, "Data Structures"));
                    list.add(new SubjectModel(R.drawable.sub3, "Software Engineering"));
                    list.add(new SubjectModel(R.drawable.sub4, "System Software"));
                    list.add(new SubjectModel(R.drawable.sub5, "D.O.S"));
                    break;

                case "Sixth":
                    list.add(new SubjectModel(R.drawable.sub0, "Computer Network"));
                    list.add(new SubjectModel(R.drawable.sub1, "Number system and codes"));
                    list.add(new SubjectModel(R.drawable.sub2, "Numerical Analysis"));
                    list.add(new SubjectModel(R.drawable.sub3, "Web Technology"));
                    list.add(new SubjectModel(R.drawable.sub4, "System Programming"));
                    list.add(new SubjectModel(R.drawable.sub5, "Project Work"));
                    break;

                default:
                    list.add(new SubjectModel(R.drawable.bg, "Some error!!!!!!"));

            }
        } else if(branch.equals("BSC(I.T)")){

            switch (sem) {
                case "First":
                    list.add(new SubjectModel(R.drawable.sub0, "C.S"));
                    list.add(new SubjectModel(R.drawable.sub1, "C++"));
                    list.add(new SubjectModel(R.drawable.sub2, " Digital Computing"));
                    list.add(new SubjectModel(R.drawable.sub3, "E & C.T"));
                    list.add(new SubjectModel(R.drawable.sub4, "A.M - I"));
                    break;

                case "Second":
                    list.add(new SubjectModel(R.drawable.sub0, "G.C"));
                    list.add(new SubjectModel(R.drawable.sub1, "N&S Methods"));
                    list.add(new SubjectModel(R.drawable.sub2, "Web Programming"));
                    list.add(new SubjectModel(R.drawable.sub3, "MPA"));
                    list.add(new SubjectModel(R.drawable.sub4, "OOPS"));
                    break;

                case "Third":
                    list.add(new SubjectModel(R.drawable.sub0, "Python"));
                    list.add(new SubjectModel(R.drawable.sub1, "Data Structures"));
                    list.add(new SubjectModel(R.drawable.sub2, "Computer Networks"));
                    list.add(new SubjectModel(R.drawable.sub3, "DBMS"));
                    list.add(new SubjectModel(R.drawable.sub4, "A.M"));
                    break;

                case "Forth":
                    list.add(new SubjectModel(R.drawable.sub0, "Core Java"));
                    list.add(new SubjectModel(R.drawable.sub1, "Embedded Systems"));
                    list.add(new SubjectModel(R.drawable.sub2, "C.O.S.T"));
                    list.add(new SubjectModel(R.drawable.sub3, "Software Engineering"));
                    list.add(new SubjectModel(R.drawable.sub4, "C.G"));
                    break;


                case "Fifth":
                    list.add(new SubjectModel(R.drawable.sub0, "S.P.M"));
                    list.add(new SubjectModel(R.drawable.sub1, "I.O.T"));
                    list.add(new SubjectModel(R.drawable.sub2, "A.W.P"));
                    list.add(new SubjectModel(R.drawable.sub3, "A.I"));
                    list.add(new SubjectModel(R.drawable.sub4, "N.G.T"));
                    break;

                case "Sixth":
                    list.add(new SubjectModel(R.drawable.sub0, "B.I"));
                    list.add(new SubjectModel(R.drawable.sub1, "S.I.C"));
                    list.add(new SubjectModel(R.drawable.sub2, "E.N"));
                    list.add(new SubjectModel(R.drawable.sub3, "S.Q.I"));
                    list.add(new SubjectModel(R.drawable.sub4, "G.I.S"));
                    break;

                default:
                    list.add(new SubjectModel(R.drawable.bg, "Some error!!!!!!"));

            }


        }

        else{
            Toast.makeText(this, "Cant get inside switch statement", Toast.LENGTH_SHORT).show();
        }


        ChooseSubjectAdapter adapter = new ChooseSubjectAdapter(list, this);
        recyclerView.setAdapter(adapter);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
//         recyclerView.setLayoutManager(gridLayoutManager);

       // recyclerView.addOnItemTouchListener(new );




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}