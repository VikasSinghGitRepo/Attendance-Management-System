package com.example.projectattendance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;



import com.example.projectattendance.Model.Users;
import com.example.projectattendance.Model.studentUser;
import com.example.projectattendance.databinding.ActivityStudentDataUploadBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentDataUpload extends AppCompatActivity {

    ActivityStudentDataUploadBinding binding;
    FirebaseAuth auth;
    ImageView profile_image;
    private DatePickerDialog datePickerDialog;
    private Button dateButton,upload_info;
    String Branch,year,semester,date;
    EditText etRollNo;
    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    String id="";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityStudentDataUploadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    // User is signed in, you can access the currentUser object here.
                } else {
                    // User is signed out.
                }
            }
        });


        if (currentUser != null) {
            id = currentUser.getUid();
            // Do something with the UID
        } else {
            Toast.makeText(StudentDataUpload.this, "User is not logged in ", Toast.LENGTH_SHORT).show();
        }

        initDatePicker();

        dateButton=findViewById(R.id.dateButton);
        dateButton.setText("ENTER DOB:"+getTodaysDate());

        getSupportActionBar().hide();



        auth=FirebaseAuth.getInstance();

        // getting ImageView by its id
        profile_image = findViewById(R.id.profile_image);

        // we will get the default FirebaseDatabase instance
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // we will get a DatabaseReference for the database root node
        DatabaseReference databaseReference = database.getReference();

        FirebaseApp.initializeApp(this);
        FirebaseStorage.getInstance().getReference();
        FirebaseDatabase.getInstance().getReference();
        FirebaseAuth.getInstance();


        etRollNo=findViewById(R.id.etRollno);


        upload_info=findViewById(R.id.upload_info);
        upload_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String id="";
                FirebaseUser currentUser = auth.getCurrentUser();
                if (currentUser != null) {
                   id = currentUser.getUid();
                    // Do something with the UID
                } else {
                    Toast.makeText(StudentDataUpload.this, "User is not logged in ", Toast.LENGTH_SHORT).show();
                }
               // String id = auth.getCurrentUser().getUid();
                studentUser stuuser=new studentUser(etRollNo.getText().toString(),date,semester,year,Branch,id);
               // String uid=auth.getCurrentUser().getUid();

                DatabaseReference Reference1 =database.getReference();
                DatabaseReference StudentReference=Reference1.child("StudentUser").child(id);
                StudentReference.setValue(stuuser);
                Toast.makeText(StudentDataUpload.this, "Data uploaded Successfully", Toast.LENGTH_SHORT).show();

                startActivity1();
            }
        });


        try {

            final String uid = auth.getUid();
            // Here "image" is the child node value we are getting
            // child node data in the getImage variable
            DatabaseReference getImage = databaseReference.child("Users").child(uid).child("profilePic");
            getImage.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // getting a DataSnapshot for the location at the specified
                    // relative path and getting in the link variable
                    String link = dataSnapshot.getValue(String.class);

                    // loading that data into rImage
                    // variable which is ImageView
                    // Picasso.get().load(link).into(profile_image);
                    Picasso.get().load(link).placeholder(R.drawable.avatar3).into(binding.profileImage);
                }

                // this will called when any problem
                // occurs in getting data
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // we are showing that error message in toast
                    Toast.makeText(StudentDataUpload.this, "Error Loading Image", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch(Exception e){
            Toast.makeText(this, "Couldn't fetch image Upload first"+e, Toast.LENGTH_SHORT).show();
        }




        //Spinner 0
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Branch = parent.getItemAtPosition(position).toString();
             //   Toast.makeText(StudentDataUpload.this, "Branch "+Branch, Toast.LENGTH_SHORT).show();

                // Use the selected item here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });


        //Spinner 1
        Spinner spinner1 = findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = parent.getItemAtPosition(position).toString();
                // Use the selected item here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });


        //Spinner 2
        Spinner spinner2 = findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                semester = parent.getItemAtPosition(position).toString();
                // Use the selected item here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
       // semester = String.valueOf(spinner2.getSelectedItem());


//        String[] SEMESTER = {"Null","First","Second","Third","Forth","Fifth","Sixth"};
//        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,SEMESTER);
//        spinner2.setAdapter(adapter2);
//        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                // String UserType= spinner.getSelectedItem().toString();
//
//                semester = String.valueOf(spinner2.getItemAtPosition(position));
//                Toast.makeText(ChooseStudent.this, "Selected: " + spinner2.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//
//            }
//        });





    }//closing onCreate

    public void chooseImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            CircleImageView profileImage = findViewById(R.id.profile_image);
            profileImage.setImageURI(imageUri);
            uploadImageToFirebase(imageUri);
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("profile_pictures/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
            UploadTask uploadTask = storageRef.putFile(imageUri);
            uploadTask.addOnSuccessListener(taskSnapshot -> {
                storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String downloadUrl = uri.toString();
                    FirebaseDatabase.getInstance().getReference().child("user_profile_pictures").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profile_picture").setValue(downloadUrl);
                });
            });
        }
        else{
          //  Toast.makeText(this, "user is not logged in", Toast.LENGTH_SHORT).show();
        }

    }

    public void startActivity1(){
        Intent intent = new Intent(StudentDataUpload.this, ChooseSubject.class);
        intent.putExtra("SEM",semester);
        intent.putExtra("FIELD",Branch);
        startActivity(intent);
        finish();

//        Bundle bundle = new Bundle();
//        bundle.putString("SEMESTER",semester);
//        bundle.putString("Pursuing_year",year);
//        bundle.putString("Branch",Branch);
//        intent.putExtras(bundle);
//        startActivity(intent);


    }




    private String getTodaysDate() {

        Calendar cal = Calendar.getInstance();
        int Year=cal.get(Calendar.YEAR);
        int Month=cal.get(Calendar.MONTH);
        Month=Month+1;
        int Day=cal.get(Calendar.DAY_OF_MONTH);

        return(makeDataString(Day,Month,Year));

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initDatePicker(){

        DatePickerDialog.OnDateSetListener dataSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month=month+1;
                date = makeDataString(day,month,year);
                dateButton.setText(date);

            }
        };

        Calendar cal = Calendar.getInstance();
        int Year=cal.get(Calendar.YEAR);
        int Month=cal.get(Calendar.MONTH);
        int Day=cal.get(Calendar.DAY_OF_MONTH);


        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog=new DatePickerDialog(this,style,dataSetListener,Year,Month,Day);
    }



    public String makeDataString(int day,int month,int year){

        return getMonthFormat(month)+" "+day+" "+year;
    }

    private String getMonthFormat(int month) {

        if(month==1)
            return "JAN";
        if(month==2)
            return "FEB";
        if(month==3)
            return "MARCH";
        if(month==4)
            return "APRIL";
        if(month==5)
            return "MAy";
        if(month==6)
            return "JUNe";
        if(month==7)
            return "JULY";
        if(month==8)
            return "AUGUST";
        if(month==9)
            return "SEPT";
        if(month==10)
            return "OCT";
        if(month==11)
            return "NOV";
        if(month==12)
            return "DEC";

        else
            return "JAN";


    }

    public void openDatePicker(View view){
        datePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}//closing class
