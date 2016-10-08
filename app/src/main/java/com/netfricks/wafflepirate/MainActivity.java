package com.netfricks.wafflepirate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

//    private ListView mainListView;
//    private String[] mainOptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

//        mainListView = (ListView) findViewById(R.id.mainListView);

//        mainOptions = new String[] {"Driving Settings","Calendar Settings"};

//        ArrayList<String> mainOptionsList = new ArrayList<String>();
//        mainOptionsList.addAll(Arrays.asList(mainOptions));

//        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, R.layout.list_item, mainOptionsList);

//        mainListView.setAdapter(listAdapter);
    }

    public void viewActivityDriving(View view) {
        Intent intent = new Intent(this, DrivingActivity.class);
        startActivity(intent);
    }

    public void viewActivityCalendar(View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }
}
