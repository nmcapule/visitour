package com.android.visitour.inquiry;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.visitour.BasicInfo;
import com.android.visitour.Map.MapsActivity;
import com.android.visitour.R;
import com.android.visitour.Reservation.ReservationActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class inquiry extends AppCompatActivity implements View.OnClickListener {


    TextView name,add;
    FloatingActionButton select;
    ImageView resev,img;
    private EditText estadd,estcon,estweb;
    LinearLayout not,show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_inquiry);
            select = (FloatingActionButton) findViewById(R.id.floatselect);
            resev = (ImageView) findViewById(R.id.revervation);
            img = (ImageView) findViewById(R.id.imgview);
            not = (LinearLayout) findViewById(R.id.layoutnot);
            show = (LinearLayout) findViewById(R.id.layoutshow);
            estadd = (EditText) findViewById(R.id.estadd);
            estcon = (EditText) findViewById(R.id.estcontact);
            estweb = (EditText) findViewById(R.id.estweb);

            Bundle bundle = getIntent().getExtras();
            final String as = bundle.getString("set");

            final String lati = bundle.getString("lat");
            final String id = bundle.getString("id");
            String con = bundle.getString("con");
            String add = bundle.getString("add");
            String web = bundle.getString("web");

            if(id.equals("1"))
            {

            }
            else
            {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("registered");
                databaseReference.child(id).child("establishment_reserv").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String value = dataSnapshot.getValue(String.class);

                        if(value.equals("No"))
                        {

                        }
                        else
                        {
                            resev.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getApplication(), ReservationActivity.class);
                                    intent.putExtra("set", as);
                                    intent.putExtra("id", id);
                                    intent.putExtra("lati", lati);
                                    startActivity(intent);
                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
            if (id.equals("1")) {
                not.setVisibility(View.VISIBLE);
                show.setVisibility(View.GONE);
            } else {
                show.setVisibility(View.VISIBLE);
                not.setVisibility(View.GONE);
                estadd.setText(add);
                estcon.setText(con);
                estweb.setText(web);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("image");

                databaseReference.child(id).child("uri").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String value = dataSnapshot.getValue(String.class);
//
                        if(value!=null)
                        {
                            Glide.with(getApplicationContext()).load(value).into(img);
                        }



                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }


//        Toast.makeText(this,id,Toast.LENGTH_LONG).show();

            name = (TextView) findViewById(R.id.txtnameas);
            name.setText(as);



            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(getApplication(), MapsActivity.class);
                    intent.putExtra("set", as);
                    startActivity(intent);

                }
            });
//        setinfo();


    }


//    public void setinfo()
//    {
//        Bundle bundle = getIntent().getExtras();
//        String as = bundle.getString("set");
//        SharedPreferences sharedPreferences = getSharedPreferences("event", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("name",as);
//        editor.apply();
//
//    }


    public void shows(View view)
    {


        Bundle bundle = getIntent().getExtras();
        String as = bundle.getString("set");
        String aw = bundle.getString("lat");
        String id = bundle.getString("id");

        Intent intent=new Intent(getApplication(), BasicInfo.class);
        intent.putExtra("set",as);
        intent.putExtra("grouname","Select");
        intent.putExtra("groupid","");
        intent.putExtra("lati",aw);
        intent.putExtra("id",id);

        startActivity(intent);


    }



    @Override
    public void onClick(View v) {




    }
}
