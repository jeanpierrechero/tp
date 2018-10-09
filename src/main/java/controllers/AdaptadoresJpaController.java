/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import clases.Adaptadores;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import clases.ComandosAdaptadores;
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
public class AdaptadoresJpaController implements Serializable {

    public AdaptadoresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Adaptadores adaptadores) {
        if (adaptadores.getComandosAdaptadoresCollection() == null) {
            adaptadores.setComandosAdaptadoresCollection(new ArrayList<ComandosAdaptadores>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<ComandosAdaptadores> attachedComandosAdaptadoresCollection = new ArrayList<ComandosAdaptadores>();
            for (ComandosAdaptadores comandosAdaptadoresCollectionComandosAdaptadoresToAttach : adaptadores.getComandosAdaptadoresCollection()) {
                comandosAdaptadoresCollectionComandosAdaptadoresToAttach = em.getReference(comandosAdaptadoresCollectionComandosAdaptadoresToAttach.getClass(), comandosAdaptadoresCollectionComandosAdaptadoresToAttach.getId());
                attachedComandosAdaptadoresCollection.add(comandosAdaptadoresCollectionComandosAdaptadoresToAttach);
            }
            adaptadores.setComandosAdaptadoresCollection(attachedComandosAdaptadoresCollection);
            em.persist(adaptadores);
            for (ComandosAdaptadores comandosAdaptadoresCollectionComandosAdaptadores : adaptadores.getComandosAdaptadoresCollection()) {
                Adaptadores oldAdaptadorOfComandosAdaptadoresCollectionComandosAdaptadores = comandosAdaptadoresCollectionComandosAdaptadores.getAdaptador();
                comandosAdaptadoresCollectionComandosAdaptadores.setAdaptador(adaptadores);
                comandosAdaptadoresCollectionComandosAdaptadores = em.merge(comandosAdaptadoresCollectionComandosAdaptadores);
                if (oldAdaptadorOfComandosAdaptadoresCollectionComandosAdaptadores != null) {
                    oldAdaptadorOfComandosAdaptadoresCollectionComandosAdaptadores.getComandosAdaptadoresCollection().remove(comandosAdaptadoresCollectionComandosAdaptadores);
                    oldAdaptadorOfComandosAdaptadoresCollectionComandosAdaptadores = em.merge(oldAdaptadorOfComandosAdaptadoresCollectionComandosAdaptadores);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Adaptadores adaptadores) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Adaptadores persistentAdaptadores = em.find(Adaptadores.class, adaptadores.getId());
            Collection<ComandosAdaptadores> comandosAdaptadoresCollectionOld = persistentAdaptadores.getComandosAdaptadoresCollection();
            Collection<ComandosAdaptadores> comandosAdaptadoresCollectionNew = adaptadores.getComandosAdaptadoresCollection();
            List<String> illegalOrphanMessages = null;
            for (ComandosAdaptadores comandosAdaptadoresCollectionOldComandosAdaptadores : comandosAdaptadoresCollectionOld) {
                if (!comandosAdaptadoresCollectionNew.contains(comandosAdaptadoresCollectionOldComandosAdaptadores)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ComandosAdaptadores " + comandosAdaptadoresCollectionOldComandosAdaptadores + " since its adaptador field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<ComandosAdaptadores> attachedComandosAdaptadoresCollectionNew = new ArrayList<ComandosAdaptadores>();
            for (ComandosAdaptadores comandosAdaptadoresCollectionNewComandosAdaptadoresToAttach : comandosAdaptadoresCollectionNew) {
                comandosAdaptadoresCollectionNewComandosAdaptadoresToAttach = em.getReference(comandosAdaptadoresCollectionNewComandosAdaptadoresToAttach.getClass(), comandosAdaptadoresCollectionNewComandosAdaptadoresToAttach.getId());
                attachedComandosAdaptadoresCollectionNew.add(comandosAdaptadoresCollectionNewComandosAdaptadoresToAttach);
            }
            comandosAdaptadoresCollectionNew = attachedComandosAdaptadoresCollectionNew;
            adaptadores.setComandosAdaptadoresCollection(comandosAdaptadoresCollectionNew);
            adaptadores = em.merge(adaptadores);
            for (ComandosAdaptadores comandosAdaptadoresCollectionNewComandosAdaptadores : comandosAdaptadoresCollectionNew) {
                if (!comandosAdaptadoresCollectionOld.contains(comandosAdaptadoresCollectionNewComandosAdaptadores)) {
                    Adaptadores oldAdaptadorOfComandosAdaptadoresCollectionNewComandosAdaptadores = comandosAdaptadoresCollectionNewComandosAdaptadores.getAdaptador();
                    comandosAdaptadoresCollectionNewComandosAdaptadores.setAdaptador(adaptadores);
                    comandosAdaptadoresCollectionNewComandosAdaptadores = em.merge(comandosAdaptadoresCollectionNewComandosAdaptadores);
                    if (oldAdaptadorOfComandosAdaptadoresCollectionNewComandosAdaptadores != null && !oldAdaptadorOfComandosAdaptadoresCollectionNewComandosAdaptadores.equals(adaptadores)) {
                        oldAdaptadorOfComandosAdaptadoresCollectionNewComandosAdaptadores.getComandosAdaptadoresCollection().remove(comandosAdaptadoresCollectionNewComandosAdaptadores);
                        oldAdaptadorOfComandosAdaptadoresCollectionNewComandosAdaptadores = em.merge(oldAdaptadorOfComandosAdaptadoresCollectionNewComandosAdaptadores);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = adaptadores.getId();
                if (findAdaptadores(id) == null) {
                    throw new NonexistentEntityException("The adaptadores with id " + id + " no longer exists.");
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
            Adaptadores adaptadores;
            try {
                adaptadores = em.getReference(Adaptadores.class, id);
                adaptadores.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The adaptadores with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ComandosAdaptadores> comandosAdaptadoresCollectionOrphanCheck = adaptadores.getComandosAdaptadoresCollection();
            for (ComandosAdaptadores comandosAdaptadoresCollectionOrphanCheckComandosAdaptadores : comandosAdaptadoresCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Adaptadores (" + adaptadores + ") cannot be destroyed since the ComandosAdaptadores " + comandosAdaptadoresCollectionOrphanCheckComandosAdaptadores + " in its comandosAdaptadoresCollection field has a non-nullable adaptador field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(adaptadores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Adaptadores> findAdaptadoresEntities() {
        return findAdaptadoresEntities(true, -1, -1);
    }

    public List<Adaptadores> findAdaptadoresEntities(int maxResults, int firstResult) {
        return findAdaptadoresEntities(false, maxResults, firstResult);
    }

    private List<Adaptadores> findAdaptadoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Adaptadores.class));
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

    public Adaptadores findAdaptadores(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Adaptadores.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdaptadoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Adaptadores> rt = cq.from(Adaptadores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
