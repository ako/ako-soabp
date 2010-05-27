/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.iteye.services.relationrestservice.dao;

import com.sun.jersey.spi.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import nl.iteye.services.relationrestservice.model.Address;

/**
 *
 * @author andrej
 */
public class AddressDao {

    @Inject
    public DataSource dataSource;
    private static final Logger LOG = Logger.getLogger(
            AddressDao.class.getName());

    public Address getAddress(Long id) {
        LOG.info("getAddress: " + id);
        Address address = null;
        try {
            LOG.info("getAddress: " + id);
            Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "select * from address where id = ?");
            stmt.setLong(1, id);
            ResultSet rset = stmt.executeQuery();
            if (rset.next()){
                LOG.info("found address");
                address = new Address();
                address.setId(rset.getLong("id"));
                address.setStreetName(rset.getString("street"));
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null,ex);
        }
        return address;
    }
}
