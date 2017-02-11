package com.mdevsolutions.bttestzone.model;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.provider.SyncStateContract;
import android.util.Log;

import com.mdevsolutions.bttestzone.Constants;
import com.mdevsolutions.bttestzone.adapter.DeviceAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Michi on 5/02/2017.
 */
public class DeviceData {

//    private static String mName;
//    private static String mAddress;
//

    public static List<BtDevice> getDeviceData() {
        List<BtDevice> devices = new ArrayList<>();

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter.getBondedDevices();


        for (BluetoothDevice device : mPairedDevices) {
            BtDevice dev = new BtDevice();
            String name = device.getName();

            String address = device.getAddress();

            dev.setName(name);
            dev.setAddress(address);
            devices.add(dev);
            Log.d(Constants.DEBUG_TAG, "I've just added " + dev.getName());

        }
       return devices;

    }
}





