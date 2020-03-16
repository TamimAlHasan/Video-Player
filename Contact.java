package com.example.user.videoplayer;

/**
 * Created by User on 2/6/2018.
 */


public class Contact {
    int _id;
    String _name;
    String _phoneNumber;

    public Contact()
    {

    }
    public Contact(int Id,String Name ,String ContactNumber)
    {

        this._id=Id;
        this._name=Name;
        this._phoneNumber=ContactNumber;

    }

    public Contact(String Name,String ContactNumber)
    {
        this._name=Name;
        this._phoneNumber=ContactNumber;

    }

    public int getId()
    {
        return this._id;
    }
    public void setId(int Id)
    {
        this._id=Id;
    }

    public String getName()
    {
        return this._name;
    }
    public void setName(String Name)
    {
        this._name=Name;
    }

    public String getContactNumber()
    {
        return this._phoneNumber;
    }
    public void setContactNumber(String ContactNumber)
    {
        this._phoneNumber=ContactNumber;
    }



}

