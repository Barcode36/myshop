/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.imp;

import entites.Vente;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaController.VenteJpaController;
import service.IVenteService;

/**
 *
 * @author Christ
 */
public class VenteService implements IVenteService {

    public final EntityManagerFactory emf;
    public final VenteJpaController venteJpaController;

    public VenteService() {
        this.emf = Persistence.createEntityManagerFactory("MyShopServicePU");;
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
    public List<Vente> ventes() {
        return venteJpaController.findVenteEntities();
    }

    @Override
    public List<Vente> ventesParCaissier(Vente vente) {
        return venteJpaController.venteParCaissier(vente);
    }

}
