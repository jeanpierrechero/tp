/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import clases.Actuadores;
import clases.Reglas;
import controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jean Pierre
 */
public class ReglasJpaController implements Serializable {

    public ReglasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reglas reglas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Actuadores actuador = reglas.getActuador();
            if (actuador != null) {
                actuador = em.getReference(actuador.getClass(), actuador.getId());
                reglas.setActuador(actuador);
            }
            em.persist(reglas);
            if (actuador != null) {
                actuador.getReglasCollection().add(reglas);
                actuador = em.merge(actuador);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reglas reglas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reglas persistentReglas = em.find(Reglas.class, reglas.getId());
            Actuadores actuadorOld = persistentReglas.getActuador();
            Actuadores actuadorNew = reglas.getActuador();
            if (actuadorNew != null) {
                actuadorNew = em.getReference(actuadorNew.getClass(), actuadorNew.getId());
                reglas.setActuador(actuadorNew);
            }
            reglas = em.merge(reglas);
            if (actuadorOld != null && !actuadorOld.equals(actuadorNew)) {
                actuadorOld.getReglasCollection().remove(reglas);
                actuadorOld = em.merge(actuadorOld);
            }
            if (actuadorNew != null && !actuadorNew.equals(actuadorOld)) {
                actuadorNew.getReglasCollection().add(reglas);
                actuadorNew = em.merge(actuadorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reglas.getId();
                if (findReglas(id) == null) {
                    throw new NonexistentEntityException("The reglas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reglas reglas;
            try {
                reglas = em.getReference(Reglas.class, id);
                reglas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reglas with id " + id + " no longer exists.", enfe);
            }
            Actuadores actuador = reglas.getActuador();
            if (actuador != null) {
                actuador.getReglasCollection().remove(reglas);
                actuador = em.merge(actuador);
            }
            em.remove(reglas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reglas> findReglasEntities() {
        return findReglasEntities(true, -1, -1);
    }

    public List<Reglas> findReglasEntities(int maxResults, int firstResult) {
        return findReglasEntities(false, maxResults, firstResult);
    }

    private List<Reglas> findReglasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reglas.class));
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

    public Reglas findReglas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reglas.class, id);
        } finally {
            em.close();
        }
    }

    public int getReglasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reglas> rt = cq.from(Reglas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
