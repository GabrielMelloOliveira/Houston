package watermaps.hackathon.com.br.watermaps;

import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import watermaps.hackathon.com.br.watermaps.model.Locais;
import watermaps.hackathon.com.br.watermaps.model.User;

public class ChoiceAddressActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_address);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng padrao = new LatLng(-40, 600);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(padrao));

        mMap.setOnMapLongClickListener(this);

    }

    LatLng localizacao;
    String marcador;

    @Override
    public void onMapLongClick(LatLng posicao) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posicao, 17));
        mMap.addMarker(new MarkerOptions().position(posicao).title("Localização"));

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        marcador = new Date().toString();

        try {

            List<Address> listaLocais = geocoder.getFromLocation(posicao.latitude, posicao.longitude, 1);
            if (listaLocais != null && listaLocais.size() > 0){
                marcador = listaLocais.get(0).getAddressLine(0);
            }

        }catch (IOException e){
            e.printStackTrace();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Salvar ?");
        builder.setMessage("Deseja salva essa localização ?");

        localizacao = posicao;

        //HomeActivity.locais.add(new Locais(localizacao, marcador));

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Salvar localização de serviço
                User.getInstance().setLocalizacao(localizacao);
                User.getInstance().setNomeLocalizacao(marcador);

                finish();

                Toast.makeText(ChoiceAddressActivity.this, "Localização salva! " + marcador, Toast.LENGTH_SHORT).show();
            }
        });

        builder.create();

        builder.show();

    }
}
