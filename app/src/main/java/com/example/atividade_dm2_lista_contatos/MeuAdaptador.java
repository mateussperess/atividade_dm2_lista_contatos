package com.example.atividade_dm2_lista_contatos;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MeuAdaptador extends RecyclerView.Adapter<MeuAdaptador.ViewHolder> {
    List<Contato> contatos; //TODO: sair daqui?

    public MeuAdaptador(List<Contato> contatos) {
        this.contatos = contatos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView txtNome;
        final TextView txtFone;
        final TextView txtEmail;
        final Button btnDeletar;
        final Button btnAtualizar;

        public ViewHolder(View view) {
            super(view);
            txtNome = (TextView) view.findViewById(R.id.txtNome);
            txtFone = (TextView) view.findViewById(R.id.txtFone);
            txtEmail = (TextView) view.findViewById(R.id.txtEmail);
            btnDeletar = (Button) view.findViewById(R.id.btnDeletar);
            btnAtualizar = (Button) view.findViewById(R.id.btnAtualizar);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contato, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contato contato = contatos.get(position);
        holder.txtNome.setText(contato.nome);
        holder.txtFone.setText(contato.telefone);
        holder.txtEmail.setText(contato.email);

        holder.btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TESTE", "Deletando ... " + contato.id);
            }
        });

        holder.btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itAtualizar = new Intent(v.getContext(), AtualizarContato.class);
                itAtualizar.putExtra("idContato", contato.id);
                v.getContext().startActivity(itAtualizar);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }
}
