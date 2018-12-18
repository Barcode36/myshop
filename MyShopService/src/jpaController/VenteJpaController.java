/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaController;

import entites.Vente;
import entites.VentePK;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpaController.exceptions.NonexistentEntityException;
import jpaController.exceptions.PreexistingEntityException;

/**
 *
 * @author Christ
 */
public class VenteJpaController implements Serializable {

    public VenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vente vente) throws PreexistingEntityException, Exception {
        if (vente.getVentePK() == null) {
            vente.setVentePK(new VentePK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(vente);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVente(vente.getVentePK()) != null) {
                throw new PreexistingEntityException("Vente " + vente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vente vente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            vente = em.merge(vente);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                VentePK id = vente.getVentePK();
                if (findVente(id) == null) {
                    throw new NonexistentEntityException("The vente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(VentePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vente vente;
            try {
                vente = em.getReference(Vente.class, id);
                vente.getVentePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vente with id " + id + " no longer exists.", enfe);
            }
            em.remove(vente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vente> findVenteEntities() {
        return findVenteEntities(true, -1, -1);
    }

    public List<Vente> findVenteEntities(int maxResults, int firstResult) {
        return findVenteEntities(false, maxResults, firstResult);
    }

    private List<Vente> findVenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vente.class));
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

    public Vente findVente(VentePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vente.class, id);
        } finally {
            em.close();
        }
    }

    public int getVenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vente> rt = cq.from(Vente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
