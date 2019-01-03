/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.imp;

import entites.Concerner;
import entites.Livraison;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaController.ConcernerJpaController;
import service.IConcerner;

/**
 *
 * @author Christ
 */
public class ConcernerService implements IConcerner {
    
    public final EntityManagerFactory emf;
    public final ConcernerJpaController concernerJpaController;
    
    public ConcernerService() {
        this.emf = Persistence.createEntityManagerFactory("MyShopServicePU");;
        this.concernerJpaController = new ConcernerJpaController(emf);
    }
    
    @Override
    public List<Concerner> findByIdLiv(Livraison livraison) {
        return concernerJpaController.findByNumLiv(livraison);
    }
    
}
