/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.iteye.service.orderservice.dao;

import java.util.Date;
import nl.iteye.service.orderservice.model.Order;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author akoelewijn
 */
public class OrderDaoTest {

    public OrderDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of getOrderById method, of class OrderDao.
     */
    @Test
    public void testGetOrderById() {
        OrderDao dao = new OrderDao();
        Order order = dao.getOrderById(1L);
        System.out.println("order: " + order);
        Order order2 = new Order();
        order2.setId(2L);
        order2.setDescription("One bread");
        order2.setOrderDate(new Date());
        dao.insertOrder(order2);
        Order order3 = dao.getOrderById(2L);
        System.out.println("order: " + order3);
    }

}