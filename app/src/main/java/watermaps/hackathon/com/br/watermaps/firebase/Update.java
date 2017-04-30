package watermaps.hackathon.com.br.watermaps.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import watermaps.hackathon.com.br.watermaps.model.User;

/**
 * Created by User on 30/04/2017.
 */

public class Update {

    public static void atualiza(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.child("nomeLocalizacao").setValue(User.getInstance().getNomeLocalizacao());
        ref.child("latLng").setValue(User.getInstance().getLocalizacao());

    }

}
