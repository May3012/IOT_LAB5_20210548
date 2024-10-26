package com.example.lab5.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab5.Objetos.Comida;
import com.example.lab5.R;

import java.util.List;

public class ComidaAdapter extends RecyclerView.Adapter<ComidaAdapter.ComidaViewHolder>{
    private List<Comida> listaComidas;

    public ComidaAdapter(List<Comida> listaComidas) {
        this.listaComidas = listaComidas;
    }

    @NonNull
    @Override
    public ComidaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comida, parent, false);
        return new ComidaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComidaViewHolder holder, int position) {
        Comida comida = listaComidas.get(position);
        holder.tvNombreComida.setText(comida.getNomrbe());
        holder.tvCaloriasComida.setText(String.format("Calorías: %.1f", comida.getCalorias()));
    }

    @Override
    public int getItemCount() {
        return listaComidas.size();
    }

    public static class ComidaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreComida, tvCaloriasComida;

        public ComidaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreComida = itemView.findViewById(R.id.tvNombreComida);
            tvCaloriasComida = itemView.findViewById(R.id.tvCaloriasComida);
        }
    }
}
