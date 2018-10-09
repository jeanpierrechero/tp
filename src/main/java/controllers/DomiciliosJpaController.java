/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import clases.Domicilios;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import clases.Usuarios;
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
public class DomiciliosJpaController implements Serializable {

    public DomiciliosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Domicilios domicilios) {
        if (domicilios.getUsuariosCollection() == null) {
            domicilios.setUsuariosCollection(new ArrayList<Usuarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Usuarios> attachedUsuariosCollection = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionUsuariosToAttach : domicilios.getUsuariosCollection()) {
                usuariosCollectionUsuariosToAttach = em.getReference(usuariosCollectionUsuariosToAttach.getClass(), usuariosCollectionUsuariosToAttach.getId());
                attachedUsuariosCollection.add(usuariosCollectionUsuariosToAttach);
            }
            domicilios.setUsuariosCollection(attachedUsuariosCollection);
            em.persist(domicilios);
            for (Usuarios usuariosCollectionUsuarios : domicilios.getUsuariosCollection()) {
                Domicilios oldDomicilioOfUsuariosCollectionUsuarios = usuariosCollectionUsuarios.getDomicilio();
                usuariosCollectionUsuarios.setDomicilio(domicilios);
                usuariosCollectionUsuarios = em.merge(usuariosCollectionUsuarios);
                if (oldDomicilioOfUsuariosCollectionUsuarios != null) {
                    oldDomicilioOfUsuariosCollectionUsuarios.getUsuariosCollection().remove(usuariosCollectionUsuarios);
                    oldDomicilioOfUsuariosCollectionUsuarios = em.merge(oldDomicilioOfUsuariosCollectionUsuarios);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Domicilios domicilios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Domicilios persistentDomicilios = em.find(Domicilios.class, domicilios.getId());
            Collection<Usuarios> usuariosCollectionOld = persistentDomicilios.getUsuariosCollection();
            Collection<Usuarios> usuariosCollectionNew = domicilios.getUsuariosCollection();
            List<String> illegalOrphanMessages = null;
            for (Usuarios usuariosCollectionOldUsuarios : usuariosCollectionOld) {
                if (!usuariosCollectionNew.contains(usuariosCollectionOldUsuarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuarios " + usuariosCollectionOldUsuarios + " since its domicilio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Usuarios> attachedUsuariosCollectionNew = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionNewUsuariosToAttach : usuariosCollectionNew) {
                usuariosCollectionNewUsuariosToAttach = em.getReference(usuariosCollectionNewUsuariosToAttach.getClass(), usuariosCollectionNewUsuariosToAttach.getId());
                attachedUsuariosCollectionNew.add(usuariosCollectionNewUsuariosToAttach);
            }
            usuariosCollectionNew = attachedUsuariosCollectionNew;
            domicilios.setUsuariosCollection(usuariosCollectionNew);
            domicilios = em.merge(domicilios);
            for (Usuarios usuariosCollectionNewUsuarios : usuariosCollectionNew) {
                if (!usuariosCollectionOld.contains(usuariosCollectionNewUsuarios)) {
                    Domicilios oldDomicilioOfUsuariosCollectionNewUsuarios = usuariosCollectionNewUsuarios.getDomicilio();
                    usuariosCollectionNewUsuarios.setDomicilio(domicilios);
                    usuariosCollectionNewUsuarios = em.merge(usuariosCollectionNewUsuarios);
                    if (oldDomicilioOfUsuariosCollectionNewUsuarios != null && !oldDomicilioOfUsuariosCollectionNewUsuarios.equals(domicilios)) {
                        oldDomicilioOfUsuariosCollectionNewUsuarios.getUsuariosCollection().remove(usuariosCollectionNewUsuarios);
                        oldDomicilioOfUsuariosCollectionNewUsuarios = em.merge(oldDomicilioOfUsuariosCollectionNewUsuarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = domicilios.getId();
                if (findDomicilios(id) == null) {
                    throw new NonexistentEntityException("The domicilios with id " + id + " no longer exists.");
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
            Domicilios domicilios;
            try {
                domicilios = em.getReference(Domicilios.class, id);
                domicilios.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The domicilios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Usuarios> usuariosCollectionOrphanCheck = domicilios.getUsuariosCollection();
            for (Usuarios usuariosCollectionOrphanCheckUsuarios : usuariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Domicilios (" + domicilios + ") cannot be destroyed since the Usuarios " + usuariosCollectionOrphanCheckUsuarios + " in its usuariosCollection field has a non-nullable domicilio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(domicilios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Domicilios> findDomiciliosEntities() {
        return findDomiciliosEntities(true, -1, -1);
    }

    public List<Domicilios> findDomiciliosEntities(int maxResults, int firstResult) {
        return findDomiciliosEntities(false, maxResults, firstResult);
    }

    private List<Domicilios> findDomiciliosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Domicilios.class));
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

    public Domicilios findDomicilios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Domicilios.class, id);
        } finally {
            em.close();
        }
    }

    public int getDomiciliosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Domicilios> rt = cq.from(Domicilios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
