package com.netfricks.wafflepirate;

import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;

/**
 * Created by blainefricks on 11/11/16.
 */

public class HandleBluetooth extends AppCompatActivity {

    static BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    static ArrayAdapter mArrayAdapter = null;

    public boolean isBluetoothSupported(Context context) {
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Toast.makeText(context, "Bluetooth not supported on this device.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    // Detect paired devices
    public List getPairedDevices() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        List<String> listPairedDevices = new ArrayList<String>();
        listPairedDevices.add("None Selected");
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                listPairedDevices.add(device.getName() + "\n" + device.getAddress());
//                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());

            }
        }
        return listPairedDevices;
    }

    // Check if currently connected device is user's car
    public boolean comparePairedDeviceCarBluetooth() {
        boolean deviceConnected = false;
        // TODO: Get currently paired device

        // TODO: Is that device listed in readCarBluetoothDeviceList()?
        readCarBluetoothDeviceList();

        // TODO: Return true if there is a match
        return deviceConnected;
    }

    // TODO: Save list of car bluetooth devices
    public void saveCarBluetoothDeviceList(Context context, String newDeviceName, String newDeviceAddress) {
        // TODO: Get a handle on list
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);

        // TODO: Update list with selected devices
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.saved_driving_paired_device_name), newDeviceName);
        editor.putString(getString(R.string.saved_driving_paired_device_address), newDeviceAddress);
        editor.commit();
//        editor.apply();
        Toast.makeText(context, "Device Name: "+newDeviceName+", Device Address: "+newDeviceAddress, Toast.LENGTH_LONG).show();

    }

    // TODO: Read list of saved car bluetooth devices
    public void readCarBluetoothDeviceList() {
        // TODO: Get list of saved car bluetooth devices
    }
}
