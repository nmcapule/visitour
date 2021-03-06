package com.android.visitour.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.visitour.R;
import com.android.visitour.ShowEvent.show_Event;
import com.android.visitour.data.SharedPreferenceHelper;
import com.android.visitour.data.StaticConfig;
import com.android.visitour.model.Consersation;
import com.android.visitour.model.Message;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.android.visitour.ui.ChatActivity.uname;


public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerChat;
    public static final int VIEW_TYPE_USER_MESSAGE = 0;
    public static final int VIEW_TYPE_FRIEND_MESSAGE = 1;
    private ListMessageAdapter adapter;
    private String roomId;
    private ArrayList<CharSequence> idFriend;
    private Consersation consersation;
    private ImageButton btnSend;
    private EditText editWriteMessage;
    private LinearLayoutManager linearLayoutManager;
    public static HashMap<String, Bitmap> bitmapAvataFriend;
    public Bitmap bitmapAvataUser;
    private Button close;
    private Toolbar bar;
    private TextView eventname;
   static String uname ="a";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        try {


            Intent intentData = getIntent();
            idFriend = intentData.getCharSequenceArrayListExtra(StaticConfig.INTENT_KEY_CHAT_ID);
            roomId = intentData.getStringExtra(StaticConfig.INTENT_KEY_CHAT_ROOM_ID);
            String nameFriend = intentData.getStringExtra(StaticConfig.INTENT_KEY_CHAT_FRIEND);
            uname = nameFriend;
            eventname = (TextView) findViewById(R.id.txtname);
            close = (Button) findViewById(R.id.btnshow);
            bar = (Toolbar) findViewById(R.id.toolbar2);
//
//        Toast.makeText(getApplication(),uname,Toast.LENGTH_LONG).show();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("group_event");

            databaseReference.child(uname).child("eventname").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final String value = dataSnapshot.getValue(String.class);

                    eventname.setText(value);

                    if (eventname.getText().equals("")) {
                        bar.setVisibility(View.GONE);
                    }

                    eventname.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(getApplication(), show_Event.class);
                            intent.putExtra("eventname", value);
                            intent.putExtra("uid", uname);
//////////////////////////////////////TO FOLLOW NEEDED
                            startActivity(intent);

                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    bar.setVisibility(v.GONE);
                }
            });


            consersation = new Consersation();
            btnSend = (ImageButton) findViewById(R.id.btnSend);
            btnSend.setOnClickListener(this);

            String base64AvataUser = SharedPreferenceHelper.getInstance(this).getUserInfo().avata;
            if (!base64AvataUser.equals(StaticConfig.STR_DEFAULT_BASE64)) {
                byte[] decodedString = Base64.decode(base64AvataUser, Base64.DEFAULT);
                bitmapAvataUser = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            } else {
                bitmapAvataUser = null;
            }

            editWriteMessage = (EditText) findViewById(R.id.editWriteMessage);
            if (idFriend != null && nameFriend != null) {
                getSupportActionBar().setTitle(nameFriend);
                Toast.makeText(this, nameFriend, Toast.LENGTH_LONG).show();
                linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                linearLayoutManager.setReverseLayout(false);
                linearLayoutManager.setStackFromEnd(true);
                recyclerChat = (RecyclerView) findViewById(R.id.recyclerChat);
                recyclerChat.setLayoutManager(linearLayoutManager);
                adapter = new ListMessageAdapter(this, consersation, bitmapAvataFriend, bitmapAvataUser);
                FirebaseDatabase.getInstance().getReference().child("message/" + roomId).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot.getValue() != null) {
                            HashMap mapMessage = (HashMap) dataSnapshot.getValue();
                            Message newMessage = new Message();
                            newMessage.idSender = (String) mapMessage.get("idSender");
                            newMessage.idReceiver = (String) mapMessage.get("idReceiver");
                            newMessage.text = (String) mapMessage.get("text");
                            newMessage.timestamp = (long) mapMessage.get("timestamp");
                            consersation.getListMessageData().add(newMessage);
                            adapter.notifyDataSetChanged();
                            linearLayoutManager.scrollToPosition(consersation.getListMessageData().size() - 1);
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                recyclerChat.setAdapter(adapter);
            }
        }
        catch (Exception ex)
        {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        try {


            if (item.getItemId() == android.R.id.home) {
                Intent result = new Intent();
                result.putExtra("idFriend", idFriend.get(0));
                setResult(RESULT_OK, result);
                this.finish();
            }
        }
        catch (Exception ex)
        {

        }

        return true;
    }

    @Override
    public void onBackPressed() {

        try
        {
            Intent result = new Intent();
            result.putExtra("idFriend", idFriend.get(0));
            setResult(RESULT_OK, result);
            this.finish();

        }
        catch (Exception ex)
        {

        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSend) {
            String content = editWriteMessage.getText().toString().trim();
            if (content.length() > 0) {
                editWriteMessage.setText("");
                Message newMessage = new Message();
                newMessage.text = content;
                newMessage.idSender = StaticConfig.UID;
                newMessage.idReceiver = roomId;
                newMessage.timestamp = System.currentTimeMillis();
                FirebaseDatabase.getInstance().getReference().child("message/" + roomId).push().setValue(newMessage);
            }
        }
    }
}

class ListMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private Consersation consersation;
    private HashMap<String, Bitmap> bitmapAvata;
    private HashMap<String, DatabaseReference> bitmapAvataDB;
    private TextView name ;
    private Bitmap bitmapAvataUser;

    public ListMessageAdapter(Context context, Consersation consersation, HashMap<String, Bitmap> bitmapAvata, Bitmap bitmapAvataUser) {
        this.context = context;
        this.consersation = consersation;
        this.bitmapAvata = bitmapAvata;
        this.bitmapAvataUser = bitmapAvataUser;
        bitmapAvataDB = new HashMap<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)

    {
        String ln = uname;

        if (viewType == ChatActivity.VIEW_TYPE_FRIEND_MESSAGE)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.rc_item_message_friend, parent, false);
            return new ItemMessageFriendHolder(view);
        } else if (viewType == ChatActivity.VIEW_TYPE_USER_MESSAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.rc_item_message_user, parent, false);
            return new ItemMessageUserHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemMessageFriendHolder) {
            ((ItemMessageFriendHolder) holder).txtContent.setText(consersation.getListMessageData().get(position).text);
            Bitmap currentAvata = bitmapAvata.get(consersation.getListMessageData().get(position).idSender);
            if (currentAvata != null) {
                ((ItemMessageFriendHolder) holder).avata.setImageBitmap(currentAvata);
            } else {
                final String id = consersation.getListMessageData().get(position).idSender;
                if(bitmapAvataDB.get(id) == null){
                    bitmapAvataDB.put(id, FirebaseDatabase.getInstance().getReference().child("user/" + id + "/avata"));
                    bitmapAvataDB.get(id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                String avataStr = (String) dataSnapshot.getValue();
                                if(!avataStr.equals(StaticConfig.STR_DEFAULT_BASE64)) {
                                    byte[] decodedString = Base64.decode(avataStr, Base64.DEFAULT);
                                    ChatActivity.bitmapAvataFriend.put(id, BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
                                }else{
                                    ChatActivity.bitmapAvataFriend.put(id, BitmapFactory.decodeResource(context.getResources(), R.drawable.default_avata));
                                }
                                notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        } else if (holder instanceof ItemMessageUserHolder) {
            ((ItemMessageUserHolder) holder).txtContent.setText(consersation.getListMessageData().get(position).text);
            if (bitmapAvataUser != null) {
                ((ItemMessageUserHolder) holder).avata.setImageBitmap(bitmapAvataUser);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return consersation.getListMessageData().get(position).idSender.equals(StaticConfig.UID) ? ChatActivity.VIEW_TYPE_USER_MESSAGE : ChatActivity.VIEW_TYPE_FRIEND_MESSAGE;
    }

    @Override
    public int getItemCount() {
        return consersation.getListMessageData().size();
    }
}

class ItemMessageUserHolder extends RecyclerView.ViewHolder {
    public TextView txtContent;
    public CircleImageView avata;

    public ItemMessageUserHolder(View itemView) {
        super(itemView);
        txtContent = (TextView) itemView.findViewById(R.id.textContentUser);
        avata = (CircleImageView) itemView.findViewById(R.id.imageView2);
    }
}

class ItemMessageFriendHolder extends RecyclerView.ViewHolder {
    public TextView txtContent;
    public CircleImageView avata;

    public ItemMessageFriendHolder(View itemView) {
        super(itemView);
        txtContent = (TextView) itemView.findViewById(R.id.textContentFriend);
        avata = (CircleImageView) itemView.findViewById(R.id.imageView3);
    }
}
