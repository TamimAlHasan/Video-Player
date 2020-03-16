package com.example.user.videoplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.List;

public class DatabaseActivity extends AppCompatActivity {

    Button goToSecondPageBtn,addNewContact,deleteContact,updateContact,getSingleContactBtn;
    EditText userName,phoneNumber,contactId;
    TextView display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        goToSecondPageBtn = (Button) findViewById(R.id.goToSecondPageBtn);
        userName = (EditText) findViewById(R.id.userNameET);

        addNewContact = (Button) findViewById(R.id.addNewContactBtn);
        phoneNumber = (EditText) findViewById(R.id.phoneNumberET);

        deleteContact = (Button) findViewById(R.id.deleteContactBtn);
        contactId = (EditText) findViewById(R.id.contactIdET);

        updateContact=(Button)findViewById(R.id.updateContact);
        contactId = (EditText) findViewById(R.id.contactIdET);


        getSingleContactBtn = (Button) findViewById(R.id.getSingleContactBtn);

        display = (TextView) findViewById(R.id.displayTV);
        display.setMovementMethod(new ScrollingMovementMethod());

        final DatabaseHandler db = new DatabaseHandler(this);

        addNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userNameValue = userName.getText().toString();
                String phoneValue = phoneNumber.getText().toString();
                if (userNameValue.equals("") || phoneValue.equals("")) {
                    Toast.makeText(getApplicationContext(), "Information Missing", Toast.LENGTH_SHORT).show();
                } else {
                    db.addContact(new Contact(userNameValue, phoneValue));
                    Toast.makeText(getApplicationContext(), "New Contact Is Added.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contactIdValue = contactId.getText().toString();
                if (contactIdValue.equals("")) {
                    Toast.makeText(getApplicationContext(), "Information Missing", Toast.LENGTH_SHORT).show();
                } else {
                    db.deleteContact(Integer.parseInt(contactIdValue));
                    Toast.makeText(getApplicationContext(), contactIdValue + " is deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
       /** updateContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contactIdValue = contactId.getText().toString();
                String userNameValue = userName.getText().toString();
                String phoneValue = phoneNumber.getText().toString();
                //if(idNo.equals("") || equation.equals("") || root.equals("") )
                if(contactIdValue.equals("")  )
                {
                    Toast.makeText (getApplicationContext(),"Information Missing",Toast.LENGTH_SHORT).show();
                }
                else{
                    db.updateContact(new Contact(Integer.parseInt(contactIdValue),userNameValue,phoneValue));
                    Toast.makeText (getApplicationContext(),contactIdValue+" is updated",Toast.LENGTH_SHORT).show();
                }
            }
        });**/
       updateContact.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String contactIdValue = contactId.getText().toString();
               String userNameValue = userName.getText().toString();
               String phoneValue = phoneNumber.getText().toString();
               //if(idNo.equals("") || equation.equals("") || root.equals("") )
               if(contactIdValue.equals("")  )
               {
                   Toast.makeText (getApplicationContext(),"Information Missing",Toast.LENGTH_SHORT).show();
               }
               else{
                   db.updateContact(new Contact(Integer.parseInt(contactIdValue),userNameValue,phoneValue));
                   Toast.makeText (getApplicationContext(),contactIdValue+" is updated",Toast.LENGTH_SHORT).show();
               }
           }

       });

        goToSecondPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Contact> myContactList = db.getAllContact();
                String result = "";
                for (Contact myContact : myContactList) {
                    result += "Id: " + myContact.getId() + " Name: " + myContact.getName() + " Phone No.: " + myContact.getContactNumber();
                    result += "\n";
                    Log.d("Result", result);
                }
                if (myContactList.size() == 0) {
                    result = "No contact to display.";
                }
                display.setText(result);

            }
        });

        getSingleContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contactIdValue = contactId.getText().toString();
                if (contactIdValue.equals("")) {
                    Toast.makeText(getApplicationContext(), "Information Missing", Toast.LENGTH_SHORT).show();
                } else {
                    Contact myContact = db.getSingleContact(Integer.parseInt(contactIdValue));
                    String result = "";
                    if (myContact == null) {
                        result = "No contact to display.";
                    } else {
                        result += "Id: " + myContact.getId() + " Name: " + myContact.getName() + " Phone No.: " + myContact.getContactNumber();
                    }
                    display.setText(result);
                }


            }
        });

    }

    public void showText(View v) {
        //String userNameValue= userName.getText().toString();
        //display.setText("Hi,"+ userNameValue);

        // Intent i = new Intent(getApplicationContext(),SecondActivity.class);
        // i.putExtra(USER_NAME,userName.getText().toString());
        // startActivity(i);
    }


    @Override
    protected void onStart() {
        super.onStart();
        notify("onStart");
    }


    @Override
    protected void onPause() {
        super.onPause();
        notify("onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        notify("onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        notify("onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        notify("onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notify("onDestroy");
    }

   /* @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        notify("onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        notify("onSaveInstanceState");
    }*/

    private void notify(String methodName) {
        Log.d("Demo-MainActivity: ", methodName);
    }


}

