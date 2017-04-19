package com.philips.lighting.samples;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.philips.lighting.hue.listener.PHLightListener;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResource;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;
import com.philips.lighting.quickstart.R;

import java.util.List;
import java.util.Map;
/**
 * Created by zhangpeng on 4/16/17.
 */

public class LightModeFragment extends Fragment {
    public static final String TAG = "Lights Mode Fragment";
    private boolean lightStateOn = true;
    private PHHueSDK phHueSDK;
    public LightModeFragment() {
        // Required empty public constructor
    }

    public static LightModeFragment newInstance() {
        return new LightModeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phHueSDK = PHHueSDK.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView =  inflater.inflate(R.layout.fragment_mode_lights, container, false);
        final Button studyButton = (Button) mainView.findViewById(R.id.buttonStudy);
        final Button sleepButton = (Button) mainView.findViewById(R.id.buttonSleep);
        final Button dinnerButton = (Button) mainView.findViewById(R.id.buttonDinner);

        studyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switchStudyLights();
            }

        });
        sleepButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switchSleepLights();

            }

        });
        dinnerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switchDinnerLights();

            }

        });
        return mainView;
    }

    public void switchStudyLights() {
        PHBridge bridge = phHueSDK.getSelectedBridge();

        List<PHLight> allLights = bridge.getResourceCache().getAllLights();

        PHLightState lightState = new PHLightState();

        for (PHLight light : allLights) {
            System.out.println("Current Light Mode = Study");
            lightState.setBrightness(250);

            bridge.updateLightState(light, lightState, listener);
        }


    }
    public void switchSleepLights() {
        PHBridge bridge = phHueSDK.getSelectedBridge();

        List<PHLight> allLights = bridge.getResourceCache().getAllLights();

        PHLightState lightState = new PHLightState();

        for (PHLight light : allLights) {
            System.out.println("Current Light Mode = Sleep");
            lightState.setBrightness(64);

            bridge.updateLightState(light, lightState, listener);
        }


    }
    public void switchDinnerLights() {
        PHBridge bridge = phHueSDK.getSelectedBridge();
        List<PHLight> allLights = bridge.getResourceCache().getAllLights();

        PHLightState lightState = new PHLightState();

        for (PHLight light : allLights) {
            System.out.println("Current Light Mode = Dinner");
            lightState.setBrightness(128);

            bridge.updateLightState(light, lightState, listener);
        }


    }

    // If you want to handle the response from the bridge, create a PHLightListener object.
    PHLightListener listener = new PHLightListener() {

        @Override
        public void onSuccess() {
        }

        @Override
        public void onStateUpdate(Map<String, String> arg0, List<PHHueError> arg1) {
            Log.w(TAG, "Light has updated");
        }

        @Override
        public void onError(int arg0, String arg1) {}

        @Override
        public void onReceivingLightDetails(PHLight arg0) {}

        @Override
        public void onReceivingLights(List<PHBridgeResource> arg0) {}

        @Override
        public void onSearchComplete() {}
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        PHBridge bridge = phHueSDK.getSelectedBridge();
        if (bridge != null) {

            if (phHueSDK.isHeartbeatEnabled(bridge)) {
                phHueSDK.disableHeartbeat(bridge);
            }

            phHueSDK.disconnect(bridge);
        }
    }
}
