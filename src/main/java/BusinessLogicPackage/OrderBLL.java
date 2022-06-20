package BusinessLogicPackage;

import BusinessLogicPackage.validators.AmountValidator;
import BusinessLogicPackage.validators.Validator;
import DataAccessPackage.OrderDAO;
import DataAccessPackage.ProductDAO;
import ModelPackage.Order;
import ModelPackage.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class is a BLL Class (Business Logic Layer)
 * It will contain the Business Logic for the operations on order objects.
 */
public class OrderBLL {

    private final List<Validator<Order>> validators;
    private final OrderDAO orderDAO;

    /**
     * The class constructor.
     */
    public OrderBLL() {

        validators = new ArrayList<>();
        validators.add(new AmountValidator());

        orderDAO = new OrderDAO();
    }

    /**
     * This method call the findById method from OrderDAO class, for searching an order with order id = param id.
     * It also validates the result of the method called.
     * @param id is the order id of the order we are looking for in the database.
     * @return the order with the id or an exception if the order was not found.
     */
    public Order findOrderById(int id) {
        Order order = orderDAO.findById(id);
        if (order == null) {
            throw new NoSuchElementException("The order with id = " + id + " was not found!");
        }
        return order;
    }

    /**
     * This method call the findByIds method from OrderDAO class,
     * for searching orders with product id = param idProduct and client id = param idClient
     * @param idProduct is the product id of the order we are looking for in the database.
     * @param idClient is the client id of the order we are looking for in the database.
     * @return a list of orders from database or an exception if it doesn't exist such orders.
     */
    public List<Order> findOrderByIds(int idProduct, int idClient) {
        List<Order> orders = orderDAO.findByIds(idProduct, idClient);
        if (orders == null) {
            throw new NoSuchElementException("No order with product id = " + idProduct +
                    " and client id = " + idClient + " was not found !");
        }
        return orders;
    }

    /**
     * This method call the findAll method from OrderDAO class, for searching all the orders in the database.
     * It also validates the result of the method called.
     * @return a list of orders from database or an exception if the table is empty.
     */
    public List<Order> findAll() {
        List<Order> orders = orderDAO.findAll();
        if (orders == null) {
            throw new NoSuchElementException("Empty table !");
        }
        return orders;
    }

    /**
     * This method call the insert method from OrderDAO class, for inserting an order in the database.
     * It also validates the order input before calling the method and the result of the method called.
     * After performing the insertion will update the product stock of the order, calling the update method from ClientDAO class.
     * @param t is the order which we want to insert in the database.
     * @return the order which was inserted in the database or an exception if the order has not been inserted.
     */
    public Order insert(Order t) {

        for (Validator<Order> validator: validators) {
            validator.validate(t);
        }

        Order order = orderDAO.insert(t);

        if (order == null) {
            throw new NoSuchElementException("The order was not inserted in the table !");
        }

        t.getProduct().decrementStock(t.getAmount());
        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.update(t.getProduct());

        if (product == null) {
            throw new NoSuchElementException("The product stock was not decremented !");
        }

        return order;
    }

    /**
     * This method call the update method from OrderDAO class, for updating a client from database.
     * It also validates the order input before calling the method and the result of the method called.
     * @param t is the order which we want to update.
     * @return the order which was updated in the database or an exception if the order has not been updated.
     */
    public Order update(Order t) {

        for (Validator<Order> validator: validators) {
            validator.validate(t);
        }

        Order order = orderDAO.update(t);

        if (order == null) {
            throw new NoSuchElementException("The order has not been updated !");
        }
        return order;
    }

    /**
     * This method call the delete method from OrderDAO class, for deleting an order from database.
     * It also validates the result of the method called.
     * @param t is the order which we want to delete.
     * @return the order which was deleted in the database or an exception if the order has not been deleted.
     */
    public Order delete (Order t) {
        Order order = orderDAO.delete(t);

        if (order == null) {
            throw new NoSuchElementException("The order has not been deleted !");
        }
        return order;
    }
}
