package com.sap;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import android.app.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import jp.ne.docomo.smt.dev.hitoetransmitter.HitoeSdkAPI;
import jp.ne.docomo.smt.dev.hitoetransmitter.sdk.HitoeSdkAPIImpl;
import com.sap.service.SAPPhireService;

import android.content.Context;
import java.lang.Thread;

/**
 * This class echoes a string called from JavaScript.
 */
public class Hitoe_SDK extends CordovaPlugin {
    private boolean bRunning;
    private Thread thread;
    private static SAPPhireService service;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        //HitoeSdkAPI mHitoeSdkAPI;
        //mHitoeSdkAPI = HitoeSdkAPIImpl.getInstance(this.getApplicationContext());

        if(action.equals("init")){
            String message = args.getString(0);
            this.init(message, callbackContext);
        }

        if (action.equals("hitoe_echo")) {
            String message = args.getString(0);
            this.hitoe_echo(message, callbackContext);
            return true;
        }
        return false;
    }

    private void init(String message, CallbackContext callbackContext){
        Context context = this.cordova.getActivity().getApplicationContext();
        service = new SAPPhireService();
        service.returnData(context);
        System.out.println("init started");
    }

    private void hitoe_echo(String message, CallbackContext callbackContext) {
        //Context context = this.cordova.getActivity().getApplicationContext(); 
        //SAPPhireService service = new SAPPhireService();
        // try{
        //     service.connectSensor(null);
        // }catch(Exception e){
        //     e.printStackTrace();
        // }
        
        //service.onCreate();

        //service.returnData(context);
        if(service == null)
            return;

        String b = service.getJSON();
        //System.out.println("a = " + b);

        // bRunning = true;
        // thread = new Thread() {
        //     public void run() {
        //         while(bRunning) {
        //             try {
        //                 Thread.sleep(1000);
        //             } catch (InterruptedException e) {
        //                 e.printStackTrace();
        //             }

        //                 String b = service.getJSON();
        //                 System.out.println("a = " + b);
        //             // if(gpsListner != null) {
        //             //     try {
        //             //         gpsListner.onGPSChanged(gpsLongitude, gpsLatitude);
        //             //     } catch (RemoteException e) {
        //             //         e.printStackTrace();
        //             //     }
        //             // }
        //         }
        //     }
        // };

        if (message != null && message.length() > 0) {
            callbackContext.success(service.getJSON());
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}