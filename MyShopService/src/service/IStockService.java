/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entites.Stock;
import java.util.List;

/**
 *
 * @author Christ
 */
public interface IStockService {

    public void ajouter(Stock stock);

    public void modifier(Stock stock);

    public void supprimer(Stock stock);

    public Stock findById(Stock stock);

    public List<Object[]> findAll(int idProd);
    
    //public List<Object[]> getAllStock();

    public List<Stock> recLikeNomOrNum(Stock stock);

    public Stock findByNom(Stock stock);
}
