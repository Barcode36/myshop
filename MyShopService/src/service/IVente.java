/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entites.Vente;

/**
 *
 * @author Christ
 */
public interface IVente {
    public void ajouter(Vente vente);

    public void modifier(Vente vente);

   // public void supprimer(Vente vente);

    //public Vente findById(Vente vente);
}
