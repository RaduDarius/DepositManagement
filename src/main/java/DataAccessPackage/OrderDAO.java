package DataAccessPackage;

import ConnectionPackage.ConnectionFactory;
import ModelPackage.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * This class is a DAO Class (Data Access Object)
 * It must contain methods for accessing data from database.
 * It extends the AbstractDAO class, which contains all the generic methods for accessing data.
 * But we have some extra methods.
 */

public class OrderDAO extends AbstractDAO<Order> {

    /**
     * create and return a sql query for selecting orders from database.
     * @return a sql query for selecting every order which has product and client fields equals with the parameters sent.
     */
    private String createSelectByIdsQuery() {
        return "SELECT " +
                " * " +
                " FROM " +
                ConnectionFactory.DATABASE +
                ".Order" +
                " WHERE " + "product" + " = ?" +
                " and " + "client" + " = ?";
    }

    /**
     * This method call the createSelectByIdsQuery method for creating a sql query
     * After that, will set the parameters of the query and call the statement.
     * Call the createObjects from superclass for creating the list of needed objects.
     * In the final, we close the resultSet, statement and the connection.
     * @param idProduct is the product id of the order we are looking for in the database.
     * @param idClient is the client id of the order we are looking for in the database.
     * @return a list of orders from database or null if we had an error.
     */
    public List<Order> findByIds(int idProduct, int idClient) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectByIdsQuery();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, idProduct);
            statement.setInt(2, idClient);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Order" + "DAO:findByIds " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * This method call the createSelectQuery method from superclass for creating a sql query
     * After that, will set the parameters of the query and call the statement.
     * Call the createObjects from the superclass for creating the list of needed objects.
     * In the final, we close the resultSet, statement and the connection.
     * @param idProduct is the product id of the order we are looking for in the database.
     * @return a list of orders from database or null if we had an error.
     */
    public List<Order> findByIdProduct(int idProduct) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("product");

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, idProduct);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Order" + "DAO:findByIdProduct " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * This method call the createSelectQuery method from superclass for creating a sql query
     * After that, will set the parameters of the query and call the statement.
     * Call the createObjects from the superclass for creating the list of needed objects.
     * In the final, we close the resultSet, statement and the connection.
     * @param idClient is the client id of the order we are looking for in the database.
     * @return a list of orders from database or null if we had an error.
     */
    public List<Order> findByIdClient(int idClient) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("client");

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, idClient);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Order" + "DAO:findByIdClient " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
}
