// ISAPPhireGPSListener.aidl
package com.sap.aidl;

// Declare any non-default types here with import statements

interface ISAPPhireGPSListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onGPSChanged(double longitude, double latitude);
}
