package com.example.atividade_dm2_lista_contatos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Contato> contatos;
    Button btnCriarContato;

    Button btnAtualizar;
    Button btnDeletar;

    String DATABASE_NAME = "my-db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnCriarContato = findViewById(R.id.btnCriarContato);
        btnCriarContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itCriarContato = new Intent(MainActivity.this, CriarContato.class);
                startActivity(itCriarContato);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                buscaContatos();

                RecyclerView rvContatos = findViewById(R.id.rvContatos);
                MeuAdaptador adapter = new MeuAdaptador(contatos);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RecyclerView.LayoutManager layout =
                                new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                        rvContatos.setLayoutManager(layout);
                        rvContatos.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }

    private void buscaContatos() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                .build();

        ContatoDAO contatoDAO = db.contatoDAO();
        contatos = contatoDAO.getAll();
        for (Contato c : contatos) {
            Log.d("TESTE", "ID: " + c.id + " | Nome: " + c.nome + " | Fone: " + c.telefone + " | Email: " + c.email);
        }
    }
}