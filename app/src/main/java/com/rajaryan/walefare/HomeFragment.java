package com.rajaryan.walefare;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.google.android.gms.maps.MapView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements LocationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ViewFlipper viewFlipper;
    MapView mapView;
    boolean permission;
    String Location;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    String lat;
    private Fragment fragment = null;
    String gc1;
    String fc1;
    String l1;
    String sc1;
    String tc1;
    String ffc1;
    FrameLayout l11;
    FrameLayout l22;
    String provider;
    protected String latitude, longitude;
    String name1;
    RoundedImageView t1,t2,t3;
    private CardView cardTop,cardRight,cardLeft,cardLeft2;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_home, container, false);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Main_Page_Header");


        // ini Animations

        Animation animeBottomToTop = AnimationUtils.loadAnimation(getActivity(),R.anim.anime_bottom_to_top);
        Animation animeTopToBottom = AnimationUtils.loadAnimation(getActivity(),R.anim.anime_top_to_bottom);
        Animation animeRightToleft = AnimationUtils.loadAnimation(getActivity(),R.anim.anime_right_to_left);
        Animation animeLeftToRight = AnimationUtils.loadAnimation(getActivity(),R.anim.anime_left_to_right);
        l11=v.findViewById(R.id.l11);
        l22=v.findViewById(R.id.l22);
        l22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissioncheck= ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS);
                if(permissioncheck== PackageManager.PERMISSION_GRANTED){
                    MessageSend();
                }
                else {
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.SEND_SMS},0);

                }
            }
        });
        l11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissioncheck= ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS);
                if(permissioncheck== PackageManager.PERMISSION_GRANTED){
                    MessageSendLocation();
                }
                else {
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.SEND_SMS},0);

                }
            }
        });
        // setup Animation :


        t2=v.findViewById(R.id.imageView2);
        t3=v.findViewById(R.id.imageView1);
        t1=v.findViewById(R.id.imageView3);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return v;
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onLocationChanged(@NonNull android.location.Location location) {
        Location="Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        databaseReference.child("Location").setValue("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void MessageSendLocation(){
        try{
            DatabaseReference databaseReference2= FirebaseDatabase.getInstance().getReference("Users");
            databaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds:snapshot.getChildren()){
                        if(ds.child("Id").getValue().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                            name1=""+ds.child("Name").getValue().toString();
                            String email1=""+ds.child("Email").getValue().toString();
                            String gn1=""+ds.child("Guardian's Name").getValue().toString();
                            gc1=""+ds.child("Guardians Number").getValue().toString();
                            fc1=""+ds.child("First Contact").getValue().toString();
                            sc1=""+ds.child("Second Contact").getValue().toString();
                            tc1=""+ds.child("Third Contact").getValue().toString();
                            ffc1=""+ds.child("Fourth Contact").getValue().toString();
                            l1=""+ds.child("Location").getValue().toString();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            Toast.makeText(getContext(),l1,Toast.LENGTH_LONG).show();
            android.telephony.SmsManager mSmsManager = android.telephony.SmsManager.getDefault();
            mSmsManager.sendTextMessage("+91"+gc1, null,"My Current Location Quadrants Are "+ l1, null, null);
            mSmsManager.sendTextMessage("+91"+fc1, null, "My Current Location Quadrants Are "+ l1, null, null);
            mSmsManager.sendTextMessage("+91"+sc1, null, "My Current Location Quadrants Are "+ l1, null, null);
            mSmsManager.sendTextMessage("+91"+tc1, null, "My Current Location Quadrants Are "+ l1, null, null);
            mSmsManager.sendTextMessage("+91"+ffc1, null, "My Current Location Quadrants Are "+ l1, null, null);
            Toast.makeText(getContext(), "Your SMS has sent successfully!", Toast.LENGTH_LONG).show();
        }

        catch(Exception e){
            Log.d("error", "MessageSend: "+e);
            Toast.makeText(getContext(), "Your SMS sent has failed!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        DatabaseReference databaseReference1= FirebaseDatabase.getInstance().getReference("Users");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    if(ds.child("Id").getValue().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void MessageSend(){
        try{
            DatabaseReference databaseReference2= FirebaseDatabase.getInstance().getReference("Users");
            databaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds:snapshot.getChildren()){
                        if(ds.child("Id").getValue().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                            name1=""+ds.child("Name").getValue().toString();
                            String email1=""+ds.child("Email").getValue().toString();
                            String gn1=""+ds.child("Guardian's Name").getValue().toString();
                            gc1=""+ds.child("Guardians Number").getValue().toString();
                            fc1=""+ds.child("First Contact").getValue().toString();
                            sc1=""+ds.child("Second Contact").getValue().toString();
                            tc1=""+ds.child("Third Contact").getValue().toString();
                            ffc1=""+ds.child("Fourth Contact").getValue().toString();
                            l1=""+ds.child("Location").getValue().toString();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            Toast.makeText(getContext(),l1,Toast.LENGTH_LONG).show();
            android.telephony.SmsManager mSmsManager = android.telephony.SmsManager.getDefault();
            mSmsManager.sendTextMessage("+91"+gc1, null,"Your Loved Ones"+name1+"Needs Your Help At Quadrants "+ l1, null, null);
            mSmsManager.sendTextMessage("+91"+fc1, null, "Your Loved Ones"+name1+"Needs Your Help At Quadrants "+ l1, null, null);
            mSmsManager.sendTextMessage("+91"+sc1, null, "Your Loved Ones"+name1+"Needs Your Help At Quadrants "+ l1, null, null);
            mSmsManager.sendTextMessage("+91"+tc1, null, "Your Loved Ones"+name1+"Needs Your Help At Quadrants "+ l1, null, null);
            mSmsManager.sendTextMessage("+91"+ffc1, null, "Your Loved Ones"+name1+"Needs Your Help At Quadrants "+ l1, null, null);
            Toast.makeText(getContext(), "Your SMS has sent successfully!", Toast.LENGTH_LONG).show();
        }

        catch(Exception e){
            Log.d("error", "MessageSend: "+e);
            Toast.makeText(getContext(), "Your SMS sent has failed!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
}
