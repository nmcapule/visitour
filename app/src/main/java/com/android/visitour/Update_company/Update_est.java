package com.android.visitour.Update_company;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.visitour.R;
import com.android.visitour.data.EstablishmentRegister;
import com.android.visitour.data.Image;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Update_est extends AppCompatActivity  implements View.OnClickListener{

    private TextView estname;
    private EditText estadd,estcon,estweb,estclose;
    private ImageView imageView,btncontact,btnweb,btnopen;
    private Button save;
    final Context mContext = this;
    private StorageReference mStorageRef;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    private Uri imgUri;
    public static final String Database_path = "image";

    public static final String Storage_path = "image/";
    public static final String Image_path = "image";
    public static final int Request_code = 1234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_est);

        try
        {


        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        estname = (TextView)findViewById(R.id.estname);
        estadd = (EditText) findViewById(R.id.estadd);
        estcon = (EditText) findViewById(R.id.estcontact);
        estweb = (EditText) findViewById(R.id.estweb);
        estclose = (EditText) findViewById(R.id.edit_reserv);
        imageView  = (ImageView)findViewById(R.id.estimages);
        btnopen  = (ImageView)findViewById(R.id.btnopen);
        btncontact  = (ImageView)findViewById(R.id.btncontact);
        btnweb  = (ImageView)findViewById(R.id.btnweb);
//        save  = (Button)findViewById(R.id.btnsave);


        Bundle bundle = getIntent().getExtras();
        String as = bundle.getString("estname");
        String add = bundle.getString("estadd");
        String contact = bundle.getString("estcontact");
        String web = bundle.getString("estweb");
        String estreserv = bundle.getString("estreserv");

         show();
        estname.setText(as);
        estadd.setText(add);
        estcon.setText(contact);
        estweb.setText(web);
        if(estreserv.equals("Yes"))
        {
            estclose.setText("Close reservation");
        }
        else
        {
            estclose.setText("Open reservation");
            btnopen.setImageResource(R.drawable.ic_lock_outline_black_24dp);
        }


        btncontact.setOnClickListener(this);
        btnweb.setOnClickListener(this);
        btnopen.setOnClickListener(this);


        }
        catch (Exception ex)
        {

        }

