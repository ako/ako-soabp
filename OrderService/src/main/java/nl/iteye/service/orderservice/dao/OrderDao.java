package nl.iteye.service.orderservice.dao;

import java.util.logging.Logger;
import javax.inject.Inject;
import nl.iteye.service.orderservice.model.Order;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 *
 * @author akoelewijn
 */
public class OrderDao {

    private static final Logger LOG = Logger.getLogger(OrderDao.class.getName());
    @Inject
    private SqlSessionFactory sqlMapper = null;

    public SqlSessionFactory getSqlMapper() {
        return sqlMapper;
    }

    public void setSqlMapper(SqlSessionFactory sqlMapper) {
        this.sqlMapper = sqlMapper;
    }

    public OrderDao() {
    }

    public Order getOrderById(Long id) {
        LOG.info("getOrderById: " + id);
        SqlSession session = sqlMapper.openSession();
        Order order = null;
        try {
            order = (Order) session.getMapper(OrderMapper.class).selectOrder(id);
        } finally {
            session.close();
        }
        return order;
    }

    public void insertOrder(Order order) {
        LOG.info("insertOrder: " + order);
        SqlSession session = sqlMapper.openSession();
        try {

            session.getMapper(OrderMapper.class).insertOrder(order);
        } finally {
            session.close();
        }
    }
}
