package com.sap.service;

/**
 * Created by charlie on 3/10/16.
 */
public class NativeHelp {
    static {
        System.loadLibrary("swlib");
    }

    public static native void init();
    public static native void uninit();
    public static native void postJson(String sURL, String sJSON);
    public static native long getCurTimestamp();
    public static native long openLocationFile(String sFolder);
    public static native void writeLocation(long lFile, double longitude, double latitude);
    public static native void closeLocationFile(long lFile);
}
