package com.example.hw1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmantMap extends Fragment implements OnMapReadyCallback {

    private SupportMapFragment mapFragment ;
    private AppCompatActivity activity;
    private GoogleMap googleMap;
    private FrameLayout map;
    private DataManager dataManager;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setDataManager(DataManager dataManager) {this.dataManager =dataManager; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmant_map, container, false);
        findViews(view);
        mapFragment=(SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if(mapFragment == null){
            FragmentManager fm = getChildFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map , mapFragment).commit();
        }
        mapFragment.getMapAsync( this);



        return view;
    }

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    private void zoomOnMap(double lon, double lat) {
        LatLng point = new LatLng(lat, lon);
        googleMap.addMarker(new MarkerOptions()
                .position(point)
                .title("* Crash Site * | Pilot Name: "));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 13.0f));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;

        LatLng melbourne = new LatLng(-37.67073140377376, 144.84332141711963);
        googleMap.addMarker(new MarkerOptions()
                .position(melbourne)
                .title("Flight Destination : Melbourne Airport, Vic, Australia"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(melbourne, 14.0f));
    }

    private void findViews (View view){
        this.map= view.findViewById(R.id.map);
    }
}
