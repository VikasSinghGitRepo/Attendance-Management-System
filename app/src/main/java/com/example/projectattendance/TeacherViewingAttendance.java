package com.example.projectattendance;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.projectattendance.Adapters.AttendanceListAdapter;
import com.example.projectattendance.Model.Attendance;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeacherViewingAttendance extends AppCompatActivity {

    private DatabaseReference mAttendanceRef;
    private ListView mAttendanceListView;
    private List<Attendance> mAttendanceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_viewing_attendance);


        // Get a reference to the attendance node in the Firebase Realtime Database
        mAttendanceRef = FirebaseDatabase.getInstance().getReference("Attendance");

        // Get a reference to the ListView in the activity's layout file
        mAttendanceListView = findViewById(R.id.attendance_list_view);

        // Initialize the attendance list
        mAttendanceList = new ArrayList<>();

        // Create an adapter to convert the attendance list to ListView items
        AttendanceListAdapter adapter = new AttendanceListAdapter(this, mAttendanceList);

        // Set the adapter for the ListView
        mAttendanceListView.setAdapter(adapter);

        // Attach a listener to the attendance node to populate the attendance list
        mAttendanceRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Get the attendance record values
                String academicYear = snapshot.child("academicYear").getValue(String.class);
                String semester = snapshot.child("semester").getValue(String.class);
                String date = snapshot.child("date").getValue(String.class);
                String subject = snapshot.child("subject").getValue(String.class);
                String rollNo = snapshot.child("id").getKey();


                // Create a new attendance record and add it to the attendance list
                Attendance attendanceRecord = new Attendance(academicYear, semester, date, subject, rollNo);
                mAttendanceList.add(attendanceRecord);

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Handle attendance record updates
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // Handle attendance record deletions
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Handle attendance record movements
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }
}

