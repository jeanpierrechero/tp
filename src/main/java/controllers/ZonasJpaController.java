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

import ar.com.sge.geografia.Zonas;
import clases.ZonaTransformadores;
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
public class ZonasJpaController implements Serializable {

    public ZonasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Zonas zonas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(zonas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
/*
    public void edit(Zonas zonas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Zonas persistentZonas = em.find(Zonas.class, zonas.getId());
            Collection<ZonaTransformadores> zonaTransformadoresCollectionOld = persistentZonas.getZonaTransformadoresCollection();
            Collection<ZonaTransformadores> zonaTransformadoresCollectionNew = zonas.getZonaTransformadoresCollection();
            List<String> illegalOrphanMessages = null;
            for (ZonaTransformadores zonaTransformadoresCollectionOldZonaTransformadores : zonaTransformadoresCollectionOld) {
                if (!zonaTransformadoresCollectionNew.contains(zonaTransformadoresCollectionOldZonaTransformadores)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ZonaTransformadores " + zonaTransformadoresCollectionOldZonaTransformadores + " since its zona field is not nullable.");
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
            zonas.setZonaTransformadoresCollection(zonaTransformadoresCollectionNew);
            zonas = em.merge(zonas);
            for (ZonaTransformadores zonaTransformadoresCollectionNewZonaTransformadores : zonaTransformadoresCollectionNew) {
                if (!zonaTransformadoresCollectionOld.contains(zonaTransformadoresCollectionNewZonaTransformadores)) {
                    Zonas oldZonaOfZonaTransformadoresCollectionNewZonaTransformadores = zonaTransformadoresCollectionNewZonaTransformadores.getZona();
                    zonaTransformadoresCollectionNewZonaTransformadores.setZona(zonas);
                    zonaTransformadoresCollectionNewZonaTransformadores = em.merge(zonaTransformadoresCollectionNewZonaTransformadores);
                    if (oldZonaOfZonaTransformadoresCollectionNewZonaTransformadores != null && !oldZonaOfZonaTransformadoresCollectionNewZonaTransformadores.equals(zonas)) {
                        oldZonaOfZonaTransformadoresCollectionNewZonaTransformadores.getZonaTransformadoresCollection().remove(zonaTransformadoresCollectionNewZonaTransformadores);
                        oldZonaOfZonaTransformadoresCollectionNewZonaTransformadores = em.merge(oldZonaOfZonaTransformadoresCollectionNewZonaTransformadores);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = zonas.getId();
                if (findZonas(id) == null) {
                    throw new NonexistentEntityException("The zonas with id " + id + " no longer exists.");
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
            Zonas zonas;
            try {
                zonas = em.getReference(Zonas.class, id);
                zonas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The zonas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ZonaTransformadores> zonaTransformadoresCollectionOrphanCheck = zonas.getZonaTransformadoresCollection();
            for (ZonaTransformadores zonaTransformadoresCollectionOrphanCheckZonaTransformadores : zonaTransformadoresCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Zonas (" + zonas + ") cannot be destroyed since the ZonaTransformadores " + zonaTransformadoresCollectionOrphanCheckZonaTransformadores + " in its zonaTransformadoresCollection field has a non-nullable zona field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(zonas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Zonas> findZonasEntities() {
        return findZonasEntities(true, -1, -1);
    }

    public List<Zonas> findZonasEntities(int maxResults, int firstResult) {
        return findZonasEntities(false, maxResults, firstResult);
    }

    private List<Zonas> findZonasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Zonas.class));
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

    public Zonas findZonas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Zonas.class, id);
        } finally {
            em.close();
        }
    }

    public int getZonasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Zonas> rt = cq.from(Zonas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    */
}
