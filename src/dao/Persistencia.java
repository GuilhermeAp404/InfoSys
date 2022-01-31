/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
/**
 *
 * @author Tegon Faria
 */
public interface Persistencia<T> {
    public int create (T obj); //grava
    public T findByCodigo(int id); //busca Pelo codigo
    public void delete (int i);
    public void update(T obj);
    public List<T> read();
    
}
