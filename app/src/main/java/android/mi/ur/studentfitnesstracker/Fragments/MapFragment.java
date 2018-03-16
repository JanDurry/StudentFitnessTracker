package android.mi.ur.studentfitnesstracker.Fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.location.Location;
import android.mi.ur.studentfitnesstracker.Constants.Constants;
import android.mi.ur.studentfitnesstracker.R;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by JanDurry on 24.02.2018.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap googleMap;
    MapView mapView;
    View view;
    Polyline line;

    private Location currentLocation = null;
    private Location startLocation = null;

    private ArrayList<LatLng> points;

    private boolean bound;

    public MapFragment () {
        // required emtpy public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        points = new ArrayList<LatLng>();
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


    /* on App start show basic Location Regensburg */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        this.googleMap = googleMap;
        resetLocation();
    }

    /* function to draw a line */

    private void redrawLine() {
        googleMap.clear();  //clears all Markers and Polylines

        PolylineOptions options = new PolylineOptions()
                .width(Constants.POLYLINE_WIDTH)
                .color(Color.BLUE)
                .geodesic(true);
        for (int i = 0; i < points.size(); i++) {
            LatLng point = points.get(i);
            options.add(point);
        }
        googleMap.addMarker(new MarkerOptions().position(new LatLng(points.get(points.size()-1).latitude, points.get(points.size()-1).longitude)));
        line = googleMap.addPolyline(options); //add Polyline
    }

    /* LocationChange is called whenever OnNewLocation is called
    * -> see SessionFragmentOnGoing and Callback in MainMenu
    * */

    public void locationChange(Location current) {
        currentLocation = current;
        CameraPosition currentPosition = CameraPosition.builder()
                .target(new LatLng(current.getLatitude(), current.getLongitude()))
                .zoom(Constants.CAMERA_DEFAULT_ZOOM)
                .bearing(Constants.CAMERA_BEARING)
                .tilt(Constants.CAMERA_TILT).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentPosition));
        LatLng latLng = new LatLng(current.getLatitude(), current.getLongitude());
        points.add(latLng);
        redrawLine();

    }

    /* onFinishSession is called when Service is unbounded in SessionFragmentOnGoing
    * -> see SessionFragmentOnGoing and Callback in MainMenu
    * */

    public void onFinishSession() {
        if (currentLocation != null) {
            CameraPosition currentPosition = CameraPosition.builder().target(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
                    .zoom(Constants.CAMERA_ON_SESSION_FINISHED_ZOOM)
                    .bearing(Constants.CAMERA_BEARING)
                    .tilt(Constants.CAMERA_TILT)
                    .build();
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentPosition));
            redrawLine();
        }
    }

    /* onStartLocation is called as soon the firstLocation after Session start is called
    *  -> see SessionFragmentOnGoing and Callback in MainMenu
    * */

    public void onStartLocation(Location first) {
        googleMap.clear();
        points.clear();
        startLocation = first;
        CameraPosition currentPosition = CameraPosition.builder()
                .target(new LatLng(startLocation.getLatitude(), startLocation.getLongitude()))
                .zoom(Constants.CAMERA_DEFAULT_ZOOM)
                .bearing(Constants.CAMERA_BEARING)
                .tilt(Constants.CAMERA_TILT)
                .build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentPosition));
        LatLng latLng = new LatLng(startLocation.getLatitude(), startLocation.getLongitude());
        points.add(latLng);
        redrawLine();
    }

    private void resetLocation() {
        googleMap.clear();
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(Constants.CAMERA_DEFAULT_LATITUDE, Constants.CAMERA_DEFAULT_LONGITUDE))
                .title(Constants.CAMERA_DEFAULT_TITLE));
        CameraPosition Regensburg = CameraPosition.builder()
                .target(new LatLng(Constants.CAMERA_DEFAULT_LATITUDE, Constants.CAMERA_DEFAULT_LONGITUDE))
                .zoom(Constants.CAMERA_DEFAULT_ZOOM)
                .bearing(Constants.CAMERA_BEARING)
                .tilt(Constants.CAMERA_TILT)
                .build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Regensburg));
    }
}
