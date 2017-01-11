package com.greenland.android.manbogi;

/**
 * Created by mudaya4 on 2017-01-10.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class ManboLocationManager {
    private static Context mContext = null;
    private static ManboLocationManager mInstance = null;
    private Handler mHandler  = null;

    double myLat;
    double myLng;
    String tempData;

    public static ManboLocationManager getInstance(Context context) {
        mContext = context;

        synchronized (ManboLocationManager.class) {
            if (mInstance == null) {
                mInstance = new ManboLocationManager();
            }
            return mInstance;
        }
    }

    public static ManboLocationManager getInstance() {
        synchronized (ManboLocationManager.class) {
            if (mInstance == null) {
                mInstance = new ManboLocationManager();
            }
            return mInstance;
        }
    }

    ManboLocationManager() {
        tempData = new String();
    }

    public void setHandler(Handler handler) {
        mHandler = handler;
    }

    public Boolean isActiveNetwork() {

        return false;
    }

    public void getLocation() {
        if (mHandler == null) {
            return;
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                viewCurrentLocation();
            }
        });

        thread.start();
    }

    private void viewCurrentLocation(){
        Log.e("HSH", "HSH viewCurrentLocation(");
        String clientId = "b40JMlbFg5RsPGEZWjWb";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "J7nbK40t6g";//애플리케이션 클라이언트 시크릿값";
        try {
//          String addr = URLEncoder.encode("불정로 6", "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/map/geocode?query=" + tempData; //json
            //String apiURL = "https://openapi.naver.com/v1/map/geocode.xml?query=" + addr; // xml
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            Log.i("HSH", "HSH \\n" + response.toString());
            System.out.println(response.toString());

            parseResult(response.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void parseResult(String jason) {

    }


    public void getMyLocation(){
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        // GPS 프로바이더 사용가능여부
        Boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 네트워크 프로바이더 사용가능여부
        Boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Log.d("Main", "isGPSEnabled=" + isGPSEnabled);
        Log.d("Main", "isNetworkEnabled=" + isNetworkEnabled);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                myLat = lat;
                myLng = lng;
                tempData = String.valueOf(lat) + "," + String.valueOf(lng);
                Toast.makeText(mContext, lat + " " + lng, Toast.LENGTH_SHORT).show();
                getLocation();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        // Register the listener with the Location Manager to receive location updates
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        // 수동으로 위치 구하기
        String locationProvider = LocationManager.GPS_PROVIDER;
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        if (lastKnownLocation != null) {
            double lng = lastKnownLocation.getLatitude();
            double lat = lastKnownLocation.getLatitude();
            myLat = lat;
            myLng = lng;
            Log.d("Main", "longtitude=" + lng + ", latitude=" + lat);
        }
        tempData = String.valueOf(myLat) + "," + String.valueOf(myLng);
    }
}

