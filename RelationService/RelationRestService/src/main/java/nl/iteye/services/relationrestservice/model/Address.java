/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.iteye.services.relationrestservice.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andrej
 */
@XmlRootElement
public class Address {
private String streetName;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
private String streetNumber;
private String zipCode;
private String city;
private String country;
private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
