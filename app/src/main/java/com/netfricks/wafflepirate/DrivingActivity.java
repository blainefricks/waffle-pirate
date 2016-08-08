package com.netfricks.wafflepirate;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DrivingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving);
        permissionCheck();
    }

    public void permissionCheck() {
        int permissionCheckBluetooth = ContextCompat.checkSelfPermission(DrivingActivity, Manifest.permission.BLUETOOTH);

        if (permissionCheckBluetooth != PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(DrivingActivity, new String[]{Manifest.permission.BLUETOOTH}, MY_PERMISSIONS_REQUEST_BLUETOOTH);

            // MY_PERMISSIONS_REQUEST_BLUETOOTH is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            // TODO: make MY_PERMISSIONS_REQUEST_BLUETOOTH an app-defined int constant?
        }
    }
}
