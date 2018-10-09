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
import clases.Roles;
import clases.UsuarioRoles;
import clases.Usuarios;
import controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jean Pierre
 */
public class UsuarioRolesJpaController implements Serializable {

    public UsuarioRolesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsuarioRoles usuarioRoles) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Roles rol = usuarioRoles.getRol();
            if (rol != null) {
                rol = em.getReference(rol.getClass(), rol.getId());
                usuarioRoles.setRol(rol);
            }
            Usuarios usuario = usuarioRoles.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getId());
                usuarioRoles.setUsuario(usuario);
            }
            em.persist(usuarioRoles);
            if (rol != null) {
                rol.getUsuarioRolesCollection().add(usuarioRoles);
                rol = em.merge(rol);
            }
            if (usuario != null) {
                usuario.getUsuarioRolesCollection().add(usuarioRoles);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsuarioRoles usuarioRoles) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UsuarioRoles persistentUsuarioRoles = em.find(UsuarioRoles.class, usuarioRoles.getId());
            Roles rolOld = persistentUsuarioRoles.getRol();
            Roles rolNew = usuarioRoles.getRol();
            Usuarios usuarioOld = persistentUsuarioRoles.getUsuario();
            Usuarios usuarioNew = usuarioRoles.getUsuario();
            if (rolNew != null) {
                rolNew = em.getReference(rolNew.getClass(), rolNew.getId());
                usuarioRoles.setRol(rolNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getId());
                usuarioRoles.setUsuario(usuarioNew);
            }
            usuarioRoles = em.merge(usuarioRoles);
            if (rolOld != null && !rolOld.equals(rolNew)) {
                rolOld.getUsuarioRolesCollection().remove(usuarioRoles);
                rolOld = em.merge(rolOld);
            }
            if (rolNew != null && !rolNew.equals(rolOld)) {
                rolNew.getUsuarioRolesCollection().add(usuarioRoles);
                rolNew = em.merge(rolNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getUsuarioRolesCollection().remove(usuarioRoles);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getUsuarioRolesCollection().add(usuarioRoles);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuarioRoles.getId();
                if (findUsuarioRoles(id) == null) {
                    throw new NonexistentEntityException("The usuarioRoles with id " + id + " no longer exists.");
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
            UsuarioRoles usuarioRoles;
            try {
                usuarioRoles = em.getReference(UsuarioRoles.class, id);
                usuarioRoles.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarioRoles with id " + id + " no longer exists.", enfe);
            }
            Roles rol = usuarioRoles.getRol();
            if (rol != null) {
                rol.getUsuarioRolesCollection().remove(usuarioRoles);
                rol = em.merge(rol);
            }
            Usuarios usuario = usuarioRoles.getUsuario();
            if (usuario != null) {
                usuario.getUsuarioRolesCollection().remove(usuarioRoles);
                usuario = em.merge(usuario);
            }
            em.remove(usuarioRoles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UsuarioRoles> findUsuarioRolesEntities() {
        return findUsuarioRolesEntities(true, -1, -1);
    }

    public List<UsuarioRoles> findUsuarioRolesEntities(int maxResults, int firstResult) {
        return findUsuarioRolesEntities(false, maxResults, firstResult);
    }

    private List<UsuarioRoles> findUsuarioRolesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsuarioRoles.class));
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

    public UsuarioRoles findUsuarioRoles(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsuarioRoles.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioRolesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsuarioRoles> rt = cq.from(UsuarioRoles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
