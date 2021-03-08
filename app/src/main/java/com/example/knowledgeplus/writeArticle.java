package com.example.knowledgeplus;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Calendar;

public class writeArticle extends AppCompatActivity {
    DatabaseReference databaseReference;
    EditText titleET;
    EditText bodyET;
    String uid;
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView locationTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writearticle);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("article");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(20 * 1000);
        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        double wayLatitude = location.getLatitude();
                        double wayLongitude = location.getLongitude();
                        Log.d("72", String.valueOf(wayLatitude));
                        Log.d("73", String.valueOf(wayLongitude));
                    }

                }
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titleET = (EditText) findViewById(R.id.title);
        bodyET = (EditText) findViewById(R.id.body);
        locationTV = findViewById(R.id.location);
        locationTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get location needs specific version above
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // check whether permission to get location in granted
                    if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        // get the location here
                        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(Task<Location> task) {
                                Location location = task.getResult();
                                if (location != null) {
                                    try {
                                        Geocoder geocoder = new Geocoder(writeArticle.this, Locale.getDefault());

                                        List<Address> addresses = geocoder.getFromLocation(
                                            location.getLatitude(), location.getLongitude(), 1
                                        );
                                        String cityName = addresses.get(0).getLocality();
                                        String stateName = addresses.get(0).getAdminArea();
                                        String countryName = addresses.get(0).getCountryName();
                                        locationTV.setText(cityName + ", " + stateName + ", " + countryName);
                                        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                    else {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addArticle(View view) {
        String titleString = titleET.getText().toString();
        String bodyString = bodyET.getText().toString();
        String location = locationTV.getText().toString();
        if (location.equals("Add my location")) {
            location = null;
        }
        if (!titleString.isEmpty() && !bodyString.isEmpty()) {
            // creating unique id for article
            String id = databaseReference.push().getKey();
            String username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            ArticleCard articleCard = new ArticleCard(id, titleString, 0, 0, username, uid, location, Calendar.getInstance().getTime().toString(), bodyString, null);
            // inside the id node, the new article will be stored
            databaseReference.child(id).setValue(articleCard);
            Toast.makeText(this, "Article added", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(writeArticle.this, HomeActivity.class));
        }
        else {
            if (titleString.isEmpty()) {
                Toast.makeText(this, "title is empty", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "body is empty", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
