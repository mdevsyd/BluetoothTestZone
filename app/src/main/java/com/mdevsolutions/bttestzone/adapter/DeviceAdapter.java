package com.mdevsolutions.bttestzone.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdevsolutions.bttestzone.R;

import java.util.List;


public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceHolder>{

    private List<BluetoothDevice> mDeviceList;
    private LayoutInflater mInflater;



    @Override
    public DeviceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(DeviceHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /** class represents a single view of our data item**/
    class DeviceHolder extends RecyclerView.ViewHolder{

        private TextView mName;
        private TextView mAddress;
        private TextView mRssi;
        private View container;

        public DeviceHolder(View itemView) {
            super(itemView);

            mName = (TextView)itemView.findViewById(R.id.device_name);
            mAddress = (TextView)itemView.findViewById(R.id.device_address);
            mRssi = (TextView)itemView.findViewById(R.id.device_rssi);
            container = itemView.findViewById(R.id.list_item_root);

        }
    }
//    private ArrayList<BluetoothDevice> mDevices;
//    private LayoutInflater mInflater;
    Context mContext;
    /**
    public DeviceAdapter(Activity activity){
        mDevices = new ArrayList<BluetoothDevice>();
        mInflater = activity.getLayoutInflater();
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }**/


}
