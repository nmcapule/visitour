package com.android.visitour.Company;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.visitour.R;
import com.android.visitour.data.Approvedlist;
import com.android.visitour.data.EstablishmentRegister;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ApprovedActivity extends Fragment implements View.OnClickListener {

    EditText txtcomn, txtcomo;
    Button submit;
    DatabaseReference databaseReference;
    ListView listView;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    List<EstablishmentRegister> userlist;
    String x = "z";
    String uid = "";
    TextView check;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_pending, container, false);
        check = (TextView)view.findViewById(R.id.check);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

//        String id=this.getArguments().getString("UID").toString();
        databaseReference = FirebaseDatabase.getInstance().getReference("registered");
        listView = (ListView)view.findViewById(R.id.listviewlayout);
        userlist = new ArrayList<>();

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    userlist.clear();

                    for (DataSnapshot artistSnaphot : dataSnapshot.getChildren()) {


                        EstablishmentRegister artist = artistSnaphot.getValue(EstablishmentRegister.class);
                        uid = artistSnaphot.getKey();
//                    Toast.makeText(getContext(),artistSnaphot.getKey(),Toast.LENGTH_LONG).show();
                        checking();
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("event", Context.MODE_PRIVATE);

                        String starte = sharedPreferences.getString("chec", "");

                        if (starte.equals("a")) {
                            userlist.add(artist);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("chec", "a");
                            editor.apply();

                        }
                    }

                    Approvedlist adapter = new Approvedlist((Activity) ApprovedActivity.this.getContext(), userlist);
                    listView.setAdapter(adapter);

                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void checking()
    {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("registered");

        databaseReference.child(uid).child("establishment_email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    final String value = dataSnapshot.getValue(String.class);
//                Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    if (value.equals(user.getEmail())) {

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("event", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("chec", "a");
                        editor.apply();

                    }

//                Toast.makeText(getActivity(),user.getEmail(),Toast.LENGTH_LONG).show();
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }
            }




            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onClick(View v) {

    }
}
