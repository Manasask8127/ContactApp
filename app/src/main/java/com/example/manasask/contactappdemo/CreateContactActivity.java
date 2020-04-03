package com.example.manasask.contactappdemo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.manasask.contactappdemo.data.MySQLiteHelper;
import com.example.manasask.contactappdemo.model.UserContact;
import com.example.manasask.contactappdemo.utils.AndroidProjUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateContactActivity extends AppCompatActivity implements View.OnTouchListener{

    @BindView(R.id.details_contact_first_name)
    EditText contact_first_name;

    @BindView(R.id.details_contact_last_name)
    EditText contact_last_name;

    @BindView(R.id.details_contact_mail)
    EditText contact_mail;

    @BindView(R.id.details_contact_phn_num)
    EditText contact_phn_num;

    @BindView(R.id.details_contact_image)
    ImageView contact_image;

    @BindView(R.id.btn_save)
    Button save;

    @BindView(R.id.create_contact_relative_layout)
    RelativeLayout create_contact_relative_layout;

    String mFirstName,mLastName,mMail,mPhone;
    UserContact userContact;
    MySQLiteHelper mySQLiteHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);
        setTitle("Create New Contact");
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        create_contact_relative_layout.setOnTouchListener(this);
        mySQLiteHelper=new MySQLiteHelper(this);
    }

    @OnClick(R.id.btn_save)
    void onBtnSave(){
        AlertDialog.Builder builder=new AlertDialog.Builder(CreateContactActivity.this);
        builder.setCancelable(true);
        builder.setMessage("Do you want to save the contact?");
        builder.setTitle("alert dialog");
        if(!validateUser()){
            return;
        } else {
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    Toast.makeText(getApplicationContext(),"contact saved!",Toast.LENGTH_LONG).show();
                    addNewContact();
                    //AndroidProjUtils.hideSoftKeyboard(this, create_contact_relative_layout);
                    AndroidProjUtils.launchNewActivity(CreateContactActivity.this,ContactHomeActivity.class,true);
                    }
            });
            builder.show();


        }
    }

    private void addNewContact() {
        UserContact userContact=new UserContact();
        userContact.setContactUserFirstName(mFirstName);
        userContact.setContactUserLastName(mLastName);
        userContact.setContactUserAddress(mMail);
        userContact.setContactUserPhone(mPhone);
        if (userContact!=null){
            mySQLiteHelper.addContact(userContact);
            Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"empty field",Toast.LENGTH_SHORT).show();
        }

    }

    private boolean validateUser(){
        getUserValue();
        if (mFirstName.isEmpty()){
            contact_first_name.setError("First Name missing");
            requestFocus(contact_first_name);
            return false;
        }

        else if (mLastName.isEmpty()){
            contact_last_name.setError("Last Name missing");
            requestFocus(contact_last_name);
            return false;
        }

        else if (mMail.isEmpty()){
            contact_mail.setError("Address missing");
            requestFocus(contact_mail);
            return false;
        }

        else if (mPhone.isEmpty()){
            contact_phn_num.setError("Phone missing");
            requestFocus(contact_phn_num);
            return false;
        }


        return true;

    }

    private void requestFocus(View view) {
    if (view.requestFocus()){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
    }

    private void getUserValue() {
        mFirstName=contact_first_name.getText().toString().trim();
        mLastName=contact_last_name.getText().toString().trim();
        mMail=contact_mail.getText().toString().trim();
        mPhone=contact_phn_num.getText().toString().trim();
    }
    @Override
    protected void onDestroy() {
        mySQLiteHelper.close();
        super.onDestroy();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        AndroidProjUtils.hideSoftKeyboard(this, v);
        return false;
    }
}



