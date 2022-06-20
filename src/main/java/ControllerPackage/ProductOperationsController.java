package ControllerPackage;

import BusinessLogicPackage.ProductBLL;
import ConnectionPackage.ConnectionFactory;
import ModelPackage.Product;
import ViewPackage.HomePage;
import ViewPackage.ProductOperationsView;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class
 * This class is used for controlling the ProductOperationsView frame.
 */
public class ProductOperationsController {

    protected static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());

    private final ProductOperationsView view;
    private final ProductBLL productBLL;

    /**
     * The class constructor.
     * @param view is the view we want to a controller for.
     */
    public ProductOperationsController(ProductOperationsView view) {
        this.view = view;
        productBLL = new ProductBLL();

        view.addInsertButtonListener(e -> controlInsert());
        view.addUpdateButtonListener(e -> controlUpdate());
        view.addDeleteButtonListener(e -> controlDelete());
        view.addBackButtonListener(e -> controlBack());
    }

    /**
     * This method is used for the control of the back button which sends you back to the previous frame
     */
    private void controlBack() {
        view.dispose();
        new HomePageController(new HomePage());
    }

    /**
     * This method is used for the control of the delete button
     * We take the id of the product we want to delete
     * And after that call the update method from ProductBLL class
     * If the operation was successfully realised we print a success message, else we print an error message.
     * Before performing any deleting we ask again if really is the wanted operation.
     */
    private void controlDelete() {
        try {

            int valid = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this product ?");

            if (valid == 0) {
                int idSelected = view.getIdProductSelectedFromDelete();

                Product product = new Product(idSelected);

                product = productBLL.delete(product);
                if (product != null) {
                    JOptionPane.showMessageDialog(null, "The product was successfully deleted !");
                    view.setTable();
                    view.setList();
                } else {
                    JOptionPane.showMessageDialog(null, "The product was not deleted !");
                }
            }

        } catch (Exception ex) {
            LOGGER.log(Level.INFO, ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    /**
     * This method is used for the control of the update button
     * We take the id of the product we want to update
     * We take the input from the TextFields, create a product object
     * And after that call the update method from ProductBLL class
     * If the operation was successfully realised we print a success message, else we print an error message.
     */
    private void controlUpdate() {
        try {
            int idSelected = view.getIdProductSelectedFromUpdate();
            String name = view.getProductNameFromUpdate();
            int stock = view.getProductStockFromUpdate();
            float price = view.getProductPriceFromUpdate();

            Product product = new Product(idSelected, name, stock, price);
            product = productBLL.update(product);

            if (product != null) {
                JOptionPane.showMessageDialog(null, "The product was successfully updated !");
                view.setTable();
                view.setList();
            } else {
                JOptionPane.showMessageDialog(null, "The product was not updated !");
            }

        } catch (Exception ex) {
            LOGGER.log(Level.INFO, ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    /**
     * This method is used for the control of the insert button
     * We take the input from the TextFields, create a product object
     * And after that call the insert method from ProductBLL class
     * If the operation was successfully realised we print a success message, else we print an error message.
     */
    private void controlInsert() {
        try {
            String name = view.getProductNameFromInsert();
            int stock = view.getProductStockFromInsert();
            float price = view.getProductPriceFromInsert();

            Product product = new Product(name, stock, price);
            product = productBLL.insert(product);

            if (product != null) {
                JOptionPane.showMessageDialog(null, "The product was successfully inserted !");
                view.setTable();
                view.setList();
            } else {
                JOptionPane.showMessageDialog(null, "The product was not inserted !");
            }

        } catch (Exception ex) {
            LOGGER.log(Level.INFO, ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }


}
