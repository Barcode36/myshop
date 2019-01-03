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
import entites.Vente;
import entites.VentePK;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.IClientService;
import service.ICompteService;
import service.IProduitService;
import service.ITypeService;
import service.IVenteService;
import service.imp.ClientService;
import service.imp.CompteService;
import service.imp.ProduitService;
import service.imp.TypeService;
import service.imp.VenteService;

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
        compte.setMdpComp("1234");
        compte.setNomComp("Eben");
        compte.setPrenomComp("bien");
        compte.setPseudoComp("non");
        compte.setEtatComp("actif");
        compte.setIdTypComp(typeCompte.getIdTyp());
        compteService.ajouter(compte);

        IProduitService produitService = new ProduitService();
        Produit produit = new Produit();
        produit.setCodeProd("6921734952417");
        produit.setLibProd("pile");
        produit.setPrixUniProd("9877");
        //produit.setQteIniProd(10);
        produitService.ajouter(produit);

        IVenteService venteService = new VenteService();
        Vente vente = new Vente();
        VentePK ventePK = new VentePK();
        ventePK.setIdClt(0);
        ventePK.setIdComp(compte.getIdComp());
        ventePK.setIdProd(produit.getIdProd());
        ventePK.setDateVen(new java.sql.Date(new Date().getTime()));
        long l = new Date().getTime();
        System.out.println(new Date(l));
        vente.setVentePK(ventePK);
        vente.setQteVen(2);
        venteService.ajouter(vente);

    }

}
