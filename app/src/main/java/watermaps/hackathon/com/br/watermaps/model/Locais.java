package watermaps.hackathon.com.br.watermaps.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by User on 29/04/2017.
 */
public class Locais {

    private LatLng latLng;
    private String nomeLocal;
    private String descricaoLocal;

    public Locais(LatLng latLng, String nomeLocal, String descricaoLocal) {
        this.latLng = latLng;
        this.nomeLocal = nomeLocal;
        this.descricaoLocal = descricaoLocal;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getNomeLocal() {
        return nomeLocal;
    }

    public void setNomeLocal(String nomeLocal) {
        this.nomeLocal = nomeLocal;
    }

    public String getdescricaoLocal() {
        return descricaoLocal;
    }

    public void setdescricaoLocal(String descricaoLocal) {
        this.descricaoLocal = descricaoLocal;
    }
}
