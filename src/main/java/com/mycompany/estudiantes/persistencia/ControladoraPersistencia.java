/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.estudiantes.persistencia;

import com.mycompany.estudiantes.logica.Curso;
import com.mycompany.estudiantes.logica.Estudiante;
import com.mycompany.estudiantes.logica.Inscripcion;





public class ControladoraPersistencia {
   EstudianteJpaController Es = new EstudianteJpaController();
   InscripcionJpaController Ins = new InscripcionJpaController();
   CursoJpaController Curs = new CursoJpaController();

    public void crearEstudiante(Estudiante es) {
        Es.create(es);
    }

    public void crearCurso(Curso curs) {
       Curs.create(curs);
    }

    public void crearInscripcion(Inscripcion Insc) {
        Ins.create(Insc);
    }
   

}
