package com.example.monster.projetofilmeseries.activity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.monster.projetofilmeseries.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.content.IntentFilter;
import android.content.BroadcastReceiver;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText emailUsuario, senhaUsuario;
    private FirebaseAuth firebaseAutenticacao;
    private WifiManager wifiManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAutenticacao = FirebaseAuth.getInstance();

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (firebaseAutenticacao.getCurrentUser() != null) {
            startActivity( new Intent(MainActivity.this, PrincipalActivity.class)); //logado
        }else {
            setContentView(R.layout.activity_main); //deslogado
        }

        //inicializar os componentes
        emailUsuario = findViewById(R.id.loginEmail);
        senhaUsuario = findViewById(R.id.loginSenha);

    }

    public void verificarLogin(View view) {

        String textoEmail = emailUsuario.getText().toString();
        String textoSenha = senhaUsuario.getText().toString();

        if (!textoEmail.isEmpty()) {
            if (!textoSenha.isEmpty()) {
                firebaseAutenticacao.signInWithEmailAndPassword(textoEmail, textoSenha)
                                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    startActivity(new Intent(MainActivity.this, PrincipalActivity.class));
                                } else {
                                    Toast.makeText(MainActivity.this,"Email ou Senha incorretos", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });//acaba aqui
            }else{
                Toast.makeText(MainActivity.this,"Preencha a Senha", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(MainActivity.this,"Preencha o Email", Toast.LENGTH_SHORT).show();
        }
    }

    public void abrirTelaCadastro(View view) {
        startActivity( new Intent(this, CadastroUsuarioActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiStateReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiStateReceiver);
    }


    //PADRÃO DE PROJETO STATE
    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);

            switch (wifiStateExtra) {
                case WifiManager.WIFI_STATE_ENABLED:
                    Toast.makeText(MainActivity.this,"Wifi Conectado", Toast.LENGTH_SHORT).show();
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    Toast.makeText(MainActivity.this,"Wifi Offiline", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}