package com.example.daneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.daneapp.DB.DB_controller;
import com.example.daneapp.Model.Persona;

public class MainActivity extends AppCompatActivity {

    DB_controller controller;

    private EditText edNombre;
    private EditText edCedula;
    private EditText edSalario;
    private Spinner spEstrato;
    private Spinner spNivel;
    private Button btCancelar;
    private Button btGuardar;
    int estrato;
    int educacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        edNombre = findViewById(R.id.et_nombre);
        edCedula = findViewById(R.id.et_cedula);
        edSalario = findViewById(R.id.et_salario);
        spEstrato = findViewById(R.id.spinner_estrato);
        spNivel = findViewById(R.id.spinner_educacion);
        btCancelar = findViewById(R.id.btn_cancelar);
        btGuardar = findViewById(R.id.btn_guardar);

        controller = new DB_controller(getApplicationContext());

        String est  [] = {"1","2","3","4","5","6"};
        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,est);
        spEstrato.setAdapter(a);

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

        spNivel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                educacion = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btGuardar.setOnClickListener((View.OnClickListener) this);
        btCancelar.setOnClickListener((View.OnClickListener) this);


    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_guardar:
                try {
                    int cedula = edCedula.getText().toString().isEmpty() ? 0 : Integer.parseInt(edCedula.getText().toString());
                    int salario = edSalario.getText().toString().isEmpty() ? 0 : Integer.parseInt(edSalario.getText().toString());
                    Persona persona = new Persona(cedula, edNombre.getText().toString(), estrato, salario, educacion);
                    long retorno = controller.agregarRegistro(persona);
                    if (retorno != -1) {
                        Toast.makeText(v.getContext(), "registro guardado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "registro fallido", Toast.LENGTH_SHORT).show();
                    }
                    limpiarCampo();
                } catch (NumberFormatException numEx) {
                    Toast.makeText(getApplicationContext(), "numero muy grande", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_cancelar:
                limpiarCampo();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_buscar_registro:
                Intent intent1 = new Intent(this, BuscarActivity.class);
                startActivity(intent1);
                return true;
            case R.id.action_listado:
                Intent intent = new Intent(this, ListadoActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void limpiarCampo() {
        edCedula.setText("");
        edNombre.setText("");
        edSalario.setText("");
    }
}
