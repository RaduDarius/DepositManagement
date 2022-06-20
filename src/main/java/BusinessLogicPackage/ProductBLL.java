package BusinessLogicPackage;

import BusinessLogicPackage.validators.*;
import DataAccessPackage.OrderDAO;
import DataAccessPackage.ProductDAO;
import ModelPackage.Order;
import ModelPackage.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class is a BLL Class (Business Logic Layer)
 * It will contain the Business Logic for the operations on product objects.
 */

public class ProductBLL {

    private final List<Validator<Product>> validators;
    private final ProductDAO productDAO;

    /**
     * The class constructor.
     */
    public ProductBLL() {
        validators = new ArrayList<>();
        validators.add(new PriceValidator());
        validators.add(new StockValidator());

        productDAO = new ProductDAO();
    }

    /**
     * This method call the findById method from ProductDAO class, for searching a product with product id = param id.
     * It also validates the result of the method called.
     * @param id is the product id of the product we are looking for in the database.
     * @return the product with the id or an exception if the product was not found.
     */
    public Product findProductById(int id) {
        Product product = productDAO.findById(id);
        if (product == null) {
            throw new NoSuchElementException("The product with id = " + id + " was not found!");
        }
        return product;
    }

    /**
     * This method call the findAll method from ProductDAO class, for searching all the products in the database.
     * It also validates the result of the method called.
     * @return a list of products from database or an exception if the table is empty.
     */
    public List<Product> findAll() {
        List<Product> products = productDAO.findAll();
        if (products == null) {
            throw new NoSuchElementException("Empty table !");
        }
        return products;
    }

    /**
     * This method call the insert method from ProductDAO class, for inserting a product in the database.
     * It also validates the product input before calling the method and the result of the method called.
     * @param t is the product which we want to insert in the database.
     * @return the product which was inserted in the database or an exception if the product has not been inserted.
     */
    public Product insert(Product t) {

        for (Validator<Product> validator: validators) {
            validator.validate(t);
        }

        Product product = productDAO.insert(t);

        if (product == null) {
            throw new NoSuchElementException("The product was not inserted in the table !");
        }
        return product;
    }

    /**
     * This method call the update method from ProductDAO class, for updating a product from database.
     * It also validates the product input before calling the method and the result of the method called.
     * @param t is the product which we want to update.
     * @return the product which was updated in the database or an exception if the product has not been updated.
     */
    public Product update(Product t) {

        for (Validator<Product> validator: validators) {
            validator.validate(t);
        }

        Product product = productDAO.update(t);

        if (product == null) {
            throw new NoSuchElementException("The product has not been updated !");
        }
        return product;
    }

    /**
     * This method call the delete method from ProductDAO class, for deleting a product from database.
     * It also deletes if exists the orders for the input product from database and validates the result of the method called.
     * @param t is the product which we want to delete.
     * @return the product which was deleted in the database or an exception if the product has not been deleted.
     */
    public Product delete (Product t) {

        OrderDAO orderDAO = new OrderDAO();
        ArrayList<Order> orders = (ArrayList<Order>) orderDAO.findByIdProduct(t.getId());

        if (orders != null) {
            for (Order o : orders) {
                orderDAO.delete(o);
            }
        }
        Product product = productDAO.delete(t);

        if (product == null) {
            throw new NoSuchElementException("The product has not been deleted !");
        }
        return product;
    }

}
