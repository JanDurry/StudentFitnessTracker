package android.mi.ur.studentfitnesstracker.Fragments;

import android.app.Fragment;
import android.location.Location;
import android.mi.ur.studentfitnesstracker.R;
import android.mi.ur.studentfitnesstracker.TrackingTools.SessionService;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by JanDurry on 24.02.2018.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {

    SessionService sessionService;

    GoogleMap googleMap;
    MapView mapView;
    View view;

    private boolean bound;

    public MapFragment () {
        // required emtpy public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) view.findViewById(R.id.map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.map_fragment, container, false);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());

        this.googleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        googleMap.addMarker(new MarkerOptions().position(new LatLng(49.013432, 12.101624)).title("Blabla").snippet("blablalblalbal"));

        CameraPosition Liberty = CameraPosition.builder().target(new LatLng(49.013432, 12.101624)).zoom(16).bearing(0).tilt(45).build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));


    }

    public void newLocation(Location current) {
        googleMap.addMarker(new MarkerOptions().position(new LatLng(current.getLatitude(), current.getLongitude())));

        CameraPosition currentLocation = CameraPosition.builder().target(new LatLng(current.getLatitude(), current.getLongitude())).zoom(16).bearing(0).tilt(45).build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentLocation));

    }
}
