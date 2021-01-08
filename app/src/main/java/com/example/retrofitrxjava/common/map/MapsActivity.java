package com.example.retrofitrxjava.common.map;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.b.BActivity;
import com.example.retrofitrxjava.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends BActivity<ActivityMapsBinding> implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void initLayout() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        binding.ivBack.setOnClickListener(v -> finish());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_maps;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng hv = new LatLng(20.980872, 105.796186);
        mMap.addMarker(new MarkerOptions().position(hv).title("Học viện kỹ thuật mật mã"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(hv));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(hv, 16));
    }
}