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
import clases.ComandosAdaptadores;
import java.util.ArrayList;
import java.util.Collection;
import clases.ActuadoresComandos;
import clases.Comandos;
import controllers.exceptions.IllegalOrphanException;
import controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jean Pierre
 */
public class ComandosJpaController implements Serializable {

    public ComandosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comandos comandos) {
        if (comandos.getComandosAdaptadoresCollection() == null) {
            comandos.setComandosAdaptadoresCollection(new ArrayList<ComandosAdaptadores>());
        }
        if (comandos.getActuadoresComandosCollection() == null) {
            comandos.setActuadoresComandosCollection(new ArrayList<ActuadoresComandos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<ComandosAdaptadores> attachedComandosAdaptadoresCollection = new ArrayList<ComandosAdaptadores>();
            for (ComandosAdaptadores comandosAdaptadoresCollectionComandosAdaptadoresToAttach : comandos.getComandosAdaptadoresCollection()) {
                comandosAdaptadoresCollectionComandosAdaptadoresToAttach = em.getReference(comandosAdaptadoresCollectionComandosAdaptadoresToAttach.getClass(), comandosAdaptadoresCollectionComandosAdaptadoresToAttach.getId());
                attachedComandosAdaptadoresCollection.add(comandosAdaptadoresCollectionComandosAdaptadoresToAttach);
            }
            comandos.setComandosAdaptadoresCollection(attachedComandosAdaptadoresCollection);
            Collection<ActuadoresComandos> attachedActuadoresComandosCollection = new ArrayList<ActuadoresComandos>();
            for (ActuadoresComandos actuadoresComandosCollectionActuadoresComandosToAttach : comandos.getActuadoresComandosCollection()) {
                actuadoresComandosCollectionActuadoresComandosToAttach = em.getReference(actuadoresComandosCollectionActuadoresComandosToAttach.getClass(), actuadoresComandosCollectionActuadoresComandosToAttach.getId());
                attachedActuadoresComandosCollection.add(actuadoresComandosCollectionActuadoresComandosToAttach);
            }
            comandos.setActuadoresComandosCollection(attachedActuadoresComandosCollection);
            em.persist(comandos);
            for (ComandosAdaptadores comandosAdaptadoresCollectionComandosAdaptadores : comandos.getComandosAdaptadoresCollection()) {
                Comandos oldComandoOfComandosAdaptadoresCollectionComandosAdaptadores = comandosAdaptadoresCollectionComandosAdaptadores.getComando();
                comandosAdaptadoresCollectionComandosAdaptadores.setComando(comandos);
                comandosAdaptadoresCollectionComandosAdaptadores = em.merge(comandosAdaptadoresCollectionComandosAdaptadores);
                if (oldComandoOfComandosAdaptadoresCollectionComandosAdaptadores != null) {
                    oldComandoOfComandosAdaptadoresCollectionComandosAdaptadores.getComandosAdaptadoresCollection().remove(comandosAdaptadoresCollectionComandosAdaptadores);
                    oldComandoOfComandosAdaptadoresCollectionComandosAdaptadores = em.merge(oldComandoOfComandosAdaptadoresCollectionComandosAdaptadores);
                }
            }
            for (ActuadoresComandos actuadoresComandosCollectionActuadoresComandos : comandos.getActuadoresComandosCollection()) {
                Comandos oldComandoOfActuadoresComandosCollectionActuadoresComandos = actuadoresComandosCollectionActuadoresComandos.getComando();
                actuadoresComandosCollectionActuadoresComandos.setComando(comandos);
                actuadoresComandosCollectionActuadoresComandos = em.merge(actuadoresComandosCollectionActuadoresComandos);
                if (oldComandoOfActuadoresComandosCollectionActuadoresComandos != null) {
                    oldComandoOfActuadoresComandosCollectionActuadoresComandos.getActuadoresComandosCollection().remove(actuadoresComandosCollectionActuadoresComandos);
                    oldComandoOfActuadoresComandosCollectionActuadoresComandos = em.merge(oldComandoOfActuadoresComandosCollectionActuadoresComandos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comandos comandos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comandos persistentComandos = em.find(Comandos.class, comandos.getId());
            Collection<ComandosAdaptadores> comandosAdaptadoresCollectionOld = persistentComandos.getComandosAdaptadoresCollection();
            Collection<ComandosAdaptadores> comandosAdaptadoresCollectionNew = comandos.getComandosAdaptadoresCollection();
            Collection<ActuadoresComandos> actuadoresComandosCollectionOld = persistentComandos.getActuadoresComandosCollection();
            Collection<ActuadoresComandos> actuadoresComandosCollectionNew = comandos.getActuadoresComandosCollection();
            List<String> illegalOrphanMessages = null;
            for (ComandosAdaptadores comandosAdaptadoresCollectionOldComandosAdaptadores : comandosAdaptadoresCollectionOld) {
                if (!comandosAdaptadoresCollectionNew.contains(comandosAdaptadoresCollectionOldComandosAdaptadores)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ComandosAdaptadores " + comandosAdaptadoresCollectionOldComandosAdaptadores + " since its comando field is not nullable.");
                }
            }
            for (ActuadoresComandos actuadoresComandosCollectionOldActuadoresComandos : actuadoresComandosCollectionOld) {
                if (!actuadoresComandosCollectionNew.contains(actuadoresComandosCollectionOldActuadoresComandos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ActuadoresComandos " + actuadoresComandosCollectionOldActuadoresComandos + " since its comando field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<ComandosAdaptadores> attachedComandosAdaptadoresCollectionNew = new ArrayList<ComandosAdaptadores>();
            for (ComandosAdaptadores comandosAdaptadoresCollectionNewComandosAdaptadoresToAttach : comandosAdaptadoresCollectionNew) {
                comandosAdaptadoresCollectionNewComandosAdaptadoresToAttach = em.getReference(comandosAdaptadoresCollectionNewComandosAdaptadoresToAttach.getClass(), comandosAdaptadoresCollectionNewComandosAdaptadoresToAttach.getId());
                attachedComandosAdaptadoresCollectionNew.add(comandosAdaptadoresCollectionNewComandosAdaptadoresToAttach);
            }
            comandosAdaptadoresCollectionNew = attachedComandosAdaptadoresCollectionNew;
            comandos.setComandosAdaptadoresCollection(comandosAdaptadoresCollectionNew);
            Collection<ActuadoresComandos> attachedActuadoresComandosCollectionNew = new ArrayList<ActuadoresComandos>();
            for (ActuadoresComandos actuadoresComandosCollectionNewActuadoresComandosToAttach : actuadoresComandosCollectionNew) {
                actuadoresComandosCollectionNewActuadoresComandosToAttach = em.getReference(actuadoresComandosCollectionNewActuadoresComandosToAttach.getClass(), actuadoresComandosCollectionNewActuadoresComandosToAttach.getId());
                attachedActuadoresComandosCollectionNew.add(actuadoresComandosCollectionNewActuadoresComandosToAttach);
            }
            actuadoresComandosCollectionNew = attachedActuadoresComandosCollectionNew;
            comandos.setActuadoresComandosCollection(actuadoresComandosCollectionNew);
            comandos = em.merge(comandos);
            for (ComandosAdaptadores comandosAdaptadoresCollectionNewComandosAdaptadores : comandosAdaptadoresCollectionNew) {
                if (!comandosAdaptadoresCollectionOld.contains(comandosAdaptadoresCollectionNewComandosAdaptadores)) {
                    Comandos oldComandoOfComandosAdaptadoresCollectionNewComandosAdaptadores = comandosAdaptadoresCollectionNewComandosAdaptadores.getComando();
                    comandosAdaptadoresCollectionNewComandosAdaptadores.setComando(comandos);
                    comandosAdaptadoresCollectionNewComandosAdaptadores = em.merge(comandosAdaptadoresCollectionNewComandosAdaptadores);
                    if (oldComandoOfComandosAdaptadoresCollectionNewComandosAdaptadores != null && !oldComandoOfComandosAdaptadoresCollectionNewComandosAdaptadores.equals(comandos)) {
                        oldComandoOfComandosAdaptadoresCollectionNewComandosAdaptadores.getComandosAdaptadoresCollection().remove(comandosAdaptadoresCollectionNewComandosAdaptadores);
                        oldComandoOfComandosAdaptadoresCollectionNewComandosAdaptadores = em.merge(oldComandoOfComandosAdaptadoresCollectionNewComandosAdaptadores);
                    }
                }
            }
            for (ActuadoresComandos actuadoresComandosCollectionNewActuadoresComandos : actuadoresComandosCollectionNew) {
                if (!actuadoresComandosCollectionOld.contains(actuadoresComandosCollectionNewActuadoresComandos)) {
                    Comandos oldComandoOfActuadoresComandosCollectionNewActuadoresComandos = actuadoresComandosCollectionNewActuadoresComandos.getComando();
                    actuadoresComandosCollectionNewActuadoresComandos.setComando(comandos);
                    actuadoresComandosCollectionNewActuadoresComandos = em.merge(actuadoresComandosCollectionNewActuadoresComandos);
                    if (oldComandoOfActuadoresComandosCollectionNewActuadoresComandos != null && !oldComandoOfActuadoresComandosCollectionNewActuadoresComandos.equals(comandos)) {
                        oldComandoOfActuadoresComandosCollectionNewActuadoresComandos.getActuadoresComandosCollection().remove(actuadoresComandosCollectionNewActuadoresComandos);
                        oldComandoOfActuadoresComandosCollectionNewActuadoresComandos = em.merge(oldComandoOfActuadoresComandosCollectionNewActuadoresComandos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comandos.getId();
                if (findComandos(id) == null) {
                    throw new NonexistentEntityException("The comandos with id " + id + " no longer exists.");
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
            Comandos comandos;
            try {
                comandos = em.getReference(Comandos.class, id);
                comandos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comandos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ComandosAdaptadores> comandosAdaptadoresCollectionOrphanCheck = comandos.getComandosAdaptadoresCollection();
            for (ComandosAdaptadores comandosAdaptadoresCollectionOrphanCheckComandosAdaptadores : comandosAdaptadoresCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Comandos (" + comandos + ") cannot be destroyed since the ComandosAdaptadores " + comandosAdaptadoresCollectionOrphanCheckComandosAdaptadores + " in its comandosAdaptadoresCollection field has a non-nullable comando field.");
            }
            Collection<ActuadoresComandos> actuadoresComandosCollectionOrphanCheck = comandos.getActuadoresComandosCollection();
            for (ActuadoresComandos actuadoresComandosCollectionOrphanCheckActuadoresComandos : actuadoresComandosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Comandos (" + comandos + ") cannot be destroyed since the ActuadoresComandos " + actuadoresComandosCollectionOrphanCheckActuadoresComandos + " in its actuadoresComandosCollection field has a non-nullable comando field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(comandos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comandos> findComandosEntities() {
        return findComandosEntities(true, -1, -1);
    }

    public List<Comandos> findComandosEntities(int maxResults, int firstResult) {
        return findComandosEntities(false, maxResults, firstResult);
    }

    private List<Comandos> findComandosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comandos.class));
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

    public Comandos findComandos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comandos.class, id);
        } finally {
            em.close();
        }
    }

    public int getComandosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comandos> rt = cq.from(Comandos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
