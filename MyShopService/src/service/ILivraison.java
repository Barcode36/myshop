/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entites.Fournisseur;
import entites.Livraison;
import java.util.List;

/**
 *
 * @author Christ
 */
public interface ILivraison {

    public List<Livraison> listParFour(Fournisseur fournisseur);

    public void ajouterLiv(Livraison livraison);

    public void modifierLiv(Livraison livraison);

    public void supprimerLiv(Livraison livraison);
}
