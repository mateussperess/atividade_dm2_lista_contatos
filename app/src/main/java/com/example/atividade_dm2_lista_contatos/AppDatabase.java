package com.example.atividade_dm2_lista_contatos;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Contato.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContatoDAO contatoDAO();
}
