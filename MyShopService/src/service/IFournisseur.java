/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entites.Fournisseur;
import java.util.List;

/**
 *
 * @author Christ
 */
public interface IFournisseur {

    public void ajouterFour(Fournisseur fournisseur);

    public void modifierFour(Fournisseur fournisseur);

    public void supprimerFour(Fournisseur fournisseur);

    public List<Fournisseur> listFour();

}
