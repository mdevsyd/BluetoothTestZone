package com.mdevsolutions.bttestzone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdevsolutions.bttestzone.R;
import com.mdevsolutions.bttestzone.model.BtDevice;

import java.util.List;


public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceHolder>{

    private List<BtDevice> deviceList;
    private LayoutInflater inflater;

    //constructor
    public DeviceAdapter (List<BtDevice> deviceList, Context c){
        this.inflater = LayoutInflater.from(c);
        this.deviceList = deviceList;
    }

    @Override
    public DeviceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.bluetooth_device, parent, false);
        return new DeviceHolder(view);
    }

    @Override
    public void onBindViewHolder(DeviceHolder holder, int position) {
        BtDevice device = deviceList.get(position);
        holder.mName.setText(device.getName());
        holder.mAddress.setText(device.getAddress());

        //holder.mRssi.setText(""+device.getRssi());
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    /** class represents a single view of our data item**/
    class DeviceHolder extends RecyclerView.ViewHolder{

        private TextView mName;
        private TextView mAddress;
        private TextView mRssi;
        private View container;

        //itemView is an instance of the single item view (one device in the list)
        public DeviceHolder(View itemView) {
            super(itemView);

            mName = (TextView)itemView.findViewById(R.id.device_name);
            mAddress = (TextView)itemView.findViewById(R.id.device_address);
            //mRssi = (TextView)itemView.findViewById(R.id.device_rssi);
            container = itemView.findViewById(R.id.list_item_root);

        }
    }




}
