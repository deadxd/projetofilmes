package com.example.monster.projetofilmeseries.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.monster.projetofilmeseries.Listener.RecyclerItemClickListener;
import com.example.monster.projetofilmeseries.R;
import com.example.monster.projetofilmeseries.adapter.AdapterFilmes;
import com.example.monster.projetofilmeseries.api.RetrofitCliente;
import com.example.monster.projetofilmeseries.api.Service;
import com.example.monster.projetofilmeseries.model.Filme;
import com.example.monster.projetofilmeseries.model.Results;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CardsActivity extends AppCompatActivity {

    private RecyclerView recyclerFilmes;
    private List<Results> results = new ArrayList<>();
    private Filme filme;
    private AdapterFilmes adapterFilmes;
    private String TipoLista;

    //Retrofit
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        //inicializar componentes
        recyclerFilmes = findViewById(R.id.recyclerFilmes);

        retrofit = RetrofitCliente.getRetrofit();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            TipoLista = bundle.getString("TipoLista");

            if (TipoLista.equals("TopPopular")) {
                recuperarPopular();
            } else if (TipoLista.equals("TopRated")) {
                recuperarRated();
            }
        }
    }

    private void recuperarPopular(){

        Service filmeService = retrofit.create( Service.class );

        filmeService.recuperarPopular(
            RetrofitCliente.API_KEY,
                "pt-BR"
        ).enqueue(new Callback<Filme>() {
            @Override
            public void onResponse(Call<Filme> call, Response<Filme> response) {
                if ( response.isSuccessful() ) {
                    filme = response.body();
                    results = filme.getResults();
                    configurarRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<Filme> call, Throwable t) {

            }
        });
    }

    private void recuperarRated(){

        Service filmeService = retrofit.create( Service.class );

        filmeService.recuperarRated(
                RetrofitCliente.API_KEY,
                "pt-BR"
        ).enqueue(new Callback<Filme>() {
            @Override
            public void onResponse(Call<Filme> call, Response<Filme> response) {
                if ( response.isSuccessful() ) {
                    filme = response.body();
                    results = filme.getResults();
                    configurarRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<Filme> call, Throwable t) {

            }
        });
    }

    public void configurarRecyclerView(){

        adapterFilmes = new AdapterFilmes(results, this);
        recyclerFilmes.setHasFixedSize( true );
        recyclerFilmes.setLayoutManager( new LinearLayoutManager(this));
        recyclerFilmes.setAdapter( adapterFilmes );

        recyclerFilmes.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerFilmes,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Results page = results.get(position);
                                String idPagina = page.getId().toString();
                                String posterImage = page.getPoster_path().toString();
                                String tituloOriginal = page.getOriginal_title().toString();
                                String idiomaOficial = page.getOriginal_language().toString();
                                String legenda = page.getOverview().toString();

                                Toast.makeText(CardsActivity.this, idPagina , Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(CardsActivity.this, InfoActivity.class);
                                i.putExtra("idPagina", idPagina);
                                i.putExtra("posterImage", posterImage);
                                i.putExtra("tituloOriginal", tituloOriginal);
                                i.putExtra("idiomaOficial", idiomaOficial);
                                i.putExtra("legenda", legenda);
                                startActivity(i);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                ));
    }

}
