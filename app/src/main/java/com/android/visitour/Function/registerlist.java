package com.android.visitour.Function;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.visitour.Map.MapsActivity;
import com.android.visitour.R;
import com.android.visitour.data.EstablishmentRegister;

import java.util.List;

/**
 * Created by Miguel Aicrag on 8/12/2017.
 */

public class registerlist extends ArrayAdapter implements View.OnClickListener
{
    private Activity context;
    private List<EstablishmentRegister> artistList;
    TextView txtemail;

    public registerlist(Activity context, List<EstablishmentRegister> artistList)
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
        txtemail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getContext(), MapsActivity.class);
                intent.putExtra("name",register.getEstablishment_Name());
                intent.putExtra("lat",register.getEstablishment_lat());
                intent.putExtra("id",register.getEstablishment_id());
                intent.putExtra("con",register.getEstablishment_phone());
                intent.putExtra("add",register.getEstablishment_add());
                intent.putExtra("web",register.getEstablishment_website());
                intent.putExtra("estreserv",register.getEstablishment_reserv());


                getContext().startActivity(intent);
            }
        });


        return listViewItem;

    }


    @Override
    public void onClick(View v)
    {

    }
}
