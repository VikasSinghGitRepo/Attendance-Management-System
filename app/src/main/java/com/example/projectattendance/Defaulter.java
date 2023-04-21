package com.example.projectattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectattendance.Model.studentUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Defaulter extends AppCompatActivity {

    TextView tv1;
    Button btn1;
    EditText et1;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defaulter);
//        tv1=findViewById(R.id.tv_1);
//        et1=findViewById(R.id.et_roll_NO);
//        btn1=findViewById(R.id.btn_check);

        tv1=findViewById(R.id.tv_1);
        et1=findViewById(R.id.et_roll_NO);
        btn1=findViewById(R.id.btn_check);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    int val=Integer.parseInt(et1.getText().toString());
                /*

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference attendanceRef = database.getReference("Attendance");

                attendanceRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int count = (int) snapshot.getChildrenCount();
                        // Display the count in a TextView or use it as needed
                        tv1.setText(String.valueOf(count));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle database errors
                    }
                });


                */
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference attendanceRef = database.getReference("attendance");

                Query query = attendanceRef.orderByValue().equalTo(val);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        long count = snapshot.getChildrenCount();
                        // Display the count in a TextView or use it as needed
                        if(count<=1){
                        tv1.setText("Yes He is defaulter "+String.valueOf(count));
                        }
                        else{
                            tv1.setText("No He is not  defaulter "+String.valueOf(count));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle database errors
                    }
                });



            }
        });

    }
}