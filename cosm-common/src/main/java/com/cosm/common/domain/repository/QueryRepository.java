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
public interface QueryRepository<T> {

    T find(Object id);

    List<T> findAll();

}