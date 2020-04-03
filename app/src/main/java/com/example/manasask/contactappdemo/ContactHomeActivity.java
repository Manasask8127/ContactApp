package com.example.manasask.contactappdemo;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manasask.contactappdemo.Adapter.ContactViewAdapter;
import com.example.manasask.contactappdemo.data.MySQLiteHelper;
import com.example.manasask.contactappdemo.model.UserContact;
import com.example.manasask.contactappdemo.utils.AndroidProjUtils;

import java.util.ArrayList;
import java.util.List;

public class ContactHomeActivity extends AppCompatActivity
{

    TextView noContact;
    MySQLiteHelper mySQLiteHelper;


    private static final String TAG = "ContactHomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Home");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                AndroidProjUtils.launchNewActivity(ContactHomeActivity.this, CreateContactActivity.class, false);
            }
        });

    getContacts();
    }

    private void getContacts() {
        List<UserContact> userContactList =new ArrayList<>();
        mySQLiteHelper=new MySQLiteHelper(this);
        userContactList.addAll(mySQLiteHelper.getAllContacts());
        if (!userContactList.isEmpty()){
            Log.d(TAG, "getContacts: ");
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            RecyclerView recyclerView=findViewById(R.id.contact_home_recycler_view);
            ContactViewAdapter contactViewAdapter=new ContactViewAdapter(userContactList,this);
            recyclerView.setAdapter(contactViewAdapter);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
        else
        {
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
            noContact=findViewById(R.id.no_contact);
            noContact.setVisibility(View.VISIBLE);
        }
    }

    protected void onDestroy(){
        mySQLiteHelper.close();
        super.onDestroy();

    }

}