/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaController;

import entites.Fournisseur;
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
public class LivraisonJpaController implements Serializable {

    public LivraisonJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Livraison livraison) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(livraison);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLivraison(livraison.getNumLiv()) != null) {
                throw new PreexistingEntityException("Livraison " + livraison + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Livraison livraison) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            livraison = em.merge(livraison);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = livraison.getNumLiv();
                if (findLivraison(id) == null) {
                    throw new NonexistentEntityException("The livraison with id " + id + " no longer exists.");
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
            Livraison livraison;
            try {
                livraison = em.getReference(Livraison.class, id);
                livraison.getNumLiv();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The livraison with id " + id + " no longer exists.", enfe);
            }
            em.remove(livraison);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Livraison> findLivraisonEntities() {
        return findLivraisonEntities(true, -1, -1);
    }

    public List<Livraison> findLivraisonEntities(int maxResults, int firstResult) {
        return findLivraisonEntities(false, maxResults, firstResult);
    }

    private List<Livraison> findLivraisonEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Livraison.class));
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

    public Livraison findLivraison(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Livraison.class, id);
        } finally {
            em.close();
        }
    }

    public int getLivraisonCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Livraison> rt = cq.from(Livraison.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Livraison> findByidFour(Fournisseur fournisseur) {
        EntityManager em = this.getEntityManager();
        TypedQuery<Livraison> query = (TypedQuery<Livraison>) em.createNamedQuery("Livraison.findByNumFour");
        query.setParameter("numFour", fournisseur.getIdFour());
        return query.getResultList();
    }

}
