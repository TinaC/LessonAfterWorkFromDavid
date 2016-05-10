// ISAPPhireService.aidl
package com.sap.aidl;

import com.sap.aidl.IHitoeSensorListListener;
import com.sap.aidl.ISAPPhireGPSListener;
// Declare any non-default types here with import statements

interface ISAPPhireService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void testDlg();
    void listSensor(IHitoeSensorListListener listener, ISAPPhireGPSListener gps);
    void connectSensor(String sSensor);
    void disconnectSensor();
    void startGPS();
    void stopGPS();

    void closeAlertDialog();
}
