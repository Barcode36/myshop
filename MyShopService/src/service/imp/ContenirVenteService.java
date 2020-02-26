/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.imp;

import entites.ContenirVente;
import entites.ContenirVentePK;
import entites.Produit;
import entites.Vente;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaController.ContenirVenteJpaController;
import jpaController.exceptions.NonexistentEntityException;
import service.IContenirVente;

/**
 *
 * @author Christ
 */
public class ContenirVenteService implements IContenirVente{

    public final EntityManagerFactory emf;
    public final ContenirVenteJpaController contenirVenteJpaController;

    public ContenirVenteService() {
        this.emf = Persistence.createEntityManagerFactory("MyShopServicePU");
        this.contenirVenteJpaController = new ContenirVenteJpaController(emf);
    }
    
    
    
    @Override
    public void ajouterContenirVente(ContenirVente contenirVente) {
        try {
            contenirVenteJpaController.create(contenirVente);
        } catch (Exception ex) {
            Logger.getLogger(ContenirVenteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void supprimerContenirVente(ContenirVente contenirVente) {
        try {
            contenirVenteJpaController.destroy(contenirVente.getIdCon());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ContenirVenteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<ContenirVente> listParVente(Vente vente) {
        return  contenirVenteJpaController.recuperationParVente(vente);
    }

    @Override
    public List<ContenirVente> listParVente(Produit p) {
        return contenirVenteJpaController.findByProd(p);
    }
    
    
    @Override
    public List<Object[] > listMieuxVen() {
        return contenirVenteJpaController.listMieuxVen();
    }
    
    @Override
    public List<Object[] > listMieuxVenByDate(Date dt1, Date dt2) {
        return contenirVenteJpaController.listMieuxVenByDate(dt1, dt2);
    }
    
    @Override
     public List<Object[]> findVenteByPeriode(Date dt) {
         return contenirVenteJpaController.findVenteByPeriode(dt);
     }
     
    @Override
     public List<Object[]> listEnFinition(){
         return contenirVenteJpaController.listEnFinition();
     }
     
    @Override
     public List<Object[]> historiqueVente(Date dt1, Date dt2,int idCompte) {
         return contenirVenteJpaController.historiqueVente(dt1, dt2, idCompte);
     }
     
    @Override
     public List<Object[]> findTotVteEffectueByPeriode(Date dt) {
         return contenirVenteJpaController.findTotVteEffectueByPeriode(dt);
     }
    
    @Override
    public List<Object[]> findTotQteVendueByTwoPeriode(Date dt1, Date dt2,int idComp){
        return contenirVenteJpaController.findTotQteVendueByTwoPeriode(dt1, dt2, idComp);
    } 
     
    @Override
     public List<Object[]> findTotVteEffectueByTwoPeriode(Date dt1, Date dt2){
         return  contenirVenteJpaController.findTotVteEffectueByTwoPeriode(dt1, dt2);
    }
     
    @Override
     public List<Object[]> findAllVteDesc(){
         return  contenirVenteJpaController.findAllVteDesc();
    }
     
    @Override
     public List<Object[]> findDetailsVte(int idVente){
         return  contenirVenteJpaController.findDetailsVte(idVente);
    }
     
    @Override
    public List<Object[]> findVenteByLike(int idVenteD ){
         return  contenirVenteJpaController.findVenteByLike(idVenteD);
    }
     
    
}
