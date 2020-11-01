package com.example.kudowazdroj;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.kudowazdroj.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                    .icon(setMarkerImage(getContext(), R.drawable.map_pin)));
        }

        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 17));
                //gMap.animateCamera(CameraUpdateFactory.zoomTo(17));
                marker.showInfoWindow();
               // gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), (float) 11.20));
                //gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 17));
                return true;
            }
        });



/*        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                markerOptions.icon(setMarkerImage(getContext(), R.drawable.map_pin));
                gMap.clear();
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                gMap.addMarker(markerOptions);
            }

        });*/



    }



/*    public void getLocationPermission(){
        String permissions[] = {Manifest.permission.ACCESS_FINE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) locationPermissionGranted = true;
        else ActivityCompat.requestPermissions(this.getActivity(), permissions, LOCATION_PERMISSION_REQUEST_CODE);
    }*/

/*    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationPermissionGranted = false;

        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                for(int i=0; i<grantResults.length; i++){
                    if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                        locationPermissionGranted = false;
                        return;
                    }
                }
                locationPermissionGranted = true;

            }
        }
    }*/

    private BitmapDescriptor setMarkerImage(Context context, int resID){
        Drawable drawable = ContextCompat.getDrawable(context, resID);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()/2);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()/2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

/*
    public boolean isServicesOK(){
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapFragment.this.getContext());
        if(available == ConnectionResult.SUCCESS) return true;
        else Toast.makeText(this.getContext(), "Brak dostępu do mapy", Toast.LENGTH_SHORT).show();
        return false;
    }

*/






    public void stuffFromOnCreate(){
/*        editText1 = root.findViewById(R.id.edit1);
        editText2 = root.findViewById(R.id.edit2);
        editText3 = root.findViewById(R.id.edit3);
        editText4 = root.findViewById(R.id.edit4);
        editText5 = root.findViewById(R.id.edit5);
        editText6 = root.findViewById(R.id.edit6);
        editText7 = root.findViewById(R.id.edit7);
        button = root.findViewById(R.id.add_stuff);

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Accommodation");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.valueOf(editText1.getText().toString());
                String name = editText2.getText().toString();
                String address = editText3.getText().toString();
                String rating = editText4.getText().toString();
                String phone = editText5.getText().toString();
                String website = editText6.getText().toString();
                String content = editText7.getText().toString();
                Rest rest = new Rest(id, name, address, rating, phone, website,content);

                reference.child(name).setValue(rest);
            }
        });*/
    }



    public class Rest{

        public int id;
        public String name;
        public String address;
        public String rating;
        public String phone;
        public String website;
        public String content;

        public Rest(){
        }

        public Rest(int id, String n, String a, String r, String p, String w, String c){
            this.id = id;
            this.name = n;
            this.address = a;
            this.rating = r;
            this.phone = p;
            this.website = w;
            this.content = c;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

}
