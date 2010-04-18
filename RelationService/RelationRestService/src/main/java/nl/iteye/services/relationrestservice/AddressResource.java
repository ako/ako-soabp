/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.iteye.services.relationrestservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import nl.iteye.services.relationrestservice.model.Address;

/**
 *
 * @author andrej
 */
@Path("/address/{id}")
public class AddressResource {

    @GET
    @Produces("text/xml")
    public Address getAddress(@PathParam("id") int id) {
        Address address = new Address();
        address.setId(1L);
        address.setCity("The Hague");
        address.setStreetName("Scheveningseweg");
        address.setStreetNumber("4a");
        address.setZipCode("2580AB");
        address.setCountry("The Netherlands");
        return address;
    }
}
