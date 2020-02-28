/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.imp;

import entites.Stock;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaController.StockJpaController;
import service.IStockService;

/**
 *
 * @author Christ
 */
public class StockService implements IStockService {

    public final EntityManagerFactory emf;
    public final StockJpaController stockJpaController;

    public StockService() {
        this.emf = Persistence.createEntityManagerFactory("MyShopServicePU");
        this.stockJpaController = new StockJpaController(emf);
    }

    @Override
    public void ajouter(Stock stock) {
        try {
            stockJpaController.create(stock);
        } catch (Exception ex) {
            Logger.getLogger(StockService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void modifier(Stock stock) {
        try {
            stockJpaController.edit(stock);
        } catch (Exception ex) {
            Logger.getLogger(StockService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void supprimer(Stock stock) {
        try {
            stockJpaController.edit(stock);
        } catch (Exception ex) {
            Logger.getLogger(StockService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Stock findById(Stock stock) {
        return stockJpaController.findStock(stock.getIdStock());
    }
    
    @Override
    public Stock findStockByIdProd(Stock stock) {
        return stockJpaController.findStockByIdProd(stock);
    }

    @Override
    public List<Object[]> findAll(int idProd) {
        return stockJpaController.findAll(idProd);
    }

    @Override
    public Stock findByNom(Stock stock) {
        return stockJpaController.findStockByCode(stock);
    }

    @Override
    public List<Stock> recLikeNomOrNum(Stock c) {
        return stockJpaController.recLikeCodeOrDtOrFour(c);
    }

}
