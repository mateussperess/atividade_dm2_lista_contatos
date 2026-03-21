package com.example.atividade_dm2_lista_contatos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

public class AtualizarContato extends AppCompatActivity {
    EditText etNomeContato;
    EditText etTelefoneContato;
    EditText etEmailContato;

    String DATABASE_NAME = "my-db";
    int extraId;

    Button btnAtualizarContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_atualizar_contato);

        etNomeContato = findViewById(R.id.etNomeContato);
        etTelefoneContato = findViewById(R.id.etTelefoneContato);
        etEmailContato = findViewById(R.id.etEmailContato);

        Intent it = getIntent();
        Bundle extras = it.getExtras();
        extraId = extras.getInt("idContato");

        btnAtualizarContato = findViewById(R.id.btnAtualizarContato);

        btnAtualizarContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String novoNome = etNomeContato.getText().toString();
                String novoTelefone = etTelefoneContato.getText().toString();
                String novoEmail = etEmailContato.getText().toString();

                Contato novosDadosContato = new Contato(novoNome, novoTelefone, novoEmail);
                novosDadosContato.id = extraId;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        atualizarContato(novosDadosContato);
                    }
                }).start();

                Toast.makeText(AtualizarContato.this, "Contato atualizado!", Toast.LENGTH_SHORT);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainAtualizarContato), (v, insets) -> {
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
                        etNomeContato.setText(contato.nome);
                        etTelefoneContato.setText(contato.telefone);
                        etEmailContato.setText(contato.email);
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

    private void atualizarContato(Contato contato) {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                .build();

        ContatoDAO contatoDAO = db.contatoDAO();
        contatoDAO.update(contato);
    }
}