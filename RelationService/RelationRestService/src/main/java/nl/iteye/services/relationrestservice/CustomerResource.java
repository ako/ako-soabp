
package nl.iteye.services.relationrestservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import nl.iteye.services.relationrestservice.model.Customer;

/** Example resource class hosted at the URI path "/myresource"
 */
@Path("/customer/{id}")
public class CustomerResource {
    
    /** Method processing HTTP GET requests, producing "text/plain" MIME media
     * type.
     * @return String that will be send back as a response of type "text/plain".
     */
    @GET 
    @Produces("text/xml")
    public Customer getCustomer(@PathParam("id") int id) {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Pete");
        customer.setLastName("Stevens");
        return customer;
    }
}
