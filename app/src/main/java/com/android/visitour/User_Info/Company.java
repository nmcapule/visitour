package com.android.visitour.User_Info;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.visitour.R;
import com.android.visitour.data.StaticConfig;
import com.android.visitour.model.Companydata;
import com.google.firebase.database.FirebaseDatabase;


public class Company extends Fragment implements View.OnClickListener {

    EditText txtcomn, txtcomo;
    Button submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company, container, false);

        txtcomn = (EditText) view.findViewById(R.id.txtcomname);
        txtcomo = (EditText) view.findViewById(R.id.txtcomowner);
        submit = (Button) view.findViewById(R.id.btnsubmitc);

        final String id = this.getArguments().getString("UID").toString();
        final String email = this.getArguments().getString("EMAIL").toString();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String companyname = txtcomn.getText().toString();
                String companyowner = txtcomo.getText().toString();
                String type = "Company";

                Companydata newUser = new Companydata();

                newUser.email = email;
                newUser.avata = StaticConfig.STR_DEFAULT_BASE64;

                newUser.usertype = type;
                newUser.companyname =companyname;
                newUser.companyowner =companyowner;

                FirebaseDatabase.getInstance().getReference().child("user/"+id).setValue(newUser);

//                startActivity(new Intent(getActivity(), CompanyMainActivity.class));

            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
