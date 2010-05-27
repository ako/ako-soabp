
package nl.iteye.services.relationrestservice;

import java.net.URI;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import nl.iteye.services.relationrestservice.model.Customer;
import nl.iteye.services.relationrestservice.model.Link;

/** Example resource class hosted at the URI path "/myresource"
 */
@Path("/customer/{id}")
public class CustomerResource {
    private static final Logger log = Logger.getLogger(CustomerResource.class.
            getName());
    /** Method processing HTTP GET requests, producing "text/plain" MIME media
     * type.
     * @return String that will be send back as a response of type "text/plain".
     */
    @GET 
    @Produces("text/xml")
    public Customer getCustomer(@PathParam("id") int id, @Context UriInfo uriInfo) {
        log.info(">getCustomer: " + id);
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Pete");
        customer.setLastName("Stevens");
        Link addressLink = new Link();
        UriBuilder builder = uriInfo.getBaseUriBuilder();
        URI addressURI = builder.path("/address/1").build();

        addressLink.setHref(addressURI.toString());
        addressLink.setRelationship("address");
        addressLink.setType("text/xml");
        customer.setAddressLink(addressLink);
        return customer;
    }
}
