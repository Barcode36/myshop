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
}
