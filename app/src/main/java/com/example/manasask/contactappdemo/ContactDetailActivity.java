package com.example.manasask.contactappdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manasask.contactappdemo.data.MySQLiteHelper;
import com.example.manasask.contactappdemo.model.UserContact;
import com.example.manasask.contactappdemo.utils.AndroidProjUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ContactDetailActivity extends AppCompatActivity {
    //TextView first_name=findViewById(R.id.create_contact_first_name);

    @BindView(R.id.create_contact_first_name)
    TextView first_name;

    @BindView(R.id.create_contact_last_name)
    TextView last_name;

    @BindView(R.id.create_contact_mail)
    TextView address;

    @BindView(R.id.create_contact_phn_num)
    TextView phn_num;

    @BindView(R.id.create_contact_image)
    CircleImageView image;

    @BindView(R.id.edit)
    FloatingActionButton edit;

    UserContact userContact;
    MySQLiteHelper mySQLiteHelper;
    String contactUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Details");

      getIntentData();
      retrieveContact();
    }

    private void retrieveContact() {
        mySQLiteHelper=new MySQLiteHelper(this);
        userContact=new UserContact();
        userContact=mySQLiteHelper.getContact(Long.parseLong(contactUserId));

        first_name.setText(userContact.getContactUserFirstName());
        last_name.setText(userContact.getContactUserLastName());
        phn_num.setText(userContact.getContactUserPhone());
        address.setText(userContact.getContactUserAddress());

    }

    private void getIntentData() {
        Intent intent=getIntent();
        if (intent!=null){
            contactUserId=intent.getStringExtra("contactUserID");

        }
    }
    @OnClick(R.id.edit)
    void onFabEditContactClicked(){
        Intent intent=new Intent(ContactDetailActivity.this,EditContactActivity.class);
        intent.putExtra("contactUserId",contactUserId);
        startActivity(intent);
    }
    @OnClick(R.id.delete)
    void onDeleteContactClicked()
    {
        mySQLiteHelper=new MySQLiteHelper(this);
        userContact=new UserContact();
        userContact=mySQLiteHelper.getContact(Long.parseLong(contactUserId));




        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ContactDetailActivity.this);
        alertDialog.setMessage("Delete contact?");
        alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(getApplicationContext(),"contact deleted!",Toast.LENGTH_LONG).show();
                mySQLiteHelper.deleteContact(userContact);
                AndroidProjUtils.launchNewActivity(ContactDetailActivity.this,ContactHomeActivity.class,true);

            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
                AndroidProjUtils.launchNewActivity(ContactDetailActivity.this, ContactHomeActivity.class, true);
            }
        });
        alertDialog.show();

    }

}
