/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaController;

import entites.TypeCompte;
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
public class TypeCompteJpaController implements Serializable {

    public TypeCompteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TypeCompte typeCompte) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(typeCompte);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTypeCompte(typeCompte.getIdTyp()) != null) {
                throw new PreexistingEntityException("TypeCompte " + typeCompte + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TypeCompte typeCompte) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            typeCompte = em.merge(typeCompte);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = typeCompte.getIdTyp();
                if (findTypeCompte(id) == null) {
                    throw new NonexistentEntityException("The typeCompte with id " + id + " no longer exists.");
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
            TypeCompte typeCompte;
            try {
                typeCompte = em.getReference(TypeCompte.class, id);
                typeCompte.getIdTyp();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The typeCompte with id " + id + " no longer exists.", enfe);
            }
            em.remove(typeCompte);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TypeCompte> findTypeCompteEntities() {
        return findTypeCompteEntities(true, -1, -1);
    }

    public List<TypeCompte> findTypeCompteEntities(int maxResults, int firstResult) {
        return findTypeCompteEntities(false, maxResults, firstResult);
    }

    private List<TypeCompte> findTypeCompteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TypeCompte.class));
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

    public TypeCompte findTypeCompte(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TypeCompte.class, id);
        } finally {
            em.close();
        }
    }

    public int getTypeCompteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TypeCompte> rt = cq.from(TypeCompte.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
