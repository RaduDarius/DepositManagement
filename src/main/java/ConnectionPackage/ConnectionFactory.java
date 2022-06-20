package ConnectionPackage;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contains methods for creating and closing connections.
 */
public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tp_3_db";
    private static final String USER = "root";
    private static final String PASS = "1234";
    public static final String DATABASE = "tp_3_db";

    private static final ConnectionFactory singleInstance = new ConnectionFactory();

    /**
     * The class constructor.
     */
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method creates a connection or throw an exception if it's not possible
     * @return a connection
     */
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "An error occurred while trying to connect to the database");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * This method return the connection created.
     * @return the connection created
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     * This method will close a connection sent as parameter.
     * @param connection is the connection which we need to close.
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occurred while trying to close the connection");
            }
        }
    }

    /**
     * This method will close a statement sent as parameter.
     * @param statement is the statement which we need to close.
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occurred while trying to close the statement");
            }
        }
    }

    /**
     * This method will close a resultSet sent as parameter.
     * @param resultSet is the resultSet which we need to close.
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occurred while trying to close the ResultSet");
            }
        }
    }
}
