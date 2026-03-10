package com.example.atividade_dm2_lista_contatos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Contato> contatos;
    Button btnCriarContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnCriarContato = findViewById(R.id.btnCriarContato);
        btnCriarContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itCriarContato = new Intent(MainActivity.this, CriarContato.class);
                startActivity(itCriarContato);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gerarContatos();

        RecyclerView rvContatos = findViewById(R.id.rvContatos);
        MeuAdaptador adapter = new MeuAdaptador(contatos);
        RecyclerView.LayoutManager layout =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvContatos.setLayoutManager(layout);
        rvContatos.setAdapter(adapter);

    }

    private void gerarContatos() {
        contatos = new ArrayList<Contato>();
        criarContato("Juca", "51 99532 2218", "teste@email.com");
        criarContato("Jucão", "51 95423 2848", "teste@email.com");
        criarContato("Juquinha", "51 99325 3388", "teste@email.com");
        criarContato("Jocalino", "51 98765 1247", "teste@email.com");
        criarContato("Juju", "51 99281 1234", "teste@email.com");
        criarContato("Juca", "51 98121 2128", "teste@email.com");
        criarContato("Juquinha", "51 97723 3388", "teste@email.com");
    }

    private void criarContato(String nome, String fone, String email) {
        Contato contato = new Contato(nome, fone, email);
        contatos.add(contato);
    }
}