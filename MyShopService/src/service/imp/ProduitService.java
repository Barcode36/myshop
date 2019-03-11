/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.imp;

import entites.Produit;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaController.ProduitJpaController;
import jpaController.exceptions.NonexistentEntityException;
import service.IProduitService;

/**
 *
 * @author Christ
 */
public class ProduitService implements IProduitService {

    public final EntityManagerFactory emf;
    public final ProduitJpaController produitJpaController;

    public ProduitService() {
        this.emf = Persistence.createEntityManagerFactory("MyShopServicePU");
        this.produitJpaController = new ProduitJpaController(emf);
    }

    @Override
    public void ajouter(Produit produit) {
        try {
            produitJpaController.create(produit);
        } catch (Exception ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void modifier(Produit produit) {
        try {
            produitJpaController.edit(produit);
        } catch (Exception ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void supprimer(Produit produit) {
        try {
            produitJpaController.destroy(produit.getIdProd());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Produit findById(Produit produit) {
        return produitJpaController.findProduit(produit.getIdProd());
    }

    @Override
    public List<Produit> produitList() {
        return produitJpaController.findProduitEntities();
    }

    @Override
    public Produit findByCode(Produit produit) {
        return produitJpaController.findByCode(produit);
    }

    @Override
    public List<Produit> recherche(String s) {
        return produitJpaController.Recherche(s);
    }

}
