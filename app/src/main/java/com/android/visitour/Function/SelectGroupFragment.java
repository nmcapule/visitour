package com.android.visitour.Function;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.visitour.BasicInfo;
import com.android.visitour.R;
import com.android.visitour.data.FriendDB;
import com.android.visitour.data.GroupDB;
import com.android.visitour.data.StaticConfig;
import com.android.visitour.model.Group;
import com.android.visitour.model.ListFriend;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static com.android.visitour.Function.SelectGroupFragment.addname;
import static com.android.visitour.Function.SelectGroupFragment.id;


public class SelectGroupFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView recyclerListGroups;
//    public FragGroupClickFloatButton onClickFloatButton;
    private ArrayList<Group> listGroup;
    private ListGroupsAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public static final int CONTEXT_MENU_DELETE = 1;
    public static final int CONTEXT_MENU_EDIT = 2;
    public static final int CONTEXT_MENU_LEAVE = 3;
    public static final int REQUEST_EDIT_GROUP = 0;
    public static final String CONTEXT_MENU_KEY_INTENT_DATA_POS = "pos";
    Button select;
    static String addname,id;



    LovelyProgressDialog progressDialog, waitingLeavingGroup;

    public SelectGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_select_group, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        addname = bundle.getString("set");
        id = bundle.getString("id");
        select = (Button) layout.findViewById(R.id.btnselect);
        listGroup = GroupDB.getInstance(getContext()).getListGroups();
        recyclerListGroups = (RecyclerView) layout.findViewById(R.id.recycleListGroup);
        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerListGroups.setLayoutManager(layoutManager);
        adapter = new ListGroupsAdapter(getContext(), listGroup);
        recyclerListGroups.setAdapter(adapter);
