package com.example.cinet;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CinesFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMap.setMinZoomPreference(13.00f);

            LatLng cinet_Maquinista = new LatLng(41.439605802741276, 2.1983377230049324);
            LatLng cinet_Magic = new LatLng(41.44344182223131, 2.229998153709825);
            LatLng cinet_Diagonal = new LatLng(41.41039893822629, 2.216464073430923);
            LatLng cinet_glories = new LatLng(41.40811016274522, 2.192440875301859);
            LatLng cinet_experience = new LatLng(41.41347716616296, 2.173130568480523);
            LatLng cinet_renoir = new LatLng(41.38669406198077, 2.162144239785664);
            LatLng cinet_cinema = new LatLng(41.398026722349385, 2.152874524949368);
            LatLng balmes_cinet = new LatLng(41.41064485994699, 2.1384549717428656);
            LatLng cinet_diagonal = new LatLng(41.39931439959538, 2.135365065782355);
            LatLng cinet_verdi = new LatLng(41.41012988253883, 2.156651078358803);
            LatLng cinet_cinema2 = new LatLng(41.394678642247925, 2.1988797787276426);
            LatLng cinet_cinema3 = new LatLng(41.40472236603313, 2.1659207926430373);

            googleMap.addMarker(new MarkerOptions().position(cinet_Maquinista).title("Cinet la Maquinista"));
            googleMap.addMarker(new MarkerOptions().position(cinet_Magic).title("Cinet Màgic Badalona"));
            googleMap.addMarker(new MarkerOptions().position(cinet_Diagonal).title("Cinet Diagonal Mar"));
            googleMap.addMarker(new MarkerOptions().position(cinet_glories).title("Cinet Glòries"));
            googleMap.addMarker(new MarkerOptions().position(cinet_experience).title("Cinet Experience"));
            googleMap.addMarker(new MarkerOptions().position(cinet_renoir).title("Cinet Renoir"));
            googleMap.addMarker(new MarkerOptions().position(cinet_cinema).title("Cinet Cinema"));
            googleMap.addMarker(new MarkerOptions().position(balmes_cinet).title("Balmes Cinet"));
            googleMap.addMarker(new MarkerOptions().position(cinet_diagonal).title("Cinet Diagonal"));
            googleMap.addMarker(new MarkerOptions().position(cinet_verdi).title("Cinet Verdi"));
            googleMap.addMarker(new MarkerOptions().position(cinet_cinema2).title("Cinet Cinema"));
            googleMap.addMarker(new MarkerOptions().position(cinet_cinema3).title("Cinet Cinema"));

            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setCompassEnabled(true);

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            googleMap.setMyLocationEnabled(true);

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(cinet_Maquinista));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cines, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}