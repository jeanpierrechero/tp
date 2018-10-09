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
import clases.Estados;
import clases.EstadosDispositivos;
import clases.UsuarioDispositivos;
import controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jean Pierre
 */
public class EstadosDispositivosJpaController implements Serializable {

    public EstadosDispositivosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadosDispositivos estadosDispositivos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estados estado = estadosDispositivos.getEstado();
            if (estado != null) {
                estado = em.getReference(estado.getClass(), estado.getId());
                estadosDispositivos.setEstado(estado);
            }
            UsuarioDispositivos usuarioDispositivo = estadosDispositivos.getUsuarioDispositivo();
            if (usuarioDispositivo != null) {
                usuarioDispositivo = em.getReference(usuarioDispositivo.getClass(), usuarioDispositivo.getId());
                estadosDispositivos.setUsuarioDispositivo(usuarioDispositivo);
            }
            em.persist(estadosDispositivos);
            if (estado != null) {
                estado.getEstadosDispositivosCollection().add(estadosDispositivos);
                estado = em.merge(estado);
            }
            if (usuarioDispositivo != null) {
                usuarioDispositivo.getEstadosDispositivosCollection().add(estadosDispositivos);
                usuarioDispositivo = em.merge(usuarioDispositivo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadosDispositivos estadosDispositivos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadosDispositivos persistentEstadosDispositivos = em.find(EstadosDispositivos.class, estadosDispositivos.getId());
            Estados estadoOld = persistentEstadosDispositivos.getEstado();
            Estados estadoNew = estadosDispositivos.getEstado();
            UsuarioDispositivos usuarioDispositivoOld = persistentEstadosDispositivos.getUsuarioDispositivo();
            UsuarioDispositivos usuarioDispositivoNew = estadosDispositivos.getUsuarioDispositivo();
            if (estadoNew != null) {
                estadoNew = em.getReference(estadoNew.getClass(), estadoNew.getId());
                estadosDispositivos.setEstado(estadoNew);
            }
            if (usuarioDispositivoNew != null) {
                usuarioDispositivoNew = em.getReference(usuarioDispositivoNew.getClass(), usuarioDispositivoNew.getId());
                estadosDispositivos.setUsuarioDispositivo(usuarioDispositivoNew);
            }
            estadosDispositivos = em.merge(estadosDispositivos);
            if (estadoOld != null && !estadoOld.equals(estadoNew)) {
                estadoOld.getEstadosDispositivosCollection().remove(estadosDispositivos);
                estadoOld = em.merge(estadoOld);
            }
            if (estadoNew != null && !estadoNew.equals(estadoOld)) {
                estadoNew.getEstadosDispositivosCollection().add(estadosDispositivos);
                estadoNew = em.merge(estadoNew);
            }
            if (usuarioDispositivoOld != null && !usuarioDispositivoOld.equals(usuarioDispositivoNew)) {
                usuarioDispositivoOld.getEstadosDispositivosCollection().remove(estadosDispositivos);
                usuarioDispositivoOld = em.merge(usuarioDispositivoOld);
            }
            if (usuarioDispositivoNew != null && !usuarioDispositivoNew.equals(usuarioDispositivoOld)) {
                usuarioDispositivoNew.getEstadosDispositivosCollection().add(estadosDispositivos);
                usuarioDispositivoNew = em.merge(usuarioDispositivoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estadosDispositivos.getId();
                if (findEstadosDispositivos(id) == null) {
                    throw new NonexistentEntityException("The estadosDispositivos with id " + id + " no longer exists.");
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
            EstadosDispositivos estadosDispositivos;
            try {
                estadosDispositivos = em.getReference(EstadosDispositivos.class, id);
                estadosDispositivos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadosDispositivos with id " + id + " no longer exists.", enfe);
            }
            Estados estado = estadosDispositivos.getEstado();
            if (estado != null) {
                estado.getEstadosDispositivosCollection().remove(estadosDispositivos);
                estado = em.merge(estado);
            }
            UsuarioDispositivos usuarioDispositivo = estadosDispositivos.getUsuarioDispositivo();
            if (usuarioDispositivo != null) {
                usuarioDispositivo.getEstadosDispositivosCollection().remove(estadosDispositivos);
                usuarioDispositivo = em.merge(usuarioDispositivo);
            }
            em.remove(estadosDispositivos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadosDispositivos> findEstadosDispositivosEntities() {
        return findEstadosDispositivosEntities(true, -1, -1);
    }

    public List<EstadosDispositivos> findEstadosDispositivosEntities(int maxResults, int firstResult) {
        return findEstadosDispositivosEntities(false, maxResults, firstResult);
    }

    private List<EstadosDispositivos> findEstadosDispositivosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadosDispositivos.class));
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

    public EstadosDispositivos findEstadosDispositivos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadosDispositivos.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadosDispositivosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadosDispositivos> rt = cq.from(EstadosDispositivos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
