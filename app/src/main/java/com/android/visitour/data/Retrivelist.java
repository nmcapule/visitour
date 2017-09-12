package com.android.visitour.data;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.visitour.Function.AddReqActivity;
import com.android.visitour.R;

import java.util.List;

/**
 * Created by Miguel Aicrag on 8/12/2017.
 */

public class Retrivelist extends ArrayAdapter implements View.OnClickListener
{
    private Activity context;
    private List<EstablishmentRegister> artistList;
    TextView txtemail;
    ImageView img;


    public Retrivelist(Activity context, List<EstablishmentRegister> artistList)
    {
        super(context, R.layout.list_layout,artistList);
        this.context = context;
        this.artistList = artistList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);
         txtemail = (TextView)listViewItem.findViewById(R.id.txtemail);
//        img = (ImageView)listViewItem.findViewById( R.id.imageView );



        final EstablishmentRegister register = artistList.get(position);

        txtemail.setText(register.getEstablishment_Name());
        txtemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent=new Intent(getContext(), AddReqActivity.class);
                intent.putExtra("name",txtemail.getText().toString().trim());
                intent.putExtra("id",register.establishment_id);
                intent.putExtra("type",register.establishment_type);
                intent.putExtra("owner",register.establishment_owner);
                intent.putExtra("lat",register.establishment_lat);
                intent.putExtra("add",register.establishment_add);
                intent.putExtra("postal",register.establishment_postal);
                intent.putExtra("email",register.establishment_email);
                intent.putExtra("phone",register.establishment_phone);


                getContext().startActivity(intent);
            }
        });


        return listViewItem;

    }


    @Override
    public void onClick(View v)
    {
//        if(v == txtemail)
//        {
//
//        }

    }
}
