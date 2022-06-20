package ViewPackage;

import BusinessLogicPackage.ProductBLL;
import ModelPackage.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * View class.
 * It contains a menu for choosing what operation we want to perform on the product entities.
 */
public class ProductOperationsView extends JFrame{

    private JPanel mainPanel;
    private JTextField txtProductPriceInsert;
    private JTextField txtProductNameInsert;
    private JButton insertButton;
    private JTextField txtProductPriceUpdate;
    private JTextField txtProductNameUpdate;
    private JButton updateButton;
    private JList<Product> listEditProduct;
    private JTable tableProducts;
    private JList<Product> listDeleteProduct;
    private JButton deleteButton;
    private JTextField txtProductStockInsert;
    private JTextField txtProductStockUpdate;
    private JButton backButton;

    private final String title = "Product operations frame";

    /**
     * The class constructor
     */
    public ProductOperationsView() {

        setTitle(title);
        setSize(550, 350);
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
     * After that sets the Table's header with the name of the product's attributes
     * In the end, it will populate the table with all the products in the database.
     */
    public void setTable() {
        ProductBLL productBLL = new ProductBLL();
        ArrayList<Product> products = (ArrayList<Product>) productBLL.findAll();

        ArrayList<Field> fields = getAttributes(new Product());

        TableModel tableModel = new DefaultTableModel(products.size() + 1, fields.size());
        for (int i = 0; i < fields.size(); i++) {
            tableModel.setValueAt(fields.get(i).getName(), 0, i);
        }

        int cont = 1;
        for (Product c: products) {
            int col = 0;
            for (Field field : c.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    tableModel.setValueAt(field.get(c), cont, col);
                    col++;
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            cont++;
        }

        tableProducts.setModel(tableModel);
    }

    /**
     * This method will call the findAll method from ProductBLL
     * After that will set both lists with those products.
     */
    public void setList() {
        ProductBLL productBLL = new ProductBLL();
        ArrayList<Product> products = (ArrayList<Product>) productBLL.findAll();

        Product[] data = new Product[products.size()];
        int cont = 0;
        for (Product p: products) {
            data[cont++] = p;
        }
        listEditProduct.setListData(data);
        listDeleteProduct.setListData(data);
    }

    /**
     * This method adds an ActionListener to the insertButton
     * @param a is an ActionListener who will be added to the insertButton
     */
    public void addInsertButtonListener(ActionListener a) {
        insertButton.addActionListener(a);
    }

    /**
     * This method adds an ActionListener to the updateButton
     * @param a is an ActionListener who will be added to the updateButton
     */
    public void addUpdateButtonListener(ActionListener a) {
        updateButton.addActionListener(a);
    }

    /**
     * This method adds an ActionListener to the deleteButton
     * @param a is an ActionListener who will be added to the deleteButton
     */
    public void addDeleteButtonListener(ActionListener a) {
        deleteButton.addActionListener(a);
    }

    /**
     * This method adds an ActionListener to the backButton
     * @param a is an ActionListener who will be added to the backButton
     */
    public void addBackButtonListener(ActionListener a) {
        backButton.addActionListener(a);
    }

    /**
     * The method is used for choosing the product who we want to update and return its id.
     * @return the id of the product who has been selected.
     * @throws Exception with the message "No product selected !", because the selected product was null
     */
    public int getIdProductSelectedFromUpdate() throws Exception {
        if (listEditProduct.getSelectedValue() != null) {
            return listEditProduct.getSelectedValue().getId();
        } else {
            throw new Exception("No product selected !");
        }
    }

    /**
     * The method is used for choosing the product who we want to delete and return its id.
     * @return the id of the product who has been selected.
     * @throws Exception with the message "No product selected !", because the selected product was null
     */
    public int getIdProductSelectedFromDelete() throws Exception {
        if (listDeleteProduct.getSelectedValue() != null) {
            return listDeleteProduct.getSelectedValue().getId();
        } else {
            throw new Exception("No client selected !");
        }
    }

    /**
     * This method is used for returning what we inserted in the name TextField
     * for the insert operation
     * @return the product's name which we want to insert
     */
    public String getProductNameFromInsert() {
        return txtProductNameInsert.getText();
    }

    /**
     * This method is used for returning what we inserted in the stock TextField
     * for the insert operation
     * @return the product's stock which we want to insert
     * @throws Exception with the message "The stock must be a number !", because the inserted stock isn't a number
     */
    public int getProductStockFromInsert() throws Exception {
        try {
            return Integer.parseInt(txtProductStockInsert.getText());
        } catch (Exception e) {
            throw new Exception("The stock must be a number !");
        }
    }

    /**
     * This method is used for returning what we inserted in the price TextField
     * for the insert operation
     * @return the product's price which we want to insert
     * @throws Exception with the message "The price must be a number !", because the inserted price isn't a number
     */
    public float getProductPriceFromInsert() throws Exception {
        try {
            return Float.parseFloat(txtProductPriceInsert.getText());
        } catch (Exception e) {
            throw new Exception("The price must be a number !");
        }
    }

    /**
     * This method is used for returning what we inserted in the name TextField
     * for the update operation
     * @return the product's name which we want to update
     */
    public String getProductNameFromUpdate() {
        return txtProductNameUpdate.getText();
    }

    /**
     * This method is used for returning what we inserted in the stock TextField
     * for the update operation
     * @return the product's stock which we want to update
     * @throws Exception with the message "The stock must be a number !", because the inserted stock isn't a number
     */
    public int getProductStockFromUpdate() throws Exception {
        try {
            return Integer.parseInt(txtProductStockUpdate.getText());
        } catch (Exception e) {
            throw new Exception("The stock must be a number !");
        }
    }

    /**
     * This method is used for returning what we inserted in the price TextField
     * for the update operation
     * @return the product's price which we want to update
     * @throws Exception with the message "The price must be a number !", because the inserted price isn't a number
     */
    public float getProductPriceFromUpdate() throws Exception {
        try {
            return Float.parseFloat(txtProductPriceUpdate.getText());
        } catch (Exception e) {
            throw new Exception("The price must be a number !");
        }
    }

}
