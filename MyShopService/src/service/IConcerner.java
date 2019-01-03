/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entites.Concerner;
import entites.ConcernerPK;
import entites.Livraison;
import java.util.List;

/**
 *
 * @author Christ
 */
public interface IConcerner {

    public List<Concerner> findByIdLiv(Livraison livraison);

    public void supprimerCon(ConcernerPK concernerPK);

    public void ajouterCon(Concerner concerner);
}
