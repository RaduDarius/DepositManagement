import BusinessLogicPackage.ClientBLL;
import BusinessLogicPackage.OrderBLL;
import BusinessLogicPackage.ProductBLL;
import ConnectionPackage.ConnectionFactory;
import ControllerPackage.HomePageController;
import DataAccessPackage.ClientDAO;
import DataAccessPackage.OrderDAO;
import ModelPackage.Client;
import ModelPackage.Order;
import ModelPackage.Product;
import ViewPackage.HomePage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    protected static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());

    public static void main(String[] args) {

        ClientBLL clientBLL = new ClientBLL();
        ProductBLL productBLL = new ProductBLL();
        OrderBLL orderBLL = new OrderBLL();
        OrderDAO orderDAO = new OrderDAO();

        Client client = null;
        Product product = null;
        Order order = null;

        try {

//            client = clientBLL.delete(new Client(2));

//            ArrayList<Order> orders = (ArrayList<Order>) orderDAO.findByIdClient(2);

//            System.out.println(orders);

        } catch (Exception ex) {
            LOGGER.log(Level.INFO, ex.getMessage());
        }

        new HomePageController(new HomePage());

    }

}