//        onClickFloatButton = new FragGroupClickFloatButton();
        progressDialog = new LovelyProgressDialog(getContext())
                .setCancelable(false)
                .setIcon(R.drawable.ic_dialog_delete_group)
                .setTitle("Deleting....")
                .setTopColorRes(R.color.colorAccent);

        waitingLeavingGroup = new LovelyProgressDialog(getContext())
                .setCancelable(false)
                .setIcon(R.drawable.ic_dialog_delete_group)
                .setTitle("Group leaving....")
                .setTopColorRes(R.color.colorAccent);

        if(listGroup.size() == 0){

            mSwipeRefreshLayout.setRefreshing(true);
            getListGroup();
        }


        return layout;
    }

    private void getListGroup(){
        FirebaseDatabase.getInstance().getReference().child("user/"+ StaticConfig.UID+"/group").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null) {
                    HashMap mapListGroup = (HashMap) dataSnapshot.getValue();
                    Iterator iterator = mapListGroup.keySet().iterator();
                    while (iterator.hasNext()){
                        String idGroup = (String) mapListGroup.get(iterator.next().toString());
                        Group newGroup = new Group();
                        newGroup.id = idGroup;
                        listGroup.add(newGroup);
                    }
                    getGroupInfo(0);
                }else{
                    mSwipeRefreshLayout.setRefreshing(false);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_EDIT_GROUP && resultCode == Activity.RESULT_OK) {
            listGroup.clear();
            ListGroupsAdapter.listFriend = null;
            GroupDB.getInstance(getContext()).dropDB();
            getListGroup();
        }
    }

    private void getGroupInfo(final int indexGroup)
    {

        if(indexGroup == listGroup.size()){
            adapter.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing(false);

        }
        else

            {
            FirebaseDatabase.getInstance().getReference().child("group/"+listGroup.get(indexGroup).id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue() != null)
                        {

                        HashMap mapGroup = (HashMap) dataSnapshot.getValue();
                        ArrayList<String> member = (ArrayList<String>) mapGroup.get("member");
                        HashMap mapGroupInfo = (HashMap) mapGroup.get("groupInfo");
                        for(String idMember: member){
                            listGroup.get(indexGroup).member.add(idMember);
                        }
                        listGroup.get(indexGroup).groupInfo.put("name", (String) mapGroupInfo.get("name"));
                        listGroup.get(indexGroup).groupInfo.put("admin", (String) mapGroupInfo.get("admin"));
                            listGroup.get(indexGroup).groupInfo.put("id", (String) mapGroupInfo.get("id"));
                    }
                    GroupDB.getInstance(getContext()).addGroup(listGroup.get(indexGroup));
                    Log.d("GroupFragment", listGroup.get(indexGroup).id +": " + dataSnapshot.toString());
                    getGroupInfo(indexGroup +1);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onRefresh() {
        listGroup.clear();
        ListGroupsAdapter.listFriend = null;
        GroupDB.getInstance(getContext()).dropDB();
        adapter.notifyDataSetChanged();
        getListGroup();
    }
}

class ListGroupsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Group> listGroup;
    public static ListFriend listFriend = null;
    private Context context;

    public ListGroupsAdapter(Context context,ArrayList<Group> listGroup){
        this.context = context;
        this.listGroup = listGroup;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rc_item_select_group, parent, false);
        return new ItemGroupViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final String groupName = listGroup.get(position).groupInfo.get("name");
        final String admin = listGroup.get(position).groupInfo.get("admin");
       final String ids = listGroup.get(position).groupInfo.get("id");
        if(groupName != null && groupName.length() > 0) {
            ((ItemGroupViewHolder) holder).txtGroupName.setText(groupName);
            ((ItemGroupViewHolder) holder).iconGroup.setText((groupName.charAt(0) + "").toUpperCase());
        }
//        ((ItemGroupViewHolder) holder).btnMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                view.setTag(new Object[]{groupName, position});
//                view.getParent().showContextMenuForChild(view);
//            }
//        });

        ((RelativeLayout)((ItemGroupViewHolder) holder).txtGroupName.getParent()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listFriend == null)
                {
                    listFriend = FriendDB.getInstance(context).getListFriend();
                }

//                ArrayList<CharSequence> idFriend = new ArrayList<>();
//                for(String id : listGroup.get(position).member) {
//                    idFriend.add(id);
//                    String avata = listFriend.getAvataById(id);
//                    if(!avata.equals(StaticConfig.STR_DEFAULT_BASE64)) {
//                        byte[] decodedString = Base64.decode(avata, Base64.DEFAULT);
//                        ChatActivity.bitmapAvataFriend.put(id, BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
//                    }else if(avata.equals(StaticConfig.STR_DEFAULT_BASE64)) {
//                        ChatActivity.bitmapAvataFriend.put(id, BitmapFactory.decodeResource(context.getResources(), R.drawable.default_avata));
//                    }else {
//                        ChatActivity.bitmapAvataFriend.put(id, null);
//                    }
//                }
                Intent intent = new Intent(context, BasicInfo.class);

                intent.putExtra("set", addname);
                intent.putExtra("grouname", groupName);
                intent.putExtra("groupid", id);
                intent.putExtra("id", id);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listGroup.size();
    }
}

class ItemGroupViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
    public TextView iconGroup, txtGroupName;
    public ImageButton btnMore;
    public CheckBox checkBox;

    public ItemGroupViewHolder(View itemView) {
        super(itemView);
        itemView.setOnCreateContextMenuListener(this);
        iconGroup = (TextView) itemView.findViewById(R.id.icon_group);
        txtGroupName = (TextView) itemView.findViewById(R.id.txtName);
        checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
//        btnMore = (ImageButton) itemView.findViewById(R.id.btnMoreAction);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
//        menu.setHeaderTitle((String) ((Object[])btnMore.getTag())[0]);
//        Intent data = new Intent();
//        data.putExtra(SelectGroupFragment.CONTEXT_MENU_KEY_INTENT_DATA_POS, (Integer) ((Object[])btnMore.getTag())[1]);
//        menu.add(Menu.NONE, SelectGroupFragment.CONTEXT_MENU_EDIT, Menu.NONE, "Edit group").setIntent(data);
//        menu.add(Menu.NONE, SelectGroupFragment.CONTEXT_MENU_DELETE, Menu.NONE, "Delete group").setIntent(data);
//        menu.add(Menu.NONE, SelectGroupFragment.CONTEXT_MENU_LEAVE, Menu.NONE, "Leave group").setIntent(data);
    }
}