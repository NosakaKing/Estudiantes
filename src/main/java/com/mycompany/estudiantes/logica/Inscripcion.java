/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.estudiantes.logica;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Inscripcion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Basic
    @Temporal(TemporalType.DATE)
    private Date Fecha;
    
    @ManyToOne
    private Estudiante es;
    @ManyToOne
    private Curso cur;

    public Inscripcion() {
    }

    public Inscripcion(int id, Date Fecha, Estudiante es, Curso cur) {
        this.id = id;
        this.Fecha = Fecha;
        this.es = es;
        this.cur = cur;
    }


    public Curso getCur() {
        return cur;
    }

    public void setCur(Curso cur) {
        this.cur = cur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public Estudiante getEs() {
        return es;
    }

    public void setEs(Estudiante es) {
        this.es = es;
    }

    
    
    
    
}
