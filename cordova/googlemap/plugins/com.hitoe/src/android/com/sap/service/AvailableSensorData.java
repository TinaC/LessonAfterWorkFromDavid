/**
 * Copyright(c) 2015 NTT DOCOMO, INC. All Rights Reserved.
 */

package com.sap.service;

/**
 * 利用可能センサデータクラス
 */
public class AvailableSensorData {

    public String mDeviceType = null;
    public String mDeviceName = null;
    public String mDeviceId = null;
    public String mConnectMode = null;
    // 利用可能Rawデータ
    public String mAvailableData = null;

    public AvailableSensorData(){}
    public AvailableSensorData(String val) {

        setData(val);
    }

    /**
     * データ登録
     * @param val 登録データ
     */
    public void setData(String val) {

        if(val == null) {

            return;
        }

        String[] list = val.split(CommonConsts.COMMA, -1);
        this.mDeviceType = list[0];
        this.mDeviceName = list[1];
        this.mDeviceId = list[2];
        this.mConnectMode = list[3];
        this.mAvailableData = list[4];

        return;
    }
}
