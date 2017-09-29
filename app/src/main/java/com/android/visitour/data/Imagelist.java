package com.android.visitour.data;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.visitour.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Miguel Aicrag on 8/12/2017.
 */

public class Imagelist extends ArrayAdapter implements View.OnClickListener
{
    private Activity context;
    private List<Image> artistList;
    TextView txtemail;
    ImageView img;


    public Imagelist(Activity context, List<Image> artistList)
    {
        super(context, R.layout.list_image_layout,artistList);
        this.context = context;
        this.artistList = artistList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_image_layout,null,true);
         txtemail = (TextView)listViewItem.findViewById(R.id.txtemail);
        img = (ImageView)listViewItem.findViewById( R.id.imageView );



        final Image register = artistList.get(position);

        txtemail.setText(register.getFilesname());
        Glide.with(context).load(register.getUri()).into(img);
        Toast.makeText(getContext(),register.getUri(),Toast.LENGTH_LONG).show();


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
