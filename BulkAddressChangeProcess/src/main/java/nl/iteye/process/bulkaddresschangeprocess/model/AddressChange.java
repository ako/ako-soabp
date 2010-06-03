package nl.iteye.process.bulkaddresschangeprocess.model;

/**
 *
 * @author andrej
 */
public class AddressChange {

    private Link oldAddress;

    public String getNewStreet() {
        return newStreet;
    }

    public void setNewStreet(String newStreet) {
        this.newStreet = newStreet;
    }

    public Link getOldAddress() {
        return oldAddress;
    }

    public void setOldAddress(Link oldAddress) {
        this.oldAddress = oldAddress;
    }
    private String newStreet;
}
