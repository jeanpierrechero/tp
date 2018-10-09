/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import clases.Roles;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import clases.UsuarioRoles;
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
public class RolesJpaController implements Serializable {

    public RolesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Roles roles) {
        if (roles.getUsuarioRolesCollection() == null) {
            roles.setUsuarioRolesCollection(new ArrayList<UsuarioRoles>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<UsuarioRoles> attachedUsuarioRolesCollection = new ArrayList<UsuarioRoles>();
            for (UsuarioRoles usuarioRolesCollectionUsuarioRolesToAttach : roles.getUsuarioRolesCollection()) {
                usuarioRolesCollectionUsuarioRolesToAttach = em.getReference(usuarioRolesCollectionUsuarioRolesToAttach.getClass(), usuarioRolesCollectionUsuarioRolesToAttach.getId());
                attachedUsuarioRolesCollection.add(usuarioRolesCollectionUsuarioRolesToAttach);
            }
            roles.setUsuarioRolesCollection(attachedUsuarioRolesCollection);
            em.persist(roles);
            for (UsuarioRoles usuarioRolesCollectionUsuarioRoles : roles.getUsuarioRolesCollection()) {
                Roles oldRolOfUsuarioRolesCollectionUsuarioRoles = usuarioRolesCollectionUsuarioRoles.getRol();
                usuarioRolesCollectionUsuarioRoles.setRol(roles);
                usuarioRolesCollectionUsuarioRoles = em.merge(usuarioRolesCollectionUsuarioRoles);
                if (oldRolOfUsuarioRolesCollectionUsuarioRoles != null) {
                    oldRolOfUsuarioRolesCollectionUsuarioRoles.getUsuarioRolesCollection().remove(usuarioRolesCollectionUsuarioRoles);
                    oldRolOfUsuarioRolesCollectionUsuarioRoles = em.merge(oldRolOfUsuarioRolesCollectionUsuarioRoles);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Roles roles) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Roles persistentRoles = em.find(Roles.class, roles.getId());
            Collection<UsuarioRoles> usuarioRolesCollectionOld = persistentRoles.getUsuarioRolesCollection();
            Collection<UsuarioRoles> usuarioRolesCollectionNew = roles.getUsuarioRolesCollection();
            List<String> illegalOrphanMessages = null;
            for (UsuarioRoles usuarioRolesCollectionOldUsuarioRoles : usuarioRolesCollectionOld) {
                if (!usuarioRolesCollectionNew.contains(usuarioRolesCollectionOldUsuarioRoles)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UsuarioRoles " + usuarioRolesCollectionOldUsuarioRoles + " since its rol field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<UsuarioRoles> attachedUsuarioRolesCollectionNew = new ArrayList<UsuarioRoles>();
            for (UsuarioRoles usuarioRolesCollectionNewUsuarioRolesToAttach : usuarioRolesCollectionNew) {
                usuarioRolesCollectionNewUsuarioRolesToAttach = em.getReference(usuarioRolesCollectionNewUsuarioRolesToAttach.getClass(), usuarioRolesCollectionNewUsuarioRolesToAttach.getId());
                attachedUsuarioRolesCollectionNew.add(usuarioRolesCollectionNewUsuarioRolesToAttach);
            }
            usuarioRolesCollectionNew = attachedUsuarioRolesCollectionNew;
            roles.setUsuarioRolesCollection(usuarioRolesCollectionNew);
            roles = em.merge(roles);
            for (UsuarioRoles usuarioRolesCollectionNewUsuarioRoles : usuarioRolesCollectionNew) {
                if (!usuarioRolesCollectionOld.contains(usuarioRolesCollectionNewUsuarioRoles)) {
                    Roles oldRolOfUsuarioRolesCollectionNewUsuarioRoles = usuarioRolesCollectionNewUsuarioRoles.getRol();
                    usuarioRolesCollectionNewUsuarioRoles.setRol(roles);
                    usuarioRolesCollectionNewUsuarioRoles = em.merge(usuarioRolesCollectionNewUsuarioRoles);
                    if (oldRolOfUsuarioRolesCollectionNewUsuarioRoles != null && !oldRolOfUsuarioRolesCollectionNewUsuarioRoles.equals(roles)) {
                        oldRolOfUsuarioRolesCollectionNewUsuarioRoles.getUsuarioRolesCollection().remove(usuarioRolesCollectionNewUsuarioRoles);
                        oldRolOfUsuarioRolesCollectionNewUsuarioRoles = em.merge(oldRolOfUsuarioRolesCollectionNewUsuarioRoles);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = roles.getId();
                if (findRoles(id) == null) {
                    throw new NonexistentEntityException("The roles with id " + id + " no longer exists.");
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
            Roles roles;
            try {
                roles = em.getReference(Roles.class, id);
                roles.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The roles with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<UsuarioRoles> usuarioRolesCollectionOrphanCheck = roles.getUsuarioRolesCollection();
            for (UsuarioRoles usuarioRolesCollectionOrphanCheckUsuarioRoles : usuarioRolesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Roles (" + roles + ") cannot be destroyed since the UsuarioRoles " + usuarioRolesCollectionOrphanCheckUsuarioRoles + " in its usuarioRolesCollection field has a non-nullable rol field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(roles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Roles> findRolesEntities() {
        return findRolesEntities(true, -1, -1);
    }

    public List<Roles> findRolesEntities(int maxResults, int firstResult) {
        return findRolesEntities(false, maxResults, firstResult);
    }

    private List<Roles> findRolesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Roles.class));
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

    public Roles findRoles(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Roles.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Roles> rt = cq.from(Roles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
