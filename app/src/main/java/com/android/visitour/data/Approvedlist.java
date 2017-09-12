package com.android.visitour.data;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.visitour.Company.ViewImageActivity;
import com.android.visitour.R;

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
                Intent intent= new Intent(getContext(), ViewImageActivity.class);
                intent.putExtra("id",register.establishment_id);
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
