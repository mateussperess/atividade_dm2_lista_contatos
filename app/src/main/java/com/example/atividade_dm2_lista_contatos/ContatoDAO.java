package com.example.atividade_dm2_lista_contatos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContatoDAO {
    @Query("SELECT * FROM contato")
    List<Contato> getAll();

    @Insert
    void insert (Contato... contatos);
}