//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

    }
    public void show()
    {
        Bundle bundle = getIntent().getExtras();
        final String id = bundle.getString("estid");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("image");

        databaseReference.child(id).child("uri").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String value = dataSnapshot.getValue(String.class);
//
                Glide.with(getApplicationContext()).load(value).into(imageView);

            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void btnchoose_Click(View view) {


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select files"), Request_code);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Request_code && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            imgUri = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imageView.setImageBitmap(bm);
                save();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public String GetimgageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }
    @SuppressWarnings("VisibleForTests")
    public void save()
    {
        try
        {


        if (imgUri != null)
        {
            final ProgressDialog  dialog = new ProgressDialog(this);
            dialog.setTitle("Uploading Image");
            dialog.show();
            Bundle bundle = getIntent().getExtras();
            final String uID = bundle.getString("estname");
            final String id = bundle.getString("estid");


            databaseReference = FirebaseDatabase.getInstance().getReference(Image_path);
            StorageReference ref = mStorageRef.child(Storage_path + System.currentTimeMillis() + "." + GetimgageExt(imgUri));

            ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    dialog.dismiss();
                    Image image = new Image(uID,taskSnapshot.getDownloadUrl().toString());
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    String uploaduid = user.getUid();
                    String uploadid = databaseReference.push().getKey();

                    databaseReference.child(id).setValue(image);
                    show();
                    Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_LONG).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Failed Upload", Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    dialog.setMessage("Uploaded "+(int)progress+"%");
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), " Please select files  ", Toast.LENGTH_LONG).show();
        }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    @Override
    public void onClick(View v)
    {
        try {
            if (v == btncontact) {
                Bundle bundle = getIntent().getExtras();
                String as = bundle.getString("estname");
                String add = bundle.getString("estadd");
                final String contact = bundle.getString("estcontact");

                LayoutInflater li = LayoutInflater.from(mContext);
                View dialogView = li.inflate(R.layout.dialog_edit_contact, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        mContext);
                // set title
                alertDialogBuilder.setTitle("Update contact");
                // set custom dialog icon
                alertDialogBuilder.setIcon(R.drawable.ic_contact_phone_black_24dp);
                // set custom_dialog.xml to alertdialog builder
                alertDialogBuilder.setView(dialogView);
                final EditText userInput = (EditText) dialogView
                        .findViewById(R.id.edit_contact);
                userInput.setHint(contact);
                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        // get user input and set it to etOutput
                                        // edit text
                                        update(userInput.getText().toString());
//                                    Toast.makeText(getApplication(),userInput.getText(),Toast.LENGTH_LONG).show();


                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            } else if (v == btnweb) {
                Bundle bundle = getIntent().getExtras();
                String web = bundle.getString("estweb");

                LayoutInflater li = LayoutInflater.from(mContext);
                View dialogView = li.inflate(R.layout.dialog_edit_contact, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        mContext);
                // set title
                alertDialogBuilder.setTitle("Update Website");
                // set custom dialog icon
                alertDialogBuilder.setIcon(R.drawable.ic_web_black_24dp);
                // set custom_dialog.xml to alertdialog builder
                alertDialogBuilder.setView(dialogView);
                final EditText userInput = (EditText) dialogView
                        .findViewById(R.id.edit_contact);
                userInput.setHint(web);
                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        // get user input and set it to etOutput
                                        // edit text
                                        updateweb(userInput.getText().toString());
//                                    Toast.makeText(getApplication(),userInput.getText(),Toast.LENGTH_LONG).show();


                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }
            else if(v == btnopen)
            {

                Bundle bundle = getIntent().getExtras();

                String estreserv = bundle.getString("estreserv");

                if(estreserv.equals("Yes"))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setTitle("Close reservation");
                    builder.setMessage("do you want confirm this action");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            OpenReserv("No");

                            dialog.dismiss();
                        }

                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // I do not need any action here you might
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();

                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setTitle("Open reservation");
                    builder.setMessage("do you want confirm this action");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            OpenReserv("Yes");

                            dialog.dismiss();
                        }

                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // I do not need any action here you might
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }



            }
        }
        catch (Exception ex)
        {
            ex.getMessage();
        }

    }

    private boolean OpenReserv(String web)
    {
        Bundle bundle = getIntent().getExtras();
        final String id = bundle.getString("estid");
        final String esname = bundle.getString("estname");
        final String add = bundle.getString("estadd");
        final String phone = bundle.getString("estcontact");
        final String eslat = bundle.getString("estlat");
        final String email = bundle.getString("estemail");
        final String esowner = bundle.getString("estowner");
        final String postal = bundle.getString("estpostal");
        final String type = bundle.getString("esttype");
        final String estweb = bundle.getString("estweb");



        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("registered/"+id);

        EstablishmentRegister update = new EstablishmentRegister(type,esname,esowner,add,eslat,postal,email,phone,id,estweb,web);
        ref.setValue(update);
        Toast.makeText(getApplication(),"Update Successfully!",Toast.LENGTH_LONG).show();

        return true;
    }

    private boolean updateweb(String web)
    {
        Bundle bundle = getIntent().getExtras();
        final String id = bundle.getString("estid");
        final String esname = bundle.getString("estname");
        final String add = bundle.getString("estadd");
        final String phone = bundle.getString("estcontact");
        final String eslat = bundle.getString("estlat");
        final String email = bundle.getString("estemail");
        final String esowner = bundle.getString("estowner");
        final String postal = bundle.getString("estpostal");
        final String type = bundle.getString("esttype");



        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("registered/"+id);

        EstablishmentRegister update = new EstablishmentRegister(type,esname,esowner,add,eslat,postal,email,phone,id,web,"");
        ref.setValue(update);
        Toast.makeText(getApplication(),"Update Successfully!",Toast.LENGTH_LONG).show();

        return true;
    }

    private boolean update(String contact)
    {
        Bundle bundle = getIntent().getExtras();
        final String id = bundle.getString("estid");
        final String esname = bundle.getString("estname");
        final String add = bundle.getString("estadd");
        final String phone = contact;
        final String eslat = bundle.getString("estlat");
        final String email = bundle.getString("estemail");
        final String esowner = bundle.getString("estowner");
        final String postal = bundle.getString("estpostal");
        final String type = bundle.getString("esttype");



       DatabaseReference ref = FirebaseDatabase.getInstance().getReference("registered/"+id);

        EstablishmentRegister update = new EstablishmentRegister(type,esname,esowner,add,eslat,postal,email,phone,id,"website","");
        ref.setValue(update);
        Toast.makeText(getApplication(),"Update Successfully!",Toast.LENGTH_LONG).show();

        return true;
    }

}

