package com.example.projectattendance;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.projectattendance.Model.Attendance;
import com.example.projectattendance.Model.studentUser;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MarkAttendanceByStudent extends AppCompatActivity {

    //private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    private FusedLocationProviderClient fusedLocationClient;

    private TextView addressTextView,branchTextView,rollnoTextView,semesterTextView,subjectTextView;
    String id;
    FirebaseAuth mAuth;
    String Branch,DOB,Year,semester,rollno;
    String todaysDate;
    String selectedSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance_by_sudent);

//        latitudeTextView=findViewById(R.id.tvlognitude);
//        longitudeTextView=findViewById(R.id.tvlatitude);
//        addressTextView=findViewById(R.id.tvaddress);
        branchTextView=findViewById(R.id.tvBranch);
        rollnoTextView=findViewById(R.id.tvRollNo);
        semesterTextView=findViewById(R.id.tvSemester);
        subjectTextView=findViewById(R.id.tvSubjectName);
        addressTextView=findViewById(R.id.tvAddress);


        mAuth=FirebaseAuth.getInstance();


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            getCurrentLocation();
        }
        id = mAuth.getCurrentUser().getUid();




        //get today's date
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        todaysDate = dateFormat.format(currentDate);


        Intent intent = getIntent();
        selectedSubject = intent.getStringExtra("SELECTED_ITEM_TEXT");
        //Toast.makeText(this, "subject name "+selectedSubject, Toast.LENGTH_SHORT).show();

        Button button=findViewById(R.id.btnMarkAttendance);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                markAttendance();
            }
        });
    }

    private void getCurrentLocation() {
        try {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                   // latitudeTextView.setText("Latitude: " + latitude);
                   // longitudeTextView.setText("Longitude: " + longitude);
                    getAddress(latitude, longitude);

                    // Specify the target location
                    double targetLat = 12.3456789;
                    double targetLon = 98.7654321;

                    // Calculate the distance between current location and target location
                    double distance = getDistanceFromLatLonInKm(latitude, longitude, targetLat, targetLon);

                    // Check if the distance is less than 0.5km
                    if (distance < 0.5) {
                        markAttendance();
                    }
                } else {
                    Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
                }

            });
        } catch (SecurityException e) {
            e.printStackTrace();
            Log.e("TAG", "Error getting location: " + e.getMessage()); // Log the error message
        }
    }


    private void getAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                String addressString = address.getAddressLine(0);
                addressTextView.setText(addressString);
            } else {
                Toast.makeText(this, "Unable to find address.", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private double getDistanceFromLatLonInKm(Double lat1, Double lon1, Double lat2, Double lon2) {
        double R = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2 - lat1);  // deg2rad below
        double dLon = deg2rad(lon2 - lon1);
        double a =
                Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c; // Distance in km
        return d;
    }


    private double deg2rad(double deg) {
        return deg * (Math.PI / 180);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void markAttendance() {


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = ref.child("StudentUser").child(id);

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get the student data from the database snapshot
                studentUser student = dataSnapshot.getValue(studentUser.class);

                // Do something with the student object (e.g. update UI)
                // For example:
                // Log.d("MyActivity", "Student name: " + student.getName());

                Branch = student.getBranch();
                branchTextView.setText(Branch);
                DOB = student.getDOB();
                Year = student.getPursuingYear();
                rollno = student.getRoll_no();
                rollnoTextView.setText(rollno);
                semester = student.getSem();
                semesterTextView.setText(semester);
                subjectTextView.setText(selectedSubject);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting student data failed, log a message
                Log.w("MyActivity", "loadStudent:onCancelled", databaseError.toException());
            }
        };

        userRef.addValueEventListener(userListener);

        Attendance attendance = new Attendance(id, Year, semester, todaysDate, selectedSubject, rollno);

        if (Year != null && semester != null && todaysDate != null && selectedSubject != null && rollno != null) {
            DatabaseReference attendanceRef = FirebaseDatabase.getInstance().getReference().child("Attendance").child(id).child(rollno).child(semester).child(todaysDate).child(selectedSubject).child(todaysDate);
            attendanceRef.setValue("true")
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MarkAttendanceByStudent.this, "Attendance marked successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MarkAttendanceByStudent.this, "Attendance marking failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
