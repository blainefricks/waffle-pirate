package com.netfricks.wafflepirate;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.Set;

/**
 * Created by blainefricks on 11/11/16.
 */

public class HandleBluetooth {


    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    ArrayAdapter mArrayAdapter = null;

    public static boolean isBluetoothSupported(Context context) {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Toast.makeText(context, "Bluetooth not supported on this device.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    // Detect paired devices
    public ArrayAdapter getPairedDevices() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }
        return mArrayAdapter;
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
    public void saveCarBluetoothDeviceList() {
        // TODO: Get a handle on list
        // TODO: Update list with selected devices
    }

    // TODO: Read list of saved car bluetooth devices
    public void readCarBluetoothDeviceList() {
        // TODO: Get list of saved car bluetooth devices
    }
}
