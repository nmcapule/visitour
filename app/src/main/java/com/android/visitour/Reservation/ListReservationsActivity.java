package com.android.visitour.Reservation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.visitour.R;
import com.android.visitour.data.StaticConfig;
import com.android.visitour.model.Reservation;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Arvin on 9/16/2017.
 */

public class ListReservationsActivity extends AppCompatActivity {
    private static final String TAG = "ListReservationsActivity";

    private RecyclerView recyclerView;
    private String idGroup;
    private String idCompany;
    private String establName;
    private ListReservationsAdapter adapter;
    private ArrayList<Reservation> reservationArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reservations);

        Intent intent = getIntent();
        idGroup = intent.getStringExtra(StaticConfig.INTENT_KEY_CHAT_ROOM_ID);
        idCompany = intent.getStringExtra(StaticConfig.INTENT_KEY_ESTABLISHMENT_ID);
        establName = intent.getStringExtra(StaticConfig.INTENT_KEY_ESTABLISHMENT);

        adapter = new ListReservationsAdapter(this, reservationArray);
        FirebaseDatabase.getInstance().getReference().child("reservations/" + idCompany).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue() != null) {
                    HashMap mapReservation = (HashMap) dataSnapshot.getValue();
                    Reservation reservation = new Reservation(idGroup, idCompany);
                    reservation.arrival = (long) mapReservation.get("arrival");
                    reservation.departure = (long) mapReservation.get("departure");
                    reservation.approved = (boolean) mapReservation.get("approved");
                    reservation.guests = (int) mapReservation.get("guests");
                    reservation.type = (int) mapReservation.get("type");

                    reservationArray.add(reservation);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerReservation);
        recyclerView.setAdapter(adapter);
    }
}

class ListReservationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Context context;
    public ArrayList<Reservation> reservationArray;

    public ListReservationsAdapter(Context context, ArrayList<Reservation> reservationArray) {
        this.context = context;
        this.reservationArray = reservationArray;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rc_item_reservation, parent);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Reservation reservation = reservationArray.get(position);
        ((ItemHolder) holder).txtTitle.setText(reservation.guestName);

        String status = "Pending";
        if (reservation.approved) {
            status = "Approved";
        }
        ((ItemHolder) holder).txtStatus.setText(status);

        String arrivalStr = "NA";
        String departureStr = "NA";

        if (reservation.arrival != 0) {
            Date arrival = new java.util.Date((long) reservation.arrival*1000);

            Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            arrivalStr = formatter.format(arrival);
        }

        if (reservation.departure != 0) {
            Date departure = new java.util.Date((long) reservation.departure*1000);

            Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            departureStr = formatter.format(departure);
        }

        String summary = "No summary available";
        if (reservation.type == Reservation.RESERVATION_TYPE_HOTEL) {
            summary = "" + reservation.guests + "pax. Start: " + arrivalStr + ". End: " + departureStr;
        }
        if (reservation.type == Reservation.RESERVATION_TYPE_RESTAURANT) {
            summary = "" + reservation.guests + "pax. Start: " + arrivalStr + ". End: " + departureStr;
        }
        ((ItemHolder) holder).txtSummary.setText(summary);
    }

    @Override
    public int getItemCount() {
        return reservationArray.size();
    }
}

class ItemHolder extends RecyclerView.ViewHolder {
    public TextView txtTitle;
    public TextView txtSummary;
    public TextView txtStatus;

    public ItemHolder(View itemView) {
        super(itemView);
        txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);
        txtSummary = (TextView) itemView.findViewById(R.id.txtSummary);
    }
}