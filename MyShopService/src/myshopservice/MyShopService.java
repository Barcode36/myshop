/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myshopservice;

import entites.Client;
import entites.Compte;
import entites.Concerner;
import entites.Livraison;
import entites.Produit;
import entites.TypeCompte;
import entites.Vente;
import entites.ContenirVentePK;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import service.IClientService;
import service.ICompteService;
import service.IConcerner;
import service.IProduitService;
import service.ITypeService;
import service.IVenteService;
import service.imp.ClientService;
import service.imp.CompteService;
import service.imp.ConcernerService;
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

//        Client client = new Client();
//        IClientService clientService = new ClientService();
//        ICompteService compteService = new CompteService();
//        ITypeService typeService = new TypeService();
//        TypeCompte t = new TypeCompte();
//        t.setLibTyp("caissier");
//        TypeCompte typeCompte = new TypeCompte();
//        typeCompte.setLibTyp("Comptable");
//        typeService.ajouter(typeCompte);
//        typeService.ajouter(t);
//        Compte compte = new Compte();
//        compte.setMdpComp("1234");
//        compte.setNomComp("Eben");
//        compte.setPrenomComp("bien");
//        compte.setPseudoComp("non");
//        compte.setEtatComp("actif");
//        compte.setIdTypComp(typeCompte.getIdTyp());
//        compteService.ajouter(compte);
//

        IProduitService produitService = new ProduitService();
        List<Produit> list = produitService.recherche("c");
        System.out.println(list);
//        Produit produit = new Produit();
//        produit.setCodeProd("6921734952417");
//        produit.setLibProd("pile");
//        produit.setPrixUniProd("9877");
//        //produit.setQteIniProd(10);
//        produitService.ajouter(produit);
//        LocalDateTime ldt = LocalDateTime.now();
//        System.out.println();
//        IVenteService venteService = new VenteService();
//        Vente vente = new Vente();
//        VentePK ventePK = new VentePK();
//        ventePK.setIdClt(0);
//        ventePK.setIdComp(3);
//        ventePK.setIdProd(produit.getIdProd());
//
//        ventePK.setDateVen(new Date());
//
//        vente.setVentePK(ventePK);
//        vente.setQteVen(2);
//        venteService.ajouter(vente);
//        List<Vente> list = venteService.ventesParMois("2019");
//        for (Vente v: list) {
//            
//            System.out.println(v.getQteVen());
//        }
//        Date mois = new Date();
//        List<Vente> ventes = venteService.ventesParMois(01);
//        System.out.println(ventes);
//        java.sql.Date d = new java.sql.Date
//IConcerner concernerService = new ConcernerService();
//        Livraison livraison = new Livraison(1);
//        List<Concerner> list = concernerService.findByIdLiv(livraison);
//        System.out.println(list);
        
    }

}
