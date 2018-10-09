/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import clases.Dispositivos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import clases.UsuarioDispositivos;
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
public class DispositivosJpaController implements Serializable {

    public DispositivosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Dispositivos dispositivos) {
        if (dispositivos.getUsuarioDispositivosCollection() == null) {
            dispositivos.setUsuarioDispositivosCollection(new ArrayList<UsuarioDispositivos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<UsuarioDispositivos> attachedUsuarioDispositivosCollection = new ArrayList<UsuarioDispositivos>();
            for (UsuarioDispositivos usuarioDispositivosCollectionUsuarioDispositivosToAttach : dispositivos.getUsuarioDispositivosCollection()) {
                usuarioDispositivosCollectionUsuarioDispositivosToAttach = em.getReference(usuarioDispositivosCollectionUsuarioDispositivosToAttach.getClass(), usuarioDispositivosCollectionUsuarioDispositivosToAttach.getId());
                attachedUsuarioDispositivosCollection.add(usuarioDispositivosCollectionUsuarioDispositivosToAttach);
            }
            dispositivos.setUsuarioDispositivosCollection(attachedUsuarioDispositivosCollection);
            em.persist(dispositivos);
            for (UsuarioDispositivos usuarioDispositivosCollectionUsuarioDispositivos : dispositivos.getUsuarioDispositivosCollection()) {
                Dispositivos oldDispositivoOfUsuarioDispositivosCollectionUsuarioDispositivos = usuarioDispositivosCollectionUsuarioDispositivos.getDispositivo();
                usuarioDispositivosCollectionUsuarioDispositivos.setDispositivo(dispositivos);
                usuarioDispositivosCollectionUsuarioDispositivos = em.merge(usuarioDispositivosCollectionUsuarioDispositivos);
                if (oldDispositivoOfUsuarioDispositivosCollectionUsuarioDispositivos != null) {
                    oldDispositivoOfUsuarioDispositivosCollectionUsuarioDispositivos.getUsuarioDispositivosCollection().remove(usuarioDispositivosCollectionUsuarioDispositivos);
                    oldDispositivoOfUsuarioDispositivosCollectionUsuarioDispositivos = em.merge(oldDispositivoOfUsuarioDispositivosCollectionUsuarioDispositivos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Dispositivos dispositivos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dispositivos persistentDispositivos = em.find(Dispositivos.class, dispositivos.getId());
            Collection<UsuarioDispositivos> usuarioDispositivosCollectionOld = persistentDispositivos.getUsuarioDispositivosCollection();
            Collection<UsuarioDispositivos> usuarioDispositivosCollectionNew = dispositivos.getUsuarioDispositivosCollection();
            List<String> illegalOrphanMessages = null;
            for (UsuarioDispositivos usuarioDispositivosCollectionOldUsuarioDispositivos : usuarioDispositivosCollectionOld) {
                if (!usuarioDispositivosCollectionNew.contains(usuarioDispositivosCollectionOldUsuarioDispositivos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UsuarioDispositivos " + usuarioDispositivosCollectionOldUsuarioDispositivos + " since its dispositivo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<UsuarioDispositivos> attachedUsuarioDispositivosCollectionNew = new ArrayList<UsuarioDispositivos>();
            for (UsuarioDispositivos usuarioDispositivosCollectionNewUsuarioDispositivosToAttach : usuarioDispositivosCollectionNew) {
                usuarioDispositivosCollectionNewUsuarioDispositivosToAttach = em.getReference(usuarioDispositivosCollectionNewUsuarioDispositivosToAttach.getClass(), usuarioDispositivosCollectionNewUsuarioDispositivosToAttach.getId());
                attachedUsuarioDispositivosCollectionNew.add(usuarioDispositivosCollectionNewUsuarioDispositivosToAttach);
            }
            usuarioDispositivosCollectionNew = attachedUsuarioDispositivosCollectionNew;
            dispositivos.setUsuarioDispositivosCollection(usuarioDispositivosCollectionNew);
            dispositivos = em.merge(dispositivos);
            for (UsuarioDispositivos usuarioDispositivosCollectionNewUsuarioDispositivos : usuarioDispositivosCollectionNew) {
                if (!usuarioDispositivosCollectionOld.contains(usuarioDispositivosCollectionNewUsuarioDispositivos)) {
                    Dispositivos oldDispositivoOfUsuarioDispositivosCollectionNewUsuarioDispositivos = usuarioDispositivosCollectionNewUsuarioDispositivos.getDispositivo();
                    usuarioDispositivosCollectionNewUsuarioDispositivos.setDispositivo(dispositivos);
                    usuarioDispositivosCollectionNewUsuarioDispositivos = em.merge(usuarioDispositivosCollectionNewUsuarioDispositivos);
                    if (oldDispositivoOfUsuarioDispositivosCollectionNewUsuarioDispositivos != null && !oldDispositivoOfUsuarioDispositivosCollectionNewUsuarioDispositivos.equals(dispositivos)) {
                        oldDispositivoOfUsuarioDispositivosCollectionNewUsuarioDispositivos.getUsuarioDispositivosCollection().remove(usuarioDispositivosCollectionNewUsuarioDispositivos);
                        oldDispositivoOfUsuarioDispositivosCollectionNewUsuarioDispositivos = em.merge(oldDispositivoOfUsuarioDispositivosCollectionNewUsuarioDispositivos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = dispositivos.getId();
                if (findDispositivos(id) == null) {
                    throw new NonexistentEntityException("The dispositivos with id " + id + " no longer exists.");
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
            Dispositivos dispositivos;
            try {
                dispositivos = em.getReference(Dispositivos.class, id);
                dispositivos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dispositivos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<UsuarioDispositivos> usuarioDispositivosCollectionOrphanCheck = dispositivos.getUsuarioDispositivosCollection();
            for (UsuarioDispositivos usuarioDispositivosCollectionOrphanCheckUsuarioDispositivos : usuarioDispositivosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Dispositivos (" + dispositivos + ") cannot be destroyed since the UsuarioDispositivos " + usuarioDispositivosCollectionOrphanCheckUsuarioDispositivos + " in its usuarioDispositivosCollection field has a non-nullable dispositivo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(dispositivos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Dispositivos> findDispositivosEntities() {
        return findDispositivosEntities(true, -1, -1);
    }

    public List<Dispositivos> findDispositivosEntities(int maxResults, int firstResult) {
        return findDispositivosEntities(false, maxResults, firstResult);
    }

    private List<Dispositivos> findDispositivosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Dispositivos.class));
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

    public Dispositivos findDispositivos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dispositivos.class, id);
        } finally {
            em.close();
        }
    }

    public int getDispositivosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Dispositivos> rt = cq.from(Dispositivos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
