package com.tina.workshop;

import android.os.Bundle;
import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;


public class MyOwnPlugin extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {

        if("do_something".equals(action)) {
            // do something
        } else if("do_another_thing".equals(action)) {
            // do another thing
        } else if("do_other_things".equals(action)) {
            // do other things
        } else {
            return false;
        }

        return true;
    }
}