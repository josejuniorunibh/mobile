package br.unibh.sdm.appcriptomoeda2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.unibh.sdm.appcriptomoeda2.R;
import br.unibh.sdm.appcriptomoeda2.api.CriptomoedaService;
import br.unibh.sdm.appcriptomoeda2.api.RestServiceGenerator;
import br.unibh.sdm.appcriptomoeda2.entidades.Criptomoeda;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormularioCriptomoedaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_criptomoeda);
        setTitle("Edição de Criptomoeda");
        configuraBotaoSalvar();
    }

    private void configuraBotaoSalvar() {
        Button botaoSalvar = findViewById(R.id.buttonSalvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("FormularioCripto","Clicou em Salvar");
                Criptomoeda criptomoeda = recuperaInformacoesFormulario();
                salvaCriptomoeda(criptomoeda);
            }
        });
    }

    private void salvaCriptomoeda(Criptomoeda criptomoeda) {
        CriptomoedaService service = RestServiceGenerator.createService(CriptomoedaService.class);
        Call<Criptomoeda> call = service.criaCriptomoeda(criptomoeda);
        call.enqueue(new Callback<Criptomoeda>() {
            @Override
            public void onResponse(Call<Criptomoeda> call, Response<Criptomoeda> response) {
                if (response.isSuccessful()) {
                    Log.i("FormularioCripto", "Salvou a Criptomoeda "+ criptomoeda.getCodigo());
                    Toast.makeText(getApplicationContext(), "Salvou a Criptomoeda "+ criptomoeda.getCodigo(), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Log.e("FormularioCripto", "Erro (" + response.code()+"): Verifique novamente os valores");
                    Toast.makeText(getApplicationContext(), "Erro (" + response.code()+"): Verifique novamente os valores", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Criptomoeda> call, Throwable t) {
                Log.e("FormularioCripto", "Erro: " + t.getMessage());
            }
        });
    }

    @NotNull
    private Criptomoeda recuperaInformacoesFormulario() {
        EditText codigo = findViewById(R.id.editTextCodigo);
        EditText nome = findViewById(R.id.editTextNome);
        EditText descricao = findViewById(R.id.editTextTextMultiLineDescricao);
        Criptomoeda criptomoeda = new Criptomoeda();
        criptomoeda.setCodigo(codigo.getText().toString());
        criptomoeda.setNome(nome.getText().toString());
        criptomoeda.setDescricao(descricao.getText().toString());
        criptomoeda.setDataCriacao(new Date());
        return criptomoeda;
    }
}