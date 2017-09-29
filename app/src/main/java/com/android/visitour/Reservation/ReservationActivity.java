package com.android.visitour.Reservation;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.visitour.MainActivity;
import com.android.visitour.R;
import com.android.visitour.model.Reservation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * TODO: ken dalbahjan
 */
public class ReservationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "ReservationActivity";
    private DatabaseReference databaseReference;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button reserve;
    private EditText date,time,people,name,email,phone,note;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    Calendar calendar = Calendar.getInstance();
    Calendar time1 = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        date = (EditText) findViewById(R.id.editdate);
        time = (EditText) findViewById(R.id.edittime);
        people = (EditText) findViewById(R.id.editperson);
        name = (EditText) findViewById(R.id.editName);
        email = (EditText) findViewById(R.id.editEmail);
        phone = (EditText) findViewById(R.id.editPhone);
        note = (EditText) findViewById(R.id.editNote);
        reserve = (Button) findViewById(R.id.btnreserv);

        SimpleDateFormat sdf1 = new SimpleDateFormat("MMM-dd-yyyy"); //Date and time
        String currentDate = sdf1.format(calendar.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);

        date.setText(currentDate);
        date.setFocusable(false);
        date.setClickable(true);

        time.setFocusable(false);
        time.setClickable(true);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedate();
            }
        });

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = getIntent().getExtras();
                String Name = bundle.getString("set");
                String lati = bundle.getString("lati");
                String id = bundle.getString("id");




                Reservation reserv = new Reservation();


                    reserv.date = date.getText().toString().trim();
                    reserv.time = time.getText().toString().trim();
                    reserv.people = people.getText().toString().trim();
                    reserv.name = name.getText().toString().trim();
                    reserv.email = email.getText().toString().trim();
                    reserv.phone = phone.getText().toString().trim();
                    reserv.note = note.getText().toString().trim();
                    reserv.user_id = user.getUid();
                    reserv.establishment_id = id;
                    reserv.establishment_loc = lati;
                    reserv.establishment_name = Name;


                FirebaseDatabase.getInstance().getReference().child("reservation/"+user.getUid()).setValue(reserv);
                Toast.makeText(getApplication(),"Successfully !"+lati,Toast.LENGTH_LONG).show();

                startActivity(new Intent(ReservationActivity.this, MainActivity.class));


            }
        });


    }

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            try {


                time1.set(Calendar.HOUR_OF_DAY, hourOfDay);
                time1.set(Calendar.MINUTE, minute);


                String myFormat = "hh:mm a"; // your own format
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                String formated_time = sdf.format(time1.getTime()); //format your time
                time.setText(formated_time);
                SharedPreferences sharedPreferences = getSharedPreferences("event", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("starttime", formated_time);
                editor.apply();


            } catch (Exception ex) {

            }
        }

    };

    private void updatedate() {

        try
        {


            new TimePickerDialog(this, t, time1.get(Calendar.HOUR_OF_DAY), time1.get(Calendar.MINUTE), true).show();

        }
        catch (Exception ex)
        {

        }
    }

    public void pickdate(View view) {
        ReservationActivity.DatePickerFragment fragment = new ReservationActivity.DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "date");


    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        try
        {

            Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
            setDate(cal);

        }
        catch (Exception ex)
        {

        }

    }
    private void setDate(final Calendar calender) {

        try
        {


            final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);

                date.setText(dateFormat.format(calender.getTime()));
                SharedPreferences sharedPreferences = getSharedPreferences("event", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("startevent", dateFormat.format(calender.getTime()));
                editor.apply();


        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Toast.makeText(getApplication(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }


    }

    public static class DatePickerFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {


            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            return datePickerDialog;


        }

        }

}