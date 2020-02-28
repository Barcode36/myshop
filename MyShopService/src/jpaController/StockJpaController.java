/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaController;

import entites.Client;
import entites.Stock;
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
public class StockJpaController implements Serializable {

    public StockJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Stock stock) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(stock);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findStock(stock.getIdStock()) != null) {
                throw new PreexistingEntityException("Stock " + stock + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Stock stock) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            stock = em.merge(stock);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = stock.getIdStock();
                if (findStock(id) == null) {
                    throw new NonexistentEntityException("The stock with id " + id + " no longer exists.");
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
            Stock stock;
            try {
                stock = em.getReference(Stock.class, id);
                stock.getIdStock();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The stock with id " + id + " no longer exists.", enfe);
            }
            em.remove(stock);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Stock> findStockEntities() {
        return findStocktEntities(true, -1, -1);
    }

    public List<Stock> findStockEntities(int maxResults, int firstResult) {
        return findStocktEntities(false, maxResults, firstResult);
    }

    private List<Stock> findStocktEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Stock.class));
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

    public Stock findStock(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Stock.class, id);
        } finally {
            em.close();
        }
    }

    public int geStockCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Stock> rt = cq.from(Client.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Stock findStockByCode(Stock c) {
        EntityManager em = this.getEntityManager();
        TypedQuery<Stock> query = (TypedQuery<Stock>) em.createNamedQuery("Stock.findByCodeStock");
        query.setParameter("codeStock", c.getCodeStock());
        return query.getSingleResult();
    }
    
    public Stock findStockByIdProd(Stock c) {
        EntityManager em = this.getEntityManager();
        TypedQuery<Stock> query = (TypedQuery<Stock>) em.createNamedQuery("Stock.findByIdProdMme");
        query.setParameter("idProd", c.getCodeStock());
        return query.getSingleResult();
    }
    
    

    public List<Object[]> findAll(int idProd) {
        EntityManager em = this.getEntityManager();
        TypedQuery<Object[]> query = (TypedQuery<Object[]>) em.createNamedQuery("Stock.findStocks");
        query.setParameter("idProd", idProd);
        return query.getResultList();
    }

    public List<Stock> recLikeCodeOrDtOrFour(Stock c) {
        EntityManager em = this.getEntityManager();
        TypedQuery<Stock> query = (TypedQuery<Stock>) em.createNamedQuery("Stock.findByRec");
        query.setParameter("codeStock", "%" + c.getCodeStock()+ "%");
        return query.getResultList();
    }

}
