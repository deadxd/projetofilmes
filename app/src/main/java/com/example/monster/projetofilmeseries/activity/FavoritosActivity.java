package com.example.monster.projetofilmeseries.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.monster.projetofilmeseries.R;
import com.example.monster.projetofilmeseries.adapter.AdapterFavoritos;
import com.example.monster.projetofilmeseries.api.RetrofitCliente;
import com.example.monster.projetofilmeseries.api.Service;
import com.example.monster.projetofilmeseries.model.Filme;
import com.example.monster.projetofilmeseries.model.Results;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FavoritosActivity extends AppCompatActivity {

    private ListView listView ;
    //private ArrayAdapter<String> adapter ;
    //private ArrayList<String> arrayList;
    private AdapterFavoritos adapterFavoritos;

    private List<Results> results = new ArrayList<>();
    private Filme filme;

    //Retrofit
    private Retrofit retrofit;
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth firebaseAutenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        firebaseAutenticacao = FirebaseAuth.getInstance();

        listView = (ListView) findViewById(R.id.listFilmes);

        retrofit = RetrofitCliente.getRetrofit();

        recuperarFavoritos();
    }

    public void recuperarFavoritos() {

        Service filmeService = retrofit.create( Service.class );

        filmeService.recuperarLancamento(
                RetrofitCliente.API_KEY,
                "pt-BR"
        ).enqueue(new Callback<Filme>() {
            @Override
            public void onResponse(Call<Filme> call, Response<Filme> response) {
                if ( response.isSuccessful() ) {
                    filme = response.body();
                    results = filme.getResults();
                    configurarListView();
                }
            }

            @Override
            public void onFailure(Call<Filme> call, Throwable t) {

            }
        });
    }

    public void configurarListView(){

        adapterFavoritos = new AdapterFavoritos(this, results);
        listView.setAdapter( adapterFavoritos );
    }
}
