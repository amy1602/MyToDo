package com.example.thinha.todo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    static int id;
    static ArrayAdapter arrayAdapter;
    static ArrayList<String> nameArrayList = new ArrayList<String>();

    static Set<String> set;
    ListView list;
    Button btn;
    static DBHelper mydb ;
    static SQLiteDatabase mySQL;

    static ArrayList<Integer> arrayItemID = new ArrayList<Integer>();
    static ArrayList<String> arrayDate = new ArrayList<String>();
    static ArrayList<String> arrayMonth = new ArrayList<String>();
    static ArrayList<String> arrayYear = new ArrayList<String>();
    static ArrayList<String> arrayHour = new ArrayList<String>();
    static ArrayList<String> arrayMinute = new ArrayList<String>();
    static ArrayList<String> arraySecond = new ArrayList<String>();
    @Override
    protected void onStart() {
        super.onStart();
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.list);
        btn=(Button)findViewById(R.id.btnAdd);


        mydb = new DBHelper(this);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), EditItemActivity.class);
                intent.putExtra("ItemID",i);
                intent.putExtra("checkBtn",0);
                startActivity(intent);
            }
        });

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nameArrayList);
        list.setAdapter(arrayAdapter);


        mySQL=mydb.getReadableDatabase();

        id = mydb.getLastId();

        mydb.parseData(mydb.getData());
        arrayAdapter.notifyDataSetChanged();

        Toast.makeText(getBaseContext(),"id = "+id,Toast.LENGTH_SHORT).show();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mySQL=mydb.getWritableDatabase();
                Intent intent = new Intent(getApplicationContext(), EditItemActivity.class);
                startActivity(intent);
                arrayAdapter.notifyDataSetChanged();
            }
        });




        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int positon, long l) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)

                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")

                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override

                            public void onClick(DialogInterface dialog, int which) {

                                MainActivity.nameArrayList.remove(positon);

                                MainActivity.arrayAdapter.notifyDataSetChanged();

                            }

                        })

                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        mySQL=mydb.getWritableDatabase();
        if (id == R.id.add);
        {
            Intent intent = new Intent(getApplicationContext(), EditItemActivity.class);
            intent.putExtra("ItemID", nameArrayList.size() -1);
            startActivity(intent);
            return true;
        }
    }
}
