/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.iteye.webapps.orderapp;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import java.util.UUID;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import nl.iteye.webapps.orderapp.model.Address;
import nl.iteye.webapps.orderapp.model.Customer;

/**
 *
 * @author andrej
 */
@ManagedBean(name = "relationService")
@RequestScoped
public class RelationClient {

    private static final int relationSvcPort = 9080;
    private static final String relationSvcHost = "localhost";
    private static final String relationSvcPath = "relation";
    private static final Logger log = Logger.getLogger(RelationClient.class.getName());
    private Address address;
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String showAddressAction() {
        log.info("showAddressAction");
        this.address = getAddressResourceById(1);
        return null;
    }

    public Address getAddressResourceById(int id) {
        log.info("getAddressResource");
        Client c = Client.create();
        WebResource r = c.resource(
                "http://" + relationSvcHost + ":" + relationSvcPort + "/" + relationSvcPath + "/");
        Address address = r.path("address/" + id).get(Address.class);
        return address;
    }

    public Address getAddressResourceByHref(String href) {
        log.info("getAddressResourceByHref");
        Client c = Client.create();
        WebResource r = c.resource(href);
        Address address = r.get(Address.class);
        return address;
    }

    public String showCustomerAction() {
        log.info("showCustomerAction");
        this.customer = getCustomerResource(1);
        this.address = getAddressResourceByHref(customer.getAddressLink().getHref());
        return null;
    }

    public Customer getCustomerResource(int id) {
        log.info("getCustomerResource: " + id);
        Client c = Client.create();
        WebResource r = c.resource(
                "http://" + relationSvcHost + ":" + relationSvcPort + "/" + relationSvcPath + "/");
        Customer customer = r.path("customer/" + id).get(Customer.class);

        return customer;
    }
}
