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

import clases.ZonaTransformadores;
import ar.com.sge.geografia.Transformadores;
import ar.com.sge.geografia.Zonas;
import controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jean Pierre
 */
public class ZonaTransformadoresJpaController implements Serializable {

    public ZonaTransformadoresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ZonaTransformadores zonaTransformadores) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transformadores transformador = zonaTransformadores.getTransformador();
            if (transformador != null) {
                transformador = em.getReference(transformador.getClass(), transformador.getId());
                zonaTransformadores.setTransformador(transformador);
            }
            Zonas zona = zonaTransformadores.getZona();
            if (zona != null) {
                zona = em.getReference(zona.getClass(), zona.getId());
                zonaTransformadores.setZona(zona);
            }
            em.persist(zonaTransformadores);
            if (transformador != null) {
                transformador.getZonaTransformadoresCollection().add(zonaTransformadores);
                transformador = em.merge(transformador);
            }
            if (zona != null) {
                zona.getZonaTransformadoresCollection().add(zonaTransformadores);
                zona = em.merge(zona);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ZonaTransformadores zonaTransformadores) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ZonaTransformadores persistentZonaTransformadores = em.find(ZonaTransformadores.class, zonaTransformadores.getId());
            Transformadores transformadorOld = persistentZonaTransformadores.getTransformador();
            Transformadores transformadorNew = zonaTransformadores.getTransformador();
            Zonas zonaOld = persistentZonaTransformadores.getZona();
            Zonas zonaNew = zonaTransformadores.getZona();
            if (transformadorNew != null) {
                transformadorNew = em.getReference(transformadorNew.getClass(), transformadorNew.getId());
                zonaTransformadores.setTransformador(transformadorNew);
            }
            if (zonaNew != null) {
                zonaNew = em.getReference(zonaNew.getClass(), zonaNew.getId());
                zonaTransformadores.setZona(zonaNew);
            }
            zonaTransformadores = em.merge(zonaTransformadores);
            if (transformadorOld != null && !transformadorOld.equals(transformadorNew)) {
                transformadorOld.getZonaTransformadoresCollection().remove(zonaTransformadores);
                transformadorOld = em.merge(transformadorOld);
            }
            if (transformadorNew != null && !transformadorNew.equals(transformadorOld)) {
                transformadorNew.getZonaTransformadoresCollection().add(zonaTransformadores);
                transformadorNew = em.merge(transformadorNew);
            }
            if (zonaOld != null && !zonaOld.equals(zonaNew)) {
                zonaOld.getZonaTransformadoresCollection().remove(zonaTransformadores);
                zonaOld = em.merge(zonaOld);
            }
            if (zonaNew != null && !zonaNew.equals(zonaOld)) {
                zonaNew.getZonaTransformadoresCollection().add(zonaTransformadores);
                zonaNew = em.merge(zonaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = zonaTransformadores.getId();
                if (findZonaTransformadores(id) == null) {
                    throw new NonexistentEntityException("The zonaTransformadores with id " + id + " no longer exists.");
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
            ZonaTransformadores zonaTransformadores;
            try {
                zonaTransformadores = em.getReference(ZonaTransformadores.class, id);
                zonaTransformadores.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The zonaTransformadores with id " + id + " no longer exists.", enfe);
            }
            Transformadores transformador = zonaTransformadores.getTransformador();
            if (transformador != null) {
                transformador.getZonaTransformadoresCollection().remove(zonaTransformadores);
                transformador = em.merge(transformador);
            }
            Zonas zona = zonaTransformadores.getZona();
            if (zona != null) {
                zona.getZonaTransformadoresCollection().remove(zonaTransformadores);
                zona = em.merge(zona);
            }
            em.remove(zonaTransformadores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ZonaTransformadores> findZonaTransformadoresEntities() {
        return findZonaTransformadoresEntities(true, -1, -1);
    }

    public List<ZonaTransformadores> findZonaTransformadoresEntities(int maxResults, int firstResult) {
        return findZonaTransformadoresEntities(false, maxResults, firstResult);
    }

    private List<ZonaTransformadores> findZonaTransformadoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ZonaTransformadores.class));
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

    public ZonaTransformadores findZonaTransformadores(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ZonaTransformadores.class, id);
        } finally {
            em.close();
        }
    }

    public int getZonaTransformadoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ZonaTransformadores> rt = cq.from(ZonaTransformadores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
