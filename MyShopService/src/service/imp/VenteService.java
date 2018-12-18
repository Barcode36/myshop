/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.imp;

import entites.Vente;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaController.VenteJpaController;
import jpaController.exceptions.NonexistentEntityException;
import service.IVente;

/**
 *
 * @author Christ
 */
public class VenteService implements IVente {

    public final EntityManagerFactory emf;
    public final VenteJpaController venteJpaController;

    public VenteService() {
        this.emf = Persistence.createEntityManagerFactory("MyShopServicePU");
        this.venteJpaController = new VenteJpaController(emf);
    }

    @Override
    public void ajouter(Vente vente) {
        try {
            venteJpaController.create(vente);
        } catch (Exception ex) {
            Logger.getLogger(VenteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void modifier(Vente vente) {
        try {
            venteJpaController.edit(vente);
        } catch (Exception ex) {
            Logger.getLogger(VenteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    @Override
//    public void supprimer(Vente vente) {
//        try {
//            venteJpaController.destroy(id);
//        } catch (NonexistentEntityException ex) {
//            Logger.getLogger(VenteService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    @Override
//    public Vente findById(Vente vente) {
//        return venteJpaController.findVente(ventePK);
//    }

}
