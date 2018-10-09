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
import clases.Categorias;
import clases.Domicilios;
import clases.UsuarioDispositivos;
import java.util.ArrayList;
import java.util.Collection;
import clases.UsuarioRoles;
import clases.Usuarios;
import controllers.exceptions.IllegalOrphanException;
import controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jean Pierre
 */
public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarios usuarios) {
        if (usuarios.getUsuarioDispositivosCollection() == null) {
            usuarios.setUsuarioDispositivosCollection(new ArrayList<UsuarioDispositivos>());
        }
        if (usuarios.getUsuarioRolesCollection() == null) {
            usuarios.setUsuarioRolesCollection(new ArrayList<UsuarioRoles>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categorias categoria = usuarios.getCategoria();
            if (categoria != null) {
                categoria = em.getReference(categoria.getClass(), categoria.getId());
                usuarios.setCategoria(categoria);
            }
            Domicilios domicilio = usuarios.getDomicilio();
            if (domicilio != null) {
                domicilio = em.getReference(domicilio.getClass(), domicilio.getId());
                usuarios.setDomicilio(domicilio);
            }
            Collection<UsuarioDispositivos> attachedUsuarioDispositivosCollection = new ArrayList<UsuarioDispositivos>();
            for (UsuarioDispositivos usuarioDispositivosCollectionUsuarioDispositivosToAttach : usuarios.getUsuarioDispositivosCollection()) {
                usuarioDispositivosCollectionUsuarioDispositivosToAttach = em.getReference(usuarioDispositivosCollectionUsuarioDispositivosToAttach.getClass(), usuarioDispositivosCollectionUsuarioDispositivosToAttach.getId());
                attachedUsuarioDispositivosCollection.add(usuarioDispositivosCollectionUsuarioDispositivosToAttach);
            }
            usuarios.setUsuarioDispositivosCollection(attachedUsuarioDispositivosCollection);
            Collection<UsuarioRoles> attachedUsuarioRolesCollection = new ArrayList<UsuarioRoles>();
            for (UsuarioRoles usuarioRolesCollectionUsuarioRolesToAttach : usuarios.getUsuarioRolesCollection()) {
                usuarioRolesCollectionUsuarioRolesToAttach = em.getReference(usuarioRolesCollectionUsuarioRolesToAttach.getClass(), usuarioRolesCollectionUsuarioRolesToAttach.getId());
                attachedUsuarioRolesCollection.add(usuarioRolesCollectionUsuarioRolesToAttach);
            }
            usuarios.setUsuarioRolesCollection(attachedUsuarioRolesCollection);
            em.persist(usuarios);
            if (categoria != null) {
                categoria.getUsuariosCollection().add(usuarios);
                categoria = em.merge(categoria);
            }
            if (domicilio != null) {
                domicilio.getUsuariosCollection().add(usuarios);
                domicilio = em.merge(domicilio);
            }
            for (UsuarioDispositivos usuarioDispositivosCollectionUsuarioDispositivos : usuarios.getUsuarioDispositivosCollection()) {
                Usuarios oldUsuarioOfUsuarioDispositivosCollectionUsuarioDispositivos = usuarioDispositivosCollectionUsuarioDispositivos.getUsuario();
                usuarioDispositivosCollectionUsuarioDispositivos.setUsuario(usuarios);
                usuarioDispositivosCollectionUsuarioDispositivos = em.merge(usuarioDispositivosCollectionUsuarioDispositivos);
                if (oldUsuarioOfUsuarioDispositivosCollectionUsuarioDispositivos != null) {
                    oldUsuarioOfUsuarioDispositivosCollectionUsuarioDispositivos.getUsuarioDispositivosCollection().remove(usuarioDispositivosCollectionUsuarioDispositivos);
                    oldUsuarioOfUsuarioDispositivosCollectionUsuarioDispositivos = em.merge(oldUsuarioOfUsuarioDispositivosCollectionUsuarioDispositivos);
                }
            }
            for (UsuarioRoles usuarioRolesCollectionUsuarioRoles : usuarios.getUsuarioRolesCollection()) {
                Usuarios oldUsuarioOfUsuarioRolesCollectionUsuarioRoles = usuarioRolesCollectionUsuarioRoles.getUsuario();
                usuarioRolesCollectionUsuarioRoles.setUsuario(usuarios);
                usuarioRolesCollectionUsuarioRoles = em.merge(usuarioRolesCollectionUsuarioRoles);
                if (oldUsuarioOfUsuarioRolesCollectionUsuarioRoles != null) {
                    oldUsuarioOfUsuarioRolesCollectionUsuarioRoles.getUsuarioRolesCollection().remove(usuarioRolesCollectionUsuarioRoles);
                    oldUsuarioOfUsuarioRolesCollectionUsuarioRoles = em.merge(oldUsuarioOfUsuarioRolesCollectionUsuarioRoles);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getId());
            Categorias categoriaOld = persistentUsuarios.getCategoria();
            Categorias categoriaNew = usuarios.getCategoria();
            Domicilios domicilioOld = persistentUsuarios.getDomicilio();
            Domicilios domicilioNew = usuarios.getDomicilio();
            Collection<UsuarioDispositivos> usuarioDispositivosCollectionOld = persistentUsuarios.getUsuarioDispositivosCollection();
            Collection<UsuarioDispositivos> usuarioDispositivosCollectionNew = usuarios.getUsuarioDispositivosCollection();
            Collection<UsuarioRoles> usuarioRolesCollectionOld = persistentUsuarios.getUsuarioRolesCollection();
            Collection<UsuarioRoles> usuarioRolesCollectionNew = usuarios.getUsuarioRolesCollection();
            List<String> illegalOrphanMessages = null;
            for (UsuarioDispositivos usuarioDispositivosCollectionOldUsuarioDispositivos : usuarioDispositivosCollectionOld) {
                if (!usuarioDispositivosCollectionNew.contains(usuarioDispositivosCollectionOldUsuarioDispositivos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UsuarioDispositivos " + usuarioDispositivosCollectionOldUsuarioDispositivos + " since its usuario field is not nullable.");
                }
            }
            for (UsuarioRoles usuarioRolesCollectionOldUsuarioRoles : usuarioRolesCollectionOld) {
                if (!usuarioRolesCollectionNew.contains(usuarioRolesCollectionOldUsuarioRoles)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UsuarioRoles " + usuarioRolesCollectionOldUsuarioRoles + " since its usuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (categoriaNew != null) {
                categoriaNew = em.getReference(categoriaNew.getClass(), categoriaNew.getId());
                usuarios.setCategoria(categoriaNew);
            }
            if (domicilioNew != null) {
                domicilioNew = em.getReference(domicilioNew.getClass(), domicilioNew.getId());
                usuarios.setDomicilio(domicilioNew);
            }
            Collection<UsuarioDispositivos> attachedUsuarioDispositivosCollectionNew = new ArrayList<UsuarioDispositivos>();
            for (UsuarioDispositivos usuarioDispositivosCollectionNewUsuarioDispositivosToAttach : usuarioDispositivosCollectionNew) {
                usuarioDispositivosCollectionNewUsuarioDispositivosToAttach = em.getReference(usuarioDispositivosCollectionNewUsuarioDispositivosToAttach.getClass(), usuarioDispositivosCollectionNewUsuarioDispositivosToAttach.getId());
                attachedUsuarioDispositivosCollectionNew.add(usuarioDispositivosCollectionNewUsuarioDispositivosToAttach);
            }
            usuarioDispositivosCollectionNew = attachedUsuarioDispositivosCollectionNew;
            usuarios.setUsuarioDispositivosCollection(usuarioDispositivosCollectionNew);
            Collection<UsuarioRoles> attachedUsuarioRolesCollectionNew = new ArrayList<UsuarioRoles>();
            for (UsuarioRoles usuarioRolesCollectionNewUsuarioRolesToAttach : usuarioRolesCollectionNew) {
                usuarioRolesCollectionNewUsuarioRolesToAttach = em.getReference(usuarioRolesCollectionNewUsuarioRolesToAttach.getClass(), usuarioRolesCollectionNewUsuarioRolesToAttach.getId());
                attachedUsuarioRolesCollectionNew.add(usuarioRolesCollectionNewUsuarioRolesToAttach);
            }
            usuarioRolesCollectionNew = attachedUsuarioRolesCollectionNew;
            usuarios.setUsuarioRolesCollection(usuarioRolesCollectionNew);
            usuarios = em.merge(usuarios);
            if (categoriaOld != null && !categoriaOld.equals(categoriaNew)) {
                categoriaOld.getUsuariosCollection().remove(usuarios);
                categoriaOld = em.merge(categoriaOld);
            }
            if (categoriaNew != null && !categoriaNew.equals(categoriaOld)) {
                categoriaNew.getUsuariosCollection().add(usuarios);
                categoriaNew = em.merge(categoriaNew);
            }
            if (domicilioOld != null && !domicilioOld.equals(domicilioNew)) {
                domicilioOld.getUsuariosCollection().remove(usuarios);
                domicilioOld = em.merge(domicilioOld);
            }
            if (domicilioNew != null && !domicilioNew.equals(domicilioOld)) {
                domicilioNew.getUsuariosCollection().add(usuarios);
                domicilioNew = em.merge(domicilioNew);
            }
            for (UsuarioDispositivos usuarioDispositivosCollectionNewUsuarioDispositivos : usuarioDispositivosCollectionNew) {
                if (!usuarioDispositivosCollectionOld.contains(usuarioDispositivosCollectionNewUsuarioDispositivos)) {
                    Usuarios oldUsuarioOfUsuarioDispositivosCollectionNewUsuarioDispositivos = usuarioDispositivosCollectionNewUsuarioDispositivos.getUsuario();
                    usuarioDispositivosCollectionNewUsuarioDispositivos.setUsuario(usuarios);
                    usuarioDispositivosCollectionNewUsuarioDispositivos = em.merge(usuarioDispositivosCollectionNewUsuarioDispositivos);
                    if (oldUsuarioOfUsuarioDispositivosCollectionNewUsuarioDispositivos != null && !oldUsuarioOfUsuarioDispositivosCollectionNewUsuarioDispositivos.equals(usuarios)) {
                        oldUsuarioOfUsuarioDispositivosCollectionNewUsuarioDispositivos.getUsuarioDispositivosCollection().remove(usuarioDispositivosCollectionNewUsuarioDispositivos);
                        oldUsuarioOfUsuarioDispositivosCollectionNewUsuarioDispositivos = em.merge(oldUsuarioOfUsuarioDispositivosCollectionNewUsuarioDispositivos);
                    }
                }
            }
            for (UsuarioRoles usuarioRolesCollectionNewUsuarioRoles : usuarioRolesCollectionNew) {
                if (!usuarioRolesCollectionOld.contains(usuarioRolesCollectionNewUsuarioRoles)) {
                    Usuarios oldUsuarioOfUsuarioRolesCollectionNewUsuarioRoles = usuarioRolesCollectionNewUsuarioRoles.getUsuario();
                    usuarioRolesCollectionNewUsuarioRoles.setUsuario(usuarios);
                    usuarioRolesCollectionNewUsuarioRoles = em.merge(usuarioRolesCollectionNewUsuarioRoles);
                    if (oldUsuarioOfUsuarioRolesCollectionNewUsuarioRoles != null && !oldUsuarioOfUsuarioRolesCollectionNewUsuarioRoles.equals(usuarios)) {
                        oldUsuarioOfUsuarioRolesCollectionNewUsuarioRoles.getUsuarioRolesCollection().remove(usuarioRolesCollectionNewUsuarioRoles);
                        oldUsuarioOfUsuarioRolesCollectionNewUsuarioRoles = em.merge(oldUsuarioOfUsuarioRolesCollectionNewUsuarioRoles);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuarios.getId();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
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
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<UsuarioDispositivos> usuarioDispositivosCollectionOrphanCheck = usuarios.getUsuarioDispositivosCollection();
            for (UsuarioDispositivos usuarioDispositivosCollectionOrphanCheckUsuarioDispositivos : usuarioDispositivosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the UsuarioDispositivos " + usuarioDispositivosCollectionOrphanCheckUsuarioDispositivos + " in its usuarioDispositivosCollection field has a non-nullable usuario field.");
            }
            Collection<UsuarioRoles> usuarioRolesCollectionOrphanCheck = usuarios.getUsuarioRolesCollection();
            for (UsuarioRoles usuarioRolesCollectionOrphanCheckUsuarioRoles : usuarioRolesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the UsuarioRoles " + usuarioRolesCollectionOrphanCheckUsuarioRoles + " in its usuarioRolesCollection field has a non-nullable usuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Categorias categoria = usuarios.getCategoria();
            if (categoria != null) {
                categoria.getUsuariosCollection().remove(usuarios);
                categoria = em.merge(categoria);
            }
            Domicilios domicilio = usuarios.getDomicilio();
            if (domicilio != null) {
                domicilio.getUsuariosCollection().remove(usuarios);
                domicilio = em.merge(domicilio);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
