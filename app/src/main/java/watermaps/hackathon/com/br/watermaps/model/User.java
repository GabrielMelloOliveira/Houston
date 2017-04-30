package watermaps.hackathon.com.br.watermaps.model;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by User on 29/04/2017.
 */

public class User {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String nome;
    private String email;
    private String telefone;
    private LatLng localizacao;
    private String nomeLocalizacao;

    private Servico servico;

    private User(){}

    private static User user;

    public static User getInstance(){
        if(user == null){
            user = new User();
        }
        return user;
    }

    public static void clear(){
        user = null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LatLng getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(LatLng localizacao) {
        this.localizacao = localizacao;
    }

    public String getNomeLocalizacao() {
        return nomeLocalizacao;
    }

    public void setNomeLocalizacao(String nomeLocalizacao) {
        this.nomeLocalizacao = nomeLocalizacao;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }
}
