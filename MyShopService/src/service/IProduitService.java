/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entites.Produit;
import java.util.List;

/**
 *
 * @author Christ
 */
public interface IProduitService {

    public void ajouter(Produit produit);

    public void modifier(Produit produit);

    public void supprimer(Produit produit);

    public Produit findById(Produit produit);

    public Produit findByCode(Produit produit);

    public List<Produit> produitList();

    public List<Produit> recherche(String s);

    public List<Produit> recherche2(String s);
}
