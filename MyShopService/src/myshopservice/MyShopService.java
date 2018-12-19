/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myshopservice;

import entites.Client;
import entites.Compte;
import entites.Produit;
import entites.TypeCompte;
import service.IClientService;
import service.ICompteService;
import service.IProduitService;
import service.ITypeService;
import service.imp.ClientService;
import service.imp.CompteService;
import service.imp.ProduitService;
import service.imp.TypeService;

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
        ICompteService compteService = new CompteService();
        ITypeService typeService = new TypeService();
        TypeCompte t = new TypeCompte();
        t.setLibTyp("caissier");
        TypeCompte typeCompte = new TypeCompte();
        typeCompte.setLibTyp("Comptable");
        typeService.ajouter(typeCompte);
        typeService.ajouter(t);
        Compte compte = new Compte();
        compte.setMdpComp("Ohrel");
        compte.setNomComp("Eben");
        compte.setPrenomComp("bien");
        compte.setPseudoComp("non");
        compte.setEtatComp("actif");
        compte.setIdTypComp(typeCompte.getIdTyp());
        compteService.ajouter(compte);
        
        IProduitService produitService = new ProduitService();
        Produit produit = new Produit();
        produit.setCodeProd("jygy");
        produit.setLibProd("kgi");
        produit.setPrixUniProd("9877");
        produit.setQteIniProd(10);
        produitService.ajouter(produit);

        

    }

}
