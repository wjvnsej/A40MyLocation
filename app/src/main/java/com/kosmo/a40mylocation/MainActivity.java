package com.kosmo.a40mylocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "KOSMO61";

    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = findViewById(R.id.textview1);

        //권한체크 후 사용자에 의해 취소되었다면 다시 요청함.
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, 1);
        }
    }

    public void onBtnClicked(View v) {

        //취치관리자에 대한 참조값을 구한다.
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        //위치가 업데이트 되면 호출되는 리스너를 정의한다.
        LocationListener locationListener = new LocationListener() {

            //새로운 위치가 발견되면 위치제공자에 의해 호출되는 콜백메소드
            @Override
            public void onLocationChanged(Location location) {
                status.setText("위도 : " + location.getLatitude() +
                            "\n경도 : " + location.getLongitude() +
                            "\n고도 : " + location.getAltitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        //위치를 업데이트 받기위해 리스너를 위치관리자에 등록한다.
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            //네트워크를 통해서 위치를 알 수 있다.
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 1000, 1, locationListener);

            //GPS 를 통해서 위치를 알 수 있다.
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
        }

    }
}
