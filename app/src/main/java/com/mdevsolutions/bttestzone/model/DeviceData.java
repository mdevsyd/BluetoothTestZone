package com.mdevsolutions.bttestzone.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michi on 5/02/2017.
 */
public class DeviceData {

    private static String[] names = {"Device 1", "Device 2", "Device 3"};
    private static String[] addresses = {"blah", "doop", "lingling"};
    private static int[] rssi = {100, 99, 28};

    public static List<BluetoothDevice> getDeviceData() {
        List<BluetoothDevice> devices = new ArrayList<>();

        for (int i = 0; i < names.length && i < addresses.length && i < rssi.length; i++) {
            BluetoothDevice dev = new BluetoothDevice();
            dev.setName(names[i]);
            dev.setAddress(addresses[i]);
            dev.setRssi(rssi[i]);
            devices.add(dev);

        }

        return devices;
    }
}





