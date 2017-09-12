package com.android.visitour.Company;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.visitour.CompanyFragment.CompanyMainActivity;
import com.android.visitour.R;
import com.android.visitour.data.EstablishmentRegister;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFormActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btnsubmit,btnclear;
    private EditText ESname,ESowner,Eslongt,phonenum,ESpostal,ESemail,Eadd;
    private Spinner EStype;
    ImageView ESplacepicker;
    private final int REQUEST_CODE_PLACEPICKER = 1;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    public static final String Database_path ="Establishment_Registration";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_path);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        btnsubmit = (Button)findViewById(R.id.btnsubmit);
        btnclear = (Button)findViewById(R.id.btnclear);
        ESplacepicker = (ImageView)findViewById(R.id.imgbtnplacpicker);
        EStype = (Spinner)findViewById(R.id.spnestype);
        Eadd = (EditText)findViewById(R.id.txtadd);
        ESemail = (EditText)findViewById(R.id.txtemailt);
        ESname = (EditText)findViewById(R.id.txtesname);
        Eslongt = (EditText)findViewById(R.id.txtlongt);
        ESowner = (EditText)findViewById(R.id.txtowname);
        ESpostal = (EditText)findViewById(R.id.txtpos);
        phonenum = (EditText)findViewById(R.id.txtphonet);
        ESemail.setText(user.getEmail());
        btnsubmit.setOnClickListener(this);
        ESplacepicker.setOnClickListener(this);
        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlacePickerActivity();
            }
        });

    }
    private void startPlacePickerActivity() {
        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();

        try {
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, REQUEST_CODE_PLACEPICKER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PLACEPICKER && resultCode == RESULT_OK) {
            displaySelectedPlaceFromPlacePicker(data);
        }
    }

    private void displaySelectedPlaceFromPlacePicker(Intent data) {
        Place placeSelected = PlacePicker.getPlace(data, this);

        String latlong = placeSelected.getLatLng().toString();
        String address = placeSelected.getAddress().toString();


        Eslongt.setText(latlong);
        Eadd.setText(address);

    }

    private void register()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        String postal =  ESpostal.getText().toString().trim();
        String type =  EStype.getSelectedItem().toString();
        String esname =  ESname.getText().toString().trim();
        String esowner =ESowner.getText().toString().trim();
        String eslat = Eslongt.getText().toString().trim();
        String phone =phonenum.getText().toString().trim();
        String add = Eadd.getText().toString().trim();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uploaduid  = user.getUid();
        String uploadid = databaseReference.push().getKey();

        if(!TextUtils.isEmpty(type) && !TextUtils.isEmpty(esname) && !TextUtils.isEmpty(esowner) && !TextUtils.isEmpty(eslat)&& !TextUtils.isEmpty(add) && !TextUtils.isEmpty(phone)  )
        {

//
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Database_path);

            EstablishmentRegister establishmentRegister = new EstablishmentRegister(type,esname,esowner,add,eslat,postal,user.getEmail(),phone,uploadid);
            databaseReference.child(uploaduid).child(uploadid).setValue(establishmentRegister);
            databaseReference.child("admin").child(uploadid).setValue(establishmentRegister);

            Toast.makeText(this,"Pasok", Toast.LENGTH_LONG).show();



        }
        else
        {
            Toast.makeText(this,"Please enter required Field", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onClick(View v)
    {


        if(v == btnsubmit)
        {
            register();
            startActivity(new Intent(this,CompanyMainActivity.class));

        }
        else if(v == ESplacepicker)
        {
            startPlacePickerActivity();

        }
    }
}
