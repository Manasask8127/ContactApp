package com.example.manasask.contactappdemo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.manasask.contactappdemo.model.UserContact;

import java.util.ArrayList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_CONTACT_APP_DEMO="CONTACT_APP_DEMO";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_IMAGE="user_image";
    public static final String COLUMN_FIRST_NAME = "user_first_name";
    public static final String COLUMN_LAST_NAME  = "user_last_name";
    public static final String COLUMN_ADDRESS  = "user_address";
    public static final String COLUMN_PHONE_NUMBER="user_phone_number";
    private static final String TAG = "MySQLiteHelper";
    private static final String DATABASE_NAME="CONTACTAPPDEMO";
    private static final Integer DATABASE_VERSION=1;

    // Data creation sql statement;

    private static final String DATABASE_CREATE="create table "
            +TABLE_CONTACT_APP_DEMO+" ( "
            +COLUMN_USER_ID+" integer primary key autoincrement, "
            +COLUMN_USER_IMAGE+" blob, "
            +COLUMN_FIRST_NAME+" varchar(255), "
            +COLUMN_LAST_NAME+" varchar(255), "
            +COLUMN_ADDRESS+" varchar(255), "
            +COLUMN_PHONE_NUMBER+" varchar(255));";



    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT_APP_DEMO);

    // create Table again
        onCreate(db);
    }

    public long addContact(UserContact userContact){
    // open database in write mode
    SQLiteDatabase db =this.getWritableDatabase();

    //Content values is used to insert value

        ContentValues contentValues =new ContentValues();

        contentValues.put(COLUMN_FIRST_NAME,userContact.getContactUserFirstName());
        contentValues.put(COLUMN_LAST_NAME,userContact.getContactUserLastName());
        contentValues.put(COLUMN_ADDRESS,userContact.getContactUserAddress());
        contentValues.put(COLUMN_PHONE_NUMBER,userContact.getContactUserPhone());

        // insert row
       long id =db.insert(TABLE_CONTACT_APP_DEMO,null,contentValues);

       // close db;
        db.close();

        return id;
    }

    public UserContact getContact(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_USER_ID,
                COLUMN_FIRST_NAME,
                COLUMN_LAST_NAME,
                COLUMN_ADDRESS,
                COLUMN_PHONE_NUMBER
        };
        String selection = COLUMN_USER_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                TABLE_CONTACT_APP_DEMO,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    if (cursor!=null)
        cursor.moveToFirst();

    UserContact userContact=new UserContact();
    userContact.setContactUserId(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)));
    userContact.setContactUserFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)));
    userContact.setContactUserLastName(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)));
    userContact.setContactUserAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
    userContact.setContactUserPhone(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER)));

    cursor.close();
    return userContact;
    }

    public List<UserContact> getAllContacts(){
        List<UserContact> userContactList=new ArrayList<>();
        String selectQuery="SELECT * FROM "+TABLE_CONTACT_APP_DEMO+" ORDER BY "+COLUMN_USER_ID+" DESC;";

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor!=null && cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
            UserContact userContact=cursorToRecord(cursor);
            userContactList.add(userContact);
            cursor.moveToNext();
        }
    }
    db.close();
        return userContactList;
}

    private UserContact cursorToRecord(Cursor cursor) {
        UserContact userContact=new UserContact();
        userContact.setContactUserId(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)));
        userContact.setContactUserFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)));
        userContact.setContactUserLastName(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)));
        userContact.setContactUserAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
        userContact.setContactUserPhone(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER)));
      return userContact;
    }


    public int updateContact(UserContact userContact){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        String[] selectionArgs={userContact.getContactUserId()};

        contentValues.put(COLUMN_USER_ID,userContact.getContactUserId());
        contentValues.put(COLUMN_FIRST_NAME,userContact.getContactUserFirstName());
        contentValues.put(COLUMN_LAST_NAME,userContact.getContactUserLastName());
        contentValues.put(COLUMN_ADDRESS,userContact.getContactUserAddress());
        contentValues.put(COLUMN_PHONE_NUMBER,userContact.getContactUserPhone());
        Log.d(TAG, "Before return of update contact ");
        return db.update(TABLE_CONTACT_APP_DEMO,contentValues,COLUMN_USER_ID + " = ? ",selectionArgs);
    }
    public void deleteContact(UserContact userContact){
        SQLiteDatabase db=this.getWritableDatabase();
        String[] selectionArgs={userContact.getContactUserId()};
        db.delete(TABLE_CONTACT_APP_DEMO,COLUMN_USER_ID+" = ? ",selectionArgs);
        db.close();
    }

}
