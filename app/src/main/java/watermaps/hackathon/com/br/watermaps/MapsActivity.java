package watermaps.hackathon.com.br.watermaps;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import watermaps.hackathon.com.br.watermaps.firebase.Update;
import watermaps.hackathon.com.br.watermaps.model.Locais;
import watermaps.hackathon.com.br.watermaps.model.User;
import watermaps.hackathon.com.br.watermaps.utils.Conversoes;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, LocationListener {

    private GoogleMap mMap;

    int localizacao = -1;

    LocationManager locationManager;

    String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        long tempoAtualizacao = 400;
        float distancia = 104;
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                tempoAtualizacao, distancia, this);

        locationManager.requestLocationUpdates(
                provider,
                tempoAtualizacao, distancia, this);

    }

    /*DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseUsers = database.child("users");*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLongClickListener(this);

        //Minha localização
        /*mMap.addMarker(new MarkerOptions()
                .position(User.getInstance().getLocalizacao())
                .title(User.getInstance().getNomeLocalizacao())
                .snippet(User.getInstance().getServico().toString()));*/

        if(User.getInstance().getLocalizacao() != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(User.getInstance().getLocalizacao(), 14));
        }

        /*databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dados : dataSnapshot.getChildren()){

                    User user = dados.getValue(User.class);

                    Toast.makeText(MapsActivity.this, "Nome: " + user.getNome(), Toast.LENGTH_SHORT).show();

                }

                mMap.addMarker(new MarkerOptions()
                        .position(dataSnapshot.child(""))
                        .title(user.getNomeLocalizacao())
                        .snippet(user.getServico().toString())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        mMap.addMarker(new MarkerOptions()
                .position(Conversoes.AddressToLatLng(this, "Rua XV de Novembro"))
                .title("Endereço")
                .snippet("Descrição")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

        mMap.addMarker(new MarkerOptions()
                .position(Conversoes.AddressToLatLng(this, "Rua Silva Jardim"))
                .title("Endereço")
                .snippet("Descrição")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

        mMap.addMarker(new MarkerOptions()
                .position(Conversoes.AddressToLatLng(this, "Rua Getulio Vargas"))
                .title("Endereço")
                .snippet("Descrição")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

        mMap.addMarker(new MarkerOptions()
                .position(Conversoes.AddressToLatLng(this, "Rua Avenida Iguaçu"))
                .title("Endereço")
                .snippet("Descrição")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

        mMap.addMarker(new MarkerOptions()
                .position(Conversoes.AddressToLatLng(this, "Rua General Carneiro 1031"))
                .title("Endereço")
                .snippet("Descrição")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

        /*for(User user : listaUsers){
            mMap.addMarker(new MarkerOptions()
                    .position(user.getLocalizacao())
                    .title(user.getNomeLocalizacao())
                    .snippet(user.getServico().toString())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        }*/

    }

    @Override
    public void onMapLongClick(LatLng point) {

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        String marcador = new Date().toString();

        try {

            List<Address> listaLocais = geocoder.getFromLocation(point.latitude, point.longitude, 1);
            if (listaLocais != null && listaLocais.size() > 0){
                marcador = listaLocais.get(0).getAddressLine(0);
            }

        }catch (IOException e){
            e.printStackTrace();
        }

        User.getInstance().setNomeLocalizacao(marcador);

        final Marker marker = mMap.addMarker(new MarkerOptions()
                .position(point)
                .title(marcador)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        );

        //HomeActivity.locais.add(new Locais(point, marcador, ));

    }

    @Override
    public void onLocationChanged(Location localizacaoUsuario) {

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(localizacaoUsuario.getLatitude(), localizacaoUsuario.getLongitude()), 17));
        mMap.addMarker(new MarkerOptions().position(new LatLng(localizacaoUsuario.getLatitude(), localizacaoUsuario.getLongitude())).title("Minha localização"));

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
