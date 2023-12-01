/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.estudiantes.persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.estudiantes.logica.Curso;
import com.mycompany.estudiantes.logica.Estudiante;
import com.mycompany.estudiantes.logica.Inscripcion;
import com.mycompany.estudiantes.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author nosaka
 */
public class InscripcionJpaController implements Serializable {

    public InscripcionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public InscripcionJpaController(){
        emf = Persistence.createEntityManagerFactory("PEstudiantes");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Inscripcion inscripcion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Curso cur = inscripcion.getCur();
            if (cur != null) {
                cur = em.getReference(cur.getClass(), cur.getId());
                inscripcion.setCur(cur);
            }
            Estudiante es = inscripcion.getEs();
            if (es != null) {
                es = em.getReference(es.getClass(), es.getId());
                inscripcion.setEs(es);
            }
            em.persist(inscripcion);
            if (cur != null) {
                cur.getListaInscripciones1().add(inscripcion);
                cur = em.merge(cur);
            }
            if (es != null) {
                es.getListaInscripciones().add(inscripcion);
                es = em.merge(es);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Inscripcion inscripcion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inscripcion persistentInscripcion = em.find(Inscripcion.class, inscripcion.getId());
            Curso curOld = persistentInscripcion.getCur();
            Curso curNew = inscripcion.getCur();
            Estudiante esOld = persistentInscripcion.getEs();
            Estudiante esNew = inscripcion.getEs();
            if (curNew != null) {
                curNew = em.getReference(curNew.getClass(), curNew.getId());
                inscripcion.setCur(curNew);
            }
            if (esNew != null) {
                esNew = em.getReference(esNew.getClass(), esNew.getId());
                inscripcion.setEs(esNew);
            }
            inscripcion = em.merge(inscripcion);
            if (curOld != null && !curOld.equals(curNew)) {
                curOld.getListaInscripciones1().remove(inscripcion);
                curOld = em.merge(curOld);
            }
            if (curNew != null && !curNew.equals(curOld)) {
                curNew.getListaInscripciones1().add(inscripcion);
                curNew = em.merge(curNew);
            }
            if (esOld != null && !esOld.equals(esNew)) {
                esOld.getListaInscripciones().remove(inscripcion);
                esOld = em.merge(esOld);
            }
            if (esNew != null && !esNew.equals(esOld)) {
                esNew.getListaInscripciones().add(inscripcion);
                esNew = em.merge(esNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = inscripcion.getId();
                if (findInscripcion(id) == null) {
                    throw new NonexistentEntityException("The inscripcion with id " + id + " no longer exists.");
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
            Inscripcion inscripcion;
            try {
                inscripcion = em.getReference(Inscripcion.class, id);
                inscripcion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inscripcion with id " + id + " no longer exists.", enfe);
            }
            Curso cur = inscripcion.getCur();
            if (cur != null) {
                cur.getListaInscripciones1().remove(inscripcion);
                cur = em.merge(cur);
            }
            Estudiante es = inscripcion.getEs();
            if (es != null) {
                es.getListaInscripciones().remove(inscripcion);
                es = em.merge(es);
            }
            em.remove(inscripcion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Inscripcion> findInscripcionEntities() {
        return findInscripcionEntities(true, -1, -1);
    }

    public List<Inscripcion> findInscripcionEntities(int maxResults, int firstResult) {
        return findInscripcionEntities(false, maxResults, firstResult);
    }

    private List<Inscripcion> findInscripcionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Inscripcion.class));
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

    public Inscripcion findInscripcion(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Inscripcion.class, id);
        } finally {
            em.close();
        }
    }

    public int getInscripcionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Inscripcion> rt = cq.from(Inscripcion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
