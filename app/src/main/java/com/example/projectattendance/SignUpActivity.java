 package com.example.projectattendance;
 import androidx.annotation.NonNull;
 import androidx.appcompat.app.ActionBar;
 import androidx.appcompat.app.AppCompatActivity;

 import android.app.ProgressDialog;
 import android.content.Intent;
 import android.os.Bundle;
 import android.text.TextUtils;
 import android.util.Log;
 import android.util.Patterns;
 import android.view.View;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.TextView;
 import android.widget.Toast;

 import com.example.projectattendance.Model.Users;
 import com.google.android.gms.auth.api.signin.GoogleSignIn;
 import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
 import com.google.android.gms.auth.api.signin.GoogleSignInClient;
 import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
 import com.google.android.gms.common.api.ApiException;
 import com.google.android.gms.tasks.OnCompleteListener;
 import com.google.android.gms.tasks.Task;
 import com.google.firebase.auth.AuthCredential;
 import com.google.firebase.auth.AuthResult;
 import com.google.firebase.auth.FirebaseAuth;
 import com.google.firebase.auth.FirebaseUser;
 import com.google.firebase.auth.GoogleAuthProvider;
 import com.google.firebase.database.FirebaseDatabase;

 import java.util.regex.Matcher;
 import java.util.regex.Pattern;

 public class SignUpActivity extends AppCompatActivity {

     Button signUp,btn_Google;

     EditText etName,etEmail,etPass;

     // Spinner spinner;// String userType;

     TextView tv_AlreadyHaveAccount;

     boolean valid=false;

     private FirebaseAuth mAuth;

     ProgressDialog progressDialog;

     FirebaseDatabase database;

     private GoogleSignInClient mGoogleSignInClient;

     public static final int RC_SIGN_IN=9001;



     // [END declare_auth]

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);

         // [END initialize_auth]
         setContentView(R.layout.activity_sign_up);
         //getSupportActionBar().hide();
         ActionBar actionBar = getSupportActionBar();
         actionBar.hide();






         // Initialize Firebase Auth
         mAuth = FirebaseAuth.getInstance();
         //Initialize Firebase Database
         database = FirebaseDatabase.getInstance();


         progressDialog = new ProgressDialog(this);
         progressDialog.setTitle("Creating account");
         progressDialog.setMessage("we're creating your account");



         // [START config_signin]
         // Configure Google Sign In
         GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                 .requestIdToken(getString(R.string.default_web_client_id))
                 .requestEmail()
                 .build();

         mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
         // [END config_signin]



         etName=findViewById(R.id.et_name);
         etPass=findViewById(R.id.etPass);
         etEmail=findViewById(R.id.etEmail);
         signUp=findViewById(R.id.btn_signup);
         btn_Google=findViewById(R.id.btn_Google);
         tv_AlreadyHaveAccount=findViewById(R.id.tv_AlreadyHaveAccount);



         //button signUp listener
         signUp.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String name=etName.getText().toString();
                 String pass=etPass.getText().toString();
                 String email=etEmail.getText().toString();
                 checkField(etName);
                 checkField(etEmail);
                 checkField(etPass);


                 createAccount(email,pass);
                 progressDialog.show();


             }
         });



         //button google listener
         btn_Google.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 GooglesignIn();

             }
         });



         // textView Listener
         tv_AlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
             }
         });


//        Spinner spinner = findViewById(R.id.spinner);
//        String[] UserType = {"Null","Student", "Faculty", "Admin"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, UserType);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//               // String UserType= spinner.getSelectedItem().toString();
//
//                userType = String.valueOf(spinner.getItemAtPosition(position));
//                Toast.makeText(SignUp.this, "Selected: " + spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//
//            }
//        });


         //closing on create
     }




     //checking fields should not be empty
     public boolean checkField(EditText textField){
         if(textField.getText().toString().isEmpty()){
             textField.setError("Don't keep the fields empty");
             valid = false;
         }

         else if (!isValidEmail(etEmail.getText().toString())) {
             etEmail.setError("Please enter a valid email address ends with gmail.com");
             etEmail.requestFocus();
             valid = false;
             //return false;
         }
         else if (!isValidPassword(etPass.getText().toString())) {
                 etPass.setError("Please enter a valid password (at least 8 characters, 1 uppercase letter, 1 lowercase letter, and 1 special character)");
                 etPass.requestFocus();
                 valid = false;
               //   return false;
             }
//       else   if (!isValidPassword(etPass.getText().toString())) {
//             etPass.setError("Please enter a valid password (at least 8 characters, 1 uppercase letter, 1 lowercase letter, and 1 special character)");
//             etPass.requestFocus();
//             return false;
//         }

        else {
             valid = true;
         }

         return valid;
     }

     private boolean isValidEmail(CharSequence target) {
         return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches() && ( target.toString().endsWith("@gmail.com") || target.toString().endsWith("@edu.in") ));
     }
     private boolean isValidPassword(String password) {
         Pattern pattern;
         Matcher matcher;
         final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
         pattern = Pattern.compile(PASSWORD_PATTERN);
         matcher = pattern.matcher(password);

         return matcher.matches();
     }



