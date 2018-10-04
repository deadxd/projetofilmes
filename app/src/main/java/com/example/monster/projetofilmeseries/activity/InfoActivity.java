package com.example.monster.projetofilmeseries.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monster.projetofilmeseries.R;
import com.example.monster.projetofilmeseries.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class InfoActivity extends AppCompatActivity {

    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    ImageView posterImage;
    TextView tituloOriginal;
    TextView idiomaOficial;
    TextView legenda;

    private String idPagina;
    private String posterUrl;
    private String titulo;
    private String idioma;
    private String legendaTexto;
    private FirebaseAuth firebaseAutenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        firebaseAutenticacao = FirebaseAuth.getInstance();

        posterImage = findViewById(R.id.poster);
        tituloOriginal = findViewById(R.id.tituloOriginal);
        idiomaOficial = findViewById(R.id.idioma);
        legenda = findViewById(R.id.legenda);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            idPagina = bundle.getString("idPagina");
            posterUrl = bundle.getString("posterImage");
            titulo = bundle.getString("tituloOriginal");
            idioma = bundle.getString("idiomaOficial");
            legendaTexto = bundle.getString("legenda");

            Picasso.get().load("https://image.tmdb.org/t/p/w500" + posterUrl).into(posterImage);
            tituloOriginal.setText(titulo);
            idiomaOficial.setText(idioma);
            legenda.setText(legendaTexto);
        }
    }

    public void salvarFavoritos (View view) {

        DatabaseReference usuarios = referencia.child("usuarios");

        Usuario.getInstancia().setId(idPagina);

        String uuid = firebaseAutenticacao.getUid();

        usuarios.child(uuid).child("FilmeFavorito").child(titulo).setValue(idPagina);

        Toast.makeText(InfoActivity.this, "Adicionado ao Favoritos" , Toast.LENGTH_SHORT).show();
    }
}
