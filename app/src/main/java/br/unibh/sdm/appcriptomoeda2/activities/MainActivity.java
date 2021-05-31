package br.unibh.sdm.appcriptomoeda2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import br.unibh.sdm.appcriptomoeda2.R;
import br.unibh.sdm.appcriptomoeda2.api.CriptomoedaService;
import br.unibh.sdm.appcriptomoeda2.api.RestServiceGenerator;
import br.unibh.sdm.appcriptomoeda2.entidades.Criptomoeda;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private CriptomoedaService service = null;
    final private MainActivity mainActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Lista de Criptomoedas");
        setContentView(R.layout.activity_main);
        service = RestServiceGenerator.createService(CriptomoedaService.class);
        criaAcaoBotaoFlutuante();
    }

    private void criaAcaoBotaoFlutuante() {
        FloatingActionButton botaoNovo = findViewById(R.id.floatingActionButtonCriar);
        botaoNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity","Clicou no botão para adicionar Nova Criptomoeda");
                startActivity(new Intent(MainActivity.this,
                        FormularioCriptomoedaActivity.class));

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        buscaCriptomoedas();
    }

    public void buscaCriptomoedas(){
        CriptomoedaService service = RestServiceGenerator.createService(CriptomoedaService.class);
        Call<List<Criptomoeda>> call = service.getCriptomoedas();
        call.enqueue(new Callback<List<Criptomoeda>>() {
            @Override
            public void onResponse(Call<List<Criptomoeda>> call, Response<List<Criptomoeda>> response) {
                if (response.isSuccessful()) {
                    Log.i("MainActivity", "Retornou " + response.body().size() + " Criptomoedas!");
                    List<String> lista2 = new ArrayList<String>();
                    for (Criptomoeda item : response.body()) {
                        lista2.add(item.getNome());
                    }
                    Log.i("MainActivity", lista2.toArray().toString());
                    ListView listView = findViewById(R.id.listViewListaCriptomoedas);
                    listView.setAdapter(new ArrayAdapter<String>(mainActivity,
                            android.R.layout.simple_list_item_1,
                            lista2));
                } else {
                    Log.e("MainActivity", "" + response.message());
                    Toast.makeText(getApplicationContext(), "Erro (" + response.code()+"): "+ response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Criptomoeda>> call, Throwable t) {
                Log.e("MainActivity", "Erro: " + t.getMessage());
            }
        });
      }
}