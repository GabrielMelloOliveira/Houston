package watermaps.hackathon.com.br.watermaps;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import watermaps.hackathon.com.br.watermaps.firebase.Update;
import watermaps.hackathon.com.br.watermaps.model.Locais;
import watermaps.hackathon.com.br.watermaps.model.User;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class HomeActivity extends AppCompatActivity {

    public String[] permissions = new String[]{
            ACCESS_FINE_LOCATION,
            ACCESS_COARSE_LOCATION,
    };

    public static List<Locais> locais = new ArrayList<Locais>();

    LocationManager locationManager;
    String provider;

    TextView tvLocalizacao, tvNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Update.atualiza();

        PermissionUtils.validate(this, 0, permissions);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        tvLocalizacao = (TextView) findViewById(R.id.tvLocalizacao);
        tvNome = (TextView) findViewById(R.id.tvNome);

        if (User.getInstance().getNome() != null) {
            tvNome.setText("Nome: " + User.getInstance().getNome());
        }else{
            tvNome.setText("Nome: ...g");
        }

        if(User.getInstance().getNomeLocalizacao() != null) {
            tvLocalizacao.setText("Você está em " + User.getInstance().getNomeLocalizacao());
        }else{
            tvLocalizacao.setText("Você está em ...");
        }

        Button btnGE               = (Button) findViewById(R.id.btnGE);
        Button btnInfografico      = (Button) findViewById(R.id.btnInfografico);
        Button btnOfertarServico   = (Button) findViewById(R.id.btnOfertarServico);
        Button btnOfertarAgua      = (Button) findViewById(R.id.btnOfertarAgua);
        Button btnMarketplace      = (Button) findViewById(R.id.btnMarketplace);
        Button btnExit             = (Button) findViewById(R.id.btnExit);

        tvLocalizacao.setText(User.getInstance().getNomeLocalizacao());

        btnGE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, GoogleEarthActivity.class);
                startActivity(intent);
            }
        });

        btnInfografico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });

        btnOfertarServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ServiceActivity.class);
                startActivity(intent);
            }
        });

        btnOfertarAgua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, WaterActivity.class);
                startActivity(intent);
            }
        });

        btnMarketplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        tvLocalizacao.setText("Você está em " + User.getInstance().getNomeLocalizacao());
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
            //Adiciona os Buttons
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

}
