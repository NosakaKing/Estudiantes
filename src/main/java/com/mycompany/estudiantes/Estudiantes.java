/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.estudiantes;

import com.mycompany.estudiantes.logica.Controladora;
import com.mycompany.estudiantes.logica.Curso;
import com.mycompany.estudiantes.logica.Estudiante;

import com.mycompany.estudiantes.logica.Inscripcion;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author nosaka
 */
public class Estudiantes {

    public static void main(String[] args) {
        Controladora control = new Controladora();

        // Inscripciones
        ArrayList<Inscripcion> listaInscripciones = new ArrayList<Inscripcion>();
        // Crear un estudiante
        Estudiante es = new Estudiante(2, "Moises", "Loor", 19, listaInscripciones);
        Estudiante es2 = new Estudiante(3, "Justin", "Moreira", 19, listaInscripciones);
        // Crear Estudiante
        control.crearEstudiante(es);
        control.crearEstudiante(es2);

        // Crear un Curso
        Curso curs = new Curso(2, 5, "Lunes", listaInscripciones);
        control.crearCurso(curs);

        // Crear una inscripcion
        Inscripcion insc = new Inscripcion(2, new Date(), es, curs);
        Inscripcion insc2 = new Inscripcion(3, new Date(), es, curs);
        Inscripcion insc3 = new Inscripcion(4, new Date(), es2, curs);
        control.crearInscripcion(insc);
        control.crearInscripcion(insc2);
        control.crearInscripcion(insc3);
    }
}
