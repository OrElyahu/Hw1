package com.example.hw1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ScoreActivity extends AppCompatActivity {

    private Fragment_Score fragmentScore;
    private FragmantMap fragmentMap;
    private static CallBack_Update callBackUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        fragmentScore = new Fragment_Score();
        fragmentScore.setActivity(this);
        fragmentScore.setDataManager(callBackUpdate.getData());
        fragmentScore.setCallBackLocation(callBackLocation);
        getSupportFragmentManager().beginTransaction().add(R.id.frame1, fragmentScore).commit();


        fragmentMap = new FragmantMap();
        fragmentMap.setActivity(this);
        fragmentMap.setDataManager(callBackUpdate.getData());
        getSupportFragmentManager().beginTransaction().add(R.id.frame2, fragmentMap).commit();

    }

    private void zoomOnMap(double lat, double lon) {
        GoogleMap gm = fragmentMap.getGoogleMap();
        LatLng point = new LatLng(lat, lon);
        gm.addMarker(new MarkerOptions()
                .position(point)
                .title("* Crash Site * | Pilot Name: "));
        gm.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 13.0f));
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public static void setCallBack_Update(CallBack_Update callBack_update) { callBackUpdate = callBack_update; }

    CallBack_Location callBackLocation = new CallBack_Location() {
        @Override
        public void setLocation(double lat, double lon) {
            zoomOnMap(lat, lon);
        }
    };

}
