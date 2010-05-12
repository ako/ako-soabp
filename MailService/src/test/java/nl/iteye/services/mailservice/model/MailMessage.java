/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.iteye.services.mailservice.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andrej
 */
@XmlRootElement
public class MailMessage {

    String to;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
    String subject;
    String body;
}
