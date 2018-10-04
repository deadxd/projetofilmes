package com.example.monster.projetofilmeseries.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.monster.projetofilmeseries.R;
import com.google.firebase.auth.FirebaseAuth;

public class PrincipalActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAutenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        firebaseAutenticacao = FirebaseAuth.getInstance();
    }

    public void exibirPopular (View view) {

        String TipoLista = "TopPopular";

        Intent i = new Intent(PrincipalActivity.this, CardsActivity.class);
        i.putExtra("TipoLista", TipoLista);
        startActivity(i);
    }
    public void exibirRated (View view) {

        String TipoLista = "TopRated";

        Intent i = new Intent(PrincipalActivity.this, CardsActivity.class);
        i.putExtra("TipoLista", TipoLista);
        startActivity(i);
    }

    public void exibirFavoritos (View view) {

        Intent i = new Intent(PrincipalActivity.this, FavoritosActivity.class);
        startActivity(i);

    }

    public void botaoLogout (View view) {

        firebaseAutenticacao.signOut();

        if (firebaseAutenticacao.getCurrentUser() != null) {
            Toast.makeText(PrincipalActivity.this,"ERRO AO FAZER LOGOUT", Toast.LENGTH_SHORT).show();
        }else {
            startActivity( new Intent(this, MainActivity.class));
        }
    }

    @Override
    public void onBackPressed(){
        firebaseAutenticacao.signOut();
        startActivity(new Intent(this, MainActivity.class));
        finishAffinity();
        return;
    }
}
