package com.example.monster.projetofilmeseries.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.monster.projetofilmeseries.R;
import com.example.monster.projetofilmeseries.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private TextInputEditText emailUsuario, senhaUsuario, nomeUsuario;
    private FirebaseAuth firebaseAutenticacao;
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        //inicializar componentes
        emailUsuario = findViewById(R.id.cadastroLogin);
        senhaUsuario = findViewById(R.id.cadastroSenha);
        nomeUsuario = findViewById(R.id.cadastroNome);
        firebaseAutenticacao = FirebaseAuth.getInstance();
    }

    public void verificarCadastro(View view) {

        final String textoNome = nomeUsuario.getText().toString();
        String textoEmail = emailUsuario.getText().toString();
        String textoSenha = senhaUsuario.getText().toString();

        if (!textoEmail.isEmpty()) {
            if (!textoSenha.isEmpty()) {
                firebaseAutenticacao.createUserWithEmailAndPassword(textoEmail, textoSenha)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) { //Sucesso ao cadastrar
                                    DatabaseReference usuarios = referencia.child("usuarios");
                                    Usuario.getInstancia().setNome(textoNome);
                                    String uuid = firebaseAutenticacao.getUid();

                                    usuarios.child(uuid).child("nome").setValue(Usuario.getInstancia().getNome());
                                    Toast.makeText(CadastroUsuarioActivity.this,"cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                                    startActivity( new Intent(CadastroUsuarioActivity.this, MainActivity.class)); //logado
                                } else { //erro ao cadastrar usuario
                                    Toast.makeText(CadastroUsuarioActivity.this,"erro ao cadastrar", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }else{
                Toast.makeText(CadastroUsuarioActivity.this,"Preencha a Senha", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(CadastroUsuarioActivity.this,"Preencha o Email", Toast.LENGTH_SHORT).show();
        }
    }

}

