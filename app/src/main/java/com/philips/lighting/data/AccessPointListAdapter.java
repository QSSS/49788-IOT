package com.philips.lighting.data;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.quickstart.R;

import java.util.List;

/**
 * This class provides adapter view for a list of Found Bridges.
 * 
 * @author SteveyO.
 */
public class AccessPointListAdapter extends RecyclerView.Adapter<AccessPointListAdapter.BridgeListItem> {
    private List<PHAccessPoint> accessPoints;
    private RecyclerViewClickListener<Integer, View> onItemClickListener;

    /**
     * View holder class for access point list.
     * 
     * @author SteveyO.
     */
    class BridgeListItem extends RecyclerView.ViewHolder {
        private TextView bridgeIp;
        private TextView bridgeMac;

        BridgeListItem(View itemView) {
            super(itemView);
            bridgeMac = (TextView) itemView.findViewById(R.id.bridge_mac);
            bridgeIp = (TextView) itemView.findViewById(R.id.bridge_ip);
        }
    }

    /**
     * creates instance of {@link AccessPointListAdapter} class.
     *
     * @param accessPoints      an array list of {@link PHAccessPoint} object to display.
     */
    public AccessPointListAdapter(List<PHAccessPoint> accessPoints, RecyclerViewClickListener<Integer, View> onRecyclerViewClickListener) {
        // Cache the LayoutInflate to avoid asking for a new one each time.
        this.accessPoints = accessPoints;
        this.onItemClickListener = onRecyclerViewClickListener;
    }

    @Override
    public BridgeListItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.selectbridge_item, parent, false);
        final BridgeListItem viewHolder = new BridgeListItem(v);

        //add on click listener to view holder to inform caller that an item has been clicked
        if (onItemClickListener != null) {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = viewHolder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClicked(position, v);
                    }
                }
            });
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BridgeListItem holder, int position) {
        PHAccessPoint accessPoint = accessPoints.get(position);
        holder.bridgeIp.setText(accessPoint.getIpAddress());
        holder.bridgeMac.setText(accessPoint.getMacAddress());
    }

    @Override
    public int getItemCount() {
        return accessPoints.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's data set.
     * @return The data at the specified position.
     */
    public Object getItem(int position) {
        return accessPoints.get(position);
    }

    /**
     * Update date of the list view and refresh listview.
     *
     * @param accessPoints      An array list of {@link PHAccessPoint} objects.
     */
    public void updateData(List<PHAccessPoint> accessPoints) {
        this.accessPoints = accessPoints;
        notifyDataSetChanged();
    }

}