package com.example.daneapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.daneapp.DB.DB_controller;
import com.example.daneapp.Model.Persona;

public class BuscarActivity extends AppCompatActivity implements View.OnClickListener {
    DB_controller controlador;

    EditText edCedula;

    Button btBuscar, btRegresar;

    TextView tvCedula, tvNombre, tvEstrato, tvSalario, tvNivel;
    int cedula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        edCedula = findViewById(R.id.edCedula);
        btBuscar = findViewById(R.id.btn_Buscar);
        btRegresar = findViewById(R.id.btn_Regresar);
        tvCedula = findViewById(R.id.tvCedula);
        tvNombre = findViewById(R.id.tvNombre);
        tvEstrato = findViewById(R.id.tvEstrato);
        tvSalario = findViewById(R.id.tvSalario);
        tvNivel = findViewById(R.id.tvNivelEducativo);

        controlador = new DB_controller(getApplicationContext());


        btBuscar.setOnClickListener(this);
        btRegresar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Buscar:
                try {
                    cedula = Integer.parseInt(edCedula.getText().toString());
                } catch (NumberFormatException numEx) {
                    Toast.makeText(getApplicationContext(), "numero muy grande", Toast.LENGTH_SHORT).show();
                }
                Persona persona = controlador.buscarPersona(cedula);
                if (persona != null) {
                    tvCedula.setText(String.valueOf(cedula));
                    tvNombre.setText(persona.getNombre());
                    tvEstrato.setText(String.valueOf(persona.getEstrato2()));
                    tvSalario.setText(String.valueOf(persona.getSalario()));
                    switch (persona.getNivel_educativo()) {
                        case 0:
                            tvNivel.setText(getString(R.string.educacion_bachillerato));
                            break;
                        case 1:
                            tvNivel.setText(getString(R.string.educacion_pregado));
                            break;
                        case 2:
                            tvNivel.setText(getString(R.string.educacion_maestria));
                            break;
                        case 3:
                            tvNivel.setText(getString(R.string.educacion_doctorado));
                            break;
                    }
                } else {
                    tvCedula.setText(getString(R.string.error));
                    tvNombre.setText(getString(R.string.error));
                    tvEstrato.setText(getString(R.string.error));
                    tvSalario.setText(getString(R.string.error));
                    tvNivel.setText(getString(R.string.error));
                    Toast.makeText(getApplicationContext(), "no encontrado", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_Regresar:
                finish();
                break;
        }
    }
}
