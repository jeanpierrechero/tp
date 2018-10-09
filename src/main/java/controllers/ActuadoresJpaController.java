/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import clases.Actuadores;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import clases.Reglas;
import java.util.ArrayList;
import java.util.Collection;
import clases.ActuadoresComandos;
import controllers.exceptions.IllegalOrphanException;
import controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jean Pierre
 */
public class ActuadoresJpaController implements Serializable {

    public ActuadoresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Actuadores actuadores) {
        if (actuadores.getReglasCollection() == null) {
            actuadores.setReglasCollection(new ArrayList<Reglas>());
        }
        if (actuadores.getActuadoresComandosCollection() == null) {
            actuadores.setActuadoresComandosCollection(new ArrayList<ActuadoresComandos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Reglas> attachedReglasCollection = new ArrayList<Reglas>();
            for (Reglas reglasCollectionReglasToAttach : actuadores.getReglasCollection()) {
                reglasCollectionReglasToAttach = em.getReference(reglasCollectionReglasToAttach.getClass(), reglasCollectionReglasToAttach.getId());
                attachedReglasCollection.add(reglasCollectionReglasToAttach);
            }
            actuadores.setReglasCollection(attachedReglasCollection);
            Collection<ActuadoresComandos> attachedActuadoresComandosCollection = new ArrayList<ActuadoresComandos>();
            for (ActuadoresComandos actuadoresComandosCollectionActuadoresComandosToAttach : actuadores.getActuadoresComandosCollection()) {
                actuadoresComandosCollectionActuadoresComandosToAttach = em.getReference(actuadoresComandosCollectionActuadoresComandosToAttach.getClass(), actuadoresComandosCollectionActuadoresComandosToAttach.getId());
                attachedActuadoresComandosCollection.add(actuadoresComandosCollectionActuadoresComandosToAttach);
            }
            actuadores.setActuadoresComandosCollection(attachedActuadoresComandosCollection);
            em.persist(actuadores);
            for (Reglas reglasCollectionReglas : actuadores.getReglasCollection()) {
                Actuadores oldActuadorOfReglasCollectionReglas = reglasCollectionReglas.getActuador();
                reglasCollectionReglas.setActuador(actuadores);
                reglasCollectionReglas = em.merge(reglasCollectionReglas);
                if (oldActuadorOfReglasCollectionReglas != null) {
                    oldActuadorOfReglasCollectionReglas.getReglasCollection().remove(reglasCollectionReglas);
                    oldActuadorOfReglasCollectionReglas = em.merge(oldActuadorOfReglasCollectionReglas);
                }
            }
            for (ActuadoresComandos actuadoresComandosCollectionActuadoresComandos : actuadores.getActuadoresComandosCollection()) {
                Actuadores oldActuadorOfActuadoresComandosCollectionActuadoresComandos = actuadoresComandosCollectionActuadoresComandos.getActuador();
                actuadoresComandosCollectionActuadoresComandos.setActuador(actuadores);
                actuadoresComandosCollectionActuadoresComandos = em.merge(actuadoresComandosCollectionActuadoresComandos);
                if (oldActuadorOfActuadoresComandosCollectionActuadoresComandos != null) {
                    oldActuadorOfActuadoresComandosCollectionActuadoresComandos.getActuadoresComandosCollection().remove(actuadoresComandosCollectionActuadoresComandos);
                    oldActuadorOfActuadoresComandosCollectionActuadoresComandos = em.merge(oldActuadorOfActuadoresComandosCollectionActuadoresComandos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Actuadores actuadores) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Actuadores persistentActuadores = em.find(Actuadores.class, actuadores.getId());
            Collection<Reglas> reglasCollectionOld = persistentActuadores.getReglasCollection();
            Collection<Reglas> reglasCollectionNew = actuadores.getReglasCollection();
            Collection<ActuadoresComandos> actuadoresComandosCollectionOld = persistentActuadores.getActuadoresComandosCollection();
            Collection<ActuadoresComandos> actuadoresComandosCollectionNew = actuadores.getActuadoresComandosCollection();
            List<String> illegalOrphanMessages = null;
            for (Reglas reglasCollectionOldReglas : reglasCollectionOld) {
                if (!reglasCollectionNew.contains(reglasCollectionOldReglas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reglas " + reglasCollectionOldReglas + " since its actuador field is not nullable.");
                }
            }
            for (ActuadoresComandos actuadoresComandosCollectionOldActuadoresComandos : actuadoresComandosCollectionOld) {
                if (!actuadoresComandosCollectionNew.contains(actuadoresComandosCollectionOldActuadoresComandos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ActuadoresComandos " + actuadoresComandosCollectionOldActuadoresComandos + " since its actuador field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Reglas> attachedReglasCollectionNew = new ArrayList<Reglas>();
            for (Reglas reglasCollectionNewReglasToAttach : reglasCollectionNew) {
                reglasCollectionNewReglasToAttach = em.getReference(reglasCollectionNewReglasToAttach.getClass(), reglasCollectionNewReglasToAttach.getId());
                attachedReglasCollectionNew.add(reglasCollectionNewReglasToAttach);
            }
            reglasCollectionNew = attachedReglasCollectionNew;
            actuadores.setReglasCollection(reglasCollectionNew);
            Collection<ActuadoresComandos> attachedActuadoresComandosCollectionNew = new ArrayList<ActuadoresComandos>();
            for (ActuadoresComandos actuadoresComandosCollectionNewActuadoresComandosToAttach : actuadoresComandosCollectionNew) {
                actuadoresComandosCollectionNewActuadoresComandosToAttach = em.getReference(actuadoresComandosCollectionNewActuadoresComandosToAttach.getClass(), actuadoresComandosCollectionNewActuadoresComandosToAttach.getId());
                attachedActuadoresComandosCollectionNew.add(actuadoresComandosCollectionNewActuadoresComandosToAttach);
            }
            actuadoresComandosCollectionNew = attachedActuadoresComandosCollectionNew;
            actuadores.setActuadoresComandosCollection(actuadoresComandosCollectionNew);
            actuadores = em.merge(actuadores);
            for (Reglas reglasCollectionNewReglas : reglasCollectionNew) {
                if (!reglasCollectionOld.contains(reglasCollectionNewReglas)) {
                    Actuadores oldActuadorOfReglasCollectionNewReglas = reglasCollectionNewReglas.getActuador();
                    reglasCollectionNewReglas.setActuador(actuadores);
                    reglasCollectionNewReglas = em.merge(reglasCollectionNewReglas);
                    if (oldActuadorOfReglasCollectionNewReglas != null && !oldActuadorOfReglasCollectionNewReglas.equals(actuadores)) {
                        oldActuadorOfReglasCollectionNewReglas.getReglasCollection().remove(reglasCollectionNewReglas);
                        oldActuadorOfReglasCollectionNewReglas = em.merge(oldActuadorOfReglasCollectionNewReglas);
                    }
                }
            }
            for (ActuadoresComandos actuadoresComandosCollectionNewActuadoresComandos : actuadoresComandosCollectionNew) {
                if (!actuadoresComandosCollectionOld.contains(actuadoresComandosCollectionNewActuadoresComandos)) {
                    Actuadores oldActuadorOfActuadoresComandosCollectionNewActuadoresComandos = actuadoresComandosCollectionNewActuadoresComandos.getActuador();
                    actuadoresComandosCollectionNewActuadoresComandos.setActuador(actuadores);
                    actuadoresComandosCollectionNewActuadoresComandos = em.merge(actuadoresComandosCollectionNewActuadoresComandos);
                    if (oldActuadorOfActuadoresComandosCollectionNewActuadoresComandos != null && !oldActuadorOfActuadoresComandosCollectionNewActuadoresComandos.equals(actuadores)) {
                        oldActuadorOfActuadoresComandosCollectionNewActuadoresComandos.getActuadoresComandosCollection().remove(actuadoresComandosCollectionNewActuadoresComandos);
                        oldActuadorOfActuadoresComandosCollectionNewActuadoresComandos = em.merge(oldActuadorOfActuadoresComandosCollectionNewActuadoresComandos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = actuadores.getId();
                if (findActuadores(id) == null) {
                    throw new NonexistentEntityException("The actuadores with id " + id + " no longer exists.");
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
            Actuadores actuadores;
            try {
                actuadores = em.getReference(Actuadores.class, id);
                actuadores.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actuadores with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Reglas> reglasCollectionOrphanCheck = actuadores.getReglasCollection();
            for (Reglas reglasCollectionOrphanCheckReglas : reglasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Actuadores (" + actuadores + ") cannot be destroyed since the Reglas " + reglasCollectionOrphanCheckReglas + " in its reglasCollection field has a non-nullable actuador field.");
            }
            Collection<ActuadoresComandos> actuadoresComandosCollectionOrphanCheck = actuadores.getActuadoresComandosCollection();
            for (ActuadoresComandos actuadoresComandosCollectionOrphanCheckActuadoresComandos : actuadoresComandosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Actuadores (" + actuadores + ") cannot be destroyed since the ActuadoresComandos " + actuadoresComandosCollectionOrphanCheckActuadoresComandos + " in its actuadoresComandosCollection field has a non-nullable actuador field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(actuadores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Actuadores> findActuadoresEntities() {
        return findActuadoresEntities(true, -1, -1);
    }

    public List<Actuadores> findActuadoresEntities(int maxResults, int firstResult) {
        return findActuadoresEntities(false, maxResults, firstResult);
    }

    private List<Actuadores> findActuadoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Actuadores.class));
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

    public Actuadores findActuadores(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Actuadores.class, id);
        } finally {
            em.close();
        }
    }

    public int getActuadoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Actuadores> rt = cq.from(Actuadores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
