/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.estudiantes.logica;

import com.mycompany.estudiantes.persistencia.ControladoraPersistencia;

/**
 *
 * @author nosaka
 */
public class Controladora {
    ControladoraPersistencia controlPersis = new ControladoraPersistencia();
    
    public void crearEstudiante(Estudiante es){
        controlPersis.crearEstudiante(es);
        
    }
    
    public void crearCurso(Curso curs){
        controlPersis.crearCurso(curs);
        
    }
    
    public void crearInscripcion(Inscripcion Insc){
        controlPersis.crearInscripcion(Insc);
        
    }
}
