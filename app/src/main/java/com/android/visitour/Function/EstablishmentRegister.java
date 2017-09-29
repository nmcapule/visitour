package com.android.visitour.Function;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.visitour.R;
import com.android.visitour.User_Info.Regular;

public class EstablishmentRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment_register);

        Registered frag = new Registered();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction .setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.infolayout,frag);
        fragmentTransaction.commit();
    }
}
