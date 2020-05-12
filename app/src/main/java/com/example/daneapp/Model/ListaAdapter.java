package com.example.daneapp.Model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.daneapp.R;

import java.util.ArrayList;

public class ListaAdapter extends ArrayAdapter {

    private ArrayList<Persona> lista;
    Activity context;


    public ListaAdapter(@NonNull Activity context, @NonNull ArrayList<Persona> lista) {
        super(context, R.layout.item_lista_layout ,lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_lista_layout,null);
        TextView txtCedula = view.findViewById(R.id.tvCedula);
        TextView txtNombre = view.findViewById(R.id.tvNombre);
        TextView txtEstrato = view.findViewById(R.id.tvEstrato);
        TextView txtSalario = view.findViewById(R.id.tvSalario);
        TextView txtNivelE = view.findViewById(R.id.tvNivelEducativo);
        txtCedula.setText(lista.get(position).getCedula()+"");
        txtNombre.setText(lista.get(position).getNombre());
        txtSalario.setText(lista.get(position).getSalario()+"");
        txtEstrato.setText(lista.get(position).getEstrato2()+"");
        switch (lista.get(position).getNivel_educativo()) {
            case 0:
                txtNivelE.setText("Bachillerato");
                break;
            case 1:
                txtNivelE.setText("Pregrado");
                break;
            case 2:
                txtNivelE.setText("Maestria");
                break;
            case 3:
                txtNivelE.setText("Doctorado");
                break;
        }
        return view;
    }
}
