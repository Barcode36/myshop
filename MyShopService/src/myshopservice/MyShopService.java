/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myshopservice;

import entites.Client;
import service.IClientService;
import service.imp.ClientService;

/**
 *
 * @author Christ
 */
public class MyShopService {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Client client = new Client();
        IClientService clientService = new ClientService();
        client.setNomClt("sdfghjkdfgh");
        clientService.ajouter(client);
        Client c  = new Client(1);
       Client c1 =  clientService.findById(c);
       c1.setNomClt("omomomomom");
       clientService.modifier(c1);
        
    }
    
}
