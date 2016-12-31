package com.example.android.bluetoothchat;

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.android.DatabaseClass;

public class disData extends AppCompatActivity {
    //DatabaseHelper db = new DatabaseHelper(this);
    DatabaseClass data = new DatabaseClass(this);

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dis_data);
        lv = (ListView) findViewById(R.id.lv);
        data.open();
        populateListView();
    }

    public void populateListView(){
        Cursor c = data.getRecord();
        String[] Field = new String[] {data.CVAL, data.CDnT};
        int[] ids = new int[] {R.id.dnt, R.id.val};

        Toast.makeText(this, data.CVAL +" "+ data.CDnT, Toast.LENGTH_LONG).show();
        SimpleCursorAdapter simpleCursorAdapter;
        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.listform, c, Field, ids, 0);
        lv.setAdapter(simpleCursorAdapter);
    }

    public void deleteData(View v){

        data.eraseData();
        populateListView();

    }

}
