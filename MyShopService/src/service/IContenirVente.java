/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entites.ContenirVente;
import entites.ContenirVentePK;
import entites.Produit;
import entites.Vente;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Christ
 */
public interface IContenirVente {

    public void ajouterContenirVente(ContenirVente contenirVente);

    public void supprimerContenirVente(ContenirVente contenirVente);

    public List<ContenirVente> listParVente(Vente vente);

    public List<ContenirVente> listParVente(Produit p);
    
    public List<Object[] > listMieuxVen();
    
    public List<Object[] > listMieuxVenByDate(Date dt1, Date dt2);
    
    public List<Object[]> findVenteByPeriode(Date dt);
    
    public List<Object[]> listEnFinition();
    
    public List<Object[]> historiqueVente(Date dt1, Date dt2,int idCompte);
    
    public List<Object[]> findTotVteEffectueByPeriode(Date dt);
    
    public List<Object[]> findTotVteEffectueByTwoPeriode(Date dt1, Date dt2);
    
    public List<Object[]> findTotQteVendueByTwoPeriode(Date dt1, Date dt2,int idComp) ;
    
}
