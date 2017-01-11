package com.greenland.android.manbogi;

/**
 * Created by mudaya4 on 2017-01-10.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.nhn.android.maps.NMapContext;
//import com.nhn.android.maps.NMapView;

public class ManboScreen extends Fragment {

    Button btnStopService;

    Intent intentMyService;
    BroadcastReceiver receiver;

    boolean flag = true;

    Toast toast;
    TextView CountText;
    String serviceData;

    Thread mThread;

    //temp //////////////////////
    String tempData;

    double myLat = 0;
    double myLng = 0;

    private NMapContext mMapContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.main_manbogi, container, false);

        // super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

//		Runnable getLocationRun = new Runnable() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				viewCurrentLocation();
//			}
//		};
//
//		tempData = getMyLocation();

//		mThread = new Thread(getLocationRun);

        ManboLocationManager mbLocMgr = ManboLocationManager.getInstance(getActivity());
        mbLocMgr.setHandler(mHandler);
        mbLocMgr.getMyLocation();


        intentMyService = new Intent(getActivity(),
                com.greenland.android.manbogi.MyServiceIntent.class);
        // 실행되기 원하는 서비스 등록

        receiver = new MyMainLocalRecever();

        CountText = (TextView) getActivity().findViewById(R.id.walk_count);

        btnStopService = (Button) getActivity().findViewById(R.id.btnStopService);
        // 서비스 중지

        btnStopService.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (flag) {

                    btnStopService.setText("Stop !!");

                    // TODO Auto-generated method stub
                    try {

                        IntentFilter mainFilter = new IntentFilter(
                                "com.androday.test.step");

                        getActivity().registerReceiver(receiver, mainFilter);

                        getActivity().startService(intentMyService);
                        // txtMsg.setText("After stoping Service:\n"+service.getClassName());
                        Toast.makeText(getActivity(), "서비스 시작", 1)
                                .show();
                    } catch (Exception e) {
                        // TODO: handle exception
                        Toast.makeText(getActivity(), e.getMessage(),
                                1).show();
                    }
                } else {

                    btnStopService.setText("Go !!");

                    // TODO Auto-generated method stub
                    try {

                        getActivity().unregisterReceiver(receiver);

                        getActivity().stopService(intentMyService);

                        Toast.makeText(getActivity(), "서비스 중지", 1)
                                .show();
                        // txtMsg.setText("After stoping Service:\n"+service.getClassName());
                    } catch (Exception e) {
                        // TODO: handle exception
                        Toast.makeText(getActivity(), e.getMessage(),
                                1).show();
                    }
                }
                flag = !flag;
            }
        });

//		viewCurrentLocation();
    }

    public class MyMainLocalRecever extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            serviceData = intent.getStringExtra("serviceData");

            CountText.setText(serviceData);

            Toast.makeText(getActivity(), "Walking . . . ", 1).show();
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {

        }
    };

//	public void viewCurrentLocation(){
//		Log.e("HSH", "HSH viewCurrentLocation(");
//		String clientId = "b40JMlbFg5RsPGEZWjWb";//애플리케이션 클라이언트 아이디값";
//        String clientSecret = "J7nbK40t6g";//애플리케이션 클라이언트 시크릿값";
//        try {
////          String addr = URLEncoder.encode("불정로 6", "UTF-8");
//            String apiURL = "https://openapi.naver.com/v1/map/geocode?query=" + tempData; //json
//            //String apiURL = "https://openapi.naver.com/v1/map/geocode.xml?query=" + addr; // xml
//            URL url = new URL(apiURL);
//            HttpURLConnection con = (HttpURLConnection)url.openConnection();
//            con.setRequestMethod("GET");
//            con.setRequestProperty("X-Naver-Client-Id", clientId);
//            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
//            int responseCode = con.getResponseCode();
//            BufferedReader br;
//            if(responseCode==200) { // 정상 호출
//                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            } else {  // 에러 발생
//                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
//            }
//            String inputLine;
//            StringBuffer response = new StringBuffer();
//            while ((inputLine = br.readLine()) != null) {
//                response.append(inputLine);
//            }
//            br.close();
//            Log.i("HSH", "HSH \\n" + response.toString());
//            System.out.println(response.toString());
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//	}
//
//
//    private String getMyLocation(){
//    	// Acquire a reference to the system Location Manager
//        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//
//        // GPS 프로바이더 사용가능여부
//        Boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        // 네트워크 프로바이더 사용가능여부
//        Boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//
//        Log.d("Main", "isGPSEnabled=" + isGPSEnabled);
//        Log.d("Main", "isNetworkEnabled=" + isNetworkEnabled);
//
//        LocationListener locationListener = new LocationListener() {
//            public void onLocationChanged(Location location) {
//                double lat = location.getLatitude();
//                double lng = location.getLongitude();
//                myLat = lat;
//                myLng = lng;
//                tempData = String.valueOf(lat) + "," + String.valueOf(lng);
//                Toast.makeText(getActivity(), lat + " " + lng, Toast.LENGTH_SHORT).show();
//                mThread.start();
//            }
//
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//            }
//
//            public void onProviderEnabled(String provider) {
//            }
//
//            public void onProviderDisabled(String provider) {
//            }
//        };
//
//        // Register the listener with the Location Manager to receive location updates
////        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////            // TODO: Consider calling
////            //    ActivityCompat#requestPermissions
////            // here to request the missing permissions, and then overriding
////            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
////            //                                          int[] grantResults)
////            // to handle the case where the user grants the permission. See the documentation
////            // for ActivityCompat#requestPermissions for more details.
////            return;
////        }
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//
//        // 수동으로 위치 구하기
//        String locationProvider = LocationManager.GPS_PROVIDER;
//        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
//        if (lastKnownLocation != null) {
//            double lng = lastKnownLocation.getLatitude();
//            double lat = lastKnownLocation.getLatitude();
//            myLat = lat;
//            myLng = lng;
//            Log.d("Main", "longtitude=" + lng + ", latitude=" + lat);
//        }
//
//        String latlng = String.valueOf(myLat) + "," + String.valueOf(myLng);
//
//        return latlng;
//    }

}
