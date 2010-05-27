/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.iteye.service.orderservice;

import nl.iteye.service.orderservice.model.Order;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import nl.iteye.service.orderservice.dao.OrderDao;

/**
 *
 * @author akoelewijn
 */
@Path("/order/{id}")
public class OrderResource {
    private static final Logger log = Logger.getLogger(OrderResource.class.
            getName());

    @GET
    @Produces({"text/xml","application/json"})
    public Order getOrder(@PathParam("id") int id) {
        log.info(">getOrder: " + id);
        OrderDao dao = new OrderDao();
        Order order = dao.getOrderById(new Long(id));
        return order;
    }
}
