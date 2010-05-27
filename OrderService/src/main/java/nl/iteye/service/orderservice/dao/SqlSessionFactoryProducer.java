/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.iteye.service.orderservice.dao;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author akoelewijn
 */
@ApplicationScoped
public class SqlSessionFactoryProducer implements Serializable {

    SqlSessionFactory sqlMapper = null;
    private static final Logger LOG = Logger.getLogger(SqlSessionFactoryProducer.class.getName());

    @Produces
    public SqlSessionFactory createSqlSessionFactory() {
        LOG.info("createSqlSessionFactory");
        if (sqlMapper == null) {
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
        return sqlMapper;
    }
}
