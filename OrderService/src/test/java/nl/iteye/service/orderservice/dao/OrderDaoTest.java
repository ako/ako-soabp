/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.iteye.service.orderservice.dao;

import java.util.Date;
import nl.iteye.service.orderservice.model.Order;
import oracle.jdbc.pool.OracleDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert.*;

/**
 *
 * @author akoelewijn
 */
public class OrderDaoTest {

    private static SqlSessionFactory sqlSessionFactory = null;

    public OrderDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {

        OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
        ds.setURL(System.getProperty("order.db.url"));
        ds.setUser(System.getProperty("order.db.username"));
        ds.setPassword(System.getProperty("order.db.password"));
        TransactionFactory transactionFactory = new JdbcTransactionFactory();

        Environment environment =
                new Environment("development", transactionFactory, ds);

        Configuration configuration = new Configuration(environment);
        //configuration.addLoadedResource("nl/iteye/service/orderservice/dao/OrderMapper.xml");
        configuration.addMapper(OrderMapper.class);

        sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(configuration);

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
        dao.setSqlMapper(sqlSessionFactory);
        Order order = dao.getOrderById(1L);
        System.out.println("order: " + order);
        Order order2 = new Order();
        order2.setId(2L);
        order2.setDescription("One bread");
        order2.setOrderDate(new Date());
        dao.insertOrder(order2);
        Order order3 = dao.getOrderById(2L);
        System.out.println("order: " + order3);
        //org.junit.Assert.assertEquals(order2, order3);
    }
}
