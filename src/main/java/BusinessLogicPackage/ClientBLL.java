package BusinessLogicPackage;

import BusinessLogicPackage.validators.*;
import BusinessLogicPackage.validators.Validator;
import DataAccessPackage.ClientDAO;
import DataAccessPackage.OrderDAO;
import ModelPackage.Client;
import ModelPackage.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class is a BLL Class (Business Logic Layer)
 * It will contain the Business Logic for the operations on client objects.
 */

public class ClientBLL {

    private final List<Validator<Client>> validators;
    private final ClientDAO clientDAO;

    /**
     * The class constructor.
     */
    public ClientBLL() {
        validators = new ArrayList<>();
        validators.add(new EmailValidator());
        validators.add(new AgeValidator());

        clientDAO = new ClientDAO();
    }

    /**
     * This method call the findById method from ClientDAO class, for searching a client with client id = param id.
     * It also validates the result of the method called.
     * @param id is the client id of the client we are looking for in the database.
     * @return the client with the id or an exception if the client was not found.
     */
    public Client findClientById(int id) {
        Client client = clientDAO.findById(id);
        if (client == null) {
            throw new NoSuchElementException("The client with id = " + id + " was not found!");
        }
        return client;
    }

    /**
     * This method call the findAll method from ClientDAO class, for searching all the clients in the database.
     * It also validates the result of the method called.
     * @return a list of clients from database or an exception if the table is empty.
     */
    public List<Client> findAll() {
        List<Client> clients = clientDAO.findAll();
        if (clients == null) {
            throw new NoSuchElementException("Empty table !");
        }
        return clients;
    }

    /**
     * This method call the insert method from ClientDAO class, for inserting a client in the database.
     * It also validates the client input before calling the method and the result of the method called.
     * @param t is the client which we want to insert in the database.
     * @return the client which was inserted in the database or an exception if the client has not been inserted.
     */
    public Client insert(Client t) {

        for (Validator<Client> validator: validators) {
            validator.validate(t);
        }

        Client client = clientDAO.insert(t);

        if (client == null) {
            throw new NoSuchElementException("The client was not inserted in the table !");
        }
        return client;
    }

    /**
     * This method call the update method from ClientDAO class, for updating a client from database.
     * It also validates the client input before calling the method and the result of the method called.
     * @param t is the client which we want to update.
     * @return the client which was updated in the database or an exception if the client has not been updated.
     */
    public Client update(Client t) {

        for (Validator<Client> validator: validators) {
            validator.validate(t);
        }

        Client client = clientDAO.update(t);

        if (client == null) {
            throw new NoSuchElementException("The client has not been updated !");
        }
        return client;
    }

    /**
     * This method call the delete method from ClientDAO class, for deleting a client from database.
     * It also deletes if exists the client's orders from database and validates the result of the method called.
     * @param t is the client which we want to delete.
     * @return the client which was deleted in the database or an exception if the client has not been deleted.
     */
    public Client delete (Client t) {

        OrderDAO orderDAO = new OrderDAO();
        ArrayList<Order> orders = (ArrayList<Order>) orderDAO.findByIdClient(t.getId());

        if (orders != null) {
            for (Order o : orders) {
                orderDAO.delete(o);
            }
        }

        Client client = clientDAO.delete(t);

        if (client == null) {
            throw new NoSuchElementException("The client has not been deleted !");
        }
        return client;
    }
}