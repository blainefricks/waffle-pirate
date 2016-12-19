package com.netfricks.wafflepirate;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class DrivingActivity extends AppCompatActivity {

    Switch drivingActivitySwitch;
    Spinner spinnerPairedDevices;
    RelativeLayout layoutDrivingOptions;
    List pairedDevices;

    // Permissions needed
    String[] mAppPermissions = {Manifest.permission.READ_PHONE_STATE,
                                    Manifest.permission.READ_SMS,
                                    Manifest.permission.RECEIVE_SMS,
                                    Manifest.permission.SEND_SMS};
    // Manifest.permission.READ_PHONE_STATE
    // Manifest.permission.READ_SMS
    // Manifest.permission.RECEIVE_SMS
    // Manifest.permission.SEND_SMS
    private static final int DRIVING_PERMISSION_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving);

        spinnerPairedDevices = (Spinner) findViewById(R.id.spinnerPairedDevices);
        layoutDrivingOptions = (RelativeLayout) findViewById(R.id.layoutDrivingSettings);

        // Read preference file to get switch status
        final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        // Read the file
        final int defaultValue = getResources().getInteger(R.integer.saved_driving_switch_state_default);
        long switchState = sharedPref.getInt(getString(R.string.saved_driving_switch_state), defaultValue);

        final HandleBluetooth handleBluetooth = new HandleBluetooth();

        // refresh device list
        pairedDevices = handleBluetooth.getPairedDevices();
        ArrayAdapter<String> adapterPairedDevices = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, pairedDevices);
        spinnerPairedDevices.setAdapter(adapterPairedDevices);

//        handleBluetooth.saveCarBluetoothDeviceList(DrivingActivity.this, "Test Device Name", "00:AA:BB:CC:DD:11");

        // If the switch is off, only check for permission when the switch is turned on.
        drivingActivitySwitch = (Switch) findViewById(R.id.switchActivityDriving);

        if (switchState == 1) {
            if (handleBluetooth.isBluetoothSupported(DrivingActivity.this)) {
                enableDrivingMode(true);
            } else {
                enableDrivingMode(false);
            }
        }

        drivingActivitySwitch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (drivingActivitySwitch.isChecked()){
                    if (handleBluetooth.isBluetoothSupported(DrivingActivity.this)) {
                        enableDrivingMode(true);
                    } else {
                        enableDrivingMode(false);
                    }
                } else {
                    enableDrivingMode(false);
                }
            }
        });
    }

//    public void getPermissions(final String mAppPermission, final int permissionRequest) {
    public void getPermissions(final String[] mAppPermissions, int permissionRequest) {

        ActivityCompat.requestPermissions(this, mAppPermissions, permissionRequest);

        /*
        if (ContextCompat.checkSelfPermission(this, mAppPermissions) != PackageManager.PERMISSION_GRANTED) {

            // Check if the user has been asked about this permission already and denied
            // it. If so, we want to give more explanation about why the permission is needed.

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, mAppPermissions)){

                // Show UI to explain to the user why we need the these permissions
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.dialog_message_DRIVING_PERMISSIONS)
                        .setTitle(R.string.dialog_title_DRIVING_PERMISSIONS);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        ActivityCompat.requestPermissions(DrivingActivity.this, mAppPermissions,
                                READ_PHONE_STATE_PERMISSION_REQUEST);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                ActivityCompat.requestPermissions(this, mAppPermissions,
                        READ_PHONE_STATE_PERMISSION_REQUEST);
            }
        }
        */
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case DRIVING_PERMISSION_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay!
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Missing permission(s) required to use this feature.\nPlease toggle switch again or go to Application Settings to grant.", Toast.LENGTH_LONG).show();
                    enableDrivingMode(false);
                }
                return;
            }
        }
    }

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults){

        // Make sure it's our original request
        if (requestCode == DRIVING_PERMISSION_REQUEST) {

            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
            } else {
                // showRational = false if user clicks Never Ask Again, otherwise true
                // TODO: Figure this out, is returning false every time, even though permission is granted

                boolean showRational = shouldShowRequestPermissionRationale(permissions[0]);

                if (showRational) {
                    Toast.makeText(this, "Permission required to use this feature", Toast.LENGTH_SHORT).show();

                    // turn switch off.
                    drivingActivitySwitch.setChecked(false);

                    // Save switch status to off in preference file.
                    setDrivingActivitySwitchState(0);
                } else {
                    Toast.makeText(this, "To enable this feature, go to Settings and allow Phone & SMS permissions", Toast.LENGTH_LONG).show();

                    // turn switch off.
                    drivingActivitySwitch.setChecked(false);

                    // Save switch status to off in preference file.
                    setDrivingActivitySwitchState(0);
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    */

    public void setDrivingActivitySwitchState(int newState) {

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        // Save switch status in preference file.
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.saved_driving_switch_state), newState);
        editor.commit();
//        editor.apply();
    }

    public void displayDrivingActivitySettings(boolean toDisplay){

        if (toDisplay) {
            layoutDrivingOptions.setVisibility(View.VISIBLE);
        } else {
            layoutDrivingOptions.setVisibility(View.INVISIBLE);
        }
    }

    public void enableDrivingMode(boolean enableMode) {
        if(enableMode) {
            getPermissions(mAppPermissions, DRIVING_PERMISSION_REQUEST);
            setDrivingActivitySwitchState(1);
            displayDrivingActivitySettings(true);
            drivingActivitySwitch.setChecked(true);
        } else {
            setDrivingActivitySwitchState(0);
            displayDrivingActivitySettings(false);
            drivingActivitySwitch.setChecked(false);
        }
    }

}
