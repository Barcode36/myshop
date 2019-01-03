/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.imp;

import entites.Fournisseur;
import entites.Livraison;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaController.LivraisonJpaController;
import jpaController.exceptions.NonexistentEntityException;
import service.ILivraison;

/**
 *
 * @author Christ
 */
public class LivraisonService implements ILivraison {

    public final EntityManagerFactory emf;
    public final LivraisonJpaController livraisonJpaController;

    public LivraisonService() {
        this.emf = Persistence.createEntityManagerFactory("MyShopServicePU");;
        this.livraisonJpaController = new LivraisonJpaController(emf);
    }

    @Override
    public List<Livraison> listParFour(Fournisseur fournisseur) {
        return livraisonJpaController.findByidFour(fournisseur);
    }

    @Override
    public void ajouterLiv(Livraison livraison) {
        try {
            livraisonJpaController.create(livraison);
        } catch (Exception ex) {
            Logger.getLogger(LivraisonService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void modifierLiv(Livraison livraison) {
        try {
            livraisonJpaController.edit(livraison);
        } catch (Exception ex) {
            Logger.getLogger(LivraisonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void supprimerLiv(Livraison livraison) {
        try {
            livraisonJpaController.destroy(livraison.getNumLiv());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(LivraisonService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
