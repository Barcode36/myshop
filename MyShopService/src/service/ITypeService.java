/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entites.TypeCompte;

/**
 *
 * @author Christ
 */
public interface ITypeService {

    public void ajouter(TypeCompte typeCompte);

    public void modifier(TypeCompte typeCompte);

    public void supprimer(TypeCompte typeCompte);

    public TypeCompte findById(TypeCompte typeCompte);

    
}
