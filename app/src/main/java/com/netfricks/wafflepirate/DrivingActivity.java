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
import android.widget.Switch;
import android.widget.Toast;

public class DrivingActivity extends AppCompatActivity {

    Switch drivingActivitySwitch;
    private static final int DRIVING_PERMISSION_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving);

        // Read preference file to get switch status
        final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        // Read the file
        final int defaultValue = getResources().getInteger(R.integer.saved_driving_switch_state_default);
        long switchState = sharedPref.getInt(getString(R.string.saved_driving_switch_state), defaultValue);

        // If the switch is off, only check for permission when the switch is turned on.
        drivingActivitySwitch = (Switch) findViewById(R.id.switchActivityDriving);

        if (switchState == 1) {
            getPermission(Manifest.permission.BLUETOOTH,DRIVING_PERMISSION_REQUEST);
            drivingActivitySwitch.setChecked(true);
            isBluetoothSupported();
            // TODO: Show UI for Bluetooth Rules
        }

        drivingActivitySwitch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (drivingActivitySwitch.isChecked()){
                    // Check for Calendar Permission
                    getPermission(Manifest.permission.BLUETOOTH,1);

                    // Write to file
                    setDrivingActivitySwitchState(1);

                    isBluetoothSupported();

                    // TODO: Show UI for Bluetooth Rules

                } else {
                    // TODO: Hide UI for Bluetooth Rules

                    // Write to file
                    setDrivingActivitySwitchState(0);

                }
            }
        });
    }

    public void isBluetoothSupported() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Toast.makeText(this, "Bluetooth not supported on this device.", Toast.LENGTH_SHORT).show();
            drivingActivitySwitch.setChecked(false);
            setDrivingActivitySwitchState(0);
        }
    }

    public void getPermission(final String permissionManifest, final int permissionRequest) {
        if (ContextCompat.checkSelfPermission(this, permissionManifest) != PackageManager.PERMISSION_GRANTED) {

            // Check if the user has been asked about this permission already and denied
            // it. If so, we want to give more explanation about why the permission is needed.

            if (shouldShowRequestPermissionRationale(permissionManifest)){

                // Show UI to explain to the user why we need the bluetooth permission
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.dialog_message_BLUETOOTH)
                        .setTitle(R.string.dialog_title_BLUETOOTH);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        requestPermissions(new String[]{permissionManifest},
                                permissionRequest);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

    // Callback with the request from calling requestPermissions(...)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults){
        final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        // Make sure it's our original request
        if (requestCode == DRIVING_PERMISSION_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
            } else {
                // showRational = false if user clicks Never Ask Again, otherwise true
                boolean showRational = shouldShowRequestPermissionRationale(Manifest.permission.BLUETOOTH);

                if (showRational) {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();

                    // turn switch off.
                    drivingActivitySwitch.setChecked(false);

                    // Save switch status to off in preference file.
                    setDrivingActivitySwitchState(0);
                } else {
                    Toast.makeText(this, "To enable this feature, go to Settings to allow permissions", Toast.LENGTH_LONG).show();

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

    public void setDrivingActivitySwitchState(int newState) {

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        // Save switch status in preference file.
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.saved_driving_switch_state), newState);
        editor.commit();
    }

}
