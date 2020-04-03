package com.example.manasask.contactappdemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.manasask.contactappdemo.ContactDetailActivity;
import com.example.manasask.contactappdemo.R;
import com.example.manasask.contactappdemo.model.UserContact;

import java.util.List;

public class ContactViewAdapter extends RecyclerView.Adapter<ContactViewAdapter.ViewHolder>{

    List<UserContact> userContactList;
    Context context;
    private static final String TAG = "ContactViewAdapter";

    public ContactViewAdapter(List<UserContact> userContactList, Context context) {
        this.userContactList = userContactList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_display_view,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: ");
        holder.contact_name.setText(userContactList.get(position).getContactUserFirstName()+" "+ userContactList.get(position).getContactUserLastName());
        holder.contact_phn_number.setText(userContactList.get(position).getContactUserPhone());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context.getApplicationContext(),ContactDetailActivity.class);
                intent.putExtra("contactUserID",userContactList.get(position).getContactUserId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: ");
        return userContactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView contact_image;
        TextView contact_name;
        TextView contact_phn_number;
        RecyclerView recyclerView;
        RelativeLayout relativeLayout;
        FloatingActionButton add_contact;
        public ViewHolder(View itemView) {
            super(itemView);
            contact_image=itemView.findViewById(R.id.contact_image);
            contact_name=itemView.findViewById(R.id.contact_name);
            contact_phn_number=itemView.findViewById(R.id.tv_contact_phone_number);
            recyclerView=itemView.findViewById(R.id.contact_home_recycler_view);
            add_contact=itemView.findViewById(R.id.fab);
            relativeLayout=itemView.findViewById(R.id.relative_layout);
        }
    }

}
