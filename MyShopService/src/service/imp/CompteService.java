/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.imp;

import entites.Compte;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaController.CompteJpaController;
import jpaController.exceptions.NonexistentEntityException;
import service.ICompteService;

/**
 *
 * @author Christ
 */
public class CompteService implements ICompteService {

    public final EntityManagerFactory emf;
    public final CompteJpaController compteJpaController;

    public CompteService() {
        this.emf = Persistence.createEntityManagerFactory("MyShopServicePU");
        this.compteJpaController = new CompteJpaController(emf);
    }

    @Override
    public void ajouter(Compte compte) {
        try {
            compteJpaController.create(compte);
        } catch (Exception ex) {
            Logger.getLogger(CompteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void modifier(Compte compte) {
        try {
            compteJpaController.edit(compte);
        } catch (Exception ex) {
            Logger.getLogger(CompteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void supprimer(Compte compte) {
        try {
            compteJpaController.destroy(compte.getIdComp());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CompteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Compte findById(Compte compte) {
        return compteJpaController.findCompte(compte.getIdComp());
    }
    
    @Override
    public Compte findByPseudoComp(String pseudoComp) {
        return compteJpaController.findByPseudoComp(pseudoComp);
    }

    @Override
    public List<Compte> compteList() {
        return compteJpaController.AllCompte();
    }

    @Override
    public Compte Connexion(Compte compte) {
        return compteJpaController.ConnexionReussi(compte);
    }

}
