package com.android.visitour.data;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.visitour.R;
import com.android.visitour.Update_company.Update_est;

import java.util.List;

/**
 * Created by Miguel Aicrag on 8/12/2017.
 */

public class Approvedlist extends ArrayAdapter implements View.OnClickListener
{
    private Activity context;
    private List<EstablishmentRegister> artistList;
    TextView txtemail,id;
    ImageView img;



    public Approvedlist(Activity context, List<EstablishmentRegister> artistList)
    {
        super(context, R.layout.list_approved_layout,artistList);
        this.context = context;
        this.artistList = artistList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_approved_layout,null,true);
         txtemail = (TextView)listViewItem.findViewById(R.id.txtemail);
        id = (TextView)listViewItem.findViewById(R.id.txtid);
        img = (ImageView)listViewItem.findViewById( R.id.imageView );



        final EstablishmentRegister register = artistList.get(position);

        txtemail.setText(register.getEstablishment_Name());
        id.setText(register.establishment_id );
        txtemail.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent= new Intent(getContext(), Update_est.class);
                intent.putExtra("estname",register.establishment_Name);
                intent.putExtra("estadd",register.establishment_add);
                intent.putExtra("estcontact",register.establishment_phone);
                intent.putExtra("estlat",register.establishment_lat);
                intent.putExtra("estemail",register.establishment_email);
                intent.putExtra("estowner",register.establishment_owner);
                intent.putExtra("estpostal",register.establishment_postal);
                intent.putExtra("esttype",register.establishment_type);
                intent.putExtra("estid",register.establishment_id);
                intent.putExtra("estweb",register.establishment_website);
                intent.putExtra("estreserv",register.establishment_reserv);




                getContext().startActivity(intent);
            }
        } );


        return listViewItem;

    }


    @Override
    public void onClick(View v)
    {
//        if(v == txtemail)
//        {
//
//
//        }

    }
}
