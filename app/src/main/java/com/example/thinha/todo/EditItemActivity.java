package com.example.thinha.todo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class EditItemActivity extends AppCompatActivity {

    NumberPicker Date;
    NumberPicker Month;
    NumberPicker Year;
    NumberPicker Hour;
    NumberPicker Minute;
    NumberPicker Second;

    //checkEnableContent
    int itemIndex;
    EditText editText;

    Button Save;

    int checkbtn = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        editText = (EditText) findViewById(R.id.edit);
        Save = (Button) findViewById(R.id.save);
        Hour = (NumberPicker) findViewById(R.id.hour);
        Minute = (NumberPicker) findViewById(R.id.minute);
        Second = (NumberPicker) findViewById(R.id.second);
        Date = (NumberPicker) findViewById(R.id.date);
        Month = (NumberPicker) findViewById(R.id.month);
        Year = (NumberPicker) findViewById(R.id.year);


        Date.setMinValue(1);
        Date.setMaxValue(31);

        Month.setMinValue(1);
        Month.setMaxValue(12);

        Year.setMinValue(1990);
        Year.setMaxValue(2000);

        Hour.setMinValue(0);
        Hour.setMaxValue(23);

        Minute.setMinValue(0);
        Minute.setMaxValue(59);

        Second.setMinValue(0);
        Second.setMaxValue(59);

        Intent intent = getIntent();
        itemIndex = intent.getIntExtra("ItemID", -1);
        checkbtn = intent.getIntExtra("checkBtn", 1);

        if (checkbtn == 0) {
            Save.setText("EDIT");

            setEnableContent(false);
        } else {
            Save.setText("ADD");

        }

        if (itemIndex >= 0) {
            editText.setText(MainActivity.nameArrayList.get(itemIndex));
            Hour.setValue(Integer.parseInt(MainActivity.arrayHour.get(itemIndex)));
            Minute.setValue(Integer.parseInt(MainActivity.arrayMinute.get(itemIndex)));
            Second.setValue(Integer.parseInt(MainActivity.arraySecond.get(itemIndex)));
            Date.setValue(Integer.parseInt(MainActivity.arrayDate.get(itemIndex)));
            Month.setValue(Integer.parseInt(MainActivity.arrayMonth.get(itemIndex)));
            Year.setValue(Integer.parseInt(MainActivity.arrayYear.get(itemIndex)));

           }


    }

    public void Delete(View view) {
        if (itemIndex >= 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)

                    .setTitle("Are you sure?")
                    .setMessage("Do you want to delete this note?")

                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override

                        public void onClick(DialogInterface dialog, int which) {



                            MainActivity.arrayHour.remove(itemIndex);
                            MainActivity.arrayMinute.remove(itemIndex);
                            MainActivity.arraySecond.remove(itemIndex);
                            MainActivity.arrayDate.remove(itemIndex);
                            MainActivity.arrayMonth.remove(itemIndex);
                            MainActivity.arrayYear.remove(itemIndex);

                            MainActivity.nameArrayList.remove(itemIndex);
                            MainActivity.arrayAdapter.notifyDataSetChanged();

                            MainActivity.mydb.deleteContact(MainActivity.arrayItemID.get(itemIndex));
                            MainActivity.arrayItemID.remove(itemIndex);
                            MainActivity.id--;

                            finish();
                        }

                    })

                    .setNegativeButton("No", null)

                    .show();
        }
        else
        {
            Toast.makeText(getBaseContext(),"No notes exists in list",Toast.LENGTH_SHORT).show();
        }
    }




    public void setEnableContent(boolean check)
    {
        editText.setEnabled(check);

        Date.setEnabled(check);

        Month.setEnabled(check);

        Year.setEnabled(check);

        Hour.setEnabled(check);

        Minute.setEnabled(check);

        Second.setEnabled(check);
    }

    public void done(View view)
    {
        //checkbtn = 1: add
        //checkbtn = 2: save
        //checkbtn = 0: edit

        if (checkbtn==2 )        {

            if (editText.getText().toString().equals(""))
            {
                Toast.makeText(getBaseContext(),"Content is not empty!",Toast.LENGTH_SHORT).show();
            }
           else
            {
                MainActivity.nameArrayList.set(itemIndex,editText.getText().toString());
                MainActivity.arrayDate.set(itemIndex,String.valueOf(Date.getValue()));
                MainActivity.arrayMonth.set(itemIndex,String.valueOf(Month.getValue()));
                MainActivity.arrayYear.set(itemIndex,String.valueOf(Year.getValue()));

                MainActivity.arrayHour.set(itemIndex,String.valueOf(Hour.getValue()));
                MainActivity.arrayMinute.set(itemIndex,String.valueOf(Minute.getValue()));
                MainActivity.arraySecond.set(itemIndex,String.valueOf(Second.getValue()));

                int hourItem,minuteItem,secondItem;
                int dateItem,monthItem,yearItem;

                hourItem =Integer.valueOf(Hour.getValue());
                minuteItem =Integer.valueOf(Minute.getValue());
                secondItem =Integer.valueOf(Second.getValue());

                dateItem =Integer.valueOf(Date.getValue());
                monthItem =Integer.valueOf(Month.getValue());
                yearItem =Integer.valueOf(Year.getValue());

                //update table

                MainActivity.mydb.updateContact(MainActivity.arrayItemID.get(itemIndex),editText.getText().toString(),hourItem,minuteItem,secondItem,dateItem,monthItem,yearItem);

                Save.setText("ADD");
                editText.setText("");
                checkbtn=1;
                this.finish();
            }
        }
        else if (checkbtn==1)
        {
            if (editText.getText().toString().equals(""))
            {
                Toast.makeText(getBaseContext(),"Content is not empty!",Toast.LENGTH_SHORT).show();
            }
            else
            {
                MainActivity.id++;
                Save.setText("ADD");
                //add new item for arrays
                MainActivity.arrayDate.add(String.valueOf(Date.getValue()));
                MainActivity.arrayMonth.add(String.valueOf(Month.getValue()));
                MainActivity.arrayYear.add(String.valueOf(Year.getValue()));

                MainActivity.arrayHour.add(String.valueOf(Hour.getValue()));
                MainActivity.arrayMinute.add(String.valueOf(Minute.getValue()));
                MainActivity.arraySecond.add(String.valueOf(Second.getValue()));

                //to update new value for adapter
                MainActivity.nameArrayList.add(editText.getText().toString());
                MainActivity.arrayItemID.add(MainActivity.id);
                MainActivity.arrayAdapter.notifyDataSetChanged();

                int hourItem,minuteItem,secondItem;
                int dateItem,monthItem,yearItem;

                //get new value to insert to table
                hourItem=Integer.valueOf(Hour.getValue());
                minuteItem=Integer.valueOf(Minute.getValue());
                secondItem=Integer.valueOf(Second.getValue());
                dateItem=Integer.valueOf(Date.getValue());
                monthItem=Integer.valueOf(Month.getValue());
                yearItem=Integer.valueOf(Year.getValue());


                //insert into table
                MainActivity.mydb.insertContact(MainActivity.id,editText.getText().toString(),hourItem,minuteItem,secondItem,dateItem,monthItem,yearItem);
                editText.setText("");
                checkbtn=0;
                this.finish();
            }
        }
        else if (checkbtn==0)
        {
            setEnableContent(true);

            Save.setText("SAVE");
            checkbtn=2;
        }

    }


}
