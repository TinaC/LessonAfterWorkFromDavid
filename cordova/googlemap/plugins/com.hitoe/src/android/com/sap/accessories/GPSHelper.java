package com.sap.accessories;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
//import android.support.v4.app.ActivityCompat;

import java.util.Iterator;

/**
 * Created by charlie on 3/23/16.
 */
public class GPSHelper {
    private LocationManager lm;
    private IGPSListener gpsListener;

    public boolean init(Context context, IGPSListener listener) {
        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.gpsListener = listener;

        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return false;
        }
        String bestProvider = lm.getBestProvider(getCriteria(), true);
        // if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        //     // TODO: Consider calling
        //     //    ActivityCompat#requestPermissions
        //     // here to request the missing permissions, and then overriding
        //     //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //     //                                          int[] grantResults)
        //     // to handle the case where the user grants the permission. See the documentation
        //     // for ActivityCompat#requestPermissions for more details.
        //     return false;
        // }
        Location location = lm.getLastKnownLocation(bestProvider);
        updateLocation(location);
        lm.addGpsStatusListener(this.listener);
        //参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种
        //参数2，位置信息更新周期，单位毫秒
        //参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
        //参数4，监听
        //备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        return true;
    }

    public void uninit() {
        lm.removeUpdates(locationListener);
        lm.removeUpdates(networkLocationListener);
    }

    private Criteria getCriteria(){
        Criteria criteria = new Criteria();
        //设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //设置是否要求速度
        criteria.setSpeedRequired(false);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        //设置是否需要方位信息
        criteria.setBearingRequired(true);
        //设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updateLocation(location);
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                //GPS状态为可见时
                case LocationProvider.AVAILABLE:
                    break;
                //GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
                    break;
                //GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    break;
            }
        }
        @Override
        public void onProviderEnabled(String provider) {
            Location location = lm.getLastKnownLocation(provider);
            updateLocation(location);
        }
        @Override
        public void onProviderDisabled(String provider) {
        }
    };
    private LocationListener networkLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updateLocation(location);
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                //GPS状态为可见时
                case LocationProvider.AVAILABLE:
                    break;
                //GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
                    break;
                //GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    break;
            }
        }
        @Override
        public void onProviderEnabled(String provider) {
            Location location = lm.getLastKnownLocation(provider);
            updateLocation(location);
        }
        @Override
        public void onProviderDisabled(String provider) {
        }
    };
    GpsStatus.Listener listener = new GpsStatus.Listener() {
        public void onGpsStatusChanged(int event) {
            switch (event) {
                //第一次定位
                case GpsStatus.GPS_EVENT_FIRST_FIX:
                    break;
                //卫星状态改变
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                    //获取当前状态
                    GpsStatus gpsStatus=lm.getGpsStatus(null);
                    //获取卫星颗数的默认最大值
                    int maxSatellites = gpsStatus.getMaxSatellites();
                    //创建一个迭代器保存所有卫星
                    Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
                    int count = 0;
                    while (iters.hasNext() && count <= maxSatellites) {
                        GpsSatellite s = iters.next();
                        count++;
                    }
                    break;
                //定位启动
                case GpsStatus.GPS_EVENT_STARTED:
                    break;
                //定位结束
                case GpsStatus.GPS_EVENT_STOPPED:
                    break;
            }
        };
    };

    private void updateLocation(Location location) {
        if(location == null) {
            if(gpsListener != null)
                gpsListener.onGPSChanged(0, 0, GpsStatus.GPS_EVENT_STOPPED);
        }
        else {
            if(gpsListener != null)
                gpsListener.onGPSChanged(location.getLongitude(), location.getLatitude(), 0);
        }
    }
}
