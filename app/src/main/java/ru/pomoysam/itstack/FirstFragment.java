package ru.pomoysam.itstack;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class FirstFragment extends Fragment implements OnMapReadyCallback , GoogleMap.OnMapClickListener, View.OnTouchListener {


    public FirstFragment() {
    }

    public static FirstFragment newInstance() {
        return new FirstFragment();
    }


    View mView;
    MapView mMapView;
    private GoogleMap mGoole;
    private GoogleApiClient mGoogleApiClient;
    LocationManager locationManager;

    Context thiscontext;
    public static final String LOG_TAG = "map = ";

    private Marker mNovomoskovsk;
    private Marker mShekino;
    private Marker mOdodevskoe;
    private Marker mRyajskaya;
    private Marker mKarakazova;
    private Marker mRyazanskat;
    private Marker mViliams;

    private int currentItem = -1;
    AlertDialog.Builder mDialogBuilder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            Log.d(LOG_TAG, "onCreate");
    }


//    public static final List<MapItem> mapItems = new ArrayList<MapItem>() {
//        {
//            add(new MapItem("г. Новомосковск, ул. Космонавтов (где автосалон КИА)        ", 0.0F,  38.252156, 54.007482));
//            add(new MapItem("г. Щекино, пос.Первомайский, ул.Пролетарская, д.19          ", 0.0F,  37.489695, 54.033654));
//            add(new MapItem("г. Тула, Одоевское шоссе (напротив д. 85 - ОАО Балтика-Тула)", 0.0F,  37.529558, 54.187362));
//            add(new MapItem("г. Тула, ул. Ряжская, дом 1 (около Ряжского вокзала)        ", 0.0F, 37.623024, 54.207003));
//            add(new MapItem("г. Тула, Центральный район, пересечение ул. Каракозова      ", 0.0F,  37.645939, 54.209238));
//            add(new MapItem("г. Тула, Центральный район, ул. Рязанская, дом 46-а         ", 0.0F, 37.631806, 54.167056));
//            add(new MapItem("г. Тула, Пролетарский район, ул. Вильямса (напротив д. 46)  ", 0.0F,  37.696394, 54.211388));
//        }
//    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.first_fragment, container, false);
        thiscontext = container.getContext();

        mMapView = (MapView) mView.findViewById(R.id.map);

        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        mMapView.getMapAsync(this);


        mView.setOnTouchListener(this);


        return mView;
    }


    public void onMapClick (LatLng point) {

//        final Dialog dialog = mDialogBuilder.create();
//
//            dialog.cancel();




        Log.d(LOG_TAG, "123123");

        // Do Something
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//
//        if (mMapView != null) {
//            mMapView.onCreate(null);
//            mMapView.onResume();
//            mMapView.getMapAsync(this);
//
//
//        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        mGoole = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mGoole.setOnMapClickListener(this);

        if (ContextCompat.checkSelfPermission(thiscontext, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mGoole.setMyLocationEnabled(true);
            //          mGoole.setOnMyLocationButtonClickListener(thiscontext);
            //  mGoole.setOnMyLocationClickListener(thiscontext);
        } else {

            Log.d(LOG_TAG, " хуй на ны");
        }

        mGoole.setMyLocationEnabled(true);
        updateMarkers(googleMap);
        googleMap.setOnMarkerClickListener(this::onMarkerClick);



    }

    @SuppressLint("MissingPermission")
    private void updateMarkers(GoogleMap googleMap) {


        LatLng itemLocation;
        Marker marker;

        googleMap.clear();

        // For showing a move to my location button
        googleMap.setMyLocationEnabled(true);


        if (currentItem == -1) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(54.1870488,37.6203152)));
            for (int i = 0; i < ConfigApp.mapItems.size(); i++) {

                itemLocation = new LatLng(ConfigApp.mapItems.get(i).latitude, ConfigApp.mapItems.get(i).longitude);

                googleMap.addMarker(new MarkerOptions()
                        .position(itemLocation)
                        .draggable(false)
                        .title(ConfigApp.mapItems.get(i).address)).setTag(i);
            }
        } else {
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(ConfigApp.mapItems.get(currentItem).latitude, ConfigApp.mapItems.get(currentItem).longitude)));
            itemLocation = new LatLng(ConfigApp.mapItems.get(currentItem).latitude, ConfigApp.mapItems.get(currentItem).longitude);

            googleMap.addMarker(new MarkerOptions()
                    .position(itemLocation)
                    .draggable(false)
                    .title(ConfigApp.mapItems.get(currentItem).address)).setTag(currentItem);
        }

        googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));

    }



    public void setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
        updateMarkers(mGoole);
    }

    public boolean onMarkerClick(final Marker marker) {

        //еуе


        LayoutInflater li = LayoutInflater.from(thiscontext);
        View promptsView = li.inflate(R.layout.dialog, null);


        TextView tw = promptsView.findViewById(R.id.nameMarker);
        Button btnNav =  promptsView.findViewById(R.id.navigation);
        Button btnBuy =  promptsView.findViewById(R.id.buy);

        tw.setText(marker.getTitle());


        mDialogBuilder = new android.app.AlertDialog.Builder(thiscontext);
        mDialogBuilder.setView(promptsView);





        new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog,
                                int which) {
                dialog.cancel();
            }
        };

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(thiscontext, ByActivity.class);
                startActivity(intent);


            }
        });


        btnNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + marker.getPosition().latitude + "," + marker.getPosition().longitude + "&mode=d");

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps"); startActivity(mapIntent);


            }
        });



        mDialogBuilder
                .setCancelable(true);


        AlertDialog alertDialog = mDialogBuilder.create();

        alertDialog.show();



//



//        Integer clickCount = (Integer) marker.getTag();
//
//
//        if (clickCount != null) {
//            clickCount = clickCount + 1;
//            marker.setTag(clickCount);
//
//            Toast.makeText(thiscontext,
//                    marker.getTitle() +
//                            " has been clicked " + clickCount + " times.",
//                    Toast.LENGTH_SHORT).show();
//        }

        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Log.d(LOG_TAG, "onTouch");
        return false;
    }
}

