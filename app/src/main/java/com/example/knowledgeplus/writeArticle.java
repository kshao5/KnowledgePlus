package com.example.knowledgeplus;

import android.Manifest;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Calendar;

public class writeArticle extends AppCompatActivity {
    public static final String EDIT_MODE = "write_mode";
    public static final String ARTICLE_CARD = "article_card";

    boolean isEditMode;
    ArticleCard articleCard;

    DatabaseReference databaseReference;
    FirebaseStorage storage;
    StorageReference storageReference;
    String uid;
    String id;
    int nImages = 0;

    EditText titleET;
    EditText bodyET;
    TextView imageIndicatorTV, locationTV;
    Button publishButton;

    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;
    Task<Location> location;

    List<Uri> imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(String.valueOf(getApplicationContext()), "onCreate");
        setContentView(R.layout.activity_writearticle);

        Intent intent = getIntent();
        isEditMode = intent.getBooleanExtra(EDIT_MODE, false);
        if (isEditMode) {
            articleCard = (ArticleCard) intent.getSerializableExtra(ARTICLE_CARD);
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("article");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        titleET = (EditText) findViewById(R.id.title);
        bodyET = (EditText) findViewById(R.id.body);
        imageIndicatorTV = findViewById(R.id.imageIndicatorTV);
        locationTV = findViewById(R.id.location);
        publishButton = (Button) findViewById(R.id.publishButton);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        return;
                    }

                }
            }
        };
        location = getLastLocation();

        imageUri = new ArrayList<>();

        imageIndicatorTV.setVisibility(View.INVISIBLE);




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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


    private Task<Location> getLastLocation() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(20 * 1000);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        return fusedLocationProviderClient.getLastLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (imageUri.size() > 0) {
            imageIndicatorTV.setVisibility(View.VISIBLE);
            imageIndicatorTV.setText("added " + imageUri.size() + " images");
        }
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
            id = databaseReference.push().getKey();
            String username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            android.text.format.DateFormat df = new android.text.format.DateFormat();
            String date = df.format("yyyy/MM/dd", Calendar.getInstance().getTime()).toString();
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
            uploadImage();
            ArticleCard articleCard = new ArticleCard(id, titleString, 0, 0, username, uid, location, date, bodyString, nImages);
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

    public void addImage(View view) {
        // choose image from gallery from phone
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("205", String.valueOf(resultCode));
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Log.d("205", String.valueOf(imageUri.size()));
            imageUri.add(data.getData());
        }
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();

        nImages = imageUri.size();
        for (int i = 0; i < imageUri.size(); i++) {
            StorageReference imageRef = storageReference.child("images/" + id + "/" + i);
            imageRef.putFile(imageUri.get(i))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();
                            Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed To Upload", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            pd.setMessage("Percentage: " + (int) progressPercent + "%");
                        }
                    });
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AlertDialog.Builder builder = new AlertDialog.Builder(writeArticle.this);
                builder.setMessage("Go back? All modifications will be discarded")
                        .setNegativeButton("CANCEL", null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                writeArticle.this.finish();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
