package com.mdevsolutions.bttestzone;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mdevsolutions.bttestzone.adapter.DeviceAdapter;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter mBluetoothAdapter;
    private RecyclerView mDeviceRecView;
    private DeviceAdapter mDeviceAdapter = null;
    private Set<BluetoothDevice> mPairedDevices;
    private int mPairedCount = 0;
    private EditText mPairedEt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDeviceRecView = (RecyclerView) findViewById(R.id.deviceRv);
        mDeviceRecView.setLayoutManager(new LinearLayoutManager(this));
        setContentView(R.layout.activity_main);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        init();
    }


    @Override
    protected void onResume() {
        super.onResume();
        //enable BT on device
        if(!mBluetoothAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(mBluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, Constants.REQUEST_ENABLE_BT);
        }
        //mDeviceAdapter = new DeviceAdapter(this);
        mDeviceRecView.setAdapter(mDeviceAdapter);
    }

    private void init() {
        //check if BT is supported on host device
        if(mBluetoothAdapter==null){
            Toast.makeText(getApplicationContext(),"Your device does not support Bluetooth",Toast.LENGTH_LONG).show();
            finish();
        }


    }

    private void queryDevice() {
        mPairedEt = (EditText)findViewById(R.id.PairedDeviceseditText);
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        Toast.makeText(this,"Devices Found", Toast.LENGTH_LONG).show();
        if(pairedDevices.size() >0){
            //greater than 0 so at least one device was found
            for (BluetoothDevice device : pairedDevices){
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); //MAC addy
                mPairedCount++;
            }

            mPairedEt.setText(""+mPairedCount)
            ;
        }
        else{
            Toast.makeText(this,"There are no paired devices at this moment.",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //check requestCode to know what requested the result
        switch (requestCode){
            case Constants.REQUEST_ENABLE_BT:
                if(resultCode==RESULT_OK){
                    Toast.makeText(getApplicationContext(), "Bluetooth successfully enabled.", Toast.LENGTH_LONG).show();
                }
                else if (resultCode==RESULT_CANCELED){
                    Toast.makeText(getApplicationContext(), "Bluetooth enabling failed.", Toast.LENGTH_LONG).show();
                }
        }
    }
}
