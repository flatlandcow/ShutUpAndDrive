package com.DK.shutupdrive;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

public class Speed extends Service implements LocationListener {

    protected LocationManager locationManager;
    Location location;
    double latitude;
    double longitude;
    static float velocity;
    String provider;

    private static final long minDist = 0;
    private static final long minTime = 0;


    @Override
    public void onCreate() {
        getLocation();
        System.out.println("Hello");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    	System.out.println("status changed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent,flags,startId);
        //because we do not want to stop the service unless we explicitly say so.
        return START_STICKY;
    }

    public Location getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setSpeedRequired(true);
        provider = locationManager.getBestProvider(criteria, false);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDist,
                this);
        location = locationManager.getLastKnownLocation(provider);
        if(location != null) {
            onLocationChanged(location);            
        }

        return location;        
    }

    @Override
    public void onLocationChanged(Location location) {
        velocity = location.getSpeed();
        speed(velocity);
    }
    public static float speed(float velocity){
    	return velocity;
    }

}