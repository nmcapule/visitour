package com.android.visitour.User_Info;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.visitour.MainActivity;
import com.android.visitour.R;
import com.android.visitour.data.StaticConfig;
import com.android.visitour.model.User;
import com.google.firebase.database.FirebaseDatabase;


public class Regular extends Fragment implements View.OnClickListener{

    EditText txtlastn,txtmiddlen,txtfname;
    Button submit;

    private boolean firstTimeAccess;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view =  inflater.inflate(R.layout.fragment_regular, container, false);

        txtlastn = (EditText)view.findViewById(R.id.txtlastname);
        txtfname = (EditText)view.findViewById(R.id.txtfname);
        txtmiddlen = (EditText)view.findViewById(R.id.txtmiddlename);

        submit =(Button)view.findViewById(R.id.btnsubmit);
        final String id=this.getArguments().getString("UID").toString();
        final String email=this.getArguments().getString("EMAIL").toString();
//        Toast.makeText(getActivity(),id,Toast.LENGTH_LONG).show();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                String lastn = txtlastn.getText().toString();
                String middlen = txtmiddlen.getText().toString();
                String firtsn = txtfname.getText().toString();
                String type = "Regular";

                User newUser = new User();

                newUser.email = email;
                newUser.name = email.substring(0, email.indexOf("@"));
                newUser.avata = StaticConfig.STR_DEFAULT_BASE64;
                newUser.usertype =type;
                newUser.lastname =lastn;
                newUser.middlename =middlen;
                newUser.firstname =firtsn;
                FirebaseDatabase.getInstance().getReference().child("user/"+id).setValue(newUser);

                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });



        return view;
    }


    @Override
    public void onClick(View view)
    {

    }


}
