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
import clases.Adaptadores;
import clases.Comandos;
import clases.ComandosAdaptadores;
import controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jean Pierre
 */
public class ComandosAdaptadoresJpaController implements Serializable {

    public ComandosAdaptadoresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ComandosAdaptadores comandosAdaptadores) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Adaptadores adaptador = comandosAdaptadores.getAdaptador();
            if (adaptador != null) {
                adaptador = em.getReference(adaptador.getClass(), adaptador.getId());
                comandosAdaptadores.setAdaptador(adaptador);
            }
            Comandos comando = comandosAdaptadores.getComando();
            if (comando != null) {
                comando = em.getReference(comando.getClass(), comando.getId());
                comandosAdaptadores.setComando(comando);
            }
            em.persist(comandosAdaptadores);
            if (adaptador != null) {
                adaptador.getComandosAdaptadoresCollection().add(comandosAdaptadores);
                adaptador = em.merge(adaptador);
            }
            if (comando != null) {
                comando.getComandosAdaptadoresCollection().add(comandosAdaptadores);
                comando = em.merge(comando);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ComandosAdaptadores comandosAdaptadores) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ComandosAdaptadores persistentComandosAdaptadores = em.find(ComandosAdaptadores.class, comandosAdaptadores.getId());
            Adaptadores adaptadorOld = persistentComandosAdaptadores.getAdaptador();
            Adaptadores adaptadorNew = comandosAdaptadores.getAdaptador();
            Comandos comandoOld = persistentComandosAdaptadores.getComando();
            Comandos comandoNew = comandosAdaptadores.getComando();
            if (adaptadorNew != null) {
                adaptadorNew = em.getReference(adaptadorNew.getClass(), adaptadorNew.getId());
                comandosAdaptadores.setAdaptador(adaptadorNew);
            }
            if (comandoNew != null) {
                comandoNew = em.getReference(comandoNew.getClass(), comandoNew.getId());
                comandosAdaptadores.setComando(comandoNew);
            }
            comandosAdaptadores = em.merge(comandosAdaptadores);
            if (adaptadorOld != null && !adaptadorOld.equals(adaptadorNew)) {
                adaptadorOld.getComandosAdaptadoresCollection().remove(comandosAdaptadores);
                adaptadorOld = em.merge(adaptadorOld);
            }
            if (adaptadorNew != null && !adaptadorNew.equals(adaptadorOld)) {
                adaptadorNew.getComandosAdaptadoresCollection().add(comandosAdaptadores);
                adaptadorNew = em.merge(adaptadorNew);
            }
            if (comandoOld != null && !comandoOld.equals(comandoNew)) {
                comandoOld.getComandosAdaptadoresCollection().remove(comandosAdaptadores);
                comandoOld = em.merge(comandoOld);
            }
            if (comandoNew != null && !comandoNew.equals(comandoOld)) {
                comandoNew.getComandosAdaptadoresCollection().add(comandosAdaptadores);
                comandoNew = em.merge(comandoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comandosAdaptadores.getId();
                if (findComandosAdaptadores(id) == null) {
                    throw new NonexistentEntityException("The comandosAdaptadores with id " + id + " no longer exists.");
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
            ComandosAdaptadores comandosAdaptadores;
            try {
                comandosAdaptadores = em.getReference(ComandosAdaptadores.class, id);
                comandosAdaptadores.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comandosAdaptadores with id " + id + " no longer exists.", enfe);
            }
            Adaptadores adaptador = comandosAdaptadores.getAdaptador();
            if (adaptador != null) {
                adaptador.getComandosAdaptadoresCollection().remove(comandosAdaptadores);
                adaptador = em.merge(adaptador);
            }
            Comandos comando = comandosAdaptadores.getComando();
            if (comando != null) {
                comando.getComandosAdaptadoresCollection().remove(comandosAdaptadores);
                comando = em.merge(comando);
            }
            em.remove(comandosAdaptadores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ComandosAdaptadores> findComandosAdaptadoresEntities() {
        return findComandosAdaptadoresEntities(true, -1, -1);
    }

    public List<ComandosAdaptadores> findComandosAdaptadoresEntities(int maxResults, int firstResult) {
        return findComandosAdaptadoresEntities(false, maxResults, firstResult);
    }

    private List<ComandosAdaptadores> findComandosAdaptadoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ComandosAdaptadores.class));
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

    public ComandosAdaptadores findComandosAdaptadores(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ComandosAdaptadores.class, id);
        } finally {
            em.close();
        }
    }

    public int getComandosAdaptadoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ComandosAdaptadores> rt = cq.from(ComandosAdaptadores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
