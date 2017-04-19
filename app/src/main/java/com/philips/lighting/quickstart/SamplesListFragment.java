package com.philips.lighting.quickstart;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.philips.lighting.data.RecyclerViewClickListener;
import com.philips.lighting.data.SimpleRecyclerViewAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SamplesListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SamplesListFragment extends Fragment {

    private static final String LIST_ITEMS = "list items";
    private static final String TAG = "Samples List Fragment";
    private OnFragmentInteractionListener mListener;
    private SimpleRecyclerViewAdapter mAdapter;

    public SamplesListFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(ArrayList<String> listitems) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(LIST_ITEMS, listitems);
        Fragment samplesListFragment = new SamplesListFragment();
        samplesListFragment.setArguments(bundle);
        return samplesListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerViewClickListener<Integer, View> onRecylerViewItemClicked = new RecyclerViewClickListener<Integer, View>() {
            @Override
            public void onItemClicked(Integer item, View view) {
                if (mListener != null) {
                    mListener.onSampleSelected(item);
                }
            }
        };

        if (getArguments().containsKey(LIST_ITEMS)) {
            ArrayList<String> simpleList = getArguments().getStringArrayList(LIST_ITEMS);
            mAdapter = new SimpleRecyclerViewAdapter(simpleList, onRecylerViewItemClicked);
        } else {
            Log.e(TAG, "Please provide list items while creating the fragment");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_samples_list, container, false);
        RecyclerView recyclerView = (RecyclerView) mainView.findViewById(R.id.samplesList);
        recyclerView.setAdapter(mAdapter);
        return mainView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * <p>
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void onSampleSelected(int selectedSample);
    }
}
