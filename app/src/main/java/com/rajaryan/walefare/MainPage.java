package com.rajaryan.walefare;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.seismic.ShakeDetector;

import java.util.ArrayList;

public class MainPage extends AppCompatActivity  implements LocationListener, ShakeDetector.Listener {


    boolean permission;
    String Location;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    String lat;
    private Fragment fragment = null;
    String name1;
    String gc1;
    String fc1;
    String sc1;
    String tc1;
    String ffc1;
    String provider;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;
    private ChipNavigationBar chipNavigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_page);
        Fragment f = new Details();
        chipNavigationBar = findViewById(R.id.chip);

        chipNavigationBar.setItemSelected(R.id.home, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.favorites:
                        fragment = new Details();
                        break;
                }
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                }
            }
        });
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, new HomeFragment());
        ft.commit();
        if(TextUtils.isEmpty(name1)){
            Toast.makeText(MainPage.this,"Check Details First",Toast.LENGTH_LONG).show();
            chipNavigationBar.setItemSelected(R.id.favorites, true);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new Details()).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Query query=FirebaseDatabase.getInstance().getReference("Users").orderByChild("Id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    name1=""+ds.child("Name").getValue().toString();
                    String email1=""+ds.child("Email").getValue().toString();
                    String gn1=""+ds.child("Guardian's Name").getValue().toString();
                    gc1=""+ds.child("Guardians Number").getValue().toString();
                    fc1=""+ds.child("First Contact").getValue().toString();
                    sc1=""+ds.child("Second Contact").getValue().toString();
                    tc1=""+ds.child("Third Contact").getValue().toString();
                    ffc1=""+ds.child("Fourth Contact").getValue().toString();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        txtLat = (TextView) findViewById(R.id.textview1);
        txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        Location="Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        databaseReference.child("Location").setValue("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Log.d("Latitude","disable");
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void hearShake() {
        Toast.makeText(MainPage.this,"Shake Heard",Toast.LENGTH_LONG).show();
        View view1= LayoutInflater.from(getApplicationContext()).inflate(R.layout.main_page_alert_dialog, (LinearLayout)findViewById(R.id.conta));
        final android.app.AlertDialog dialog=new android.app.AlertDialog.Builder(MainPage.this).setView(view1).create();
        LinearLayout l1=view1.findViewById(R.id.l1);
        Button btn=view1.findViewById(R.id.generate_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissioncheck= ContextCompat.checkSelfPermission(MainPage.this,Manifest.permission.SEND_SMS);
                if(permissioncheck==PackageManager.PERMISSION_GRANTED){
                    MessageSend();
                }
                else {
                    ActivityCompat.requestPermissions(MainPage.this,new String[]{Manifest.permission.SEND_SMS},0);

                }
            }
        });
        dialog.show();


    }
    public void MessageSend(){
        SmsManager smsManager=SmsManager.getDefault();
        Toast.makeText(MainPage.this,gc1,Toast.LENGTH_LONG).show();
        smsManager.sendTextMessage(gc1,null,Location,null,null);
        Toast.makeText(MainPage.this,"Sent",Toast.LENGTH_LONG).show();
    }
}