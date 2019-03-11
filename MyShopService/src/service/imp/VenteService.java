/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.imp;

import entites.Compte;
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
    public List<Vente> ventesParCaissier(Compte compte) {
        return venteJpaController.venteParCaissier(compte);
    }

    @Override
    public List<Vente> ventesParMois(String mois) {
        return venteJpaController.venteParMois(mois);
    }

    @Override
    public List<Vente> ventesEntreDeuxDate(String d1, String d2,Compte compte) {
        return venteJpaController.venteEntreDeuxDate(d1, d2,compte);
    }

    @Override
    public Vente findByIdVen(Vente vente) {
        return venteJpaController.findVente(vente.getIdVen());
    }

    @Override
    public List<Vente> ventesParCaissierMois(Compte compte, String mois) {
        return venteJpaController.venteParCaissierMois(compte, mois);
    }

}
