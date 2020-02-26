/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entites.HistoriqueVente;
import entites.Produit;
import entites.Vente;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Christ
 */
public interface IHistoriqueVente {

    public void ajouterHistoriqueVente(HistoriqueVente historiqueVente);

    public void supprimerHistoriqueVente(HistoriqueVente historiqueVente);
    
    public List<Object[]> findByIdVen(int idVen);
    
    public List<Object[]> findAll();
    
    public List<Object[]> findBySmeRecue(double smeRecue);
    
    public List<Object[]> findBySmeRendue(double smeRendue);
    
    
}