//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Intent intent= new Intent(SignUp.this,MainActivity.class);
//            startActivity(intent);
//        }
//    }



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
                                 Log.d("Vikas", "onComplete: Inside issuccesful");
                                 verifyEmail();
                                 Users user = new Users(etName.getText().toString(),
                                          etEmail.getText().toString(), etPass.getText().toString());
                                 String id = task.getResult().getUser().getUid();
                                 database.getReference().child("Users").child(id).setValue(user);
                                 Toast.makeText(SignUpActivity.this, "createUserWithEmail:success", Toast.LENGTH_SHORT).show();
                                 //FirebaseUser user = mAuth.getCurrentUser();
                                 //updateUI(user);
                             } else {
                                 // If sign in fails, display a message to the user.
                                 Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                 Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                         Toast.LENGTH_SHORT).show();
                                 // updateUI(null);
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
                         Toast.makeText(SignUpActivity.this, "Email Sent Please verify from your gmail", Toast.LENGTH_SHORT).show();
                         FirebaseAuth.getInstance().signOut();
                         startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
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




     // [START on_start_check_user]
     @Override
     public void onStart() {
         super.onStart();
         // Check if user is signed in (non-null) and update UI accordingly.
         FirebaseUser currentUser = mAuth.getCurrentUser();
         if (currentUser != null) {
//            Intent intent = new Intent(SignUp.this, ChoiceTchrStu.class);
//            startActivity(intent);
         }
     }
     // [END on_start_check_user]







     // [START onactivityresult]

     @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

         // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
         if (requestCode == RC_SIGN_IN) {
             Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
             try {
                 // Google Sign In was successful, authenticate with Firebase
                 GoogleSignInAccount account = task.getResult(ApiException.class);
                 Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                 firebaseAuthWithGoogle(account.getIdToken());
             } catch (ApiException e) {
                 // Google Sign In failed, update UI appropriately
                 Log.w("TAG", "Google sign in failed", e);
             }
         }
     }

     // [START auth_with_google]
     private void firebaseAuthWithGoogle(String idToken) {
         AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
         mAuth.signInWithCredential(credential)
                 .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()) {
                             // Sign in success, update UI with the signed-in user's information

                             Log.d("google", "onComplete: inside google sign in");

                             Toast.makeText(SignUpActivity.this, "signInWithCredential:success", Toast.LENGTH_SHORT).show();
                             FirebaseUser user = mAuth.getCurrentUser();

                             Users users= new Users();
                             users.setUserId(user.getUid());
                             users.setUserName(user.getDisplayName());
//                            users.setUserType(spinner.getSelectedItem().toString());
                             users.setProfilePic(user.getPhotoUrl().toString());
                             database.getReference().child("Users").child(user.getUid()).setValue(users);
                            // startActivity(new Intent(SignUpActivity.this,StudentDataUpload.class));


                         } else {
                             // If sign in fails, display a message to the user.
                             Toast.makeText(SignUpActivity.this, "signInWithCredential:failure", Toast.LENGTH_SHORT).show();
                         }
                     }
                 });
     }

     // [START signin]
     private void GooglesignIn() {
         Intent signInIntent = mGoogleSignInClient.getSignInIntent();
         startActivityForResult(signInIntent, RC_SIGN_IN);
     }



     //onBackPressed function
     @Override
     public void onBackPressed() {
         super.onBackPressed();
         finishAffinity();
     }
 }