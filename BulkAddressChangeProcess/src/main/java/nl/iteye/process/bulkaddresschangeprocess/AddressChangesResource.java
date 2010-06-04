package nl.iteye.process.bulkaddresschangeprocess;

import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import nl.iteye.process.bulkaddresschangeprocess.model.BulkAddressChangeRequest;

/**
 *
 * @author andrej
 */
@Path("/AddressChangeRequest")
public class AddressChangesResource {
    private static final Logger LOG = Logger.getLogger(AddressChangesResource.class.getName());

    @POST
    @Consumes({"text/xml","application/json"})
    @Produces({"text/xml","application/json"})
    public String handleAddressChangeRequest(BulkAddressChangeRequest request) {
        LOG.info(">handleAddressChangeRequest: " + request);
        return "We're working on it";
    }
}
