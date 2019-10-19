/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.imp;

import entites.HistoriqueVente;
import entites.Produit;
import entites.Vente;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaController.HistoriqueVenteJpaController;
import jpaController.exceptions.NonexistentEntityException;
import service.IHistoriqueVente;

/**
 *
 * @author Christ
 */
public class HistoriqueVenteService implements IHistoriqueVente{

    public final EntityManagerFactory emf;
    public final HistoriqueVenteJpaController historiqueVenteJpaController;

    public HistoriqueVenteService() {
        this.emf = Persistence.createEntityManagerFactory("MyShopServicePU");
        this.historiqueVenteJpaController = new HistoriqueVenteJpaController(emf);
    }
    
    
    
    @Override
    public void ajouterHistoriqueVente(HistoriqueVente historiqueVente) {
        try {
            historiqueVenteJpaController.create(historiqueVente);
        } catch (Exception ex) {
            Logger.getLogger(HistoriqueVenteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void supprimerHistoriqueVente(HistoriqueVente historiqueVente) {
        try {
            historiqueVenteJpaController.destroy(historiqueVente.getIdCon());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(HistoriqueVenteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public List<Object[]> findAll() {
        return historiqueVenteJpaController.findAll();
    }
    
    @Override
    public List<Object[]> findByIdVen(int idVente) {
        return historiqueVenteJpaController.findByIdVen(idVente);
    }
    
    @Override
     public List<Object[]> findBySmeRecue(double smeRecue) {
         return historiqueVenteJpaController.findBySmeRecue(smeRecue);
     }
     
    @Override
     public List<Object[]> findBySmeRendue(double smeRendue) {
         return historiqueVenteJpaController.findBySmeRendue(smeRendue);
     }
      
}
