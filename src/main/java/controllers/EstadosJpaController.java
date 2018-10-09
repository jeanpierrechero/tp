/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import clases.Estados;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import clases.EstadosDispositivos;
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
public class EstadosJpaController implements Serializable {

    public EstadosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estados estados) {
        if (estados.getEstadosDispositivosCollection() == null) {
            estados.setEstadosDispositivosCollection(new ArrayList<EstadosDispositivos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<EstadosDispositivos> attachedEstadosDispositivosCollection = new ArrayList<EstadosDispositivos>();
            for (EstadosDispositivos estadosDispositivosCollectionEstadosDispositivosToAttach : estados.getEstadosDispositivosCollection()) {
                estadosDispositivosCollectionEstadosDispositivosToAttach = em.getReference(estadosDispositivosCollectionEstadosDispositivosToAttach.getClass(), estadosDispositivosCollectionEstadosDispositivosToAttach.getId());
                attachedEstadosDispositivosCollection.add(estadosDispositivosCollectionEstadosDispositivosToAttach);
            }
            estados.setEstadosDispositivosCollection(attachedEstadosDispositivosCollection);
            em.persist(estados);
            for (EstadosDispositivos estadosDispositivosCollectionEstadosDispositivos : estados.getEstadosDispositivosCollection()) {
                Estados oldEstadoOfEstadosDispositivosCollectionEstadosDispositivos = estadosDispositivosCollectionEstadosDispositivos.getEstado();
                estadosDispositivosCollectionEstadosDispositivos.setEstado(estados);
                estadosDispositivosCollectionEstadosDispositivos = em.merge(estadosDispositivosCollectionEstadosDispositivos);
                if (oldEstadoOfEstadosDispositivosCollectionEstadosDispositivos != null) {
                    oldEstadoOfEstadosDispositivosCollectionEstadosDispositivos.getEstadosDispositivosCollection().remove(estadosDispositivosCollectionEstadosDispositivos);
                    oldEstadoOfEstadosDispositivosCollectionEstadosDispositivos = em.merge(oldEstadoOfEstadosDispositivosCollectionEstadosDispositivos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estados estados) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estados persistentEstados = em.find(Estados.class, estados.getId());
            Collection<EstadosDispositivos> estadosDispositivosCollectionOld = persistentEstados.getEstadosDispositivosCollection();
            Collection<EstadosDispositivos> estadosDispositivosCollectionNew = estados.getEstadosDispositivosCollection();
            List<String> illegalOrphanMessages = null;
            for (EstadosDispositivos estadosDispositivosCollectionOldEstadosDispositivos : estadosDispositivosCollectionOld) {
                if (!estadosDispositivosCollectionNew.contains(estadosDispositivosCollectionOldEstadosDispositivos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EstadosDispositivos " + estadosDispositivosCollectionOldEstadosDispositivos + " since its estado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<EstadosDispositivos> attachedEstadosDispositivosCollectionNew = new ArrayList<EstadosDispositivos>();
            for (EstadosDispositivos estadosDispositivosCollectionNewEstadosDispositivosToAttach : estadosDispositivosCollectionNew) {
                estadosDispositivosCollectionNewEstadosDispositivosToAttach = em.getReference(estadosDispositivosCollectionNewEstadosDispositivosToAttach.getClass(), estadosDispositivosCollectionNewEstadosDispositivosToAttach.getId());
                attachedEstadosDispositivosCollectionNew.add(estadosDispositivosCollectionNewEstadosDispositivosToAttach);
            }
            estadosDispositivosCollectionNew = attachedEstadosDispositivosCollectionNew;
            estados.setEstadosDispositivosCollection(estadosDispositivosCollectionNew);
            estados = em.merge(estados);
            for (EstadosDispositivos estadosDispositivosCollectionNewEstadosDispositivos : estadosDispositivosCollectionNew) {
                if (!estadosDispositivosCollectionOld.contains(estadosDispositivosCollectionNewEstadosDispositivos)) {
                    Estados oldEstadoOfEstadosDispositivosCollectionNewEstadosDispositivos = estadosDispositivosCollectionNewEstadosDispositivos.getEstado();
                    estadosDispositivosCollectionNewEstadosDispositivos.setEstado(estados);
                    estadosDispositivosCollectionNewEstadosDispositivos = em.merge(estadosDispositivosCollectionNewEstadosDispositivos);
                    if (oldEstadoOfEstadosDispositivosCollectionNewEstadosDispositivos != null && !oldEstadoOfEstadosDispositivosCollectionNewEstadosDispositivos.equals(estados)) {
                        oldEstadoOfEstadosDispositivosCollectionNewEstadosDispositivos.getEstadosDispositivosCollection().remove(estadosDispositivosCollectionNewEstadosDispositivos);
                        oldEstadoOfEstadosDispositivosCollectionNewEstadosDispositivos = em.merge(oldEstadoOfEstadosDispositivosCollectionNewEstadosDispositivos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estados.getId();
                if (findEstados(id) == null) {
                    throw new NonexistentEntityException("The estados with id " + id + " no longer exists.");
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
            Estados estados;
            try {
                estados = em.getReference(Estados.class, id);
                estados.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estados with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<EstadosDispositivos> estadosDispositivosCollectionOrphanCheck = estados.getEstadosDispositivosCollection();
            for (EstadosDispositivos estadosDispositivosCollectionOrphanCheckEstadosDispositivos : estadosDispositivosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estados (" + estados + ") cannot be destroyed since the EstadosDispositivos " + estadosDispositivosCollectionOrphanCheckEstadosDispositivos + " in its estadosDispositivosCollection field has a non-nullable estado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estados);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estados> findEstadosEntities() {
        return findEstadosEntities(true, -1, -1);
    }

    public List<Estados> findEstadosEntities(int maxResults, int firstResult) {
        return findEstadosEntities(false, maxResults, firstResult);
    }

    private List<Estados> findEstadosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estados.class));
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

    public Estados findEstados(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estados.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estados> rt = cq.from(Estados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
