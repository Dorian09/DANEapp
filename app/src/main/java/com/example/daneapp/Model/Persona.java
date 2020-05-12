package com.example.daneapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Persona implements Parcelable {

    private int cedula;
    private String nombre;
    private int estrato;
    private int salario;
    private int nivel_educativo;

    public Persona(int cedula, String nombre, int estrato, int salario, int nivel_educativo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.estrato = estrato;
        this.salario = salario;
        this.nivel_educativo = nivel_educativo;
    }
    protected Persona(Parcel in) {
        cedula = in.readInt();
        nombre = in.readString();
        estrato = in.readInt();
        salario = in.readInt();
        nivel_educativo = in.readInt();
    }

    public static final Creator<Persona> CREATOR = new Creator<Persona>() {
        @Override
        public Persona createFromParcel(Parcel in) {
            return new Persona(in);
        }

        @Override
        public Persona[] newArray(int size) {
            return new Persona[size];
        }
    };

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstrato() {
        return estrato;
    }
    public int getEstrato2() {
        return estrato+1;
    }

    public void setEstrato(int estrato) {
        this.estrato = estrato;
    }

    public int getSalario() {
        return salario;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }

    public int getNivel_educativo() {
        return nivel_educativo;
    }

    public void setNivel_educativo(int nivel_educativo) {
        this.nivel_educativo = nivel_educativo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
