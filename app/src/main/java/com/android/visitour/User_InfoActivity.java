package com.android.visitour;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.android.visitour.User_Info.Company;
import com.android.visitour.User_Info.Regular;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class User_InfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner usertype;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__info);
        usertype =(Spinner)findViewById(R.id.spnusertype);
        usertype.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

    }
    private void senddata()
    {
        Bundle bundle = new Bundle();
        bundle.putString("UID",user.getUid());
        bundle.putString("EMAIL",user.getEmail());

        Regular frag = new Regular();
        frag.setArguments(bundle);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction .setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.infolayout,frag);
        fragmentTransaction.commit();
    }
    private void senddataC()
    {
        Bundle bundle = new Bundle();
        bundle.putString("UID",user.getUid());
        bundle.putString("EMAIL",user.getEmail());

        Company frag = new Company();
        frag.setArguments(bundle);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction .setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.infolayout,frag);
        fragmentTransaction.commit();
    }


//    public  void setfragment(Fragment fragment)
//    {
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = manager.beginTransaction();
//        fragmentTransaction .setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
//        fragmentTransaction.replace(R.id.infolayout,fragment);
//        fragmentTransaction.commit();
//
//    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {

        String item = adapterView.getItemAtPosition(i).toString();

        if(item.equals("Regular"))
        {
            senddata();
        }
        else if(item.equals("Company"))
        {

            senddataC();
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
