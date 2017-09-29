package com.android.visitour;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.android.visitour.Function.SelectGroupFragment;

/**
 * Created by Miguel Aicrag on 9/10/2017.
 */

public class SelectGroup extends AppCompatActivity
{
    protected void onCreate(@Nullable Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectgroup);
        Bundle bundle = getIntent().getExtras();
        String as = bundle.getString("set");
        String id1 = bundle.getString("id");


        bundle.putString("set",as);
        bundle.putString("id",id1);


        SelectGroupFragment frag = new SelectGroupFragment();
        frag.setArguments(bundle);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction .setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.framelay,frag);
        fragmentTransaction.commit();


    }
}
