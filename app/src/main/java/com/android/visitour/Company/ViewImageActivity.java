package com.android.visitour.Company;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.visitour.R;
import com.android.visitour.data.Image;
import com.android.visitour.data.Imagelist;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewImageActivity extends AppCompatActivity implements  View.OnClickListener {


    EditText txtcomn, txtcomo;
    Button submit;
    DatabaseReference databaseReference;
    ListView listView;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    List<Image> userlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_view_image );


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

       Bundle bundle = getIntent().getExtras();
        String uID = bundle.getString("id");
        databaseReference = FirebaseDatabase.getInstance().getReference("image").child(user.getUid()).child(uID);
        listView = (ListView)findViewById(R.id.listviewlayout);
        userlist = new ArrayList<>();

    }
    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                userlist.clear();
                for (DataSnapshot artistSnaphot : dataSnapshot.getChildren()) {
                    Image artist = artistSnaphot.getValue(Image.class);
                    userlist.add(artist);
                }

                Imagelist adapter = new Imagelist(ViewImageActivity.this, userlist);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
