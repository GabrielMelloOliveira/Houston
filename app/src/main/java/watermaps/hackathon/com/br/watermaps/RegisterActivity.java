package watermaps.hackathon.com.br.watermaps;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import watermaps.hackathon.com.br.watermaps.model.User;

public class RegisterActivity extends AppCompatActivity {

    public EditText etName, etEmail, etPhone, etSenha;

    public FirebaseAuth auth;

    public boolean valida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        etName  = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etSenha = (EditText) findViewById(R.id.etSenha);

        Button btnRegister   = (Button) findViewById(R.id.btnRegister);
        Button btnBack       = (Button) findViewById(R.id.btnBack);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean i = true;

                if(i) {
                    User user = User.getInstance();

                    user.setNome(String.valueOf(etName.getText()));
                    user.setEmail(String.valueOf(etEmail.getText()));
                    user.setTelefone(String.valueOf(etPhone.getText()));

                    Toast.makeText(RegisterActivity.this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();

                    finish();
                }else{

                    Toast.makeText(RegisterActivity.this, "Erro ao cadastrar!", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public boolean register(){

        String i1 = etEmail.getText().toString();
        String i2 = etSenha.getText().toString();

        Toast.makeText(this, "Email: " + i1 + " - Senha: " + i2, Toast.LENGTH_SHORT).show();

        auth.createUserWithEmailAndPassword(i1, i2)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Log.d("Log", "Erro no cadastro");
                            valida = false;
                        }else{

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference ref = database.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            ref.child("nome").setValue(etName.getText().toString());
                            ref.child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            ref.child("email").setValue(etEmail.getText().toString());
                            ref.child("telefone").setValue(etPhone.getText().toString());

                            User.getInstance().setId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            User.getInstance().setNome(etName.getText().toString());
                            User.getInstance().setEmail(etEmail.getText().toString());
                            User.getInstance().setTelefone(etPhone.getText().toString());

                            valida = true;

                        }
                    }
                });
        return valida;
    }

}
