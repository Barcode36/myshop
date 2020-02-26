/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaController;

import entites.Client;
import entites.Compte;
import entites.Vente;
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
public class VenteJpaController implements Serializable {

    public VenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vente vente) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(vente);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVente(vente.getIdVen()) != null) {
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
                Integer id = vente.getIdVen();
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

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vente vente;
            try {
                vente = em.getReference(Vente.class, id);
                vente.getIdVen();
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

    public Vente findVente(Integer id) {
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

    public List<Vente> venteParCaissier(Compte compte) {
        EntityManager em = this.getEntityManager();
        TypedQuery<Vente> query = (TypedQuery<Vente>) em.createNamedQuery("Vente.findByIdComp");
        query.setParameter("idComp", compte.getIdComp());
        return query.getResultList();
    }
    public List<Vente> venteParClient(Client c) {
        EntityManager em = this.getEntityManager();
        TypedQuery<Vente> query = (TypedQuery<Vente>) em.createNamedQuery("Vente.findByIdClt");
        query.setParameter("idClt", c.getIdClt());
        return query.getResultList();
    }

    public List<Vente> venteParCaissierMois(Compte compte, String mois) {
        EntityManager em = this.getEntityManager();
        //TypedQuery<Vente> query = (TypedQuery<Vente>) em.createNamedQuery("Vente.findByDateVen");
        Query query = em.createNativeQuery("SELECT * FROM vente where strftime('%Y-%m', dateVen / 1000, 'unixepoch')=? and idComp=?", Vente.class);
        query.setParameter(1, mois);
        query.setParameter(2, compte.getIdComp());
        return (List<Vente>) query.getResultList();
    }

    public List<Vente> venteParMois(String mois) {
        EntityManager em = this.getEntityManager();
        //TypedQuery<Vente> query = (TypedQuery<Vente>) em.createNamedQuery("Vente.findByDateVen");
        Query query = em.createNativeQuery("SELECT * FROM vente where strftime('%Y-%m', dateVen / 1000, 'unixepoch')=?", Vente.class);
        query.setParameter(1, mois);
        return (List<Vente>) query.getResultList();
    }

    public List<Vente> venteEntreDeuxDate(String date1, String date2, Compte compte) {
        EntityManager em = this.getEntityManager();
        //TypedQuery<Vente> query = (TypedQuery<Vente>) em.createNamedQuery("Vente.findByDateVen");
        Query query = em.createNativeQuery("SELECT * FROM vente where strftime('%Y-%m-%d', dateVen / 1000, 'unixepoch')>=? and strftime('%Y-%m-%d', dateVen / 1000, 'unixepoch')<=? and idComp=?", Vente.class);
        query.setParameter(1, date1);
        query.setParameter(2, date2);
        query.setParameter(3, compte.getIdComp());
        return (List<Vente>) query.getResultList();
    }
}
