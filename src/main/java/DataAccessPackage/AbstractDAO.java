package DataAccessPackage;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import ConnectionPackage.ConnectionFactory;
import ModelPackage.Client;
import ModelPackage.Product;

/**
 * This class is a DAO Class (Data Access Object)
 * It must contain methods for accessing data from database.
 * @param <T> is the class of the object which call those methods.
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    /**
     * The class constructor
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**
     * The method go through all object's attributes and store them and there values in a map
     * @param object is an object for whom we want to know all the attributes and there values
     * @return a map with fields as keys and objects as values, which represents the fields and there values of the object received as parameter
     */
    private Map<Field, Object> retrieveProperties(T object) {

        Map<Field, Object> properties = new HashMap<>();

        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                properties.put(field, field.get(object));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return properties;
    }

    /**
     * @param resultSet is the ResultSet received from database
     * @return a list of T objects which represents the selected rows from database
     */
    protected List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (Constructor constructor : ctors) {
            ctor = constructor;
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                assert ctor != null;
                ctor.setAccessible(true);

                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value;

                    if (field.getName().equals(Client.class.getSimpleName().toLowerCase())) {
                        ClientDAO clientDAO = new ClientDAO();
                        value =  clientDAO.findById(resultSet.getInt(fieldName));
                    } else if (field.getName().equals(Product.class.getSimpleName().toLowerCase())) {
                        ProductDAO productDAO = new ProductDAO();
                        value =  productDAO.findById(resultSet.getInt(fieldName));
                    } else {
                        value = resultSet.getObject(fieldName);
                    }

                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException |
                IllegalAccessException |
                SecurityException |
                IllegalArgumentException |
                InvocationTargetException |
                IntrospectionException |
                SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * create and return a sql query for selecting T objects from database depending on a field.
     * @param field is the depending on which we want to select
     * @return a sql query for selecting every T entity which has the field received as parameter equals with a variable.
     */
    protected String createSelectQuery(String field) {
        return "SELECT " +
                " * " +
                " FROM " +
                ConnectionFactory.DATABASE +
                "." +
                type.getSimpleName() +
                " WHERE " + field + " = ?";
    }

    /**
     * create and return a sql query for selecting all T objects from database.
     * @return a sql query for selecting all T entities from the database.
     */
    private String createSelectAllQuery() {
        return "SELECT " +
                " * " +
                " FROM " +
                ConnectionFactory.DATABASE +
                "." +
                type.getSimpleName();
    }

    /**
     * create and return a sql query for inserting a T object in the database.
     * @param properties is a map with fields as keys and objects as values, which represents the fields and there values of the object we want to insert into database
     * @return a sql query for inserting a T entity in the database.
     */
    private String createInsertQuery(Map<Field, Object> properties) {

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(ConnectionFactory.DATABASE);
        sb.append(".");
        sb.append(type.getSimpleName());
        sb.append(" ( ");
        for (Field f: properties.keySet()) {
            if (!f.getName().equals("id")) {
                sb.append(f.getName());
                sb.append(",");
            }
        }
        sb.replace(sb.length() - 1, sb.length(), " )");
        sb.append(" VALUES (");
        sb.append("?,".repeat(Math.max(0, properties.size() - 1)));
        sb.replace(sb.length() - 1, sb.length(), ")");

        return sb.toString();
    }

    /**
     * create and return a sql query for updating a T object from database.
     * @param properties is a map with fields as keys and objects as values, which represents the fields and there values of the object we want to update.
     * @return a sql query for updating a T entity from database.
     */
    private String createUpdateQuery(Map<Field, Object> properties) {

        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(ConnectionFactory.DATABASE);
        sb.append(".");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        Field field = null;
        for (Map.Entry<Field, Object> entry: properties.entrySet()) {
            if (!entry.getKey().getName().equals("id")) {
                sb.append(entry.getKey().getName());
                sb.append(" = ?, ");
            } else {
                field = entry.getKey();
            }
        }

        sb.replace(sb.length() - 2, sb.length(), "");
        sb.append(" WHERE id = ");

        sb.append(properties.get(field));

        return sb.toString();
    }

    /**
     * create and return a sql query for deleting a T object from database.
     * @return a sql query for deleting a T entity from database.
     */
    private String createDeleteQuery() {

        return "DELETE FROM " +
                ConnectionFactory.DATABASE +
                "." +
                type.getSimpleName() +
                " WHERE id = ?";
    }

    /**
     * This method call the createSelectAllQuery method for creating a sql query
     * After that, will set the parameters of the query and call the statement.
     * Call the createObjects for creating the list of needed objects.
     * In the final, we close the resultSet, statement and the connection.
     * @return a list of T objects from database or null if we had an error.
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = createSelectAllQuery();

        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            return createObjects(resultSet);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    /**
     * This method call the createSelectQuery method for creating a sql query
     * After that, will set the parameters of the query and call the statement.
     * Call the createObjects for creating the list of needed objects and we take just the first one.
     * In the final, we close the resultSet, statement and the connection.
     * @param id is the id depending on which we want to find.
     * @return a T object from database or null if we had an error.
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * This method call the createInsertQuery method for creating a sql query
     * After that, will set the parameters of the query and call the statement.
     * Take the id generated by insertion in the database and set that id as the id of the T object we just inserted.
     * In the final, we close the resultSet, statement and the connection.
     * @param t is the T object we want to insert into database.
     * @return the T object inserted into database or null if we had an error.
     */
    public T insert(T t) {
        Map<Field, Object> properties = retrieveProperties(t);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = createInsertQuery(properties);

        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            int cont = 1;
            for (Map.Entry<Field, Object> entry: properties.entrySet()) {
                if (!entry.getKey().getName().equals("id")) {

                    if (entry.getValue().getClass() == String.class) {
                        preparedStatement.setString(cont, (String) entry.getValue());
                    } else if (entry.getValue().getClass() == Integer.class) {
                        preparedStatement.setInt(cont, (Integer) entry.getValue());
                    } else if (entry.getValue().getClass() == Float.class) {
                        preparedStatement.setFloat(cont, (Float) entry.getValue());
                    } else if (entry.getValue().getClass() == Client.class) {
                        int id = ((Client)entry.getValue()).getId();
                        preparedStatement.setInt(cont, id);
                    } else if (entry.getValue().getClass() == Product.class) {
                        int id = ((Product)entry.getValue()).getId();
                        preparedStatement.setInt(cont, id);
                    }

                    cont++;
                }
            }
            int insertedId = -1;
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                insertedId = resultSet.getInt(1);
            }

            if (insertedId == -1) {
                return null;
            }

            PropertyDescriptor propertyDescriptor = new PropertyDescriptor("id", type);
            Method method = propertyDescriptor.getWriteMethod();
            method.invoke(t, insertedId);

            return t;

        } catch (SQLException | IntrospectionException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    /**
     * This method call the createUpdateQuery method for creating a sql query
     * After that, will set the parameters of the query and call the statement.
     * In the final, we close the resultSet, statement and the connection.
     * @param t is the T object we want to update into database.
     * @return the T object updated into database or null if we had an error.
     */
    public T update(T t) {
        Map<Field, Object> properties = retrieveProperties(t);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String query = createUpdateQuery(properties);

        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            int updatedId = -1;
            int cont = 1;
            for (Map.Entry<Field, Object> entry: properties.entrySet()) {
                if (!entry.getKey().getName().equals("id")) {

                    if (entry.getValue().getClass() == String.class) {
                        preparedStatement.setString(cont, (String) entry.getValue());
                    } else if (entry.getValue().getClass() == Integer.class) {
                        preparedStatement.setInt(cont, (Integer) entry.getValue());
                    } else if (entry.getValue().getClass() == Float.class) {
                        preparedStatement.setFloat(cont, (Float) entry.getValue());
                    } else if (entry.getValue().getClass() == Client.class) {
                        int id = ((Client)entry.getValue()).getId();
                        preparedStatement.setInt(cont, id);
                    } else if (entry.getValue().getClass() == Product.class) {
                        int id = ((Product)entry.getValue()).getId();
                        preparedStatement.setInt(cont, id);
                    }

                    cont++;
                } else {
                    updatedId = (Integer) entry.getValue();
                }
            }
            preparedStatement.execute();

            return findById(updatedId);


        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        }  finally {
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    /**
     * This method call the createDeleteQuery method for creating a sql query
     * After that, will set the parameters of the query and call the statement.
     * In the final, we close the resultSet, statement and the connection.
     * @param t is the T object we want to delete from database.
     * @return the T object deleted from database or null if we had an error.
     */
    public T delete(T t) {
        Map<Field, Object> properties = retrieveProperties(t);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String query = createDeleteQuery();

        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            for (Map.Entry<Field, Object> entry: properties.entrySet()) {
                if (entry.getKey().getName().equals("id")) {
                    preparedStatement.setInt(1, (Integer) entry.getValue());
                }
            }
            preparedStatement.execute();

            return t;

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        }  finally {
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }

        return null;
    }
}
