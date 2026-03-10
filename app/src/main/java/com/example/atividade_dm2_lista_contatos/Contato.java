package com.example.atividade_dm2_lista_contatos;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contato {
    @PrimaryKey(autoGenerate = true)
    public int id;
    String nome;
    String telefone;
    String email;

    public Contato(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }
}
