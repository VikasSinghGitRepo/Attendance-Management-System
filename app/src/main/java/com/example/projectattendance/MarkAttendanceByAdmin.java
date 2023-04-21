package com.example.projectattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectattendance.Model.Attendance;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MarkAttendanceByAdmin extends AppCompatActivity {
    EditText ed1,ed2,ed3,ed4,ed5;
    String Year,semester,date,subject,rollno;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance_by_admin);

        btn1=findViewById(R.id.button_mark_attendance);
        ed1=findViewById(R.id.ed_Year);
        ed2=findViewById(R.id.ed_semester);
        // ed3=findViewById(R.id.ed_date);
        ed4=findViewById(R.id.ed_subject);
        ed5=findViewById(R.id.ed_rollNo);

        Year = ed1.getText().toString();
        semester=ed2.getText().toString();
        subject=ed4.getText().toString();
        rollno=ed5.getText().toString();

        //get today's date
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = dateFormat.format(currentDate);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Year = ed1.getText().toString();
                semester=ed2.getText().toString();
                subject=ed4.getText().toString();
                rollno=ed5.getText().toString();

                Toast.makeText(MarkAttendanceByAdmin.this, "Year "+Year+" "+semester+" "+subject+" "+rollno, Toast.LENGTH_SHORT).show();
                Attendance attendance = new Attendance(Year,semester,date,subject, rollno);
                DatabaseReference attendanceRef = FirebaseDatabase.getInstance().getReference().child("Attendance").child(Year).child(semester).child(date).child(subject).child(rollno);
                attendanceRef.setValue(attendance).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MarkAttendanceByAdmin.this, "Attendance marked successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MarkAttendanceByAdmin.this, "Attendance marking failed", Toast.LENGTH_SHORT).show();
                            }
                        });



            }
        });



    }
}