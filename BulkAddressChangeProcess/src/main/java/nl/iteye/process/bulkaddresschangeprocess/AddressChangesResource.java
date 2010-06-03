package nl.iteye.process.bulkaddresschangeprocess;

import java.util.logging.Logger;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import nl.iteye.process.bulkaddresschangeprocess.model.BulkAddressChangeRequest;

/**
 *
 * @author andrej
 */
@Path("/AddressChangeRequest")
public class AddressChangesResource {
    private static final Logger LOG = Logger.getLogger(AddressChangesResource.class.getName());

    @POST
    public String handleAddressChangeRequest(BulkAddressChangeRequest request) {
        LOG.info(">handleAddressChangeRequest: " + request);
        return "We're working on it";
    }
}
