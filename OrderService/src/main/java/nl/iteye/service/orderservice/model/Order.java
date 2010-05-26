package nl.iteye.service.orderservice.model;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author akoelewijn
 */
@XmlRootElement
public class Order {

    private Long id;
    private Date orderDate;

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    private String description;

    @Override
    public String toString() {
        return "Order[id:" + id + ",description:" + description + ",orderDate:" + orderDate + "]";
    }
}
