package com.android.visitour.Function;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.visitour.R;
import com.android.visitour.data.Image;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AddReqActivity extends AppCompatActivity {


    TextView type, name, owner, loc, postal1, email, add, phone1;
    Button upload, choose;
    EditText txximg;

    private StorageReference mStorageRef;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ImageView imageView;

    private Uri imgUri;
    public static final String Database_path = "Registered_Establishment";

    public static final String Storage_path = "image/";
    public static final String Image_path = "image";
    public static final int Request_code = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_path).child(user.getUid());


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_req);

        name = (TextView) findViewById(R.id.txtname);
        type = (TextView) findViewById(R.id.txttype);
        owner = (TextView) findViewById(R.id.txtowner);
        upload = (Button) findViewById(R.id.btnview );
        loc = (TextView) findViewById(R.id.txtloc);
        postal1 = (TextView) findViewById(R.id.txtpostal);
        email = (TextView) findViewById(R.id.txtemail);
        owner = (TextView) findViewById(R.id.txtowner);
        add = (TextView) findViewById(R.id.txtadd);
        imageView = (ImageView) findViewById(R.id.imgview);
        phone1 = (TextView) findViewById(R.id.txtphone);
//        choose =(Button)findViewById(R.id.btnchoose);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("user");
////
//            Register establishmentRegister = new Register(type1,esname,esowner,eslong,escity,esmunicipal,postal,mal,phone);
                databaseReference1.child("1Ep27Q9WPQg0Xn4p1lM5FwLOkeG2").removeValue();////TESTING
            }
        });


        Bundle bundle = getIntent().getExtras();
        String as = bundle.getString("id");
        String ID = bundle.getString("name");
        String estype = bundle.getString("type");
        String owner1 = bundle.getString("owner");
        String loc1 = bundle.getString("lat");
        String pos = bundle.getString("postal");
        String mail = bundle.getString("email");
        String adress = bundle.getString("add");
        String phon = bundle.getString("phone");


        type.setText("Establishment type : " + estype);
        name.setText("Establishment name : " + ID);
        owner.setText("Establishment owner : " + owner1);
        loc.setText("Establishment location : " + loc1);
        postal1.setText("Establishment postal : " + pos);
        email.setText("Establishment email : " + mail);
        add.setText("Establishment address : " + adress);
        phone1.setText("Establishment location : " + phon);


        Toast.makeText(getApplication(),as, Toast.LENGTH_LONG).show();

//        choose.setOnClickListener(this);

    }

    public void btnchoose_Click(View view) {


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select files"), Request_code);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Request_code && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imageView.setImageBitmap(bm);
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
    public void btnupload(View view) {
        try {
            if (imgUri != null) {

                Bundle bundle = getIntent().getExtras();
                final String uID = bundle.getString("id");
                txximg = (EditText) findViewById(R.id.txtimgname);
                final ProgressDialog dialog = new ProgressDialog(this);
                dialog.setTitle("Uploading Image");
                dialog.show();

                databaseReference = FirebaseDatabase.getInstance().getReference(Image_path);
                StorageReference ref = mStorageRef.child(Storage_path + System.currentTimeMillis() + "." + GetimgageExt(imgUri));

                ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Files Uploaded", Toast.LENGTH_LONG).show();
                        Image image = new Image(txximg.getText().toString(), taskSnapshot.getDownloadUrl().toString());
                        FirebaseUser user = firebaseAuth.getCurrentUser();

                        String uploaduid = user.getUid();
                        String uploadid = databaseReference.push().getKey();

                        databaseReference.child(uploaduid).child(uID).child(uploadid).setValue(image);
                        databaseReference.child("admib").child(uID).child(uploadid).setValue(image);

                        txximg.setText("");
                        imageView.setImageBitmap(null);
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
            } else {
                Toast.makeText(getApplicationContext(), " Please select files  ", Toast.LENGTH_LONG).show();
            }

        }
        catch (NullPointerException ex)
        {
            ex.printStackTrace();
        }
    }
}


//    @Override
//    public void onClick(View view)
//    {
//
//        Bundle bundle = getIntent().getExtras();
//        String ID = bundle.getString("name");
//        String estype = bundle.getString("type");
//        String owner1 = bundle.getString("owner");
//        String loc1 = bundle.getString("lat");
//        String pos = bundle.getString("postal");
//        String mail = bundle.getString("email");
//        String adress = bundle.getString("add");
//        String phon = bundle.getString("phone");
//
//        if(view == upload)
//        {
////
////            String uploadid = databaseReference.push().getKey();
////            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Database_path);
////
////            Register establishmentRegister = new Register(type1,esname,esowner,eslong,escity,esmunicipal,postal,mal,phone);
////            databaseReference.child(uploadid).setValue(establishmentRegister);
////            Toast.makeText(this,"Pasok",Toast.LENGTH_LONG).show();
//
//        }
//
//    }

