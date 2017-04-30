package watermaps.hackathon.com.br.watermaps.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.security.AccessController;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by User on 29/04/2017.
 */

public class Conversoes {

    public static String LatLngToAddress(Context context, LatLng posicao){

        String address;

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        address = new Date().toString();

        try {

            List<Address> listaLocais = geocoder.getFromLocation(posicao.latitude, posicao.longitude, 1);
            if (listaLocais != null && listaLocais.size() > 0){
                address = listaLocais.get(0).getAddressLine(0);
            }

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            return address;
        }

    }

    public static LatLng AddressToLatLng(Context context, String address){

        LatLng posicao;

        try {
            Geocoder geocoder = new Geocoder (context);
            List<Address> resultados =
                    geocoder.getFromLocationName(address, 1);
            if (!resultados.isEmpty()) {
                posicao = new LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());

                return posicao;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

}
