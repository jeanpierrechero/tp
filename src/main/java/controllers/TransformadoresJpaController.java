/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import clases.ZonaTransformadores;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ar.com.sge.geografia.Transformadores;
import controllers.exceptions.IllegalOrphanException;
import controllers.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jean Pierre
 */
public class TransformadoresJpaController implements Serializable {

    public TransformadoresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transformadores transformadores) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(transformadores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
/*
    public void edit(Transformadores transformadores) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transformadores persistentTransformadores = em.find(Transformadores.class, transformadores.getId());
            Collection<ZonaTransformadores> zonaTransformadoresCollectionOld = persistentTransformadores.getZonaTransformadoresCollection();
            Collection<ZonaTransformadores> zonaTransformadoresCollectionNew = transformadores.getZonaTransformadoresCollection();
            List<String> illegalOrphanMessages = null;
            for (ZonaTransformadores zonaTransformadoresCollectionOldZonaTransformadores : zonaTransformadoresCollectionOld) {
                if (!zonaTransformadoresCollectionNew.contains(zonaTransformadoresCollectionOldZonaTransformadores)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ZonaTransformadores " + zonaTransformadoresCollectionOldZonaTransformadores + " since its transformador field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<ZonaTransformadores> attachedZonaTransformadoresCollectionNew = new ArrayList<ZonaTransformadores>();
            for (ZonaTransformadores zonaTransformadoresCollectionNewZonaTransformadoresToAttach : zonaTransformadoresCollectionNew) {
                zonaTransformadoresCollectionNewZonaTransformadoresToAttach = em.getReference(zonaTransformadoresCollectionNewZonaTransformadoresToAttach.getClass(), zonaTransformadoresCollectionNewZonaTransformadoresToAttach.getId());
                attachedZonaTransformadoresCollectionNew.add(zonaTransformadoresCollectionNewZonaTransformadoresToAttach);
            }
            zonaTransformadoresCollectionNew = attachedZonaTransformadoresCollectionNew;
            transformadores.setZonaTransformadoresCollection(zonaTransformadoresCollectionNew);
            transformadores = em.merge(transformadores);
            for (ZonaTransformadores zonaTransformadoresCollectionNewZonaTransformadores : zonaTransformadoresCollectionNew) {
                if (!zonaTransformadoresCollectionOld.contains(zonaTransformadoresCollectionNewZonaTransformadores)) {
                    Transformadores oldTransformadorOfZonaTransformadoresCollectionNewZonaTransformadores = zonaTransformadoresCollectionNewZonaTransformadores.getTransformador();
                    zonaTransformadoresCollectionNewZonaTransformadores.setTransformador(transformadores);
                    zonaTransformadoresCollectionNewZonaTransformadores = em.merge(zonaTransformadoresCollectionNewZonaTransformadores);
                    if (oldTransformadorOfZonaTransformadoresCollectionNewZonaTransformadores != null && !oldTransformadorOfZonaTransformadoresCollectionNewZonaTransformadores.equals(transformadores)) {
                        oldTransformadorOfZonaTransformadoresCollectionNewZonaTransformadores.getZonaTransformadoresCollection().remove(zonaTransformadoresCollectionNewZonaTransformadores);
                        oldTransformadorOfZonaTransformadoresCollectionNewZonaTransformadores = em.merge(oldTransformadorOfZonaTransformadoresCollectionNewZonaTransformadores);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = transformadores.getId();
                if (findTransformadores(id) == null) {
                    throw new NonexistentEntityException("The transformadores with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transformadores transformadores;
            try {
                transformadores = em.getReference(Transformadores.class, id);
                transformadores.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transformadores with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ZonaTransformadores> zonaTransformadoresCollectionOrphanCheck = transformadores.getZonaTransformadoresCollection();
            for (ZonaTransformadores zonaTransformadoresCollectionOrphanCheckZonaTransformadores : zonaTransformadoresCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transformadores (" + transformadores + ") cannot be destroyed since the ZonaTransformadores " + zonaTransformadoresCollectionOrphanCheckZonaTransformadores + " in its zonaTransformadoresCollection field has a non-nullable transformador field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(transformadores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Transformadores> findTransformadoresEntities() {
        return findTransformadoresEntities(true, -1, -1);
    }

    public List<Transformadores> findTransformadoresEntities(int maxResults, int firstResult) {
        return findTransformadoresEntities(false, maxResults, firstResult);
    }

    private List<Transformadores> findTransformadoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transformadores.class));
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

    public Transformadores findTransformadores(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transformadores.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransformadoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transformadores> rt = cq.from(Transformadores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    */
}
