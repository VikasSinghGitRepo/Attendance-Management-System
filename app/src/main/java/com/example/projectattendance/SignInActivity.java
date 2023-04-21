package com.example.projectattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectattendance.Model.Users;
import com.example.projectattendance.Model.studentUser;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class  SignInActivity extends AppCompatActivity {

    Button signIn;

    EditText etEmail,etPass;

    TextView tv_signUp;

    Spinner spLoginSelector;

    private FirebaseAuth mAuth;

    FirebaseDatabase database;

    private GoogleSignInClient mGoogleSignInClient;

    ProgressDialog progressDialog;

    boolean valid=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        getSupportActionBar().hide();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        //Initialize Firebase Database
        database=FirebaseDatabase.getInstance();



        progressDialog = new ProgressDialog(SignInActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Logging in to your account");

        etPass=findViewById(R.id.etPass);
        etEmail=findViewById(R.id.etEmail);
        signIn = findViewById(R.id.btn_signIn);
        spLoginSelector=findViewById(R.id.spLoginSelector);




        findViewById(R.id.tv_signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
            }
        });


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass=etPass.getText().toString();
                String email=etEmail.getText().toString();

                signIn(email,pass);
            }
        });


    }
/*
    public boolean checkIfStudentHasUploadedData(DatabaseReference d){
        boolean hasUploadedData=false;
        Query query = database.getReference().child("Student").orderByChild("mail").equalTo(etEmail.getText().toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // student exists, check if they have uploaded their attendance data
                    DataSnapshot studentSnapshot = dataSnapshot.getChildren().iterator().next();
                    boolean hasUploadedData = studentSnapshot.hasChild(etEmail.getText().toString());
                    if (hasUploadedData) {
                        // student has uploaded their attendance data
                        // redirect to mark_attendance page
                        Intent intent = new Intent(SignInActivity.this, MarkAttendanceBySudent.class);
                        startActivity(intent);
                        finish(); // prevent user from going back to login page
                    } else {
                        // student has not uploaded their attendance data
                        // redirect to student_data_upload page
                        Intent intent = new Intent(SignInActivity.this, StudentDataUpload.class);
                        startActivity(intent);
                        finish(); // prevent user from going back to login page
                    }
                } else {
                    // student does not exist
                    // handle this case as appropriate
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // handle errors here
            }
        });
        return hasUploadedData;

    }*/



    //Sign In with email and password
    private void signIn(String email, String password) {

        checkField(etEmail);
        checkField(etPass);

        if (valid) {
            // [START sign_in_with_email]
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {

                                // Intent intent = new Intent(signIn.this,MainActivity.class);
                                //startActivity(intent);
                                 verifyEmail();

                                Toast.makeText(SignInActivity.this, "signInWithEmail:success", Toast.LENGTH_SHORT).show();
                               // startActivity(new Intent(SignInActivity.this, TeacherMarkingAttendance.class));



                            }
                            else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            }
                    });
            // [END sign_in_with_email]
        }
    }

    //Query query = database.getReference().child("Student").orderByChild("mail").equalTo(etEmail.getText().toString());

    //checking Verification of email before signIning in
    public void verifyEmail(){
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        if(user.isEmailVerified()) {

            String userType=spLoginSelector.getSelectedItem().toString();
            if(userType.equals("Teacher")) {
                startActivity(new Intent(SignInActivity.this, TeacherMarkingAttendance.class));
                finish();
            }
            else if(userType.equals("Student")){
                startActivity(new Intent(SignInActivity.this, StudentDataUpload.class));
            }
            else if(userType.equals("Admin")){
                startActivity(new Intent(SignInActivity.this, AdminChoiceActivity.class));
            }
            finish();
        }

         else{
            mAuth.signOut();
            Toast.makeText(this, "Please Verify your Email", Toast.LENGTH_SHORT).show();
            finish();
        }

        }





    //onStart method to check current user
    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(SignInActivity.this,MainActivity.class));
        }

    }



    //checking fields should not be empty
    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Don't keep the fields empty");
            valid = false;
        }else {
            valid = true;
        }

        return valid;
    }

}