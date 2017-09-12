package com.android.visitour.Company;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.visitour.R;
import com.android.visitour.data.EstablishmentRegister;
import com.android.visitour.data.Retrivelist;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PendingActivity extends Fragment implements View.OnClickListener {

    EditText txtcomn, txtcomo;
    Button submit;
    DatabaseReference databaseReference;
    ListView listView;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    List<EstablishmentRegister> userlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

//        String id=this.getArguments().getString("UID").toString();

        databaseReference = FirebaseDatabase.getInstance().getReference("Establishment_Registration").child(user.getUid());
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
                userlist.clear();
                for (DataSnapshot artistSnaphot : dataSnapshot.getChildren()) {
                    EstablishmentRegister artist = artistSnaphot.getValue(EstablishmentRegister.class);
                    userlist.add(artist);
                }

                Retrivelist adapter = new Retrivelist((Activity) PendingActivity.this.getContext(), userlist);
                listView.setAdapter(adapter);

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
