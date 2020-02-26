/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaController;

import entites.Concerner;
import entites.ConcernerPK;
import entites.Livraison;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpaController.exceptions.NonexistentEntityException;
import jpaController.exceptions.PreexistingEntityException;

/**
 *
 * @author Christ
 */
public class ConcernerJpaController implements Serializable {

    public ConcernerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Concerner concerner) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(concerner);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findConcerner(concerner.getConcernerPK()) != null) {
                throw new PreexistingEntityException("Concerner " + concerner + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Concerner concerner) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            concerner = em.merge(concerner);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ConcernerPK id = concerner.getConcernerPK();
                if (findConcerner(id) == null) {
                    throw new NonexistentEntityException("The concerner with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ConcernerPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Concerner concerner;
            try {
                concerner = em.getReference(Concerner.class, id);
                concerner.getConcernerPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The concerner with id " + id + " no longer exists.", enfe);
            }
            em.remove(concerner);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Concerner> findConcernerEntities() {
        return findConcernerEntities(true, -1, -1);
    }

    public List<Concerner> findConcernerEntities(int maxResults, int firstResult) {
        return findConcernerEntities(false, maxResults, firstResult);
    }

    private List<Concerner> findConcernerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Concerner.class));
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

    public Concerner findConcerner(ConcernerPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Concerner.class, id);
        } finally {
            em.close();
        }
    }

    public int getConcernerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Concerner> rt = cq.from(Concerner.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Concerner> findByNumLiv(Livraison livraison) {
        EntityManager em = this.getEntityManager();
        TypedQuery<Concerner> query = (TypedQuery<Concerner>) em.createNamedQuery("Concerner.findByNumLiv");
        query.setParameter("numLiv", livraison.getNumLiv());
        return query.getResultList();
    }
    
}
