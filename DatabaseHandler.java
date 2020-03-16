package com.example.user.videoplayer;

/**
 * Created by User on 2/6/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="myContactsManager";
    private static final String TABLE_NAME="contact";
    private static final String KEY_ID="id";
    private static final String KEY_NAME="name";
    private static final String KEY_CONTACTNO="phoneNo";

    public DatabaseHandler(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        //Log.v("db created", "yes");
        String CREATE_CONTACT_TABLE="CREATE TABLE "+ TABLE_NAME +"("
                + KEY_ID +" INTEGER PRIMARY KEY,"
                + KEY_NAME +" TEXT,"
                + KEY_CONTACTNO +" TEXT" +")";

        String sql= "CREATE TABLE CONTACT (ID INTEGER PRIMARY KEY" +
                ", NAME TEXT" +
                ", PHONENO TEXT)";
        db.execSQL(CREATE_CONTACT_TABLE);
        //Log.v("db created", "yes");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void addContact(Contact contact)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "INSERT INTO CONTACT(NAME,PHONENO)" +
                "VALUES('"+contact.getName()+"','"+contact.getContactNumber()+"')";

        //String qry = "INSERT INTO CONTACT(NAME,PHONENO)VALUES('XYZ','016')";
        //db.execSQL(query);

        ContentValues value=new ContentValues();
        value.put(KEY_NAME, contact.getName());
        value.put(KEY_CONTACTNO,contact.getContactNumber());

        db.insert(TABLE_NAME, null,value);

        db.close();

    }

    public Contact getSingleContact(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT ID,NAME,PHONENO FROM CONTACT WHERE ID = "+id+" OR NAME ='A'";
        Cursor cursor=db.rawQuery(query, null);
        //Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_ID,KEY_NAME,KEY_CONTACTNO}, "Id=?",new String[]{String.valueOf(id)} ,null, null,null, null);
        Contact myContact = null;
        if(cursor.moveToFirst())
        {
            myContact=new Contact(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2));
        }

        return myContact;
    }

    public List<Contact> getAllContact()
    {
        List<Contact> mycontactList=new ArrayList<Contact>();

        String selectquery="SELECT * FROM "+ TABLE_NAME;// where phoneno LIKE '017%'";

        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.rawQuery(selectquery, null);

        if(cursor.moveToFirst())
        {
            do
            {
                Contact contact= new Contact(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2));
                mycontactList.add(contact);
            }while(cursor.moveToNext());
        }

        return mycontactList;
    }

    //public void updateContact(Contact contact)
   // {
        //SQLiteDatabase db=this.getWritableDatabase();

        //String query = "UPDATE CONTACT SET NAME='XYZ',phoneno='014' WHERE ID = "+contact.getId();
        //db.execSQL(query);

        public void updateContact(Contact contact)
        {
            SQLiteDatabase db=this.getWritableDatabase();

            String query = "UPDATE CONTACT SET name='"+contact.getName()+"',phoneNo='"+contact.getContactNumber()+"' WHERE ID = "+contact.getId();
            db.execSQL(query);



            db.close();
        }

       // db.close();
    //}

    public void deleteContact(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String query = "DELETE FROM "+TABLE_NAME+" WHERE  ID="+id;
        db.execSQL(query);

        //db.delete(TABLE_NAME, KEY_ID+"=?", new String[]{String.valueOf(id)});
        db.close();
    }

}
