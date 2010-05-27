/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.iteye.service.orderservice.dao;

import nl.iteye.service.orderservice.model.Order;

/**
 *
 * @author akoelewijn
 */
public interface OrderMapper {
    public Order selectOrder(Long id);
    public void insertOrder(Order order);
}
