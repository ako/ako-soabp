package nl.iteye.process.bulkaddresschangeprocess.model;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andrej
 */
@XmlRootElement
public class BulkAddressChangeRequest {

    private List<AddressChange> changes;

    public List<AddressChange> getChanges() {
        return changes;
    }

    public void setChanges(List<AddressChange> changes) {
        this.changes = changes;
    }
}
