package watermaps.hackathon.com.br.watermaps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import watermaps.hackathon.com.br.watermaps.firebase.Update;
import watermaps.hackathon.com.br.watermaps.model.Locais;
import watermaps.hackathon.com.br.watermaps.model.Servico;
import watermaps.hackathon.com.br.watermaps.model.User;
import watermaps.hackathon.com.br.watermaps.utils.Conversoes;

public class ServiceActivity extends AppCompatActivity {

    EditText etValor, etAddress;
    RadioButton rbPoco, rbHidraulico, rbInstalacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        Button btnAddServico = (Button) findViewById(R.id.btnAddServico);
        Button btnBack       = (Button) findViewById(R.id.btnBackService);

        rbPoco       = (RadioButton) findViewById(R.id.rbPoco);
        rbHidraulico = (RadioButton) findViewById(R.id.rbHidraulico);
        rbInstalacao = (RadioButton) findViewById(R.id.rbInstalacao);

        etValor   = (EditText) findViewById(R.id.etValor);
        etAddress = (EditText) findViewById(R.id.etQuantidadeAgua);

        btnAddServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double preco   = Double.valueOf(String.valueOf(etValor.getText()));
                String address = String.valueOf(etAddress.getText());

                String tipoServico = escolheServico();
                if(escolheServico() != null) {

                    //Cria Servico
                    Servico servico = new Servico(
                            tipoServico,
                            preco,
                            false,
                            0
                    );

                    User.getInstance().setServico(servico);

                    User.getInstance().setNomeLocalizacao(address);

                    User.getInstance().setLocalizacao(
                            Conversoes.AddressToLatLng(getApplicationContext(), String.valueOf(address))
                    );

                    //Update.atualiza();

                    HomeActivity.locais.add(new Locais(User.getInstance().getLocalizacao(), User.getInstance().getNomeLocalizacao(), User.getInstance().getServico().toString()));

                    Toast.makeText(ServiceActivity.this, "Serviço criado com sucesso!", Toast.LENGTH_SHORT).show();

                    finish();
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

    public String escolheServico(){

        if(rbPoco.isChecked()){
            return "Poço Artesiano";
        }else if(rbHidraulico.isChecked()){
            return "Hidráulico";
        }else if(rbInstalacao.isChecked()){
            return "Instalação";
        }else{
            Toast.makeText(this, "Selecione um tipo de serviço", Toast.LENGTH_SHORT).show();
            return null;
        }

    }
}
