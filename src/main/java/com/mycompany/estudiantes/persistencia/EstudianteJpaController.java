/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.estudiantes.persistencia;

import com.mycompany.estudiantes.logica.Estudiante;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.estudiantes.logica.Inscripcion;
import com.mycompany.estudiantes.persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author nosaka
 */
public class EstudianteJpaController implements Serializable {

    public EstudianteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public EstudianteJpaController(){
        emf = Persistence.createEntityManagerFactory("PEstudiantes");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estudiante estudiante) {
        if (estudiante.getListaInscripciones() == null) {
            estudiante.setListaInscripciones(new ArrayList<Inscripcion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ArrayList<Inscripcion> attachedListaInscripciones = new ArrayList<Inscripcion>();
            for (Inscripcion listaInscripcionesInscripcionToAttach : estudiante.getListaInscripciones()) {
                listaInscripcionesInscripcionToAttach = em.getReference(listaInscripcionesInscripcionToAttach.getClass(), listaInscripcionesInscripcionToAttach.getId());
                attachedListaInscripciones.add(listaInscripcionesInscripcionToAttach);
            }
            estudiante.setListaInscripciones(attachedListaInscripciones);
            em.persist(estudiante);
            for (Inscripcion listaInscripcionesInscripcion : estudiante.getListaInscripciones()) {
                Estudiante oldEsOfListaInscripcionesInscripcion = listaInscripcionesInscripcion.getEs();
                listaInscripcionesInscripcion.setEs(estudiante);
                listaInscripcionesInscripcion = em.merge(listaInscripcionesInscripcion);
                if (oldEsOfListaInscripcionesInscripcion != null) {
                    oldEsOfListaInscripcionesInscripcion.getListaInscripciones().remove(listaInscripcionesInscripcion);
                    oldEsOfListaInscripcionesInscripcion = em.merge(oldEsOfListaInscripcionesInscripcion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estudiante estudiante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante persistentEstudiante = em.find(Estudiante.class, estudiante.getId());
            ArrayList<Inscripcion> listaInscripcionesOld = persistentEstudiante.getListaInscripciones();
            ArrayList<Inscripcion> listaInscripcionesNew = estudiante.getListaInscripciones();
            ArrayList<Inscripcion> attachedListaInscripcionesNew = new ArrayList<Inscripcion>();
            for (Inscripcion listaInscripcionesNewInscripcionToAttach : listaInscripcionesNew) {
                listaInscripcionesNewInscripcionToAttach = em.getReference(listaInscripcionesNewInscripcionToAttach.getClass(), listaInscripcionesNewInscripcionToAttach.getId());
                attachedListaInscripcionesNew.add(listaInscripcionesNewInscripcionToAttach);
            }
            listaInscripcionesNew = attachedListaInscripcionesNew;
            estudiante.setListaInscripciones(listaInscripcionesNew);
            estudiante = em.merge(estudiante);
            for (Inscripcion listaInscripcionesOldInscripcion : listaInscripcionesOld) {
                if (!listaInscripcionesNew.contains(listaInscripcionesOldInscripcion)) {
                    listaInscripcionesOldInscripcion.setEs(null);
                    listaInscripcionesOldInscripcion = em.merge(listaInscripcionesOldInscripcion);
                }
            }
            for (Inscripcion listaInscripcionesNewInscripcion : listaInscripcionesNew) {
                if (!listaInscripcionesOld.contains(listaInscripcionesNewInscripcion)) {
                    Estudiante oldEsOfListaInscripcionesNewInscripcion = listaInscripcionesNewInscripcion.getEs();
                    listaInscripcionesNewInscripcion.setEs(estudiante);
                    listaInscripcionesNewInscripcion = em.merge(listaInscripcionesNewInscripcion);
                    if (oldEsOfListaInscripcionesNewInscripcion != null && !oldEsOfListaInscripcionesNewInscripcion.equals(estudiante)) {
                        oldEsOfListaInscripcionesNewInscripcion.getListaInscripciones().remove(listaInscripcionesNewInscripcion);
                        oldEsOfListaInscripcionesNewInscripcion = em.merge(oldEsOfListaInscripcionesNewInscripcion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = estudiante.getId();
                if (findEstudiante(id) == null) {
                    throw new NonexistentEntityException("The estudiante with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante estudiante;
            try {
                estudiante = em.getReference(Estudiante.class, id);
                estudiante.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estudiante with id " + id + " no longer exists.", enfe);
            }
            ArrayList<Inscripcion> listaInscripciones = estudiante.getListaInscripciones();
            for (Inscripcion listaInscripcionesInscripcion : listaInscripciones) {
                listaInscripcionesInscripcion.setEs(null);
                listaInscripcionesInscripcion = em.merge(listaInscripcionesInscripcion);
            }
            em.remove(estudiante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estudiante> findEstudianteEntities() {
        return findEstudianteEntities(true, -1, -1);
    }

    public List<Estudiante> findEstudianteEntities(int maxResults, int firstResult) {
        return findEstudianteEntities(false, maxResults, firstResult);
    }

    private List<Estudiante> findEstudianteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estudiante.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Estudiante findEstudiante(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estudiante.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstudianteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estudiante> rt = cq.from(Estudiante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
