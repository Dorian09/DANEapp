package com.example.daneapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.daneapp.Model.Persona;

import java.util.ArrayList;

public class DB_controller {
    private  DB_helper baseDatos;

    public DB_controller(Context context) {
        this.baseDatos = new DB_helper(context, Modelo_DB.NOMBRE_DB, null, 1);
    }

    public long agregarRegistro(Persona persona) {
        try {
            SQLiteDatabase database = baseDatos.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Modelo_DB.COL_CEDULA, persona.getCedula());
            values.put(Modelo_DB.COL_NOMBRE, persona.getNombre());
            values.put(Modelo_DB.COL_ESTRATO, persona.getEstrato());
            values.put(Modelo_DB.COL_SALARIO, persona.getSalario());
            values.put(Modelo_DB.COL_NIVEL_EDUCATIVO, persona.getNivel_educativo());
            return database.insert(Modelo_DB.NOMBRE_TABLA, null, values);
        } catch (Exception e) {
            return -1L;
        }
    }

    public int actualizarRegistro(Persona persona) {
        try {
            SQLiteDatabase database = baseDatos.getWritableDatabase();
            ContentValues valoresActualizados = new ContentValues();
            valoresActualizados.put(Modelo_DB.COL_NOMBRE, persona.getNombre());
            valoresActualizados.put(Modelo_DB.COL_ESTRATO, persona.getEstrato());
            valoresActualizados.put(Modelo_DB.COL_SALARIO, persona.getSalario());
            valoresActualizados.put(Modelo_DB.COL_NIVEL_EDUCATIVO, persona.getNivel_educativo());

            String campoParaActualizar = Modelo_DB.COL_CEDULA + " = ?";
            String[] argumentosParaActualizar = {String.valueOf(persona.getCedula())};

            return database.update(Modelo_DB.NOMBRE_TABLA, valoresActualizados, campoParaActualizar, argumentosParaActualizar);
        } catch (Exception e) {
            return 0;
        }
    }

    public int borrarRegistro(Persona persona) {
        try {
            SQLiteDatabase database = baseDatos.getWritableDatabase();
            String[] argumentos = {String.valueOf(persona.getCedula())};
            return database.delete(Modelo_DB.NOMBRE_TABLA, Modelo_DB.COL_CEDULA + " = ?", argumentos);
        } catch (Exception e) {
            return 0;
        }
    }

    public Persona buscarPersona(int cedula) {

        SQLiteDatabase database = baseDatos.getReadableDatabase();

        String[] columnasConsultar = {Modelo_DB.COL_CEDULA, Modelo_DB.COL_NOMBRE, Modelo_DB.COL_ESTRATO
                , Modelo_DB.COL_SALARIO, Modelo_DB.COL_NIVEL_EDUCATIVO};
        String[] argumento = {String.valueOf(cedula)};
        Cursor cursor = database.query(Modelo_DB.NOMBRE_TABLA, columnasConsultar
                , Modelo_DB.COL_CEDULA + " = ?", argumento, null, null, null);

        if (cursor == null) {
            return null;
        }

        if (!cursor.moveToFirst()) {
            return null;
        }

        Persona persona = new Persona(cursor.getInt(0), cursor.getString(1)
                , cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
        cursor.close();
        return persona;
    }

    public ArrayList<Persona> obtenerRegistros() {
        ArrayList<Persona> personas = new ArrayList<>();

        SQLiteDatabase database = baseDatos.getReadableDatabase();

        String[] columnasConsultar = {Modelo_DB.COL_CEDULA, Modelo_DB.COL_NOMBRE, Modelo_DB.COL_ESTRATO
                , Modelo_DB.COL_SALARIO, Modelo_DB.COL_NIVEL_EDUCATIVO};

        Cursor cursor = database.query(Modelo_DB.NOMBRE_TABLA, columnasConsultar
                , null, null, null, null, null);

        if (cursor == null) {
            return personas;
        }

        if (!cursor.moveToFirst()) {
            return personas;
        }

        do {
            Persona persona = new Persona(cursor.getInt(0), cursor.getString(1)
                    , cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
            personas.add(persona);
        } while (cursor.moveToNext());

        cursor.close();
        return personas;
    }
}
