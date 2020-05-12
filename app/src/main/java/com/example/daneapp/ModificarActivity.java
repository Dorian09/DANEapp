package com.example.daneapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daneapp.DB.DB_controller;
import com.example.daneapp.Model.Persona;

import java.util.ArrayList;

public class ModificarActivity extends AppCompatActivity implements View.OnClickListener {


    DB_controller controlador;

    private TextView titulo;
    private EditText edCedula;
    private EditText edNombre;
    private EditText edSalario;
    private Spinner spEstrato;
    private Spinner spNivel;
    private Button btnGuardar;
    private Button btnCancelar;
    private int estrato;
    private int nivelEducativo;
    private int indice;
    private int cedula;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titulo = findViewById(R.id.tv_titulo);
        edCedula = findViewById(R.id.et_cedula);
        edNombre = findViewById(R.id.et_nombre);
        edSalario = findViewById(R.id.et_salario);
        spEstrato = findViewById(R.id.spinner_estrato);
        spNivel = findViewById(R.id.spinner_educacion);
        btnGuardar = findViewById(R.id.btn_guardar);
        btnCancelar = findViewById(R.id.btn_cancelar);

        titulo.setText(getString(R.string.modificar_registro));
        controlador = new DB_controller(getApplicationContext());

        Intent i = getIntent();
        indice = i.getIntExtra("indice", 0);

        ArrayList<Persona> list = controlador.obtenerRegistros();

        Persona persona = list.get(indice);
        cedula = persona.getCedula();

        edCedula.setText(String.valueOf(cedula));
        edCedula.setEnabled(false);
        edNombre.setText(persona.getNombre());
        edSalario.setText(String.valueOf(persona.getSalario()));


        String est  [] = {"1","2","3","4","5","6"};
        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,est);
        spEstrato.setAdapter(a);
        spEstrato.setSelection(persona.getEstrato());
        spEstrato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estrato = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        String nivelE  [] = { "Bachillerato","Pregrado","Maestria","Doctorado"};
        ArrayAdapter<String> Nivel = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,nivelE);
        spNivel.setAdapter(Nivel);
        spNivel.setSelection(persona.getNivel_educativo());
        spNivel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nivelEducativo = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnGuardar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_guardar:
                try {
                    int salario = edSalario.getText().toString().isEmpty() ? 0 : Integer.parseInt(edSalario.getText().toString());
                    Persona persona = new Persona(cedula,edNombre.getText().toString()
                            , estrato, salario, nivelEducativo);
                    int retorno = controlador.actualizarRegistro(persona);
                    if (retorno == 1) {
                        Toast.makeText(getApplicationContext(), "actualizacion exitosa", Toast.LENGTH_SHORT).show();
                        setResult(Activity.RESULT_OK);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "fallo en la actualizacion", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException nuEx) {
                    Toast.makeText(getApplicationContext(), "numero muy grande", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_cancelar:
                setResult(Activity.RESULT_CANCELED);
                finish();
                break;
        }
    }
}
