package com.example.projectattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectattendance.Model.Faculty;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AddRemoveTeacher extends AppCompatActivity {

    EditText editTextId,editTextPass,editTextName,editTextSubject;
    Button btnAdd,btnDelete;
    String email,pass,uid;
    boolean valid=true;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    Spinner sp;


    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remove_teacher);
        editTextId=findViewById(R.id.etId);
        editTextPass=findViewById(R.id.etPass);
        editTextName=findViewById(R.id.et_name);
        editTextSubject=findViewById(R.id.etSub);



        btnAdd=findViewById(R.id.Add);
        btnDelete=findViewById(R.id.Remove);


        progressDialog = new ProgressDialog(AddRemoveTeacher.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Creating teachers account ");



        getSupportActionBar().hide();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        //Initialize Firebase Database
        database= FirebaseDatabase.getInstance();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email=editTextId.getText().toString().trim();
                pass=editTextPass.getText().toString().trim();
                String name=editTextName.getText().toString().trim();

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String userEmail = email;


                String uidToDelete = name; // replace with the actual uid to delete

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference usersRef = database.getReference("Users");
                DatabaseReference userToDeleteRef = usersRef.child(uidToDelete);

                userToDeleteRef.removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(AddRemoveTeacher.this, "User deleted successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddRemoveTeacher.this, "Failed to delete user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                DatabaseReference usersRef1 = database.getReference("Teachers");
                DatabaseReference userToDeleteRef1 = usersRef1.child(uidToDelete);
                userToDeleteRef1.removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(AddRemoveTeacher.this, "User deleted successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddRemoveTeacher.this, "Failed to delete user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });



            }
        });



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 email=editTextId.getText().toString().trim();
                 pass=editTextPass.getText().toString().trim();
                 createAccount(email,pass);
                 progressDialog.show();

            }
        });

    }



    // function for creating account with email and password
    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        if (valid) {

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                             //   Log.d("Vikas", "onComplete: Inside issuccesful");
                                //verifyEmail();
                                Faculty faculty = new Faculty(editTextId.getText().toString(),
                                        editTextPass.getText().toString());
                                Faculty f1=new Faculty(editTextName.getText().toString(),editTextId.getText().toString(),editTextSubject.getText().toString(),editTextPass.getText().toString());
                               // String id = task.getResult().getUser().getUid();
                                database.getReference().child("Teachers").child(editTextName.getText().toString().trim()).setValue(f1);
                                database.getReference().child("Users").child(editTextName.getText().toString().trim()).setValue(faculty);
                                Toast.makeText(AddRemoveTeacher.this, "Teachers Data Saved", Toast.LENGTH_SHORT).show();
                                //FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(AddRemoveTeacher.this, "Could not add teacher.",
                                        Toast.LENGTH_SHORT).show();
                                // updateUI(null);
                            }
                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Handle error
                    Log.e("TAG", "createUserWithEmailAndPassword failed", e);
                    Toast.makeText(AddRemoveTeacher.this, "Could not add teacher. " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
            // [END create_user_with_email]
        }
    }



    //
    private void verifyEmail() {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        //email sent
                        Toast.makeText(AddRemoveTeacher.this, "Email Sent Please verify from your gmail", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }
                    else{
                        //verification failed
                        finish();
                    }

                }
            });
        }
    }






}