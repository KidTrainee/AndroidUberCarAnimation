package com.example.taili.androidubercaranimation;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taili.androidubercaranimation.Remote.IGoogleApi;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

//    private static final String TAG = MapsActivity.class.getSimpleName();
//    private GoogleMap mMap;
//    SupportMapFragment mapFragment;
//    private List<LatLng> polylineList;
//    private Marker marker;
//    private float v;
//    private double lat, lng;
//    private Handler handler;
//    private LatLng startPos, endPos;
//    private int index, next;
//    private Button btnGo;
//    private EditText edtPlace;
//    private String destination = "Khu+đô+thị+Xa+La,+Phúc+La,+Hà+Đông,+Hanoi,+Vietnam";
//    private PolylineOptions greyPolylineOptions, blackPolylineOptions;
//    private Polyline blackPolyline, greyPolyline;
//    private LatLng myLocation;
//
//    IGoogleApi mService;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//        polylineList = new ArrayList<>();
//        btnGo = findViewById(R.id.btn_search);
//        edtPlace = findViewById(R.id.edtPlace);
//        btnGo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                destination = edtPlace.getText().toString();
//                destination = destination.replace(" ", "+"); // replace space to + to make url
//                mapFragment.getMapAsync(MapsActivity.this);
//            }
//        });
//        mService = Common.getGoogleApi();
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        mMap.setTrafficEnabled(false);
//        mMap.setIndoorEnabled(false);
//        mMap.setBuildingsEnabled(false);
//        mMap.getUiSettings().setZoomControlsEnabled(true);
//
//        // Add a marker in Sydney and move the camera
//        final LatLng sydney = new LatLng(16.0659970, 108.212552);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("My Location"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
//                    .target(googleMap.getCameraPosition().target)
//                    .zoom(17)
//                    .bearing(30)
//                    .tilt(45)
//                    .build()));
//        String requestUrl = null;
//        try {
//            requestUrl = "https://maps.googleapis.com/maps/api/directions/json?" + "mode=driving&" + "transit_routing_preference=less_driving&"
//                        + "origin="+sydney.latitude+","+sydney.longitude+"&"+
//                    "destination="+destination+"&"+
//                    "key=" + getResources().getString(R.string.google_direction_key);
//            Log.d("URL", requestUrl); // Print url to review by Chrome
//            final String finalRequestUrl = requestUrl;
//            mService.getDataFromGoogleApi(requestUrl)
//                .enqueue(new Callback<String>() {
//                @Override
//                public void onResponse(Call<String> call, Response<String> response) {
//                    try {
//                        JSONObject jo = new JSONObject(response.body().toString());
//
//                        JSONArray jsonArray = jo.getJSONArray("routes");
//
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject route = jsonArray.getJSONObject(i);
//                            JSONObject poly = route.getJSONObject("overview_polyline");
//                            String polyline = poly.getString("points");
//                            polylineList = decodePoly(polyline);
//                        }
//                        Log.d(TAG, "onResponse: " + finalRequestUrl);
//                        // Adjusting bounds
//                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//                        for (LatLng latLng : polylineList) {
//                            builder.include(latLng);
//                        }
//                        LatLngBounds bounds = builder.build();
//                        CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 2);
//                        mMap.animateCamera(mCameraUpdate);
//
//                        greyPolylineOptions = new PolylineOptions();
//                        greyPolylineOptions.color(Color.GRAY);
//                        greyPolylineOptions.width(5);
//                        greyPolylineOptions.startCap(new SquareCap());
//                        greyPolylineOptions.endCap(new SquareCap());
//                        greyPolylineOptions.jointType(JointType.ROUND);
//                        greyPolylineOptions.addAll(polylineList);
//                        greyPolyline = mMap.addPolyline(greyPolylineOptions);
//
//                        blackPolylineOptions = new PolylineOptions();
//                        blackPolylineOptions.color(Color.BLACK);
//                        blackPolylineOptions.width(5);
//                        blackPolylineOptions.startCap(new SquareCap());
//                        blackPolylineOptions.endCap(new SquareCap());
//                        blackPolylineOptions.jointType(JointType.ROUND);
//                        blackPolylineOptions.addAll(polylineList);
//                        blackPolyline = mMap.addPolyline(blackPolylineOptions);
//
//                        mMap.addMarker(new MarkerOptions().position(polylineList.get(polylineList.size()-1)));
//
//                        // Animator
//                        ValueAnimator polylineAnimator = ValueAnimator.ofInt(0, 100);
//                        polylineAnimator.setDuration(2000);
//                        polylineAnimator.setInterpolator(new LinearInterpolator());
//                        polylineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                            @Override
//                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                                List<LatLng> points = greyPolyline.getPoints();
//                                int percentValue = (int) valueAnimator.getAnimatedValue();
//                                int size = points.size();
//                                int newPoints = (int) (size * (percentValue / 100.0f));
//                                List<LatLng> p = points.subList(0, newPoints);
//                                blackPolyline.setPoints(p);
//                            }
//                        });
//                        polylineAnimator.start();
//
//                        // Add car marker
//                        marker = mMap.addMarker(new MarkerOptions().position(sydney)
//                                .flat(true)
//                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car)));
//
//                        // Car moving
//                        handler = new Handler();
//                        index = -1;
//                        next = 1;
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (index < polylineList.size() - 1) {
//                                    index++;
//                                    next = index+1;
//                                }
//                                if (index < polylineList.size() - 1) {
//                                    startPos = polylineList.get(index);
//                                    endPos = polylineList.get(next);
//                                }
//                                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
//                                valueAnimator.setDuration(3000);
//                                valueAnimator.setInterpolator(new LinearInterpolator());
//                                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                                    @Override
//                                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                                        v = valueAnimator.getAnimatedFraction();
//                                        lng = v*endPos.longitude + (1-v)*startPos.longitude;
//                                        lat = v*endPos.latitude + (1-v)*startPos.latitude;
//                                        LatLng newPos = new LatLng(lat, lng);
//                                        marker.setPosition(newPos);
//                                        marker.setAnchor(0.5f, 0.5f);
//                                        marker.setRotation(getBearing(startPos, newPos));
//                                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
//                                                .target(newPos)
//                                                .zoom(15.5f)
//                                                .build()));
//                                    }
//                                });
//                                valueAnimator.start();
//                                handler.postDelayed(this, 3000);
//                            }
//                        }, 3000);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<String> call, Throwable t) {
//                    Toast.makeText(MapsActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private float getBearing(LatLng startPos, LatLng newPos) {
//        double lat = Math.abs(startPos.latitude - newPos.latitude);
//        double lng = Math.abs(startPos.longitude - newPos.longitude);
//        if (startPos.latitude < newPos.latitude && startPos.longitude < newPos.longitude)
//            return (float) (Math.toDegrees(Math.atan(lng/lat)));
//        else if (startPos.latitude >= newPos.latitude && startPos.longitude < newPos.longitude)
//            return (float) ((90 - Math.toDegrees(Math.atan(lng/lat))) + 90);
//        else if (startPos.latitude >= newPos.latitude && startPos.longitude >= newPos.longitude)
//            return (float) (Math.toDegrees(Math.atan(lng/lat)) + 180);
//        else if (startPos.latitude < newPos.latitude && startPos.longitude < newPos.longitude)
//            return (float) ((90 - Math.toDegrees(Math.atan(lng/lat))) + 270);
//        else
//            return -1;
//    }
//
//    private List<LatLng> decodePoly(String polyline) {
//
//        List poly = new ArrayList();
//        int index = 0, len = polyline.length();
//        int lat = 0, lng = 0;
//
//        while (index < len) {
//            int b, shift = 0, result = 0;
//            do {
//                b = polyline.charAt(index++) - 63;
//                result |= (b & 0x1f) << shift;
//                shift += 5;
//            } while (b >= 0x20);
//            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
//            lat += dlat;
//
//            shift = 0;
//            result = 0;
//            do {
//                b = polyline.charAt(index++) - 63;
//                result |= (b & 0x1f) << shift;
//                shift += 5;
//            } while (b >= 0x20);
//            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
//            lng += dlng;
//
//            LatLng p = new LatLng((((double) lat / 1E5)),
//                    (((double) lng / 1E5)));
//            poly.add(p);
//        }
//
//        return poly;
//    }

    private GoogleMap mMap;
    private String TAG = "so47492459";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng barcelona = new LatLng(41.385064,2.173403);
        mMap.addMarker(new MarkerOptions().position(barcelona).title("Marker in Barcelona"));

        LatLng madrid = new LatLng(40.416775,-3.70379);
        mMap.addMarker(new MarkerOptions().position(madrid).title("Marker in Madrid"));

        LatLng zaragoza = new LatLng(41.648823,-0.889085);

        //Define list to get all latlng for the route
        List<LatLng> path = new ArrayList();


        //Execute Directions API request
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(getResources().getString(R.string.google_direction_key))
                .build();
        DirectionsApiRequest req = DirectionsApi.getDirections(context, "41.385064,2.173403", "40.416775,-3.70379");
        try {
            DirectionsResult res = req.await();

            //Loop through legs and steps to get encoded polylines of each step
            if (res.routes != null && res.routes.length > 0) {
                DirectionsRoute route = res.routes[0];

                if (route.legs !=null) {
                    for(int i=0; i<route.legs.length; i++) {
                        DirectionsLeg leg = route.legs[i];
                        if (leg.steps != null) {
                            for (int j=0; j<leg.steps.length;j++){
                                DirectionsStep step = leg.steps[j];
                                if (step.steps != null && step.steps.length >0) {
                                    for (int k=0; k<step.steps.length;k++){
                                        DirectionsStep step1 = step.steps[k];
                                        EncodedPolyline points1 = step1.polyline;
                                        if (points1 != null) {
                                            //Decode polyline and add points to list of route coordinates
                                            List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                            for (com.google.maps.model.LatLng coord1 : coords1) {
                                                path.add(new LatLng(coord1.lat, coord1.lng));
                                            }
                                        }
                                    }
                                } else {
                                    EncodedPolyline points = step.polyline;
                                    if (points != null) {
                                        //Decode polyline and add points to list of route coordinates
                                        List<com.google.maps.model.LatLng> coords = points.decodePath();
                                        for (com.google.maps.model.LatLng coord : coords) {
                                            path.add(new LatLng(coord.lat, coord.lng));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch(Exception ex) {
            Log.e(TAG, ex.getLocalizedMessage());
        }

        //Draw the polyline
        if (path.size() > 0) {
            PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.BLUE).width(5);
            mMap.addPolyline(opts);
        }

        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zaragoza, 6));
    }
}
