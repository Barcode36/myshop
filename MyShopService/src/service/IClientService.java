/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entites.Client;
import java.util.List;

/**
 *
 * @author Christ
 */
public interface IClientService {

    public void ajouter(Client client);

    public void modifier(Client client);

    public void supprimer(Client client);

    public Client findById(Client client);

    public List<Client> findAll();

    public Client findByNom(Client client);
}
