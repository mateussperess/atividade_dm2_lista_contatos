package com.example.atividade_dm2_lista_contatos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContatoDAO {
    @Query("SELECT * FROM contato")
    List<Contato> getAll();

    @Query("SELECT * FROM contato WHERE id LIKE :id")
    Contato getById(int id);

    @Insert
    void insert (Contato... contatos);

    @Update
    void update(Contato... contato);

    @Delete
    void delete(Contato... contato);
}
