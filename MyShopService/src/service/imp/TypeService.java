/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.imp;

import entites.TypeCompte;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaController.TypeCompteJpaController;
import jpaController.exceptions.NonexistentEntityException;
import service.ITypeService;

/**
 *
 * @author Christ
 */
public class TypeService implements ITypeService {

    public final EntityManagerFactory emf;
    public final TypeCompteJpaController typeJpaController;

    public TypeService() {
        this.emf = Persistence.createEntityManagerFactory("MyShopServicePU");
        this.typeJpaController = new TypeCompteJpaController(emf);
    }

    @Override
    public void ajouter(TypeCompte typeCompte) {
        try {
            typeJpaController.create(typeCompte);
        } catch (Exception ex) {
            Logger.getLogger(TypeService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void modifier(TypeCompte typeCompte) {
        try {
            typeJpaController.edit(typeCompte);
        } catch (Exception ex) {
            Logger.getLogger(TypeService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void supprimer(TypeCompte typeCompte) {
        try {
            typeJpaController.destroy(typeCompte.getIdTyp());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TypeService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public TypeCompte findById(TypeCompte typeCompte) {
        return typeJpaController.findTypeCompte(typeCompte.getIdTyp());
    }

}
