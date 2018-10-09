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
import clases.Actuadores;
import clases.ActuadoresComandos;
import clases.Comandos;
import controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jean Pierre
 */
public class ActuadoresComandosJpaController implements Serializable {

    public ActuadoresComandosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ActuadoresComandos actuadoresComandos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Actuadores actuador = actuadoresComandos.getActuador();
            if (actuador != null) {
                actuador = em.getReference(actuador.getClass(), actuador.getId());
                actuadoresComandos.setActuador(actuador);
            }
            Comandos comando = actuadoresComandos.getComando();
            if (comando != null) {
                comando = em.getReference(comando.getClass(), comando.getId());
                actuadoresComandos.setComando(comando);
            }
            em.persist(actuadoresComandos);
            if (actuador != null) {
                actuador.getActuadoresComandosCollection().add(actuadoresComandos);
                actuador = em.merge(actuador);
            }
            if (comando != null) {
                comando.getActuadoresComandosCollection().add(actuadoresComandos);
                comando = em.merge(comando);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ActuadoresComandos actuadoresComandos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ActuadoresComandos persistentActuadoresComandos = em.find(ActuadoresComandos.class, actuadoresComandos.getId());
            Actuadores actuadorOld = persistentActuadoresComandos.getActuador();
            Actuadores actuadorNew = actuadoresComandos.getActuador();
            Comandos comandoOld = persistentActuadoresComandos.getComando();
            Comandos comandoNew = actuadoresComandos.getComando();
            if (actuadorNew != null) {
                actuadorNew = em.getReference(actuadorNew.getClass(), actuadorNew.getId());
                actuadoresComandos.setActuador(actuadorNew);
            }
            if (comandoNew != null) {
                comandoNew = em.getReference(comandoNew.getClass(), comandoNew.getId());
                actuadoresComandos.setComando(comandoNew);
            }
            actuadoresComandos = em.merge(actuadoresComandos);
            if (actuadorOld != null && !actuadorOld.equals(actuadorNew)) {
                actuadorOld.getActuadoresComandosCollection().remove(actuadoresComandos);
                actuadorOld = em.merge(actuadorOld);
            }
            if (actuadorNew != null && !actuadorNew.equals(actuadorOld)) {
                actuadorNew.getActuadoresComandosCollection().add(actuadoresComandos);
                actuadorNew = em.merge(actuadorNew);
            }
            if (comandoOld != null && !comandoOld.equals(comandoNew)) {
                comandoOld.getActuadoresComandosCollection().remove(actuadoresComandos);
                comandoOld = em.merge(comandoOld);
            }
            if (comandoNew != null && !comandoNew.equals(comandoOld)) {
                comandoNew.getActuadoresComandosCollection().add(actuadoresComandos);
                comandoNew = em.merge(comandoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = actuadoresComandos.getId();
                if (findActuadoresComandos(id) == null) {
                    throw new NonexistentEntityException("The actuadoresComandos with id " + id + " no longer exists.");
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
            ActuadoresComandos actuadoresComandos;
            try {
                actuadoresComandos = em.getReference(ActuadoresComandos.class, id);
                actuadoresComandos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actuadoresComandos with id " + id + " no longer exists.", enfe);
            }
            Actuadores actuador = actuadoresComandos.getActuador();
            if (actuador != null) {
                actuador.getActuadoresComandosCollection().remove(actuadoresComandos);
                actuador = em.merge(actuador);
            }
            Comandos comando = actuadoresComandos.getComando();
            if (comando != null) {
                comando.getActuadoresComandosCollection().remove(actuadoresComandos);
                comando = em.merge(comando);
            }
            em.remove(actuadoresComandos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ActuadoresComandos> findActuadoresComandosEntities() {
        return findActuadoresComandosEntities(true, -1, -1);
    }

    public List<ActuadoresComandos> findActuadoresComandosEntities(int maxResults, int firstResult) {
        return findActuadoresComandosEntities(false, maxResults, firstResult);
    }

    private List<ActuadoresComandos> findActuadoresComandosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ActuadoresComandos.class));
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

    public ActuadoresComandos findActuadoresComandos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ActuadoresComandos.class, id);
        } finally {
            em.close();
        }
    }

    public int getActuadoresComandosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ActuadoresComandos> rt = cq.from(ActuadoresComandos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
