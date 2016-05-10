package com.sap.service;

import java.util.ArrayList;

/**
 * Created by charlie on 3/31/16.
 */
public class ExData {
    public String key;
    public ArrayList<String> dataList;

    public ExData(String key, ArrayList<String> dataList) {

        this.key = key;
        this.dataList = new ArrayList<String>();
        for(int i=0; i < dataList.size(); i++) {
            this.dataList.add(new String(dataList.get(i)));
        }
    }
}
