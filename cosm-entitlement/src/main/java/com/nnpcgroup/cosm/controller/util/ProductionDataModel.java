/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.controller.util;

import com.nnpcgroup.cosm.entity.forecast.jv.Forecast;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author 18359
 * @param <T>
 */
public class ProductionDataModel<T extends Forecast> extends ListDataModel<T> implements SelectableDataModel<T> {

    public ProductionDataModel(List<T> list) {
        super(list);
    }

    @Override
    public Object getRowKey(Forecast prod) {
        return prod.getForecastPK();
    }

    @Override
    public T getRowData(String rowKey) {
        List<Forecast> prodList = (List<Forecast>) getWrappedData();

        T prod = (T)prodList.stream()
                .filter(p -> p.getForecastPK()
                        .equals(new Long(rowKey)))
                .findFirst()
                .get();


        return prod;
    }
    
    public void addItem(T item){
        ((List<T>)getWrappedData()).add(item);
    }
    
    public void removeItem(T item){
        ((List<T>)getWrappedData()).remove(item);
    }

}
