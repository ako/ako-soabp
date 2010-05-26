package nl.iteye.service.orderservice.dao;

import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.iteye.service.orderservice.model.Order;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author akoelewijn
 */
public class OrderDao {

    private static final Logger LOG = Logger.getLogger(OrderDao.class.getName());
    SqlSessionFactory sqlMapper = null;

    public OrderDao() {
        Reader reader = null;
        try {
            String resource = "nl/iteye/service/orderservice/dao/ibatis-configuration.xml";
            reader = Resources.getResourceAsReader(resource);
            sqlMapper = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
    }

    public Order getOrderById(Long id) {
        LOG.info("getOrderById: " + id);
        SqlSession session = sqlMapper.openSession();
        Order order = null;
        try {
            order = (Order) session.selectOne(
                    "nl.iteye.service.orderservice.model.OrderMapper.selectOrder",id);
        } finally {
            session.close();
        }
        return order;
    }
    public void insertOrder(Order order) {
        LOG.info("insertOrder: " + order);
        SqlSession session = sqlMapper.openSession();
        try {

            int rows = session.insert("nl.iteye.service.orderservice.model.OrderMapper.insertOrder",order);
        } finally {
            session.close();
        }
    }

}
