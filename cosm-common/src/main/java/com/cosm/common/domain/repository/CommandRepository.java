/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.common.domain.repository;

import java.util.List;

/**
 *
 * @author amusa
 * @param <T>
 */
public interface CommandRepository<T> {

     void create(T entity);

     void edit(T entity);

     void remove(T entity);
    
     void remove(List<T> entityList);

     void create(List<T> entityList);

     void edit(List<T> entityList);

}
