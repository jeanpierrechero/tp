/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import clases.Sensores;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import clases.UsuarioDispositivos;
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
public class SensoresJpaController implements Serializable {

    public SensoresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sensores sensores) {
        if (sensores.getUsuarioDispositivosCollection() == null) {
            sensores.setUsuarioDispositivosCollection(new ArrayList<UsuarioDispositivos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<UsuarioDispositivos> attachedUsuarioDispositivosCollection = new ArrayList<UsuarioDispositivos>();
            for (UsuarioDispositivos usuarioDispositivosCollectionUsuarioDispositivosToAttach : sensores.getUsuarioDispositivosCollection()) {
                usuarioDispositivosCollectionUsuarioDispositivosToAttach = em.getReference(usuarioDispositivosCollectionUsuarioDispositivosToAttach.getClass(), usuarioDispositivosCollectionUsuarioDispositivosToAttach.getId());
                attachedUsuarioDispositivosCollection.add(usuarioDispositivosCollectionUsuarioDispositivosToAttach);
            }
            sensores.setUsuarioDispositivosCollection(attachedUsuarioDispositivosCollection);
            em.persist(sensores);
            for (UsuarioDispositivos usuarioDispositivosCollectionUsuarioDispositivos : sensores.getUsuarioDispositivosCollection()) {
                Sensores oldSensorOfUsuarioDispositivosCollectionUsuarioDispositivos = usuarioDispositivosCollectionUsuarioDispositivos.getSensor();
                usuarioDispositivosCollectionUsuarioDispositivos.setSensor(sensores);
                usuarioDispositivosCollectionUsuarioDispositivos = em.merge(usuarioDispositivosCollectionUsuarioDispositivos);
                if (oldSensorOfUsuarioDispositivosCollectionUsuarioDispositivos != null) {
                    oldSensorOfUsuarioDispositivosCollectionUsuarioDispositivos.getUsuarioDispositivosCollection().remove(usuarioDispositivosCollectionUsuarioDispositivos);
                    oldSensorOfUsuarioDispositivosCollectionUsuarioDispositivos = em.merge(oldSensorOfUsuarioDispositivosCollectionUsuarioDispositivos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sensores sensores) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sensores persistentSensores = em.find(Sensores.class, sensores.getId());
            Collection<UsuarioDispositivos> usuarioDispositivosCollectionOld = persistentSensores.getUsuarioDispositivosCollection();
            Collection<UsuarioDispositivos> usuarioDispositivosCollectionNew = sensores.getUsuarioDispositivosCollection();
            Collection<UsuarioDispositivos> attachedUsuarioDispositivosCollectionNew = new ArrayList<UsuarioDispositivos>();
            for (UsuarioDispositivos usuarioDispositivosCollectionNewUsuarioDispositivosToAttach : usuarioDispositivosCollectionNew) {
                usuarioDispositivosCollectionNewUsuarioDispositivosToAttach = em.getReference(usuarioDispositivosCollectionNewUsuarioDispositivosToAttach.getClass(), usuarioDispositivosCollectionNewUsuarioDispositivosToAttach.getId());
                attachedUsuarioDispositivosCollectionNew.add(usuarioDispositivosCollectionNewUsuarioDispositivosToAttach);
            }
            usuarioDispositivosCollectionNew = attachedUsuarioDispositivosCollectionNew;
            sensores.setUsuarioDispositivosCollection(usuarioDispositivosCollectionNew);
            sensores = em.merge(sensores);
            for (UsuarioDispositivos usuarioDispositivosCollectionOldUsuarioDispositivos : usuarioDispositivosCollectionOld) {
                if (!usuarioDispositivosCollectionNew.contains(usuarioDispositivosCollectionOldUsuarioDispositivos)) {
                    usuarioDispositivosCollectionOldUsuarioDispositivos.setSensor(null);
                    usuarioDispositivosCollectionOldUsuarioDispositivos = em.merge(usuarioDispositivosCollectionOldUsuarioDispositivos);
                }
            }
            for (UsuarioDispositivos usuarioDispositivosCollectionNewUsuarioDispositivos : usuarioDispositivosCollectionNew) {
                if (!usuarioDispositivosCollectionOld.contains(usuarioDispositivosCollectionNewUsuarioDispositivos)) {
                    Sensores oldSensorOfUsuarioDispositivosCollectionNewUsuarioDispositivos = usuarioDispositivosCollectionNewUsuarioDispositivos.getSensor();
                    usuarioDispositivosCollectionNewUsuarioDispositivos.setSensor(sensores);
                    usuarioDispositivosCollectionNewUsuarioDispositivos = em.merge(usuarioDispositivosCollectionNewUsuarioDispositivos);
                    if (oldSensorOfUsuarioDispositivosCollectionNewUsuarioDispositivos != null && !oldSensorOfUsuarioDispositivosCollectionNewUsuarioDispositivos.equals(sensores)) {
                        oldSensorOfUsuarioDispositivosCollectionNewUsuarioDispositivos.getUsuarioDispositivosCollection().remove(usuarioDispositivosCollectionNewUsuarioDispositivos);
                        oldSensorOfUsuarioDispositivosCollectionNewUsuarioDispositivos = em.merge(oldSensorOfUsuarioDispositivosCollectionNewUsuarioDispositivos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sensores.getId();
                if (findSensores(id) == null) {
                    throw new NonexistentEntityException("The sensores with id " + id + " no longer exists.");
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
            Sensores sensores;
            try {
                sensores = em.getReference(Sensores.class, id);
                sensores.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sensores with id " + id + " no longer exists.", enfe);
            }
            Collection<UsuarioDispositivos> usuarioDispositivosCollection = sensores.getUsuarioDispositivosCollection();
            for (UsuarioDispositivos usuarioDispositivosCollectionUsuarioDispositivos : usuarioDispositivosCollection) {
                usuarioDispositivosCollectionUsuarioDispositivos.setSensor(null);
                usuarioDispositivosCollectionUsuarioDispositivos = em.merge(usuarioDispositivosCollectionUsuarioDispositivos);
            }
            em.remove(sensores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sensores> findSensoresEntities() {
        return findSensoresEntities(true, -1, -1);
    }

    public List<Sensores> findSensoresEntities(int maxResults, int firstResult) {
        return findSensoresEntities(false, maxResults, firstResult);
    }

    private List<Sensores> findSensoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sensores.class));
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

    public Sensores findSensores(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sensores.class, id);
        } finally {
            em.close();
        }
    }

    public int getSensoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sensores> rt = cq.from(Sensores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
