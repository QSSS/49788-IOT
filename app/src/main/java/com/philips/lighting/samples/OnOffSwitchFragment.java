
/**
 * Created by zhangpeng on 4/16/17.
 */

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
import java.util.Random;




/**
 * A simple {@link Fragment} subclass.
 */
public class OnOffSwitchFragment extends Fragment {
    public static final String TAG = "Turn Lights Fragment";
    private boolean lightStateOn = true;
    private PHHueSDK phHueSDK;
    public OnOffSwitchFragment() {
        // Required empty public constructor
    }

    public static OnOffSwitchFragment newInstance() {
        return new OnOffSwitchFragment();
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
        View mainView =  inflater.inflate(R.layout.fragment_switch_lights, container, false);
        final Button switchButton = (Button) mainView.findViewById(R.id.buttonSwitch);
        switchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switchLights();
                if (lightStateOn == true) {
                    switchButton.setText("Turn Off");
                } else {
                    switchButton.setText("Turn On");
                }
            }

        });
        return mainView;
    }

    public void switchLights() {
        PHBridge bridge = phHueSDK.getSelectedBridge();

        List<PHLight> allLights = bridge.getResourceCache().getAllLights();

        PHLightState lightState = new PHLightState();

        for (PHLight light : allLights) {
            lightState.setOn(!lightStateOn);

            System.out.println("Current Light On state: " + !lightStateOn);


//            lightState.setHue(rand.nextInt(MAX_HUE));
            // To validate your lightstate is valid (before sending to the bridge) you can use:
            // String validState = lightState.validateState();
            bridge.updateLightState(light, lightState, listener);

            //  bridge.updateLightState(light, lightState);   // If no bridge response is required then use this simpler form.
        }
        lightStateOn = !lightStateOn;

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
