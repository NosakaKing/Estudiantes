/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.estudiantes.logica;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Curso implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int horas;
    private String dias;
    
    @OneToMany(mappedBy = "cur")
    private ArrayList <Inscripcion> listaInscripciones1;

    public Curso(int id, int horas, String dias, ArrayList<Inscripcion> listaInscripciones1) {
        this.id = id;
        this.horas = horas;
        this.dias = dias;
        this.listaInscripciones1 = listaInscripciones1;
    }

    public Curso() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public ArrayList<Inscripcion> getListaInscripciones1() {
        return listaInscripciones1;
    }

    public void setListaInscripciones1(ArrayList<Inscripcion> listaInscripciones1) {
        this.listaInscripciones1 = listaInscripciones1;
    }


    
    
}
