/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.imp;

import entites.Concerner;
import entites.ConcernerPK;
import entites.Livraison;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaController.ConcernerJpaController;
import jpaController.exceptions.NonexistentEntityException;
import service.IConcerner;

/**
 *
 * @author Christ
 */
public class ConcernerService implements IConcerner {

    public final EntityManagerFactory emf;
    public final ConcernerJpaController concernerJpaController;

    public ConcernerService() {
        this.emf = Persistence.createEntityManagerFactory("MyShopServicePU");;
        this.concernerJpaController = new ConcernerJpaController(emf);
    }

    @Override
    public List<Concerner> findByIdLiv(Livraison livraison) {
        return concernerJpaController.findByNumLiv(livraison);
    }

    @Override
    public void supprimerCon(ConcernerPK concernerPK) {
        try {
            concernerJpaController.destroy(concernerPK);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ConcernerService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void ajouterCon(Concerner concerner) {
        try {
            concernerJpaController.create(concerner);
        } catch (Exception ex) {
            Logger.getLogger(ConcernerService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
