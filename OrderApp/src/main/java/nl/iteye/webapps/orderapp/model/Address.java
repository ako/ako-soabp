/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.iteye.webapps.orderapp.model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

    public String toString() {
        return "Address[streetName=" + streetName + ", streetNumber=" + streetNumber + ",zipCode=" + zipCode + ",city=" + city + "]";
    }
}
