package com.wedddingapp.shankar.todoappy;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    Toolbar toolbar;   //creating reference of Toolbar to make custom Toolabr.
    ImageView addBtn,completeBtn;    //creating reference of ImageView to put in custom Toolbar.
    EditText titleET,descriptionET;    //creating reference of EditTexts
    Button saveBtn,cancelBtn;     //creating reference of Buttons
    DatePicker datePicker;     //creating reference of DatePicker.
    ListView listView;      //creating reference of ListView.

    @Override
    //onCreate method.
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);    //Setting content View.

        //Setting toolbar with its ID and settinng it as Support ActionBar.
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Setting references with their IDs.
        addBtn=(ImageView)findViewById(R.id.add_tool_btn);
        completeBtn=(ImageView)findViewById(R.id.complete_tool_btn);
        listView=(ListView)findViewById(R.id.list);


        //Creating object of EntryReport and listing Entries by Setting adapter of CustomAdapter to listView object.
        EntryReport entryRepoParent = new EntryReport(this);
        ArrayList<SEntry> initialEntries = entryRepoParent.getEntryList();
        CustomAdapter customAdapter=new CustomAdapter(this,initialEntries);
        listView.setAdapter(customAdapter);


        //On clicking + Symbol on toolbar.
        addBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Displaying Toast.
                Toast.makeText(getApplicationContext(),"Add button",Toast.LENGTH_SHORT).show();

                //Creating object of Dialog.
                final Dialog dialog=new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.custom_dialog);   //Setting ContentView.

                //Setting references with their IDs.
                titleET=(EditText)dialog.findViewById(R.id.title_et);
                descriptionET=(EditText)dialog.findViewById(R.id.description_et);
                datePicker=(DatePicker)dialog.findViewById(R.id.datePicker);
                saveBtn=(Button)dialog.findViewById(R.id.save_btn);
                cancelBtn=(Button)dialog.findViewById(R.id.cancel_btn);

                //on Clock of SAVE in Dialog
                saveBtn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        //Checking that all information is filled
                        if(!titleET.getText().toString().isEmpty() &&
                                !descriptionET.getText().toString().isEmpty())
                        {
                            //Creating EntryReport object.
                            EntryReport entryRepo=new EntryReport(MainActivity.this);
                            SEntry SEntry =new SEntry();  //Creating SEntry object and setting its variables to user input values.
                            SEntry.title=titleET.getText().toString();
                            SEntry.description=descriptionET.getText().toString();
                            SEntry.dueDate=String.valueOf(datePicker.getDayOfMonth())+"/"+
                                    String.valueOf(datePicker.getMonth())+"/"+
                                    String.valueOf(datePicker.getYear());
                            SEntry.status=0;
                            //Inserting the object in the DB.
                            SEntry.entry_ID=entryRepo.insert(SEntry);


                            //Creating object of EntryReport and listing Entries by Setting adapter of CustomAdapter to listView object.
                            ArrayList<SEntry>  arrayList=entryRepo.getEntryList();

                            CustomAdapter adapter = new CustomAdapter(MainActivity.this,arrayList);

                            listView.setAdapter(adapter);

                            //Displaying Toast.
                            Toast.makeText(MainActivity.this,"Item Saved",Toast.LENGTH_SHORT).show();

                            //Dismiss Dialog.
                            dialog.dismiss();
                        }
                        else
                            //Displaying Toast.
                            Toast.makeText(MainActivity.this,"Please fill the information correctly",Toast.LENGTH_SHORT).show();
                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        //Dismiss Dialog.
                        dialog.dismiss();
                    }
                });

                //Showing Dialog.
                dialog.show();

            }
        });

        //on Clicking LIKE button in toolbar.
        completeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Displaying Toast.
                Toast.makeText(getApplicationContext(),"List Of completed Entries",Toast.LENGTH_SHORT).show();

                //Intent to switch to another activity.
                Intent changePage = new Intent(MainActivity.this,CompletedEntries.class);
                startActivity(changePage);   //Starting Activity.


            }
        });



        //on clicking List Item.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //Creating object of Dialog.
                final Dialog dialog=new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.custom_dialog);

                //Setting references with their IDs.
                titleET=(EditText)dialog.findViewById(R.id.title_et);
                descriptionET=(EditText)dialog.findViewById(R.id.description_et);
                datePicker=(DatePicker)dialog.findViewById(R.id.datePicker);
                saveBtn=(Button)dialog.findViewById(R.id.save_btn);
                cancelBtn=(Button)dialog.findViewById(R.id.cancel_btn);

                //Creating EntryReport object.
                final EntryReport entryRepo=new EntryReport(MainActivity.this);
                ArrayList<SEntry> arrayList=entryRepo.getEntryList();
                final SEntry SEntry =arrayList.get(position);

                //Setting EditTexts of Dialogs, the value in the DB.
                titleET.setText(SEntry.title);
                descriptionET.setText(SEntry.description);

                //when we click on save button
                saveBtn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        //Checking information is filled
                        if(!titleET.getText().toString().isEmpty() &&
                                !descriptionET.getText().toString().isEmpty())
                        {
                            //Updating information in DB just like insering.
                            SEntry updatedSEntry =new SEntry();
                            updatedSEntry.entry_ID= SEntry.entry_ID;
                            updatedSEntry.title=titleET.getText().toString();
                            updatedSEntry.description=descriptionET.getText().toString();
                            updatedSEntry.dueDate=String.valueOf(datePicker.getDayOfMonth())+"/"+
                                    String.valueOf(datePicker.getMonth())+"/"+
                                    String.valueOf(datePicker.getYear());
                            updatedSEntry.status= SEntry.status;

                            entryRepo.update(updatedSEntry);

                            ArrayList<SEntry> updatedArrayList = entryRepo.getEntryList();

                            CustomAdapter adapter = new CustomAdapter(MainActivity.this,updatedArrayList);

                            listView.setAdapter(adapter);

                            Toast.makeText(MainActivity.this,"List Updated",Toast.LENGTH_SHORT).show();

                            dialog.dismiss();
                        }
                        else
                            Toast.makeText(MainActivity.this,"Please fill the information correctly",Toast.LENGTH_SHORT).show();
                    }
                });

                //on clicking CANCEL.
                cancelBtn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();
                    }
                });

                //showing Dialog.
                dialog.show();
            }
        });

        //on Long clicking the list item.
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                //Creating object of EntryRepo.
                EntryReport entryRepo = new EntryReport(MainActivity.this);
                ArrayList<SEntry> entries = entryRepo.getEntryList();

                //creating reference of SEntry.
                SEntry completedSEntry = entries.get(position);

                completedSEntry.status=1;
                entryRepo.update(completedSEntry);

                //Creating object of EntryRepo and listing Entries by Setting adapter of CustomAdapter to listView object.
                ArrayList<SEntry> updatedEntries = entryRepo.getEntryList();

                CustomAdapter adapter = new CustomAdapter(MainActivity.this,updatedEntries);

                listView.setAdapter(adapter);

                //Displaying Toast.
                Toast.makeText(MainActivity.this,"Item Updated",Toast.LENGTH_SHORT).show();

                return true;   //returning true.
            }
        });

    }

}
