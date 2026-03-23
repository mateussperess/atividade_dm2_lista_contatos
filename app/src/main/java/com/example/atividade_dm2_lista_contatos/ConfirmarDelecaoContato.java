package com.example.atividade_dm2_lista_contatos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

public class ConfirmarDelecaoContato extends AppCompatActivity {
    TextView txtInfoContato;
    Button btnConfirmarDeletar;
    Button btnCancelar;
    String DATABASE_NAME = "my-db";
    int extraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirmar_delecao_contato);

        txtInfoContato = findViewById(R.id.txtInfoContato);
        btnConfirmarDeletar = findViewById(R.id.btnConfirmarDeletar);
        btnCancelar = findViewById(R.id.btnCancelar);

        Intent it = getIntent();
        Bundle extras = it.getExtras();
        extraId = extras.getInt("idContato");

        btnConfirmarDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        deletarContato(buscaContato(extraId));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ConfirmarDelecaoContato.this, "Contato deletado com sucesso", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                }).start();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainConfirmarDelecao), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Contato contato = buscaContato(extraId);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtInfoContato.setText("O contato " + contato.nome + " será excluído.");
                    }
                });
            }
        }).start();
    }

    private Contato buscaContato(int id) {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                .build();

        ContatoDAO contatoDAO = db.contatoDAO();
        return contatoDAO.getById(id);
    }

    private void deletarContato(Contato contato) {
        AppDatabase db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                DATABASE_NAME
        ).build();

        ContatoDAO contatoDAO = db.contatoDAO();
        contatoDAO.delete(contato);
    }
}