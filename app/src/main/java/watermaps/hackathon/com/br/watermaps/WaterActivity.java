package watermaps.hackathon.com.br.watermaps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import watermaps.hackathon.com.br.watermaps.firebase.Update;
import watermaps.hackathon.com.br.watermaps.model.Locais;
import watermaps.hackathon.com.br.watermaps.model.Servico;
import watermaps.hackathon.com.br.watermaps.model.User;
import watermaps.hackathon.com.br.watermaps.utils.Conversoes;

public class WaterActivity extends AppCompatActivity {

    EditText etQuantidadeAgua, etValor, etAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

        etQuantidadeAgua = (EditText) findViewById(R.id.etQuantidadeAgua);
        etValor          = (EditText) findViewById(R.id.etValor);
        etAddress        = (EditText) findViewById(R.id.etAddress);

        Button btnFornecer = (Button) findViewById(R.id.btnFornecer);
        Button btnVoltar   = (Button) findViewById(R.id.btnVoltarWater);

        btnFornecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int quantidade = Integer.valueOf(String.valueOf(etQuantidadeAgua.getText()));
                double preco   = Double.valueOf(String.valueOf(etValor.getText()));
                String address = String.valueOf(etAddress.getText());

                //Cria Servico
                Servico servico = new Servico(
                        "Fornecedor",
                        preco,
                        true,
                        quantidade
                );

                User.getInstance().setServico(servico);

                User.getInstance().setNomeLocalizacao(address);

                User.getInstance().setLocalizacao(
                        Conversoes.AddressToLatLng(getApplicationContext(), String.valueOf(address))
                );

                //Update.atualiza();

                HomeActivity.locais.add(new Locais(User.getInstance().getLocalizacao(), User.getInstance().getNomeLocalizacao(), User.getInstance().getServico().toString()));

                Toast.makeText(WaterActivity.this, "Fornecedor criado com sucesso!", Toast.LENGTH_SHORT).show();

                finish();


            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
