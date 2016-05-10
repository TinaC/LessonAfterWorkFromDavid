package com.sap.accessories;

/**
 * Created by charlie on 3/15/16.
 */
public class Lock {
    private boolean isLocked = false;

    public synchronized void lock()
            throws InterruptedException{
        while(isLocked){
            wait();
        }
        isLocked = true;
    }

    public synchronized void unlock(){
        isLocked = false;
        notify();
    }
}
