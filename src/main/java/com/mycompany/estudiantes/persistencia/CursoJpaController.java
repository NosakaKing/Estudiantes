/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.estudiantes.persistencia;

import com.mycompany.estudiantes.logica.Curso;
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
public class CursoJpaController implements Serializable {

    public CursoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public CursoJpaController(){
        emf = Persistence.createEntityManagerFactory("PEstudiantes");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Curso curso) {
        if (curso.getListaInscripciones1() == null) {
            curso.setListaInscripciones1(new ArrayList<Inscripcion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ArrayList<Inscripcion> attachedListaInscripciones1 = new ArrayList<Inscripcion>();
            for (Inscripcion listaInscripciones1InscripcionToAttach : curso.getListaInscripciones1()) {
                listaInscripciones1InscripcionToAttach = em.getReference(listaInscripciones1InscripcionToAttach.getClass(), listaInscripciones1InscripcionToAttach.getId());
                attachedListaInscripciones1.add(listaInscripciones1InscripcionToAttach);
            }
            curso.setListaInscripciones1(attachedListaInscripciones1);
            em.persist(curso);
            for (Inscripcion listaInscripciones1Inscripcion : curso.getListaInscripciones1()) {
                Curso oldCurOfListaInscripciones1Inscripcion = listaInscripciones1Inscripcion.getCur();
                listaInscripciones1Inscripcion.setCur(curso);
                listaInscripciones1Inscripcion = em.merge(listaInscripciones1Inscripcion);
                if (oldCurOfListaInscripciones1Inscripcion != null) {
                    oldCurOfListaInscripciones1Inscripcion.getListaInscripciones1().remove(listaInscripciones1Inscripcion);
                    oldCurOfListaInscripciones1Inscripcion = em.merge(oldCurOfListaInscripciones1Inscripcion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Curso curso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Curso persistentCurso = em.find(Curso.class, curso.getId());
            ArrayList<Inscripcion> listaInscripciones1Old = persistentCurso.getListaInscripciones1();
            ArrayList<Inscripcion> listaInscripciones1New = curso.getListaInscripciones1();
            ArrayList<Inscripcion> attachedListaInscripciones1New = new ArrayList<Inscripcion>();
            for (Inscripcion listaInscripciones1NewInscripcionToAttach : listaInscripciones1New) {
                listaInscripciones1NewInscripcionToAttach = em.getReference(listaInscripciones1NewInscripcionToAttach.getClass(), listaInscripciones1NewInscripcionToAttach.getId());
                attachedListaInscripciones1New.add(listaInscripciones1NewInscripcionToAttach);
            }
            listaInscripciones1New = attachedListaInscripciones1New;
            curso.setListaInscripciones1(listaInscripciones1New);
            curso = em.merge(curso);
            for (Inscripcion listaInscripciones1OldInscripcion : listaInscripciones1Old) {
                if (!listaInscripciones1New.contains(listaInscripciones1OldInscripcion)) {
                    listaInscripciones1OldInscripcion.setCur(null);
                    listaInscripciones1OldInscripcion = em.merge(listaInscripciones1OldInscripcion);
                }
            }
            for (Inscripcion listaInscripciones1NewInscripcion : listaInscripciones1New) {
                if (!listaInscripciones1Old.contains(listaInscripciones1NewInscripcion)) {
                    Curso oldCurOfListaInscripciones1NewInscripcion = listaInscripciones1NewInscripcion.getCur();
                    listaInscripciones1NewInscripcion.setCur(curso);
                    listaInscripciones1NewInscripcion = em.merge(listaInscripciones1NewInscripcion);
                    if (oldCurOfListaInscripciones1NewInscripcion != null && !oldCurOfListaInscripciones1NewInscripcion.equals(curso)) {
                        oldCurOfListaInscripciones1NewInscripcion.getListaInscripciones1().remove(listaInscripciones1NewInscripcion);
                        oldCurOfListaInscripciones1NewInscripcion = em.merge(oldCurOfListaInscripciones1NewInscripcion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = curso.getId();
                if (findCurso(id) == null) {
                    throw new NonexistentEntityException("The curso with id " + id + " no longer exists.");
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
            Curso curso;
            try {
                curso = em.getReference(Curso.class, id);
                curso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The curso with id " + id + " no longer exists.", enfe);
            }
            ArrayList<Inscripcion> listaInscripciones1 = curso.getListaInscripciones1();
            for (Inscripcion listaInscripciones1Inscripcion : listaInscripciones1) {
                listaInscripciones1Inscripcion.setCur(null);
                listaInscripciones1Inscripcion = em.merge(listaInscripciones1Inscripcion);
            }
            em.remove(curso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Curso> findCursoEntities() {
        return findCursoEntities(true, -1, -1);
    }

    public List<Curso> findCursoEntities(int maxResults, int firstResult) {
        return findCursoEntities(false, maxResults, firstResult);
    }

    private List<Curso> findCursoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Curso.class));
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

    public Curso findCurso(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Curso.class, id);
        } finally {
            em.close();
        }
    }

    public int getCursoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Curso> rt = cq.from(Curso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
