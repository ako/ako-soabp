/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.iteye.webapps.orderapp;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import nl.iteye.webapps.orderapp.model.Address;

/**
 *
 * @author andrej
 */
@ManagedBean(name="relationService")
@RequestScoped
public class RelationClient {
private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    public String doGetAddress() {
        Client c = Client.create();
        WebResource r = c.resource("http://localhost:12573/RelationRestService/webresources/");
        String s = r.path("address/1").get(String.class);
        System.out.println("address 1 = " + s);
        Address address = r.path("address/1").get(Address.class);

        this.address = address;
        return null;
    }
}
