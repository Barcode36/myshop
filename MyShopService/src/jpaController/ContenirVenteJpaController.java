/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaController;

import entites.ContenirVente;
import entites.ContenirVentePK;
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
public class ContenirVenteJpaController implements Serializable {

    public ContenirVenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ContenirVente contenirVente) throws PreexistingEntityException, Exception {
        if (contenirVente.getContenirVentePK() == null) {
            contenirVente.setContenirVentePK(new ContenirVentePK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(contenirVente);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findContenirVente(contenirVente.getContenirVentePK()) != null) {
                throw new PreexistingEntityException("ContenirVente " + contenirVente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ContenirVente contenirVente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            contenirVente = em.merge(contenirVente);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ContenirVentePK id = contenirVente.getContenirVentePK();
                if (findContenirVente(id) == null) {
                    throw new NonexistentEntityException("The contenirVente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ContenirVentePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ContenirVente contenirVente;
            try {
                contenirVente = em.getReference(ContenirVente.class, id);
                contenirVente.getContenirVentePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contenirVente with id " + id + " no longer exists.", enfe);
            }
            em.remove(contenirVente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ContenirVente> findContenirVenteEntities() {
        return findContenirVenteEntities(true, -1, -1);
    }

    public List<ContenirVente> findContenirVenteEntities(int maxResults, int firstResult) {
        return findContenirVenteEntities(false, maxResults, firstResult);
    }

    private List<ContenirVente> findContenirVenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ContenirVente.class));
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

    public ContenirVente findContenirVente(ContenirVentePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ContenirVente.class, id);
        } finally {
            em.close();
        }
    }

    public int getContenirVenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ContenirVente> rt = cq.from(ContenirVente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    public List<ContenirVente> recuperationParVente(Vente vente) {
        EntityManager em = this.getEntityManager();
        TypedQuery<ContenirVente> query = (TypedQuery<ContenirVente>) em.createNamedQuery("ContenirVente.findByIdVen");
        query.setParameter("idVen", vente.getIdVen());
        return query.getResultList();
    }
}
