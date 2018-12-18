/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.imp;

import entites.Client;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaController.ClientJpaController;
import jpaController.exceptions.NonexistentEntityException;
import service.IClientService;

/**
 *
 * @author Christ
 */
public class ClientService implements IClientService {

    public final EntityManagerFactory emf;
    public final ClientJpaController clientJpaController;

    public ClientService() {
        this.emf = Persistence.createEntityManagerFactory("MyShopServicePU");
        this.clientJpaController = new ClientJpaController(emf);
    }

    @Override
    public void ajouter(Client client) {
        try {
            clientJpaController.create(client);
        } catch (Exception ex) {
            Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void modifier(Client client) {
        try {
            clientJpaController.edit(client);
        } catch (Exception ex) {
            Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void supprimer(Client client) {
        try {
            clientJpaController.destroy(client.getIdClt());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Client findById(Client client) {
        return clientJpaController.findClient(client.getIdClt());
    }

}
