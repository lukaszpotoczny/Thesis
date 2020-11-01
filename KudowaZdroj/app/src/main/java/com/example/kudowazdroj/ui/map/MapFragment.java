package com.example.kudowazdroj.ui.map;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.kudowazdroj.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback {

   private FirebaseAuth mAuth;
   private GoogleMap gMap;

   private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
   private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

   ArrayList<LatLng> coordinates = new ArrayList<LatLng>();
   ArrayList<String> titles = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.map_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.nav_menu_4);


        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        supportMapFragment.getMapAsync(this);

        ImageView helper = root.findViewById(R.id.loadResources);
        String url = "https://firebasestorage.googleapis.com/v0/b/kudowazdroj-4b96c.appspot.com/o/Restaurants%2Fcudova_bistro.jpg?alt=media&token=3f58eb5d-cc3d-46b2-ba98-7574d7b94576";
        Picasso.with(helper.getContext()).load(url).into(helper);

        coordinates.add(new LatLng(50.443747, 16.241871));
        coordinates.add(new LatLng(50.441439, 16.241113));
        coordinates.add(new LatLng(50.443769, 16.252571));
        titles.add("Uno");
        titles.add("Dos");
        titles.add("Tres");


        return root;
        
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_style));



        for(int i=0; i<coordinates.size(); i++){
            gMap.addMarker(new MarkerOptions()
                    .position(coordinates.get(i))
                    .title(titles.get(i))
                    .snippet("elo")
                   // .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin)));
                    .icon(setMarkerImage(getContext(), R.drawable.map_pin)));
        }

        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                gMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    boolean not_first_time_showing_info_window = false;
                    @Override
                    public View getInfoWindow(Marker marker) {
                        LayoutInflater inflater;
                        inflater = LayoutInflater.from(getContext());
                        View view = inflater.inflate(R.layout.custom_info_window, null);
                        ImageView imageView = view.findViewById(R.id.mapInfoImage);
                        String url = "https://firebasestorage.googleapis.com/v0/b/kudowazdroj-4b96c.appspot.com/o/Restaurants%2Fcudova_bistro.jpg?alt=media&token=3f58eb5d-cc3d-46b2-ba98-7574d7b94576";

                        if (not_first_time_showing_info_window) {
                            Picasso.with(getContext()).load(url).into(imageView);
                        } else { // if it's the first time, load the image with the callback set
                            not_first_time_showing_info_window=true;
                            Picasso.with(getContext()).load(url).into(imageView,new InfoWindowRefresher(marker));
                        }
                        return view;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        //   View row = getLayoutInflater().inflate(R.layout.custom_info_window, null);
                        return null;
                    }
                });

                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 17));
                marker.showInfoWindow();

                return true;
            }
        });

    }

    private class InfoWindowRefresher implements Callback {
        private Marker markerToRefresh;

        private InfoWindowRefresher(Marker markerToRefresh) {
            this.markerToRefresh = markerToRefresh;
        }

        @Override
        public void onSuccess() {
            markerToRefresh.showInfoWindow();
        }

        @Override
        public void onError() {}
    }


    private BitmapDescriptor setMarkerImage(Context context, int resID){
        Drawable drawable = ContextCompat.getDrawable(context, resID);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()/2);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()/2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}
