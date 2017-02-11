package com.mdevsolutions.bttestzone;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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
    private Set<BluetoothDevice> mPairedDevices = null;
    private Set<BluetoothDevice> mDiscoveredDevices = null;
    private List<BtDevice> mDeviceList = null;
    private EditText mPairedEt;
    private BroadcastReceiver mReceiver;
    private Button mDiscoverBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(getApplicationContext(), R.string.broadcast, Toast.LENGTH_LONG).show();
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    String deviceName = device.getName();
                    String deviceHardwareAddress = device.getAddress(); // MAC address
                }
            }
        };
        this.registerReceiver(mReceiver, filter);
        mDeviceRecView = (RecyclerView) findViewById(R.id.deviceRv);
        mDeviceRecView.setLayoutManager(new LinearLayoutManager(this));
        mDiscoverBtn = (Button) findViewById(R.id.discoverBtn);
        mDiscoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discover();
            }
        });
        mDeviceAdapter = new DeviceAdapter(DeviceData.getDeviceData(), this);
        mDeviceAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                notifyAll();
            }
        });
        //mDeviceRecView.setAdapter(mDeviceAdapter);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        init();
        queryDevice();
//        if (mPairedDevices.size()>0) {
//            mDeviceList = populateDeviceList(mPairedDevices);
//        }
    }

    public void discover() {


        // cancel any potential discoveries
        mBluetoothAdapter.cancelDiscovery();

        //check bluetooth state is STATE_ON else wait
        //TODO sort out how to wait properly
        if (mBluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {
            if (mBluetoothAdapter.startDiscovery()) {
                Toast.makeText(getApplicationContext(), R.string.discovery_started, Toast.LENGTH_LONG).show();
            }
        }
    }

    private Set<BluetoothDevice> deviceDiscovery() {
        return null;
    }


    @Override
    protected void onResume() {
        super.onResume();

        //enable BT on device
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(mBluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, Constants.REQUEST_ENABLE_BT);
        }

        mDeviceRecView.setAdapter(mDeviceAdapter);
    }

    private void init() {
        //check if BT is supported on host device
        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Your device does not support Bluetooth", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    /**
     * Check for previously paired BT devices on host device
     */
    private void queryDevice() {
        mPairedEt = (EditText) findViewById(R.id.PairedDeviceseditText);
        mPairedDevices = mBluetoothAdapter.getBondedDevices();
        if (mPairedDevices.size() == 0) {
            Toast.makeText(this, R.string.no_paired_devices, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //check requestCode to know what requested the result
        switch (requestCode) {
            case Constants.REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(getApplicationContext(), "Bluetooth successfully enabled.", Toast.LENGTH_LONG).show();
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(getApplicationContext(), "Bluetooth enabling failed.", Toast.LENGTH_LONG).show();
                }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregister the receiver when application pauses
        this.unregisterReceiver(mReceiver);
    }
}
