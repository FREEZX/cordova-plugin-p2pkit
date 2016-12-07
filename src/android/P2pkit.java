package org.freezx.p2pkit;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ch.uepaa.p2pkit.*;
import ch.uepaa.p2pkit.discovery.*;

/**
 * This class echoes a string called from JavaScript.
 */
public class P2pkit extends CordovaPlugin {
    private final P2PKitStatusCallback mStatusCallback = new P2PKitStatusCallback() {
        @Override
        public void onEnabled() {
            // ready to start discovery
        }

        @Override
        public void onSuspended() {
            // p2pkit is temporarily suspended
        }
        
        @Override
        public void onResumed() {
            // coming back from a suspended state
        }
        
        @Override
        public void onDisabled() {
            // p2pkit has been disabled
        }

        @Override
        public void onError(StatusResult statusResult) {
            // enabling failed, handle statusResult
        }
    };

    private final P2PListener mP2pDiscoveryListener = new P2PListener() {
        public void onP2PStateChanged(int state) {
            try{
                JSONObject json=new JSONObject();
                json.put("type", "onP2PStateChanged");
                json.put("state", state);
                discoveryListenerCallbackContext.success(json.toString());
            } catch(Exception e) {
                
            }
        }

        public void onPeerDiscovered(ch.uepaa.p2pkit.discovery.entity.Peer peer) {
            try{
                JSONObject json=new JSONObject();
                json.put("type", "onPeerDiscovered");

                JSONObject peerJson=new JSONObject();
                peerJson.put("uuid", peer.getNodeId().toString());
                peerJson.put("proximityStrength", peer.getProximityStrength ());
                json.put("peer", peerJson);
                discoveryListenerCallbackContext.success(json.toString());
            } catch(Exception e) {
                
            }
        }

        public void onPeerLost(ch.uepaa.p2pkit.discovery.entity.Peer peer) {
            try{
                JSONObject json=new JSONObject();
                json.put("type", "onPeerLost");

                JSONObject peerJson=new JSONObject();
                peerJson.put("uuid", peer.getNodeId().toString());
                peerJson.put("proximityStrength", peer.getProximityStrength ());
                json.put("peer", peerJson);
                discoveryListenerCallbackContext.success(json.toString());
            } catch(Exception e) {
                
            }
        }

        public void onPeerUpdatedDiscoveryInfo(ch.uepaa.p2pkit.discovery.entity.Peer peer) {
            try{
                JSONObject json=new JSONObject();
                json.put("type", "onPeerUpdatedDiscoveryInfo");

                JSONObject peerJson=new JSONObject();
                peerJson.put("uuid", peer.getNodeId().toString());
                peerJson.put("proximityStrength", peer.getProximityStrength ());
                json.put("peer", peerJson);
                discoveryListenerCallbackContext.success(json.toString());
            } catch(Exception e) {

            }
        }
        
        public void onProximityStrengthChanged(ch.uepaa.p2pkit.discovery.entity.Peer peer) {
            try{
                JSONObject json=new JSONObject();
                json.put("type", "onProximityStrengthChanged");

                JSONObject peerJson=new JSONObject();
                peerJson.put("uuid", peer.getNodeId().toString());
                peerJson.put("proximityStrength", peer.getProximityStrength ());
                json.put("peer", peerJson);
                discoveryListenerCallbackContext.success(json.toString());
            } catch(Exception e) {
                
            }
        }
    };

    CallbackContext discoveryListenerCallbackContext;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        }
        if (action.equals("isP2PServicesAvailable")) {
            this.isP2PServicesAvailable(callbackContext);
            return true;
        }
        if (action.equals("enableP2PKit")) {
            String apikey = args.getString(0);
            this.enableP2PKit(apikey, callbackContext);
            return true;
        }
        if (action.equals("createP2pDiscoveryListener")) {
            this.createP2pDiscoveryListener(callbackContext);
            return true;
        }
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    private void createP2pDiscoveryListener(CallbackContext callbackContext) {
        discoveryListenerCallbackContext = callbackContext;
        P2PKitClient.getInstance(this.cordova.getActivity()).getDiscoveryServices().addP2pListener(mP2pDiscoveryListener);
    }

    private void isP2PServicesAvailable(CallbackContext callbackContext) {
        final StatusResult result = P2PKitClient.isP2PServicesAvailable(this.cordova.getActivity());
        if (result.getStatusCode() == StatusResult.SUCCESS) {
            callbackContext.success(result.getStatusCode());
        } else {
            callbackContext.error(result.getStatusCode());
        }
    }

    private void enableP2PKit(String apikey, CallbackContext callbackContext) {
        P2PKitClient client = P2PKitClient.getInstance(this.cordova.getActivity());
        client.enableP2PKit(mStatusCallback, apikey);
    }
}

