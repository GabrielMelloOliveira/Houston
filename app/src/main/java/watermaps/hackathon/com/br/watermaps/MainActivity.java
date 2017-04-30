package watermaps.hackathon.com.br.watermaps;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import watermaps.hackathon.com.br.watermaps.model.User;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static watermaps.hackathon.com.br.watermaps.Manifest.*;

public class MainActivity extends AppCompatActivity {

    public EditText etLogin, etPassword;

    public String[] permissions = new String[]{
            ACCESS_FINE_LOCATION,
            ACCESS_COARSE_LOCATION,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        FirebaseDatabase dbr = FirebaseDatabase.getInstance();

        DatabaseReference dbrr = dbr.getReference();

        User.getInstance().setNomeLocalizacao(String.valueOf(dbrr.child("nomeLocalizacao")));

        etLogin    = (EditText) findViewById(R.id.etlogin);
        etPassword = (EditText) findViewById(R.id.etPassword);

        Button btnLogin    = (Button) findViewById(R.id.btnLogin);
        Button btnRegister = (Button) findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String login    = String.valueOf(etLogin.getText());
                String password = String.valueOf(etPassword.getText());

                User.clear();

                User.getInstance().setNome(login);

                if (login.equals("admin") && password.equals("1234")){

                    //User.getInstance().setNome(auth.getCurrentUser().getEmail());

                    Toast.makeText(MainActivity.this, "Login efetuado com sucesso", Toast.LENGTH_SHORT).show();

                    Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(homeIntent);

                }else{

                    Toast.makeText(MainActivity.this, "Login e/ou senha incorretos", Toast.LENGTH_SHORT).show();

                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int result : grantResults){
            if (result == PackageManager.PERMISSION_DENIED){
                //Quando alguma permissão é negada
                alertAndFinish();
                return;
            }
        }

    }

    private void alertAndFinish() {
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name).setMessage("Para utilizar o aplicativo, você deve aceitar as permissões de localização");
            //Adiciona os Buttonse
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    FirebaseAuth auth;

    boolean valida = true;

    public boolean login(String email, String senha){

        auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Log.d("Log", "Erro no login");
                            valida = false;
                        }else{
                            valida = true;
                        }
                    }
                });

        return valida;

    }
}
