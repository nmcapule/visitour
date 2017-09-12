package com.android.visitour;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.visitour.data.Event;
import com.android.visitour.inquiry.inquiry;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


/**
 * Created by Miguel Aicrag on 9/5/2017.
 */

public class BasicInfo extends AppCompatActivity implements DatePickerDialog.OnDateSetListener

{
    private DatabaseReference databaseReference;
    DateFormat formatDateTime = DateFormat.getDateTimeInstance();
    Calendar time = Calendar.getInstance();
    TextView name, timestart, timeend, start, end, locname, aler, select,confirm;
    EditText eventname;
    Calendar calendar = Calendar.getInstance();
    String x = "";
    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            time.set(Calendar.HOUR_OF_DAY, hourOfDay);
            time.set(Calendar.MINUTE, minute);
            if (x.equals("a")) {

//            timestart.setText(hourOfDay+":"+minute);

                String myFormat = "hh:mm a"; // your own format
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                String formated_time = sdf.format(time.getTime()); //format your time
                timestart.setText(formated_time);
                SharedPreferences sharedPreferences = getSharedPreferences("event", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("starttime", formated_time);
                editor.apply();


            } else if (x.equals("b")) {

                String myFormat = "hh:mm a"; // your own format
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                String formated_time = sdf.format(time.getTime()); //format your time
                timeend.setText(formated_time);
                SharedPreferences sharedPreferences = getSharedPreferences("event", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("endtime", formated_time);
                editor.apply();
            } else if (x.equals("c")) {

                String myFormat = "hh:mm a"; // your own format
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                String formated_time = sdf.format(time.getTime()); //format your time
                String datet = aler.getText().toString();
                aler.setText(datet + " - " + formated_time);
                SharedPreferences sharedPreferences = getSharedPreferences("event", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("alert", datet + " - " + formated_time);
                editor.apply();

            }
        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basicpopup);


        Bundle bundle = getIntent().getExtras();
        String as = bundle.getString("set");
        String gname = bundle.getString("grouname");
        String gid = bundle.getString("groupid");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        SimpleDateFormat sdf1 = new SimpleDateFormat("MMM-dd-yyyy"); //Date and time
        String currentDate = sdf1.format(calendar.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .8));

        SharedPreferences sharedPreferences = getSharedPreferences("event", Context.MODE_PRIVATE);

        String starte = sharedPreferences.getString("startevent", dayOfTheWeek + ", " + currentDate);
        String endevent = sharedPreferences.getString("endevent", dayOfTheWeek + ", " + currentDate);
        String alert = sharedPreferences.getString("alert", dayOfTheWeek + ", " + currentDate);
        String starttime = sharedPreferences.getString("starttime", "1:00");
        String endtime = sharedPreferences.getString("endtime", "1:00");
        String s = as.substring(0, 15) + "...";
        setinfo();
        String lo = sharedPreferences.getString("loc", s);


        locname = (TextView) findViewById(R.id.txtlocname);
        aler = (TextView) findViewById(R.id.txtseralert);
        name = (TextView) findViewById(R.id.txtnameas);
        eventname = (EditText) findViewById(R.id.editname);
        confirm = (TextView) findViewById(R.id.txtconfirm);
        select = (TextView) findViewById(R.id.txtselectgroup);
        start = (TextView) findViewById(R.id.txtstartevent);
        end = (TextView) findViewById(R.id.txtendevent);
        timestart = (TextView) findViewById(R.id.txttimestart);
        timeend = (TextView) findViewById(R.id.txttimeend);
        timestart.setText(starttime);
        timeend.setText(endtime);
        start.setText(starte);
        end.setText(endevent);
        aler.setText(alert);
        select.setText(gname);

        locname.setText(lo);
        locname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = getIntent().getExtras();
                String as = bundle.getString("set");
                setinfo();
                Toast.makeText(getApplication(), as, Toast.LENGTH_LONG).show();

            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = getIntent().getExtras();
                String as = bundle.getString("set");

                Intent intent = new Intent(getApplication(), SelectGroup.class);
                intent.putExtra("set", as);
                startActivity(intent);

            }
        });

        timestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedate();
            }
        });

        timeend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedateend();

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Bundle bundle = getIntent().getExtras();
                String gid = bundle.getString("groupid");
                String as = bundle.getString("set");
                Event addevent = new Event();
                addevent.eventname = eventname.getText().toString();
                addevent.eventstart = start.getText().toString();
                addevent.eventend = end.getText().toString();
                addevent.starttime = timestart.getText().toString();
                addevent.endtime = timeend.getText().toString();
                addevent.alert = aler.getText().toString();
                addevent.id = gid;
                addevent.groupname = select.getText().toString();
                addevent.location = as;
                databaseReference = FirebaseDatabase.getInstance().getReference("group_event");

                String uploadid = databaseReference.push().getKey();

                FirebaseDatabase.getInstance().getReference().child("group_event/"+uploadid).setValue(addevent);

            }
        });

    }

    public void clear(View view)
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("MMM-dd-yyyy"); //Date and time
        String currentDate = sdf1.format(calendar.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        SharedPreferences sharedPreferences = getSharedPreferences("event", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("startevent",dayOfTheWeek+" "+currentDate);
        editor.putString("endevent",dayOfTheWeek+" "+currentDate);
        editor.putString("alert",dayOfTheWeek+" "+currentDate);
        editor.putString("starttime","1:00");
        editor.putString("endtime","1:00");
        timestart.setText("1:00");
        timeend.setText("1:00");
        start.setText(dayOfTheWeek+" "+currentDate);
        end.setText(dayOfTheWeek+" "+currentDate);
        aler.setText(dayOfTheWeek+" "+currentDate);
        select.setText("Select");
        editor.apply();

        Bundle bundle = getIntent().getExtras();
        String as = bundle.getString("set");

        Intent intent = new Intent(getApplication(), inquiry.class);
        intent.putExtra("set", as);
        startActivity(intent);
    }

    public void pickdate(View view) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "date");
        x = "a";


    }

    public void pickdateend(View view) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "date");
        x = "b";


    }

    public void alert(View view) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "date");
        alerttime();
        x = "c";


    }

    public void setinfo() {
        Bundle bundle = getIntent().getExtras();
        String as = bundle.getString("set");
        String s = as.substring(0, 15) + "...";
        SharedPreferences sharedPreferences = getSharedPreferences("event", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("loc", s);
        editor.apply();

    }

    private void setDate(final Calendar calender) {

        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
//
        if (x.equals("a")) {

            start.setText(dateFormat.format(calender.getTime()));
            SharedPreferences sharedPreferences = getSharedPreferences("event", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("startevent", dateFormat.format(calender.getTime()));
            editor.apply();


        } else if (x.equals("b")) {
            end.setText(dateFormat.format(calender.getTime()));
            SharedPreferences sharedPreferences = getSharedPreferences("event", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("endevent", dateFormat.format(calender.getTime()));
            editor.apply();

        } else if (x.equals("c")) {
            aler.setText(dateFormat.format(calender.getTime()));

        }


    }

    private void alerttime() {
        new TimePickerDialog(this, t, time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), true).show();
        x = "c";
    }

    private void updatedate() {
        new TimePickerDialog(this, t, time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), true).show();
        x = "a";
    }

    private void updatedateend() {
        new TimePickerDialog(this, t, time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), true).show();
        x = "b";
    }

    private void setDate1(final Calendar calender) {

        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
//

        end.setText(dateFormat.format(calender.getTime()));

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        setDate(cal);
        setDate1(cal);
    }


    public static class DatePickerFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
        }
    }


}

