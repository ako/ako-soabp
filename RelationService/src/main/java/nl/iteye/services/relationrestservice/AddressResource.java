/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.iteye.services.relationrestservice;

import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import nl.iteye.services.relationrestservice.dao.AddressDao;
import nl.iteye.services.relationrestservice.model.Address;

/**
 *
 * @author andrej
 */
@Path("/address/{id}")
@Stateless
public class AddressResource {

    private static final Logger log = Logger.getLogger(AddressResource.class.
            getName());

    AddressDao addressDao;

    public AddressDao getAddressDao() {
        return addressDao;
    }

    @Inject public void setAddressDao(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @GET
    @Produces({"text/xml","application/json"})
    public Address getAddress(@PathParam("id") Long id) {
        log.info(">getAddress: " + id);
        Address address = getAddressDao().getAddress(id);
        return address;
    }
}
