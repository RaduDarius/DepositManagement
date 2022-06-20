package ControllerPackage;

import BusinessLogicPackage.OrderBLL;
import ConnectionPackage.ConnectionFactory;
import ModelPackage.Client;
import ModelPackage.Order;
import ModelPackage.Product;
import ViewPackage.HomePage;
import ViewPackage.OrderView;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class
 * This class is used for controlling the OrderView frame.
 */
public class OrderController {

    protected static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());

    private final OrderView view;
    private final OrderBLL orderBLL;

    private ArrayList<Order> orders = new ArrayList<>();

    /**
     * The class constructor.
     * @param view is the view we want to a controller for.
     */
    public OrderController(OrderView view) {
        this.view = view;
        orderBLL = new OrderBLL();

        view.addSubmitOrderButtonListener(e -> controlSubmit());
        view.addBackButtonListener(e -> controlBack());
        view.addBillButtonListener(e -> controlBill());
    }

    /**
     * This method prints in a file named bill.txt the bill for every client who has made an order since the application is running
     * We create a map with clients as keys and lists of orders as values.
     * After that for every client who made an order, we calculate the total price which he needs to pay.
     * In the end we print that price in the file.
     */
    private void controlBill() {
        try {
            FileOutputStream file  = new FileOutputStream("bill.txt");

            PrintStream out = new PrintStream(file);

            HashMap<Client, ArrayList<Order>> clientsBill = new HashMap<>();

            for (Order o: orders) {
                clientsBill.put(o.getClient(), new ArrayList<>());
            }

            for (Order o: orders) {
                clientsBill.get(o.getClient()).add(o);
            }

            for (Map.Entry<Client, ArrayList<Order>> entry: clientsBill.entrySet()) {
                out.print("For client {" + entry.getKey().toString() + "} the bill is: ");
                float totalPrice = 0;
                for (Order o: entry.getValue()) {
                    totalPrice += o.getAmount() * o.getProduct().getPrice();
                }
                out.println(totalPrice + " RON");
            }

            JOptionPane.showMessageDialog(null, "The bill was successfully generated !");

        }catch (FileNotFoundException ex) {
            LOGGER.log(Level.INFO, ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    /**
     * This method is used for the control of the back button which sends you back to the previous frame
     */
    private void controlBack() {
        view.dispose();
        new HomePageController(new HomePage());
    }

    /**
     * This method is used for the control of the submit button
     * We take the input from the TextFields, create an order object
     * And after that call the insert method from OrderBLL class
     * If the operation was successfully realised we print a success message, else we print an error message.
     */
    private void controlSubmit() {

        try {
            Client client = view.getClientSelected();
            Product product = view.getProductSelected();
            int amount = view.getOrderAmount();

            Order order = new Order(client, product, amount);
            order = orderBLL.insert(order);
            if (order != null) {
                JOptionPane.showMessageDialog(null, "The order was successfully inserted !");
                view.setList();
                view.setTable();
                orders.add(order);
            } else {
                JOptionPane.showMessageDialog(null, "The order was not inserted !");
            }

        } catch (Exception ex) {
            LOGGER.log(Level.INFO, ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }
}
