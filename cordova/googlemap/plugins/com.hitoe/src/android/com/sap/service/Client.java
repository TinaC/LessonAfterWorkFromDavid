package com.sap.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.sap.accessories.IGPSListener;
import com.sap.service.IHitoeSensorListListener;
import com.sap.service.ISAPPhireGPSListener;
import com.sap.service.ISAPPhireService;
import com.sap.service.SAPPhireService;

/**
 * Created by charlie on 3/10/16.
 */
public class Client {
    private static ISAPPhireService iService;
    private static ServiceConnection connection;
    private static boolean bConnected = false;
    private static IGPSListener gpsListener;

    public static void Init(final Context context, IGPSListener listener) {
        if(connection == null) {
            gpsListener = listener;
            connection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    iService = ISAPPhireService.Stub.asInterface(service);
                    try {
                        iService.testDlg();
                        iService.listSensor(new IHitoeSensorListListener.Stub() {
                            @Override
                            public void OnListSensor(final String aString) throws RemoteException {
                                Log.d("SAPPhire", aString);
                                if (!bConnected) {
                                    bConnected = true;

                                    Handler h = new Handler();
                                    final String s = aString;
                                    h.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                iService.connectSensor(s);
                                            } catch (RemoteException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, 1000);
                                }
                            }
                        },
                        new ISAPPhireGPSListener.Stub() {
                            @Override
                            public void onGPSChanged(double longitude, double latitude) throws RemoteException {
                                if(gpsListener != null)
                                    gpsListener.onGPSChanged(longitude, latitude, 0);
                            }
                        });

                        iService.startGPS();
                    } catch (RemoteException e) {
                        Log.e("SAPPhire", e.getMessage());
                    }
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    Log.d("SAPPhire", "Service disconnected");
                }
            };

            Intent intent = new Intent(context, SAPPhireService.class);
            context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }
    }

    public static void Uninit(Context context) {
        if(connection != null) {
            context.unbindService(connection);
            connection = null;
        }
    }
}
