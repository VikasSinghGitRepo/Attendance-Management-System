package com.example.projectattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminChangePassword extends AppCompatActivity {
    EditText edold,ednew;
    Button changepass;
    String oldPassword,newPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_change_password);

        edold=findViewById(R.id.oldpassword);
        ednew=findViewById(R.id.newpassword);
        changepass=findViewById(R.id.btnChangePass);

        oldPassword=edold.getText().toString().trim();
        newPassword=ednew.getText().toString().trim();

        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference attendanceRef = database.getReference("attendance");

                attendanceRef.child("0").setValue(100);
                attendanceRef.child("1").setValue(200);
                attendanceRef.child("2").setValue(300);
                attendanceRef.child("3").setValue(200);
                attendanceRef.child("4").setValue(500);
                attendanceRef.child("5").setValue(491);

                oldPassword=edold.getText().toString().trim();
                newPassword=ednew.getText().toString().trim();
                DatabaseReference adminRef = FirebaseDatabase.getInstance().getReference().child("Admin");
                // Attach a listener to fetch the password value
                adminRef.child("password").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get the password value
                        String password = dataSnapshot.getValue(String.class);
//                        Toast.makeText(AdminChangePassword.this, "PAssword in firebase "+password, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(AdminChangePassword.this, "PAssword entered "+oldPassword, Toast.LENGTH_SHORT).show();
                        if(password.equals(oldPassword)){
                            adminRef.child("password").setValue(newPassword)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(AdminChangePassword.this, "Password updated succesfully", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(AdminChangePassword.this, "Unable to update the password", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                        else
                        {
                            Toast.makeText(AdminChangePassword.this, "Please enter correct old password ", Toast.LENGTH_SHORT).show();
                        }

                        // Do something with the password value here
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle any errors that may occur
                    }
                });


            }
        });



    }
}