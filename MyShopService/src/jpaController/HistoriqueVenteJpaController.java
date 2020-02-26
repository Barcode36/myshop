/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaController;

import entites.HistoriqueVente;
import entites.Produit;
import entites.Vente;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpaController.exceptions.NonexistentEntityException;

/**
 *
 * @author Christ
 */
public class HistoriqueVenteJpaController implements Serializable {

    public HistoriqueVenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HistoriqueVente historiqueVente) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(historiqueVente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HistoriqueVente historiqueVente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            historiqueVente = em.merge(historiqueVente);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = historiqueVente.getIdHis();
                if (findHistoriqueVente(id) == null) {
                    throw new NonexistentEntityException("The historiqueVente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HistoriqueVente historiqueVente;
            try {
                historiqueVente = em.getReference(HistoriqueVente.class, id);
                historiqueVente.getIdHis();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historiqueVente with id " + id + " no longer exists.", enfe);
            }
            em.remove(historiqueVente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HistoriqueVente> findHistoriqueVenteEntities() {
        return findHistoriqueVenteEntities(true, -1, -1);
    }

    public List<HistoriqueVente> findHistoriqueVenteEntities(int maxResults, int firstResult) {
        return findHistoriqueVenteEntities(false, maxResults, firstResult);
    }

    private List<HistoriqueVente> findHistoriqueVenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HistoriqueVente.class));
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

    public HistoriqueVente findHistoriqueVente(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HistoriqueVente.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistoriqueVenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HistoriqueVente> rt = cq.from(HistoriqueVente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     public List<Object[]> findByIdVen(int idVente) {
        EntityManager em = this.getEntityManager();
        TypedQuery<Object[]> query = (TypedQuery<Object[]>) em.createNamedQuery("HistoriqueVente.findByIdVen");
        query.setParameter("idVen", idVente);
        
        return query.getResultList();
    }

    public List<Object[]> findAll() {
        EntityManager em = this.getEntityManager();
        TypedQuery<Object[]> query = (TypedQuery<Object[]>) em.createNamedQuery("HistoriqueVente.findAll");
        
        return query.getResultList();
    }
    
    public List<Object[] > findBySmeRecue( double smeRecue) {
        EntityManager em = this.getEntityManager();
        TypedQuery<Object[]> query = (TypedQuery<Object[] >) em.createNamedQuery("HistoriqueVente.findBySmeRecue");
        query.setParameter("smeRecue", smeRecue);
        
        return query.getResultList();
    }
    
    public List<Object[] > findBySmeRendue(double smeRendue) {
        EntityManager em = this.getEntityManager();
        TypedQuery<Object[]> query = (TypedQuery<Object[] >) em.createNamedQuery("HistoriqueVente.findBySmeRendue");
         query.setParameter("smeRendue", smeRendue);
        
        return query.getResultList();
    }
    
}
