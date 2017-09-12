package com.android.visitour.CompanyFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.visitour.R;

public class CompanyMainActivity extends AppCompatActivity {

    private TextView mTextMessage;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener()
    {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            FragmentManager fragmentmanager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentmanager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.home:
                    transaction.replace(R.id.content,new HomeFragment()).commit();
                    return true;
                case R.id.registration:
                    transaction.replace(R.id.content,new RegisterFragment()).commit();
                    return true;
                case R.id.company_account:
                    transaction.replace(R.id.content,new CompanyFragment()).commit();
                    return true;
                case R.id.notification:
                    transaction.replace(R.id.content,new RegisterFragment()).commit();
                    return true;


            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_main);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.home);




    }

}
