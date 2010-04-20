/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.iteye.services.relationrestservice.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andrej
 */
@XmlRootElement
public class Customer {

    private Long id;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    private String firstName;
    private String lastName;
    private Link addressLink;

    @XmlElement(name = "link")
    public Link getAddressLink() {
        return addressLink;
    }

    public void setAddressLink(Link addressLink) {
        this.addressLink = addressLink;
    }
}
