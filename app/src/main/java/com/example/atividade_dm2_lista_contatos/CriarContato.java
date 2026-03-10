package com.example.atividade_dm2_lista_contatos;

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

public class CriarContato extends AppCompatActivity {
    EditText etNome;
    EditText etTelefone;
    EditText etEmail;
    Button btnGravarContato;

    String DATABASE_NAME = "my-db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_criar_contato);

        etNome = findViewById(R.id.etNome);
        etTelefone = findViewById(R.id.etTelefone);
        etEmail = findViewById(R.id.etEmail);

        btnGravarContato = findViewById(R.id.btnGravarContato);

        btnGravarContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    gravarContato(v);
                } catch (Throwable th) {
                    Log.d("Erro", "Ocorreu um erro: " + th.getMessage() + " - " + th.getStackTrace());
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainCriarContato), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void gravarContato(View view) {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                .build();

        ContatoDAO contatoDAO = db.contatoDAO();

        Contato contato = new Contato(
                etNome.getText().toString(),
                etTelefone.getText().toString(),
                etTelefone.getText().toString()
        );

        new Thread(new Runnable() {
            @Override
            public void run() {
                contatoDAO.insert(contato);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Contato criado com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }
}