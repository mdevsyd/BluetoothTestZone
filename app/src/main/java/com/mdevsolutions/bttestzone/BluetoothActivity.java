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
import com.mdevsolutions.bttestzone.model.BtDevice;
import com.mdevsolutions.bttestzone.model.DeviceData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BluetoothActivity extends AppCompatActivity {

    private BluetoothAdapter mBluetoothAdapter;
    private RecyclerView mDeviceRecView;
    private DeviceAdapter mDeviceAdapter;
    private Set<BluetoothDevice> mPairedDevices=null;
    private Set<BluetoothDevice> mDiscoveredDevices=null;
    private List<BtDevice> mDeviceList=null;
    private EditText mPairedEt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDeviceRecView = (RecyclerView) findViewById(R.id.deviceRv);
        mDeviceRecView.setLayoutManager(new LinearLayoutManager(this));

        // mDeviceAdapter = new DeviceAdapter(mDeviceList,this);
        mDeviceAdapter = new DeviceAdapter(DeviceData.getDeviceData(),this);
        mDeviceAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                notifyAll();
            }
        });
        mDeviceRecView.setAdapter(mDeviceAdapter);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        init();

        queryDevice();
//        if (mPairedDevices.size()>0) {
//            mDeviceList = populateDeviceList(mPairedDevices);
//        }


    }

    private Set<BluetoothDevice> deviceDiscovery() {
        return null;
    }

    // TODO move this into the model package as it is data related
    private List<BtDevice> populateDeviceList(Set<BluetoothDevice> paired) {
        List<BtDevice> devices = new ArrayList<>();
        BtDevice dev = new BtDevice();

        for (BluetoothDevice device : paired) {
            String name = device.getName();
            String address = device.getAddress();
            dev.setName(name);
            dev.setAddress(address);
            devices.add(dev);
        }
        return devices;
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

    /**
     * Check for previously paired BT devices on host device
     */
    private void queryDevice() {
        mPairedEt = (EditText)findViewById(R.id.PairedDeviceseditText);
        mPairedDevices = mBluetoothAdapter.getBondedDevices();
        if (mPairedDevices.size()==0){
            Toast.makeText(this, R.string.no_paired_devices,Toast.LENGTH_LONG).show();
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
