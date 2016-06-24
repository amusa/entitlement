/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb;

import java.util.List;

/**
 *
 * @author 18359
 * @param <T>
 */
public interface AbstractCrudServices<T> {

    public void create(T entity);

    public void edit(T entity);

    public void remove(T entity);

    public void delete(T entity);

    public T find(Object id);

    public List<T> findAll();

    public List<T> findRange(int[] range);

    public int count();

    public void create(List<T> entityList);

    public void edit(List<T> entityList);
    
    public void flush();
    
    public T merge (T t);
    
    public void refresh(T t);
    
    public boolean isPersist(T t);
}
