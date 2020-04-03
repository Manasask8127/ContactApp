package com.example.manasask.contactappdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.manasask.contactappdemo.data.MySQLiteHelper;
import com.example.manasask.contactappdemo.model.UserContact;
import com.example.manasask.contactappdemo.utils.AndroidProjUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditContactActivity extends AppCompatActivity implements View.OnTouchListener{

    @BindView(R.id.update_contact_image)
    CircleImageView contact_image;

    @BindView(R.id.update_contact_first_name)
    EditText first_name;

    @BindView(R.id.update_contact_last_name)
    EditText last_name;

    @BindView(R.id.update_contact_mail)
    EditText mail;

    @BindView(R.id.update_contact_phn_num)
    EditText phn_num;

    @BindView(R.id.btn_update)
    Button update;

    @BindView(R.id.btn_update_cancel)
    Button cancel;

    @BindView(R.id.edit_contact_layout)
    RelativeLayout editContactLayout;

    UserContact userContact;
    MySQLiteHelper mySQLiteHelper;
    String contactUserId;
    String mFirstName, mLastName, mMail, mPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        ButterKnife.bind(this);


        getIntentData();
        init();
        retrieveContact();
    }

    private void init() {
        editContactLayout.setOnTouchListener((View.OnTouchListener) this);

    }

    private void retrieveContact() {
        mySQLiteHelper=new MySQLiteHelper(this);
        userContact=new UserContact();
        userContact=mySQLiteHelper.getContact(Long.parseLong(contactUserId));
        first_name.setText(userContact.getContactUserFirstName());
        last_name.setText(userContact.getContactUserLastName());
        mail.setText(userContact.getContactUserAddress());
        phn_num.setText(userContact.getContactUserPhone());
    }

    private void getIntentData()
    {
        Intent intent = getIntent();
        if (intent != null) {
            contactUserId = intent.getStringExtra("contactUserId");
        }


    }

    @OnClick(R.id.btn_update_cancel)
    void onButtonCancel() {
        onBackPressed();
    }

    @OnClick(R.id.btn_update)
    void onButtonUpdate()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(EditContactActivity.this);
        builder.setCancelable(true);
        builder.setMessage("Do you want to save the edited contact?");
        builder.setTitle("alert dialog");
        if(!validateUser()){
            return;

        }
        else {
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
                    Toast.makeText(getApplicationContext(),"contact updated!",Toast.LENGTH_LONG).show();
                    updateContact();
                }
            });



        }


    }

    private boolean validateUser() {
        getUserValue();

        if (mFirstName.isEmpty()){
            first_name.setText("First name is empty");
            requestFocus(first_name);
        }
        else if (mLastName.isEmpty()){
            last_name.setText("Last name is empty");
            requestFocus(last_name);
        } if (mMail.isEmpty()){
            mail.setText("Address name is empty");
            requestFocus(mail);
        } if (mPhoneNumber.isEmpty()){
            phn_num.setText("Phn num is empty");
            requestFocus(phn_num);
        }
        return true;
    }

    private void getUserValue() {
        mFirstName=first_name.getText().toString().trim();
       mLastName=last_name.getText().toString().trim();
        mMail=mail.getText().toString().trim();
        mPhoneNumber=phn_num.getText().toString().trim();

    }

    private void requestFocus(View view) {
        if (view.requestFocus()){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void updateContact() {
        mySQLiteHelper=new MySQLiteHelper(this);
        userContact=new UserContact();

        userContact.setContactUserId(contactUserId);
        userContact.setContactUserFirstName(mFirstName);
        userContact.setContactUserLastName(mLastName);
        userContact.setContactUserAddress(mMail);
        userContact.setContactUserPhone(mPhoneNumber);

        if (userContact!=null){
            mySQLiteHelper.updateContact(userContact);
            AndroidProjUtils.launchNewActivity(EditContactActivity.this,ContactHomeActivity.class,true);
        }
         else{
            Toast.makeText(this,"Error Occured while adding contact",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        AndroidProjUtils.hideSoftKeyboard(this,v);
        return false;
    }
}


