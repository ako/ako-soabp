/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.iteye.webapps.orderapp.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andrej
 */
@XmlRootElement
public class Customer {
    private String lastName;
    private Link addressLink;
 @XmlElement(name = "link")
    public Link getAddressLink() {
        return addressLink;
    }

    public void setAddressLink(Link addressLink) {
        this.addressLink = addressLink;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
