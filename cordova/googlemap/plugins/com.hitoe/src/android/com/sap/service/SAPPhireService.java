package com.sap.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
//import android.support.annotation.Nullable;
import android.util.Log;
import android.content.Context;

//import com.sap.MainActivity;
//import com.sap.R;
import com.sap.accessories.GPSHelper;
import com.sap.accessories.Lock;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import jp.ne.docomo.smt.dev.hitoetransmitter.HitoeSdkAPI;
import jp.ne.docomo.smt.dev.hitoetransmitter.sdk.HitoeSdkAPIImpl;

//import com.google.gson.Gson;


/**
 * Created by charlie on 3/10/16.
 */
public class SAPPhireService extends Service {
    private GPSHelper gpsHelper;
    private long lLocationFile;

    //Flag of Closed dialog, 0 means closed, 1 means opened.
//    private static ISAPPhireService iService;
    private static ServiceConnection connection;
    private static boolean bAlertDialogShow = false;

    ISAPPhireService.Stub binder = new ISAPPhireService.Stub() {
        @Override
        public void testDlg() throws RemoteException {
        }
        @Override
        public void listSensor(IHitoeSensorListListener listener, ISAPPhireGPSListener gps) throws RemoteException {
            try {
                if(listener != null) {
                    csListSensor.lock();
                    lstListSensor.add(listener);
                    csListSensor.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gpsListner = gps;

            String sParam = "search_time=" + String.valueOf(CommonConsts.GET_AVAILABLE_SENSOR_PARAM_SEARCH_TIME);
            int response_id = mHitoeSdkAPI.getAvailableSensor(CommonConsts.GET_AVAILABLE_SENSOR_DEVICE_TYPE, sParam);
            if(response_id != CommonConsts.RES_ID_SUCCESS) {
                Log.e("SAPPhire", "List sensor failed");
            }
        }
        @Override
        public void connectSensor(String sSensor) throws RemoteException {
            System.out.println("CONNECTING TO BLUETOOTH...");  //sSensor = "hitoe D01,hitoe tx 01 000770,B4:99:4C:66:C1:50,realtime,raw.hr|raw.rri|raw.ecg|raw.acc|raw.bat"
            connectDevice(sSensor);
        }
        @Override
        public void disconnectSensor() throws RemoteException {

        }

        @Override
        public void startGPS() throws RemoteException {
            gpsHelper = new GPSHelper();
            gpsHelper.init(SAPPhireService.this, new com.sap.accessories.IGPSListener() {
                @Override
                public void onGPSChanged(double longitude, double latitude, int status) {
                    if(status == 0) {
                        gpsLongitude = longitude;
                        gpsLatitude = latitude;
                        NativeHelp.writeLocation(lLocationFile, gpsLongitude, gpsLatitude);
                    }
                }
            });
        }

        @Override
        public void stopGPS() throws RemoteException {
            if(gpsHelper != null) {
                gpsHelper.uninit();
                gpsHelper = null;
            }
            gpsListner = null;
        }


        @Override
        public void closeAlertDialog() throws RemoteException {
            bAlertDialogShow = false;
        }
    };

    public String getJSON(){
        //System.out.println("BPM : " + bpm);
        return out;
    }


    private void postJSON() {
//        String json = String.format("{\"HITOE_ID\":\"%s\", \"TIME\":\"/Date(%d)/\", \"LATITUDE\":\"%.06f\", \"LONGITUDE\":\"%.06f\", \"LF_HF_DIVISION\":\"%.02f\", \"BPM\":\"%d\", \"SDNN\":\"%d\"}",
//                "1", NativeHelp.getCurTimestamp()+28800000, gpsLatitude, gpsLongitude, lfhf, (int) bpm, (int) sdnn);
//        NativeHelp.postJson("http://10.58.121.158:8001/ctsdemo_live/CTSDemo_Live/services/TRIP_DATA.xsodata/TRIP_DATA", json);

//         try {

//             //Create JSONObject here
//             JSONObject jsonParam = new JSONObject();
//             jsonParam.put("HITOE_ID", "1");
//             jsonParam.put("TIME", "/Date(" + new Date().getTime() + ")/");
//             jsonParam.put("LATITUDE", gpsLatitude);
//             jsonParam.put("LONGITUDE", gpsLongitude);
//             jsonParam.put("LF_HF_DEVISION", lfhf);
//             jsonParam.put("BPM", (int)bpm);
//             jsonParam.put("SDNN", sdnn);

//             String url="http://10.58.121.158:8001/ctsdemo_live/CTSDemo_Live/services/TRIP_DATA.xsodata/TRIP_DATA";
//             URL object=new URL(url);

//             HttpURLConnection con = (HttpURLConnection) object.openConnection();
//             con.setDoOutput(true);
//             con.setDoInput(true);
//             con.setRequestProperty("Content-Type", "application/json");
// //            con.setRequestProperty("Authorization", "Basic Q1RTX0xJVkU6QWExMTExMTE=");
// //            con.setRequestProperty("Content-Length", "" + jsonParam.toString().getBytes().length);
//             con.setRequestMethod("POST");

//             OutputStream os = con.getOutputStream();
//             os.write(jsonParam.toString().getBytes("UTF-8"));
//             os.flush();
//             os.close();

//             System.out.println("HTTP CODE: " + con.getResponseCode());


//         } catch (Exception e){
//             e.printStackTrace();
//             System.out.println("POST JSON ERROR");
//         }
    }



    private boolean bRunning;
    private Thread thread;
    private String out;


    public String returnData(Context context){
        //super.onCreate();
        mHitoeSdkAPI = HitoeSdkAPIImpl.getInstance(context);
        mHitoeSdkAPI.setAPICallback(mAPICallback);
        connectDevice("hitoe D01,hitoe tx 01 000770,B4:99:4C:66:C1:50,realtime,raw.hr|raw.rri|raw.ecg|raw.acc|raw.bat");

        mHitoeSdkAPI = HitoeSdkAPIImpl.getInstance(context);
        mHitoeSdkAPI.setAPICallback(mAPICallback);
        //System.out.println(mAPICallback);

        bRunning = true;
        thread = new Thread() {
            public void run() {
                while(bRunning) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try{
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("LF_HF_DEVISION", lfhf);
                    jsonParam.put("BPM", (int)bpm);
                    jsonParam.put("SDNN", (int)sdnn);
                    out = jsonParam.toString();
                    }catch(Exception e){}
                    //out = "bpm : " + bpm;
                    //System.out.println(out);

                    // if(gpsListner != null) {
                    //     try {
                    //         gpsListner.onGPSChanged(gpsLongitude, gpsLatitude);
                    //     } catch (RemoteException e) {
                    //         e.printStackTrace();
                    //     }
                    // }
                }
            }
        };
        thread.start();

        return out;


        // bRunning = true;
        // thread = new Thread() {
        //     public void run() {
        //         while(bRunning) {
        //             try {
        //                 Thread.sleep(1000);
        //             } catch (InterruptedException e) {
        //                 e.printStackTrace();
        //             }
        //             postJSON();

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
        // thread.start();        
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /*
        Notification notification = new Notification(R.drawable.def_header, "asdf", System.currentTimeMillis());
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        notification
        */
        NativeHelp.init();
        try {
            String s = this.getFilesDir().getAbsolutePath() + "/location.txt";
            FileInputStream fis = openFileInput("location.txt");
            BufferedReader dataIO = new BufferedReader(new InputStreamReader(fis));
            String strLine = null;

            while((strLine =  dataIO.readLine()) != null) {
                Log.d("SAPPhire", strLine);
            }
            dataIO.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        lLocationFile = NativeHelp.openLocationFile(this.getFilesDir().getAbsolutePath());

        mHitoeSdkAPI = HitoeSdkAPIImpl.getInstance(this.getApplicationContext());
        mHitoeSdkAPI.setAPICallback(mAPICallback);


        bRunning = true;
        thread = new Thread() {
            public void run() {
                while(bRunning) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postJSON();

                    // if(gpsListner != null) {
                    //     try {
                    //         gpsListner.onGPSChanged(gpsLongitude, gpsLatitude);
                    //     } catch (RemoteException e) {
                    //         e.printStackTrace();
                    //     }
                    // }
                }
            }
        };
        thread.start();

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        bRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        NativeHelp.closeLocationFile(lLocationFile);;
        NativeHelp.uninit();
    }

    //@Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }



    private HitoeSdkAPI mHitoeSdkAPI;
    private String sSessionID = null;
    private String sRawConnectionId = null;
    private String sBaConnectionId = null;
    private ArrayList<String> mExConnectionList = new ArrayList<String>();

    private String[] sensorList = null;
    private Lock csListSensor = new Lock();
    private Vector<IHitoeSensorListListener> lstListSensor = new Vector<IHitoeSensorListListener>();
    private ISAPPhireGPSListener gpsListner;
    public ArrayList<String> mAvailableRawDataList = new ArrayList<String>();
    public ArrayList<String> mAvailableBaDataList = new ArrayList<String>();
    public ArrayList<String> mAvailableExDataList = new ArrayList<String>();
    private ArrayList<ExData> mListForEx = new ArrayList<ExData>();
    private ArrayList<String> mListForPosture = new ArrayList<String>();
    private ArrayList<String> mListForWalk = new ArrayList<String>();
    private ArrayList<String> mListForLRBalance = new ArrayList<String>();

    private boolean mFlagForEx = false;
    HitoeSdkAPI.APICallback mAPICallback = new HitoeSdkAPI.APICallback() {
        @Override
        public void onResponse(int api_id, int response_id, String responseString) {
            //System.out.println("ENTERING mAPICallback, api_id = " + api_id + " response_id = " + response_id + " responseString = " + responseString);
            switch(api_id) {
                case CommonConsts.API_ID_GET_AVAILABLE_SENSOR:
                    try {
                        csListSensor.lock();

                        if(responseString != null && responseString.length() > 0) {
                            sensorList = responseString.split(CommonConsts.BR, -1);
                            for(int i = 0; i < sensorList.length; ++ i) {
                                String s = sensorList[i];
                                if(s.indexOf("memory_setting") > 0) {

                                }
                                else if(s.indexOf("memory_get") > 0) {

                                }
                                else {

                                }

                                Log.d("SAPPhire", s);

                                for(IHitoeSensorListListener listener : lstListSensor) {
                                    try {
                                        listener.OnListSensor(s);
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                            Log.d("SAPPhire", "==============================");
                        }

                        //lstListSensor.clear();
                        csListSensor.unlock();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommonConsts.API_ID_GET_AVAILABLE_DATA:
                    if(response_id == CommonConsts.RES_ID_SUCCESS && responseString != null) {
                        addReceiver(responseString);
                    }
                    break;
                case CommonConsts.API_ID_CONNECT:
                    if (response_id == CommonConsts.RES_ID_SENSOR_DISCONECT_NOTICE) {
                        break;
                    }
                    else if(response_id == CommonConsts.RES_ID_SENSOR_CONNECT_NOTICE) {
                        break;
                    }
                    else if (response_id != CommonConsts.RES_ID_SENSOR_CONNECT) {
                        break;
                    }
                    sSessionID = responseString;
                    getAvailableDataProcess();
                    break;
                case CommonConsts.API_ID_DISCONNECT:
                    disconnectDevice();
                    break;
                case CommonConsts.API_ID_ADD_RECIVER:
                    if (response_id != CommonConsts.RES_ID_SUCCESS || responseString == null) {
                        mFlagForEx = false;
                        break;
                    }
                    if(responseString.startsWith(CommonConsts.RAW_CONNECTION_PREFFIX)) {
                        sRawConnectionId = responseString;
                    } else if(responseString.startsWith(CommonConsts.BA_CONNECTION_PREFFIX)) {
                        sBaConnectionId = responseString;
                    } else if(responseString.startsWith(CommonConsts.EX_CONNECTION_PREFFIX)) {
                        mExConnectionList.add(responseString);
                    }
                    if(responseString.startsWith(CommonConsts.RAW_CONNECTION_PREFFIX)) {
                        addBaReceiver();
                    }
                    else {
                        ExData exData = null;
                        try{
                            if(mListForEx.size() > 0) {
                                exData = mListForEx.get(0);
                                mListForEx.remove(0);
                            } else {
                                mFlagForEx = false;
                            }

                        }finally {
                        }
                        if(exData != null) {
                            addExReceiverProcess(exData);
                        }
                    }
                    break;
                case CommonConsts.API_ID_REMOVE_RECEIVER:
                    Log.d("SAPPhire", "CommonConsts.API_ID_REMOVE_RECEIVER");
                    break;
                case CommonConsts.API_ID_GET_STATUS:
                    Log.d("SAPPhire", "CommonConsts.API_ID_GET_STATUS");
                    break;
                default: break;
            }
        }
    };
    private void connectDevice(String sSensor) {
        if(sSessionID == null) {
            AvailableSensorData availableSensorData = new AvailableSensorData(sSensor);
            String params = "pincode=058110";
            int response_id = mHitoeSdkAPI.connect(availableSensorData.mDeviceType, availableSensorData.mDeviceId, availableSensorData.mConnectMode, params);
            if (response_id != CommonConsts.RES_ID_SUCCESS) {
                disconnectDevice();
            }
            System.out.println("RESPONSE_ID - REG_ID_SUCCESS : " + (response_id - CommonConsts.RES_ID_SUCCESS));
        }
    }
    private void disconnectDevice() {
        if(sSessionID != null) {
            mHitoeSdkAPI.disconnect(sSessionID);
            sSessionID = null;
        }
    }



    private void getAvailableDataProcess() {
        int response_id = mHitoeSdkAPI.getAvailableData(sSessionID);
        if (response_id != CommonConsts.RES_ID_SUCCESS) {
        }
    }



    private void addReceiver(String sList) {
        mAvailableRawDataList = new ArrayList<String>();
        mAvailableBaDataList = new ArrayList<String>();
        mAvailableExDataList = new ArrayList<String>();
        String data_list[] = CommonConsts.AVAILABLE_EX_DATA_STR.split("\n");
        for(int i=0; i < data_list.length; i++) {
            mAvailableExDataList.add(data_list[i]);
        }

        String[] keyList = sList.split(CommonConsts.BR, -1);
        for(String key : keyList) {
            if(key.startsWith(CommonConsts.RAW_DATA_PREFFIX)) {
                if(!mAvailableRawDataList.contains(key)) mAvailableRawDataList.add(key);
            }
            else if(key.startsWith(CommonConsts.BA_DATA_PREFFIX)) {
                if(!mAvailableBaDataList.contains(key)) mAvailableBaDataList.add(key);
            }
            else if(key.startsWith(CommonConsts.EX_DATA_PREFFIX)) {
                if(!mAvailableExDataList.contains(key)) mAvailableExDataList.add(key);
            }
            else {
                Log.e("SAPPhire", key);
            }
        }
        addRawReceiver();
    }

    private void addRawReceiver() {
        String[] keys = new String[mAvailableRawDataList.size()];
        StringBuilder paramStringBuilder = new StringBuilder();
        for(int i = 0; i < mAvailableRawDataList.size(); ++ i) {
            keys[i] = mAvailableRawDataList.get(i);
            if (mAvailableRawDataList.get(i).equals("raw.ecg")) {
                if (paramStringBuilder.lastIndexOf(CommonConsts.BR) != paramStringBuilder.length() - 1) {
                    paramStringBuilder.append(CommonConsts.BR);
                }
                if (CommonConsts.ADD_RECEIVER_PARAM_ECG_SAMPLING_INTERVAL != -1) {
                    paramStringBuilder.append("raw.ecg_interval=" + String.valueOf(CommonConsts.ADD_RECEIVER_PARAM_ECG_SAMPLING_INTERVAL));
                }
            }
            else if (mAvailableRawDataList.get(i).equals("raw.acc")) {
                if (paramStringBuilder.lastIndexOf(CommonConsts.BR) != paramStringBuilder.length() - 1) {
                    paramStringBuilder.append(CommonConsts.BR);
                }
                if (CommonConsts.ADD_RECEIVER_PARAM_ACC_SAMPLING_INTERVAL != -1) {
                    paramStringBuilder.append("raw.acc_interval=" + String.valueOf(CommonConsts.ADD_RECEIVER_PARAM_ACC_SAMPLING_INTERVAL));
                }
            }
            else if (mAvailableRawDataList.get(i).equals("raw.rri")) {
                if (paramStringBuilder.lastIndexOf(CommonConsts.BR) != paramStringBuilder.length() - 1) {
                    paramStringBuilder.append(CommonConsts.BR);
                }
                if (CommonConsts.ADD_RECEIVER_PARAM_RRI_SAMPLING_INTERVAL != -1) {
                    paramStringBuilder.append("raw.rri_interval=" + String.valueOf(CommonConsts.ADD_RECEIVER_PARAM_RRI_SAMPLING_INTERVAL));
                }
            }
            else if (mAvailableRawDataList.get(i).equals("raw.hr")) {
                if (paramStringBuilder.lastIndexOf(CommonConsts.BR) != paramStringBuilder.length() - 1) {
                    paramStringBuilder.append(CommonConsts.BR);
                }
                if (CommonConsts.ADD_RECEIVER_PARAM_HR_SAMPLING_INTERVAL != -1) {
                    paramStringBuilder.append("raw.hr_interval=" + String.valueOf(CommonConsts.ADD_RECEIVER_PARAM_HR_SAMPLING_INTERVAL));
                }
            }
            else if (mAvailableRawDataList.get(i).equals("raw.bat")) {
                if (paramStringBuilder.lastIndexOf(CommonConsts.BR) != paramStringBuilder.length() - 1) {
                    paramStringBuilder.append(CommonConsts.BR);
                }
                if (CommonConsts.ADD_RECEIVER_PARAM_BAT_SAMPLING_INTERVAL != -1) {
                    paramStringBuilder.append("raw.bat_interval=" + String.valueOf(CommonConsts.ADD_RECEIVER_PARAM_BAT_SAMPLING_INTERVAL));
                }
            }
            else if (mAvailableRawDataList.get(i).equals("raw.saved_hr") || mAvailableRawDataList.get(i).equals("raw.saved_rri")) {

            }
            else {

            }
        }
        String sParam = paramStringBuilder.toString();
        int response_id = mHitoeSdkAPI.addReceiver(sSessionID, keys, mDataReceiverCallback, sParam, null);
        if (response_id != CommonConsts.RES_ID_SUCCESS) {
            return;
        }
    }

    private void addBaReceiver() {
        StringBuilder paramStringBuilder = new StringBuilder();
        String[] keys = new String[mAvailableBaDataList.size()];
        String paramString;

        for(int i = 0; i < mAvailableBaDataList.size(); ++ i) {
            keys[i] = mAvailableBaDataList.get(i);
            if(mAvailableBaDataList.get(i).equals("ba.extracted_rri")) {

                if(CommonConsts.ADD_RECEIVER_PARAM_BA_SAMPLING_INTERVAL != -1 && paramStringBuilder.indexOf("ba.sampling_interval") == -1) {
                    if(paramStringBuilder.length() > 0 && paramStringBuilder.lastIndexOf(CommonConsts.BR) != paramStringBuilder.length() - 1) {
                        paramStringBuilder.append(CommonConsts.BR);
                    }
                    paramStringBuilder.append("ba.sampling_interval="+String.valueOf(CommonConsts.ADD_RECEIVER_PARAM_BA_SAMPLING_INTERVAL));
                }
                if(CommonConsts.ADD_RECEIVER_PARAM_BA_ECG_THRESHHOLD != -1) {
                    if(paramStringBuilder.length() > 0 && paramStringBuilder.lastIndexOf(CommonConsts.BR) != paramStringBuilder.length() - 1) {
                        paramStringBuilder.append(CommonConsts.BR);
                    }
                    paramStringBuilder.append("ba.ecg_threshhold="+String.valueOf(CommonConsts.ADD_RECEIVER_PARAM_BA_ECG_THRESHHOLD));
                }
                if(CommonConsts.ADD_RECEIVER_PARAM_BA_SKIP_COUNT != -1) {
                    if(paramStringBuilder.length() > 0 && paramStringBuilder.lastIndexOf(CommonConsts.BR) != paramStringBuilder.length() - 1) {
                        paramStringBuilder.append(CommonConsts.BR);
                    }
                    paramStringBuilder.append("ba.ecg_skip_count="+String.valueOf(CommonConsts.ADD_RECEIVER_PARAM_BA_SKIP_COUNT));
                }

            } else if(mAvailableBaDataList.get(i).equals("ba.cleaned_rri")) {

                if(CommonConsts.ADD_RECEIVER_PARAM_BA_SAMPLING_INTERVAL != -1 && paramStringBuilder.indexOf("ba.sampling_interval") == -1) {
                    if(paramStringBuilder.length() > 0 && paramStringBuilder.lastIndexOf(CommonConsts.BR) != paramStringBuilder.length() - 1) {

                        paramStringBuilder.append(CommonConsts.BR);
                    }
                    paramStringBuilder.append("ba.sampling_interval="+String.valueOf(CommonConsts.ADD_RECEIVER_PARAM_BA_SAMPLING_INTERVAL));
                }
                if(CommonConsts.ADD_RECEIVER_PARAM_BA_RRI_MIN != -1) {
                    if(paramStringBuilder.length() > 0 && paramStringBuilder.lastIndexOf(CommonConsts.BR) != paramStringBuilder.length() - 1) {
                        paramStringBuilder.append(CommonConsts.BR);
                    }
                    paramStringBuilder.append("ba.rri_min="+String.valueOf(CommonConsts.ADD_RECEIVER_PARAM_BA_RRI_MIN));
                }
                if(CommonConsts.ADD_RECEIVER_PARAM_BA_RRI_MAX != -1) {
                    if(paramStringBuilder.length() > 0 && paramStringBuilder.lastIndexOf(CommonConsts.BR) != paramStringBuilder.length() - 1) {

                        paramStringBuilder.append(CommonConsts.BR);
                    }
                    paramStringBuilder.append("ba.rri_max="+String.valueOf(CommonConsts.ADD_RECEIVER_PARAM_BA_RRI_MAX));
                }
                if(CommonConsts.ADD_RECEIVER_PARAM_BA_SAMPLE_COUNT != -1) {
                    if(paramStringBuilder.length() > 0 && paramStringBuilder.lastIndexOf(CommonConsts.BR) != paramStringBuilder.length() - 1) {
                        paramStringBuilder.append(CommonConsts.BR);
                    }
                    paramStringBuilder.append("ba.sample_count="+String.valueOf(CommonConsts.ADD_RECEIVER_PARAM_BA_SAMPLE_COUNT));
                }
                if(CommonConsts.ADD_RECEIVER_PARAM_BA_RRI_INPUT != null) {
                    if(paramStringBuilder.length() > 0 && paramStringBuilder.lastIndexOf(CommonConsts.BR) != paramStringBuilder.length() - 1) {
                        paramStringBuilder.append(CommonConsts.BR);
                    }
                    paramStringBuilder.append("ba.rri_input="+String.valueOf(CommonConsts.ADD_RECEIVER_PARAM_BA_RRI_INPUT));
                }
            } else if(mAvailableBaDataList.get(i).equals("ba.interpolated_rri")) {
                if (CommonConsts.ADD_RECEIVER_PARAM_BA_FREQ_SAMPLING_INTERVAL != -1 && paramStringBuilder.indexOf("ba.freq_sampling_interval") == -1) {
                    if(paramStringBuilder.length() > 0 && paramStringBuilder.lastIndexOf(CommonConsts.BR) != paramStringBuilder.length() - 1) {
                        paramStringBuilder.append(CommonConsts.BR);
                    }
                    paramStringBuilder.append("ba.freq_sampling_interval=" + String.valueOf(CommonConsts.ADD_RECEIVER_PARAM_BA_FREQ_SAMPLING_INTERVAL));
                }
                if(CommonConsts.ADD_RECEIVER_PARAM_BA_FREQ_SAMPLING_WINDOW != -1 && paramStringBuilder.indexOf("ba.freq_sampling_window") == -1) {
                    if (paramStringBuilder.length() > 0 && paramStringBuilder.lastIndexOf(CommonConsts.BR) != paramStringBuilder.length() - 1) {

                        paramStringBuilder.append(CommonConsts.BR);
                    }
                    paramStringBuilder.append("ba.freq_sampling_window="+String.valueOf(CommonConsts.ADD_RECEIVER_PARAM_BA_FREQ_SAMPLING_WINDOW));
                }
                if(CommonConsts.ADD_RECEIVER_PARAM_BA_RRI_SAMPLING_RATE != -1 && paramStringBuilder.indexOf("ba.rri_sampling_rate") == -1) {
                    if(paramStringBuilder.length() > 0 && paramStringBuilder.lastIndexOf(CommonConsts.BR) != paramStringBuilder.length() - 1) {
                        paramStringBuilder.append(CommonConsts.BR);
                    }
                    paramStringBuilder.append("ba.rri_sampling_rate="+String.valueOf(CommonConsts.ADD_RECEIVER_PARAM_BA_RRI_SAMPLING_RATE));
                }
            } else if(mAvailableBaDataList.get(i).equals("ba.freq_domain")) {
                if (CommonConsts.ADD_RECEIVER_PARAM_BA_FREQ_SAMPLING_INTERVAL != -1 && paramStringBuilder.indexOf("ba.freq_sampling_interval") == -1) {
                    if(paramStringBuilder.length() > 0 && paramStringBuilder.lastIndexOf(CommonConsts.BR) != paramStringBuilder.length() - 1) {
                        paramStringBuilder.append(CommonConsts.BR);
                    }
                    paramStringBuilder.append("ba.freq_sampling_interval=" + String.valueOf(CommonConsts.ADD_RECEIVER_PARAM_BA_FREQ_SAMPLING_INTERVAL));
                }
                if(CommonConsts.ADD_RECEIVER_PARAM_BA_FREQ_SAMPLING_WINDOW != -1 && paramStringBuilder.indexOf("ba.freq_sampling_window") == -1) {
                    if (paramStringBuilder.length() > 0 && paramStringBuilder.lastIndexOf(CommonConsts.BR) != paramStringBuilder.length() - 1) {
                        paramStringBuilder.append(CommonConsts.BR);
                    }
                    paramStringBuilder.append("ba.freq_sampling_window="+String.valueOf(CommonConsts.ADD_RECEIVER_PARAM_BA_FREQ_SAMPLING_WINDOW));
                }
                if(CommonConsts.ADD_RECEIVER_PARAM_BA_RRI_SAMPLING_RATE != -1 && paramStringBuilder.indexOf("ba.rri_sampling_rate") == -1) {
                    if (paramStringBuilder.length() > 0 && paramStringBuilder.lastIndexOf(CommonConsts.BR) != paramStringBuilder.length() - 1) {
                        paramStringBuilder.append(CommonConsts.BR);
                    }
                    paramStringBuilder.append("ba.rri_sampling_rate=" + String.valueOf(CommonConsts.ADD_RECEIVER_PARAM_BA_RRI_SAMPLING_RATE));
                }
            } else if(mAvailableBaDataList.get(i).equals("ba.time_domain")) {
                if (CommonConsts.ADD_RECEIVER_PARAM_BA_TIME_SAMPLING_INTERVAL != -1) {
                    if (paramStringBuilder.length() > 0 && paramStringBuilder.lastIndexOf(CommonConsts.BR) != paramStringBuilder.length() - 1) {
                        paramStringBuilder.append(CommonConsts.BR);
                    }
                    paramStringBuilder.append("ba.time_sampling_interval=" + String.valueOf(CommonConsts.ADD_RECEIVER_PARAM_BA_TIME_SAMPLING_INTERVAL));
                }
                if(CommonConsts.ADD_RECEIVER_PARAM_BA_TIME_SAMPLING_WINDOW != -1) {
                    if(paramStringBuilder.length() > 0 && paramStringBuilder.lastIndexOf(CommonConsts.BR) != paramStringBuilder.length() - 1) {
                        paramStringBuilder.append(CommonConsts.BR);
                    }
                    paramStringBuilder.append("ba.time_sampling_window="+String.valueOf(CommonConsts.ADD_RECEIVER_PARAM_BA_TIME_SAMPLING_WINDOW));
                }
            } else {
            }
        }
        String sParam = paramStringBuilder.toString();
        int response_id = mHitoeSdkAPI.addReceiver(sSessionID, keys, mDataReceiverCallback, sParam, null);
        if (response_id != CommonConsts.RES_ID_SUCCESS) {
            return;
        }
    }

    public void addExReceiverProcess(ExData exData) {
        String keyString = exData.key;
        ArrayList<String> dataList = exData.dataList;
        if(!mAvailableExDataList.contains(keyString)) {
            mFlagForEx = false;
            return;
        }
        int response_id;
        String[] keys = new String[1];
        keys[0] = exData.key;

        StringBuilder paramStringBuilder = new StringBuilder();
        StringBuilder dataStringBuilder = new StringBuilder();
        String paramString;
        String dataString;

        for(int i = 0; i < dataList.size(); i ++) {

            if(dataStringBuilder.length() > 0) {

                dataStringBuilder.append(CommonConsts.BR);
            }
            dataStringBuilder.append(dataList.get(i));
        }

        if(keyString.equals("ex.stress")) {
        } else if(keyString.equals("ex.posture")) {
            if(CommonConsts.ADD_RECEIVER_PARAM_EX_ACC_AXIS_XYZ != null) {
                if(paramStringBuilder.length() > 0) {
                    paramStringBuilder.append(CommonConsts.BR);
                }
                paramStringBuilder.append("ex.acc_axis_xyz="+CommonConsts.ADD_RECEIVER_PARAM_EX_ACC_AXIS_XYZ);
            }
            if(CommonConsts.ADD_RECEIVER_PARAM_EX_POSTURE_WINDOW != -1) {
                if(paramStringBuilder.length() > 0) {
                    paramStringBuilder.append(CommonConsts.BR);
                }
                paramStringBuilder.append("ex.posture_window="+CommonConsts.ADD_RECEIVER_PARAM_EX_POSTURE_WINDOW);
            }
        } else if(keyString.equals("ex.walk")) {

            if(CommonConsts.ADD_RECEIVER_PARAM_EX_ACC_AXIS_XYZ != null) {
                if(paramStringBuilder.length() > 0) {
                    paramStringBuilder.append(CommonConsts.BR);
                }
                paramStringBuilder.append("ex.acc_axis_xyz="+CommonConsts.ADD_RECEIVER_PARAM_EX_ACC_AXIS_XYZ);
            }
            if(CommonConsts.ADD_RECEIVER_PARAM_EX_WALK_STRIDE != -1) {
                if(paramStringBuilder.length() > 0) {
                    paramStringBuilder.append(CommonConsts.BR);
                }
                paramStringBuilder.append("ex.walk_stride="+CommonConsts.ADD_RECEIVER_PARAM_EX_WALK_STRIDE);
            }
            if(CommonConsts.ADD_RECEIVER_PARAM_EX_RUN_STRIDE_COF != -1) {
                if(paramStringBuilder.length() > 0) {
                    paramStringBuilder.append(CommonConsts.BR);
                }
                paramStringBuilder.append("ex.run_stride_cof="+CommonConsts.ADD_RECEIVER_PARAM_EX_RUN_STRIDE_COF);
            }
            if(CommonConsts.ADD_RECEIVER_PARAM_EX_RUN_STRIDE_INT != -1) {
                if(paramStringBuilder.length() > 0) {
                    paramStringBuilder.append(CommonConsts.BR);
                }
                paramStringBuilder.append("ex.run_stride_int="+CommonConsts.ADD_RECEIVER_PARAM_EX_RUN_STRIDE_INT);
            }
        } else if(keyString.equals("ex.lr_balance")) {
            if(CommonConsts.ADD_RECEIVER_PARAM_EX_ACC_AXIS_XYZ != null) {
                if(paramStringBuilder.length() > 0) {
                    paramStringBuilder.append(CommonConsts.BR);
                }
                paramStringBuilder.append("ex.acc_axis_xyz="+CommonConsts.ADD_RECEIVER_PARAM_EX_ACC_AXIS_XYZ);
            }
        }

        paramString = paramStringBuilder.toString();
        dataString = dataStringBuilder.toString();

        response_id = mHitoeSdkAPI.addReceiver(null, keys, mDataReceiverCallback, paramString, dataString);
        if(response_id != CommonConsts.RES_ID_SUCCESS) {
            mFlagForEx = false;
        }
    }

    HitoeSdkAPI.DataReceiverCallback mDataReceiverCallback = new HitoeSdkAPI.DataReceiverCallback() {
        @Override
        public void onDataReceive(String connectionId, int response_id, String dataKey, String rawData) {
            //System.out.println("ENTERING mDataReceiverCallback, connectionId = " + connectionId + " response_id = " + response_id + " rawData = " + rawData);
            if(sSessionID == null)
                return;

            if (dataKey.equals("raw.ecg")) {
//                parseECGStr(rawData);

            } else if (dataKey.equals("raw.acc")) {
//                parseACCStr(rawData);
                ArrayList<String> postureInputList  = new ArrayList<String>();
                ArrayList<String> walkInputList  = new ArrayList<String>();
                ArrayList<String> lrBalanceInputList  = new ArrayList<String>();

                String[] lineList = rawData.split(CommonConsts.BR);
                for(int i=0; i < lineList.length; i++) {
                    if(mAvailableExDataList.contains("ex.posture")) {
                        try {
                            mListForPosture.add(lineList[i]);
                            if (mListForPosture.size() > CommonConsts.EX_POSTURE_UNIT_NUM + 5) {
                                for (int j = 0; j < CommonConsts.EX_POSTURE_UNIT_NUM; j++) {
                                    postureInputList.add(mListForPosture.get(j));
                                }
                                for (int j = CommonConsts.EX_POSTURE_UNIT_NUM; j < CommonConsts.EX_POSTURE_UNIT_NUM + 5; j++) {
                                    postureInputList.add(mListForPosture.get(j));
                                }
                                ArrayList<String> workList = new ArrayList<String>();
                                for (int j = 25; j < mListForPosture.size(); j++) {

                                    workList.add(mListForPosture.get(j));
                                }
                                mListForPosture = workList;
                            }
                        } catch (Exception e) {}

                        if(postureInputList.size() > 0) {
                            mListForEx.add(new ExData("ex.posture", postureInputList));
                            postureInputList.clear();
                        }
                    }
                    if(mAvailableExDataList.contains("ex.walk")) {
                        try {
                            mListForWalk.add(lineList[i]);
                            if (mListForWalk.size() > CommonConsts.EX_WALK_UNIT_NUM + 5) {
                                for (int j = 0; j < CommonConsts.EX_WALK_UNIT_NUM; j++) {
                                    walkInputList.add(mListForWalk.get(j));
                                }
                                for (int j = CommonConsts.EX_WALK_UNIT_NUM; j < CommonConsts.EX_WALK_UNIT_NUM + 5; j++) {
                                    walkInputList.add(mListForWalk.get(j));
                                }
                                ArrayList<String> workList = new ArrayList<String>();
                                for (int j = 25; j < mListForWalk.size(); j++) {
                                    workList.add(mListForWalk.get(j));
                                }
                            }
                        } catch (Exception e) {}

                        if(walkInputList.size() > 0) {
                            mListForEx.add(new ExData("ex.walk", walkInputList));
                        }
                    }
                    if(mAvailableExDataList.contains("ex.lr_balance")) {
                        try {
                            mListForLRBalance.add(lineList[i]);
                            if (mListForLRBalance.size() > CommonConsts.EX_LR_BALANCE_UNIT_NUM + 5) {
                                for (int j = 0; j < CommonConsts.EX_LR_BALANCE_UNIT_NUM; j++) {

                                    lrBalanceInputList.add(mListForLRBalance.get(j));
                                }
                                for (int j = CommonConsts.EX_LR_BALANCE_UNIT_NUM; j < CommonConsts.EX_LR_BALANCE_UNIT_NUM + 5; j++) {
                                    lrBalanceInputList.add(mListForLRBalance.get(j));
                                }
                                ArrayList<String> workList = new ArrayList<String>();
                                for (int j = 25; j < mListForLRBalance.size(); j++) {
                                    workList.add(mListForLRBalance.get(j));
                                }
                                mListForLRBalance = workList;

                            }
                        } catch (Exception e) {}
                        if(lrBalanceInputList.size() > 0) {
                            mListForEx.add(new ExData("ex.lr_balance", lrBalanceInputList));
                        }
                    }
                }

            } else if (dataKey.equals("raw.rri")) {
                // sdnn
               String[] ary = rawData.split(",");
               if(ary.length == 2) {
                   sdnn = Math.sqrt(Double.valueOf(ary[1]));
               }

            } else if (dataKey.equals("raw.bat")) {
//                parseBatStr(rawData);

            } else if (dataKey.equals("raw.hr")) {
//                parseHRStr(rawData);
                // bmp
                String[] ary = rawData.split(",");
                if(ary.length == 2) {
                    bpm = Double.valueOf(ary[1]);
                }

            } else if (dataKey.equals("raw.saved_hr")) {

                // 最後のフラグを見て終了であれば切断する
                if(response_id != CommonConsts.RES_ID_CONTINUE) {

                    disconnectDevice();
                }
            } else if (dataKey.equals("raw.saved_rri")) {

                // 最後のフラグを見て終了であれば切断する
                if(response_id != CommonConsts.RES_ID_CONTINUE) {

                    disconnectDevice();
                }
            } else if (dataKey.equals("ba.extracted_rri")) {

            } else if (dataKey.equals("ba.cleaned_rri")) {

            } else if (dataKey.equals("ba.interpolated_rri")) {

            } else if (dataKey.equals("ba.freq_domain")) {
                String[] lineList = rawData.split(CommonConsts.BR);
                ArrayList<String> stressInputList = new ArrayList<String>();
                if(mAvailableExDataList.contains("ex.stress")) {
                    for (int i = 0; i < lineList.length; i++) {
                        stressInputList.add(lineList[i]);
                    }
                    mListForEx.add(new ExData("ex.stress", stressInputList));
                }

            } else if (dataKey.equals("ba.time_domain")) {

            } else if (dataKey.equals("ex.stress")) {
//                parseStress(rawData);
                // lf/hf
                String[] tmpStrList = rawData.split(",");
                String tmpRawData = tmpStrList[1];
                lfhf = Double.valueOf(tmpRawData);
                if(lfhf <= 0.3||lfhf>=2.4){
//                if(true){
                    // try{
                    //     if(!bAlertDialogShow) {
                    //         bAlertDialogShow = true;
                    //         Intent intent = new Intent(SAPPhireService.this, AlertActivity.class);
                    //         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //         startActivity(intent);
                    //     }
                    // }catch(Exception e){
                    //     Log.d("SAPPhire",e.toString());
                    // }

                }
                Log.d("EX.STRESS", tmpRawData);
            } else if (dataKey.equals("ex.posture")) {
//                parsePosture(rawData);

            } else if (dataKey.equals("ex.walk")) {
//                parseWalk(rawData);

            } else if (dataKey.equals("ex.lr_balance")) {
//                parseLRBalance(rawData);

            }


            if (dataKey.startsWith(CommonConsts.EX_DATA_PREFFIX)) {
                removeConnectionId(connectionId);
            }
            else {
                ExData exData = null;
                try{
                    if(!mFlagForEx && mListForEx.size() > 0) {
                        mFlagForEx = true;
                        exData = mListForEx.get(0);
                        mListForEx.remove(0);
                    }
                }finally {
                }
                if(exData != null) {
                    addExReceiverProcess(exData);
                }
            }
        }
    };

    void removeConnectionId(String connectionId) {
        if(sRawConnectionId != null && sRawConnectionId.equals(connectionId)) {
            sRawConnectionId = null;
        } else if(sBaConnectionId != null && sBaConnectionId.equals(connectionId)) {
            sBaConnectionId = null;
        } else if(mExConnectionList != null && mExConnectionList.contains(connectionId)) {
            mExConnectionList.remove(connectionId);
        }
    }


    private double gpsLongitude;
    private double gpsLatitude;
    private double lfhf;
    private double bpm;
    private double sdnn;
}
