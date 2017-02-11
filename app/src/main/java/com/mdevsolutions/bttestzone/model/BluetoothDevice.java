package com.mdevsolutions.bttestzone.model;

/**
 * Local BluetoothDevice Data to be displayed in the recycler view
 */
public class BluetoothDevice {
    public String mName;
    public String mAddress;
    public int mRssi;

    public int getmRssi() {
        return mRssi;
    }

    public void setRssi(int rssi) {
        this.mRssi = mRssi;
    }


    public String getmName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        this.mAddress = address;
    }




}
