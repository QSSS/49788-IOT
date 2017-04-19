package com.philips.lighting.data;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.philips.lighting.quickstart.R;

import java.util.ArrayList;

/**
 * Simple adapter to show the names of the available samples
 * <p>
 * Created by burcuarabaci on 07/03/17.
 */

public class SimpleRecyclerViewAdapter extends RecyclerView.Adapter<SimpleRecyclerViewAdapter.SamplesViewHolder> {

    private ArrayList<String> samplesList;
    private RecyclerViewClickListener<Integer, View> onItemClickListener;


    /**
     * Constructor for SamplesListAdapter
     *
     * @param samplesList                 Names of the samples
     * @param onRecyclerViewClickListener clickListener returns position and the view of the clicked item
     */
    public SimpleRecyclerViewAdapter(ArrayList<String> samplesList, RecyclerViewClickListener<Integer, View> onRecyclerViewClickListener) {
        this.samplesList = samplesList;
        this.onItemClickListener = onRecyclerViewClickListener;
    }

    @Override
    public SamplesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_recyclerview_listitem, parent, false);
        final SamplesViewHolder viewHolder = new SamplesViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClicked(position, v);
                }

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SamplesViewHolder holder, int position) {
        holder.text.setText(samplesList.get(position));
    }

    @Override
    public int getItemCount() {
        return samplesList.size();
    }

    static class SamplesViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        SamplesViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.sampleTitle);
        }
    }
}