package com.example.daneapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;

import com.example.daneapp.DB.DB_controller;
import com.example.daneapp.Model.ListaAdapter;
import com.example.daneapp.Model.Persona;

import java.util.ArrayList;

public class ListadoActivity extends AppCompatActivity implements LifecycleObserver {
    ListView lista;

    ArrayList<Persona> listaPersona;

    DB_controller controlador;

    ListaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        lista = findViewById(R.id.listView);

        controlador = new DB_controller(getApplicationContext());

        listaPersona = controlador.obtenerRegistros();
        adapter = new ListaAdapter(this, listaPersona);
        lista.setAdapter(adapter);

        registerForContextMenu(lista);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<Persona> listaServis = controlador.obtenerRegistros();
                ListaAdapter adap = new ListaAdapter(this, listaServis);
                lista.setAdapter(adap);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "modificacion cancelada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.listado_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.action_modificar_registro:
                modificarRegistro(menuInfo.position);
                return true;
            case R.id.action_borrar_registro:
                borrarRegistro(menuInfo.position);
                return true;
        }
        return super.onContextItemSelected(item);
    }


    private void modificarRegistro(int posicion) {
        Intent intent = new Intent(this, ModificarActivity.class);
        intent.putExtra("indice", posicion);
        startActivityForResult(intent, 2);
    }

    private void borrarRegistro(int posicion) {
        int retorno = controlador.borrarRegistro(listaPersona.get(posicion));
        if (retorno == 1) {
            Toast.makeText(getApplicationContext(), "registro eliminado", Toast.LENGTH_SHORT).show();
            listaPersona = controlador.obtenerRegistros();
            adapter = new ListaAdapter(this, listaPersona);
            lista.setAdapter(adapter);
        } else {
            Toast.makeText(getApplicationContext(), "error al borrar", Toast.LENGTH_SHORT).show();
        }
    }
}
