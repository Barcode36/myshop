/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entites.Vente;
import java.util.List;

/**
 *
 * @author Christ
 */
public interface IVenteService {

    public void ajouter(Vente vente);

    public List<Vente> ventes();

    public List<Vente> ventesParCaissier(Vente vente);

    public List<Vente> ventesParMois(String mois);

    public List<Vente> ventesEntreDeuxDate(String d1, String d2);

}
