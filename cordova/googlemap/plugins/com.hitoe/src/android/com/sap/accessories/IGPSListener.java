package com.sap.accessories;

/**
 * Created by charlie on 3/24/16.
 */
public interface IGPSListener {
    void        onGPSChanged(double longitude, double latitude, int status);
}
