package com.philips.lighting.data;

/**
 * Item click listener interface for Recyclerview
 * Created by burcuarabaci on 07/03/17.
 */

public interface RecyclerViewClickListener<M, V> {
    void onItemClicked(M item, V view);
}