package com.android.visitour.ShowEvent;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.visitour.R;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by Miguel Aicrag on 9/5/2017.
 */

public class show_Event extends AppCompatActivity

{
    private DatabaseReference databaseReference;
    private TextView event,eventsday,eventsmonth,eventtime,eventend;
    private ImageView imageView, btncontact, btnweb;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_event);
        event = (TextView) findViewById(R.id.EVENTname);
        imageView = (ImageView) findViewById(R.id.estimages);
        eventsday = (TextView) findViewById(R.id.eventsday);
        eventsmonth = (TextView) findViewById(R.id.eventsmonth);
        eventtime = (TextView) findViewById(R.id.eventtime);
        eventend = (TextView) findViewById(R.id.eventend);
//
        Bundle bundle = getIntent().getExtras();
        String as = bundle.getString("eventname");
        String name = bundle.getString("uid");

        event.setText(as + " at ");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .8));
        get();
        startevent();
        loc();
        eventtime();
        eventend();

    }

    public void get() {
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("uid");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("group_event");

        databaseReference.child(name).child("id").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String id = dataSnapshot.getValue(String.class);

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("image");

                databaseReference.child(id).child("uri").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String value = dataSnapshot.getValue(String.class);
//
//                        Toast.makeText(getApplicationContext(),id+" "+value,Toast.LENGTH_LONG ).show();

                        if(value!=null)
                        {
                            Glide.with(getApplicationContext()).load(value).into(imageView);
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void startevent()
{
    try {
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("uid");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("group_event");
        databaseReference.child(name).child("eventstart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String value = dataSnapshot.getValue(String.class);


                String loca = value;
//                loca = loca.replaceAll("","");
                String[] parts = loca.split("(,)");

                String part1 = parts[0];
                String part2 = parts[1];

                String[] par = part1.split("(\\s+)");
                String month = par[0];
                String day = par[1];
                String s = "";


                if(month.equals("September"))
                {
                     s = month.substring(0, 4) + ".";
                    
                }
                else if(month.equals("June") || month.equals("July"))
                {
                    s = month;
                }
                else
                {
                    s = month.substring(0, 3) + ".";
                }

                eventsday.setText(day);
                eventsmonth.setText(s);
              

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    catch (ArrayIndexOutOfBoundsException e)
    {
        e.printStackTrace();
    }

}

    public void loc()
    {
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("uid");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("group_event");
        databaseReference.child(name).child("location").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String value = dataSnapshot.getValue(String.class);

                String eve = event.getText().toString();
                    event.setText(eve+" "+value);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void eventtime()
    {
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("uid");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("group_event");
        databaseReference.child(name).child("starttime").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String value = dataSnapshot.getValue(String.class);

                eventtime.setText("Start: "+value );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void eventend()
    {
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("uid");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("group_event");
        databaseReference.child(name).child("endtime").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String value = dataSnapshot.getValue(String.class);

                eventend.setText("End: "+value );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }





}

