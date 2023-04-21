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
import com.example.projectattendance.Model.Users;
import com.example.projectattendance.Model.studentUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AddRemoveStudentByAdmin extends AppCompatActivity {

    EditText editTextId,editTextPass,editTextName,editTextSubject;
    Button btnAdd,btnDelete;
    String email,pass;
    boolean valid=true;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    Spinner spcourse,spsem;
    String uid;


    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remove_student_by_admin);

        editTextId=findViewById(R.id.et_roll_no);
        editTextPass=findViewById(R.id.etPassw);
        editTextName=findViewById(R.id.et_name);
        spcourse=findViewById(R.id.spinnerCourse);
        spsem=findViewById(R.id.spinnerSem);


        email=editTextName.getText().toString().trim();
        pass=editTextPass.getText().toString().trim();





        btnAdd=findViewById(R.id.Add);
        btnDelete=findViewById(R.id.Remove);


        progressDialog = new ProgressDialog(AddRemoveStudentByAdmin.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Logging in to students account");



        getSupportActionBar().hide();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        //Initialize Firebase Database
        database= FirebaseDatabase.getInstance();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=editTextName.getText().toString().trim();
                pass=editTextPass.getText().toString().trim();
                progressDialog.show();
                createAccount(email,pass);

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=editTextName.getText().toString().trim();
                pass=editTextPass.getText().toString().trim();

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String userEmail = email;



                mAuth.fetchSignInMethodsForEmail(userEmail)
                        .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                if (task.isSuccessful()) {
                                    SignInMethodQueryResult result = task.getResult();
                                    List<String> signInMethods = result.getSignInMethods();
                                    if (signInMethods != null && !signInMethods.isEmpty()) {
                                        uid = mAuth.getCurrentUser().getUid();
                                        // Code to delete node using the uid
                                    } else {
                                        // Handle the case where the user does not exist or has no sign-in methods linked to their email.
                                    }
                                } else {
                                    // Handle the error case.
                                }
                            }
                        });


                String uidToDelete = uid; // replace with the actual uid to delete

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference usersRef = database.getReference("Users");

                if (uidToDelete != null) {
                    DatabaseReference userToDeleteRef = usersRef.child(uidToDelete);


                    userToDeleteRef.removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(AddRemoveStudentByAdmin.this, "User deleted successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddRemoveStudentByAdmin.this, "Failed to delete user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                }
                else {
                    Toast.makeText(AddRemoveStudentByAdmin.this, "uid to delete is null..", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(AddRemoveStudentByAdmin.this, "email is "+email, Toast.LENGTH_SHORT).show();
    }

    // function for creating account with email and password
    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        if (valid){

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Vikas", "onComplete: Inside issuccesful");
                                //verifyEmail();
                                studentUser sUser=new studentUser(editTextId.getText().toString(),spsem.getSelectedItem().toString(),spcourse.getSelectedItem().toString(),editTextId.getText().toString(),editTextPass.getText().toString());
                                String id = task.getResult().getUser().getUid();
                                database.getReference().child("StudentUser").child(id).setValue(sUser);
                                database.getReference().child("Users").child(id).setValue(sUser);
                                Toast.makeText(AddRemoveStudentByAdmin.this, "createUserWithEmail:success", Toast.LENGTH_SHORT).show();
                                //FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);
                            } else {
                                progressDialog.dismiss();
                                // If sign in fails, display a message to the user.
                                Toast.makeText(AddRemoveStudentByAdmin.this, "Authentication failed.for adding student",
                                        Toast.LENGTH_SHORT).show();
                            }
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
                        Toast.makeText(AddRemoveStudentByAdmin.this, "Email Sent Please verify from your gmail", Toast.LENGTH_SHORT).show();
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


