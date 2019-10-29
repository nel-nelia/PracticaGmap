package com.example.mymap;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment
    implements OnMapReadyCallback, LocationListener {

  GoogleMap gMap;
  LocationManager lm;
  Location location;



  public MapFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View v = inflater.inflate(R.layout.fragment_map, container, false);
    SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);
    return v;

  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    gMap = googleMap;

    lm = (LocationManager) getContext()
            .getSystemService(Context.LOCATION_SERVICE);
    if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      // TODO: Consider calling
      //    Activity#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for Activity#requestPermissions for more details.
      return;
    }
    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,  this);
    location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

    double longitude = location.getLongitude();
    double latitude = location.getLatitude();

    LatLng aqui = new LatLng(latitude, longitude);

    CameraPosition cameraPosition = new CameraPosition.Builder()
            .target(aqui)
            .zoom(14)//zoom
            .bearing(30)//inclinacion
            .build();

    gMap.addMarker(new MarkerOptions().position(aqui).title("Mi ubicación"));
    gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));



  }

  @Override
  public void onLocationChanged(Location location) {
    if (location != null) {
      Log.v("Location Changed", location.getLatitude() + " and " + location.getLongitude());
      lm.removeUpdates(this);
    }
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
}
