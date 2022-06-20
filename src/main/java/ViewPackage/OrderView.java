package ViewPackage;

import BusinessLogicPackage.ClientBLL;
import BusinessLogicPackage.OrderBLL;
import BusinessLogicPackage.ProductBLL;
import ModelPackage.Client;
import ModelPackage.Order;
import ModelPackage.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * View class.
 * It contains a menu for choosing what operation we want to perform on the order entities.
 */
public class OrderView extends JFrame{
    private JPanel mainPanel;
    private JList<Client> listClients;
    private JList<Product> listProducts;
    private JButton submitOrderButton;
    private JTextField txtOrderAmount;
    private JTable tableOrders;
    private JButton backButton;
    private JButton billButton;

    private final String title = "Order frame";

    /**
     * The class constructor
     */
    public OrderView() {
        setTitle(title);
        setSize(750, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        setList();
        setTable();

        setContentPane(mainPanel);
        setVisible(true);
    }

    /**
     * The method go through all object's attributes and store them in a list
     * @param object is an object whose attributes we want to know
     * @return the list of fields of the object received as parameter
     */
    private ArrayList<Field> getAttributes(Object object) {
        ArrayList<Field> fields = new ArrayList<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                fields.add(field);
            } catch (IllegalArgumentException  e) {
                e.printStackTrace();
            }
        }
        return fields;
    }

    /**
     * This method sets the JTable's number of columns and rows
     * After that sets the Table's header with the name of the order's attributes
     * In the end, it will populate the table with all the orders in the database.
     */
    public void setTable() {

        OrderBLL orderBLL = new OrderBLL();
        ArrayList<Order> orders = (ArrayList<Order>) orderBLL.findAll();

        ArrayList<Field> fields = getAttributes(new Order());

        TableModel tableModel = new DefaultTableModel(orders.size() + 1, fields.size());
        for (int i = 0; i < fields.size(); i++) {
            tableModel.setValueAt(fields.get(i).getName(), 0, i);
        }

        int cont = 1;
        for (Order o: orders) {
            int col = 0;
            for (Field field : o.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {

                    tableModel.setValueAt(field.get(o), cont, col);

                    col++;
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            cont++;
        }

        tableOrders.setModel(tableModel);
    }

    /**
     * This method will call the findAll methods from ClientBLL and ProductBLL
     * After that will set the listClients with those clients and the listProducts with those products.
     */
    public void setList() {

        ClientBLL clientBLL = new ClientBLL();
        ProductBLL productBLL = new ProductBLL();
        ArrayList<Client> clients = (ArrayList<Client>) clientBLL.findAll();
        ArrayList<Product> products = (ArrayList<Product>) productBLL.findAll();

        Client[] dataClients = new Client[clients.size()];
        Product[] dataProducts = new Product[products.size()];
        int cont = 0;
        for (Client c: clients) {
            dataClients[cont++] = c;
        }
        cont = 0;
        for (Product p: products) {
            dataProducts[cont++] = p;
        }

        listClients.setListData(dataClients);
        listProducts.setListData(dataProducts);
    }

    /**
     * This method adds an ActionListener to the submitOrderButton
     * @param a is an ActionListener who will be added to the submitOrderButton
     */
    public void addSubmitOrderButtonListener(ActionListener a) {
        submitOrderButton.addActionListener(a);
    }

    /**
     * This method adds an ActionListener to the backButton
     * @param a is an ActionListener who will be added to the backButton
     */
    public void addBackButtonListener(ActionListener a) {backButton.addActionListener(a);}

    /**
     * This method adds an ActionListener to the billButton
     * @param a is an ActionListener who will be added to the billButton
     */
    public void addBillButtonListener(ActionListener a) {billButton.addActionListener(a);}

    /**
     * The method is used for choosing the client who want to make an order.
     * @return the client who has been selected.
     * @throws Exception with the message "No client selected !", because the selected client was null
     */
    public Client getClientSelected() throws Exception {
        if (listClients.getSelectedValue() != null) {
            return listClients.getSelectedValue();
        } else {
            throw new Exception("No client selected !");
        }
    }

    /**
     * The method is used for choosing the product which is ordered.
     * @return the product who has been selected.
     * @throws Exception with the message "No product selected !", because the selected product was null
     */
    public Product getProductSelected() throws Exception {
        if (listProducts.getSelectedValue() != null) {
            return listProducts.getSelectedValue();
        } else {
            throw new Exception("No product selected !");
        }
    }

    /**
     * This method is used for returning what we inserted in the amount TextField
     * @return the amount which is ordered
     * @throws Exception with the message "The amount must be a number !", because the inserted amount isn't a number
     */
    public int getOrderAmount() throws Exception {
        try {
            return Integer.parseInt(txtOrderAmount.getText());
        } catch (Exception e) {
            throw new Exception("The amount must be a number !");
        }
    }
}
