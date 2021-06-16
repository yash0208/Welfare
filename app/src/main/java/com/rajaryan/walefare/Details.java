package com.rajaryan.walefare;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Details#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class Details extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText name,email,gn,gc,fc,sc,tc,ffc;
    LinearLayout l,l1;
    DatabaseReference firebasedatabase,databaseReference;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Details.
     */
    // TODO: Rename and change types and number of parameters
    public static Details newInstance(String param1, String param2) {
        Details fragment = new Details();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public Details() {
        // Required empty public constructor
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
        View view=inflater.inflate(R.layout.fragment_details, container, false);
        name=view.findViewById(R.id.name);
        email=view.findViewById(R.id.email);
        gn=view.findViewById(R.id.parent);
        gc=view.findViewById(R.id.pc);
        fc=view.findViewById(R.id.fc);
        sc=view.findViewById(R.id.sc);
        tc=view.findViewById(R.id.tc);
        ffc=view.findViewById(R.id.ffc);
        l=view.findViewById(R.id.linear);
        l1=view.findViewById(R.id.linear1);
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i=new Intent(getActivity(),MainActivity.class);
            }
        });
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name1=name.getText().toString();
                final String email1=email.getText().toString();
                final String gn1=gn.getText().toString();
                final String gc1=gc.getText().toString();
                final String fc1=fc.getText().toString();
                final String sc1=sc.getText().toString();
                final String tc1=tc.getText().toString();
                final String ffc1=ffc.getText().toString();

                if(TextUtils.isEmpty(name1)){
                    Toast.makeText(getContext(),"Enter Valid Name",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(email1)){
                    Toast.makeText(getContext(),"Enter Valid Name",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(gn1)){
                    Toast.makeText(getContext(),"Enter Valid Name",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(gc1)){
                    Toast.makeText(getContext(),"Enter Valid Name",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(fc1)){
                    Toast.makeText(getContext(),"Enter Valid Name",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(sc1)){
                    Toast.makeText(getContext(),"Enter Valid Name",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(tc1)){
                    Toast.makeText(getContext(),"Enter Valid Name",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(ffc1)){
                    Toast.makeText(getContext(),"Enter Valid Name",Toast.LENGTH_SHORT).show();
                }
                else {
                    firebasedatabase= FirebaseDatabase.getInstance().getReference("Users");
                    firebasedatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            HashMap<String, String> hashMap=new HashMap<>();
                            hashMap.put("Id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            hashMap.put("Name",name1);
                            hashMap.put("Email",email1);
                            hashMap.put("Guardian's Name",gn1);
                            hashMap.put("Guardians Number",gc1);
                            hashMap.put("First Contact",fc1);
                            hashMap.put("Second Contact",sc1);
                            hashMap.put("Third Contact",tc1);
                            hashMap.put("Fourth Contact",ffc1);
                            firebasedatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(hashMap);
                            Toast.makeText(getContext(),"Data Changed Successfully",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });                }
            }
        });

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        Query query=FirebaseDatabase.getInstance().getReference("Users").orderByChild("Id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    String name1=""+ds.child("Name").getValue().toString();
                    String email1=""+ds.child("Email").getValue().toString();
                    String gn1=""+ds.child("Guardian's Name").getValue().toString();
                    String gc1=""+ds.child("Guardians Number").getValue().toString();
                    String fc1=""+ds.child("First Contact").getValue().toString();
                    String sc1=""+ds.child("Second Contact").getValue().toString();
                    String tc1=""+ds.child("Third Contact").getValue().toString();
                    String ffc1=""+ds.child("Fourth Contact").getValue().toString();
                    name.setHint(name1);
                    email.setHint(email1);
                    gn.setHint(gn1);
                    gc.setHint(gc1);
                    fc.setHint(fc1);
                    sc.setHint(sc1);
                    tc.setHint(tc1);
                    ffc.setHint(ffc1);
                    break;
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
