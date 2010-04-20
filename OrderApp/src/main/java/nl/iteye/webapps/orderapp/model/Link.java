/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.iteye.webapps.orderapp.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andrej
 */
@XmlRootElement
public class Link {

    protected String relationship;
    protected String href;
    protected String type;

    public Link(){

    }
    public Link(String relationship, String href, String type){
        this.relationship = relationship;
        this.href = href;
        this.type = type;
    }

    @XmlAttribute
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @XmlAttribute(name = "rel")
    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    @XmlAttribute
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
