/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entites.Client;

/**
 *
 * @author Christ
 */
public interface IClientService {
    public void ajouter(Client client);

    public void modifier(Client client);

    public void supprimer(Client client);

    public Client  findById(Client client);
}
