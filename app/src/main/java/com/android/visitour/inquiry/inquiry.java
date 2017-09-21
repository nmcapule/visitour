package com.android.visitour.inquiry;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.visitour.BasicInfo;
import com.android.visitour.Map.MapsActivity;
import com.android.visitour.R;

public class inquiry extends AppCompatActivity implements View.OnClickListener {


    TextView name,add;
    FloatingActionButton select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);
        select = (FloatingActionButton)findViewById(R.id.floatselect);


        Bundle bundle = getIntent().getExtras();
        final String as = bundle.getString("set");
        String addr = bundle.getString("add");
        String lati = bundle.getString("lat");

//        SharedPreferences sharedPreferences = getSharedPreferences("event", Context.MODE_PRIVATE);
//
//        String starte  =   sharedPreferences.getString("name",as);


        name = (TextView)findViewById(R.id.txtnameas);
        add = (TextView)findViewById(R.id.txtadd);
        name.setText(as);
        add.setText(addr);

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(getApplication(), MapsActivity.class);
                intent.putExtra("set",as);
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
//        String aw = bundle.getString("lati");

        Intent intent=new Intent(getApplication(), BasicInfo.class);
        intent.putExtra("set",as);
        intent.putExtra("grouname","Select");
        intent.putExtra("groupid","");

        startActivity(intent);


    }



    @Override
    public void onClick(View v) {




    }
}
