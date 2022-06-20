package ControllerPackage;

import BusinessLogicPackage.ClientBLL;
import ConnectionPackage.ConnectionFactory;
import ModelPackage.Client;
import ViewPackage.ClientOperationsView;
import ViewPackage.HomePage;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class
 * This class is used for controlling the ClientOperationsView frame.
 */
public class ClientOperationsController {

    protected static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());

    private final ClientOperationsView view;
    private final ClientBLL clientBLL;

    /**
     * The class constructor.
     * @param view is the view we want to a controller for.
     */
    public ClientOperationsController(ClientOperationsView view) {
        this.view = view;

        clientBLL = new ClientBLL();

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
     * This method is used for the control of the insert button.
     * We take the input from the TextFields, create a client object
     * And after that call the insert method from ClientBLL class
     * If the operation was successfully realised we print a success message, else we print an error message.
     */
    private void controlInsert() {

        try {
            String name = view.getClientNameFromInsert();
            String address = view.getClientAddressFromInsert();
            String email = view.getClientEmailFromInsert();
            int age = view.getClientAgeFromInsert();

            Client client = new Client(name, address, email, age);
            client = clientBLL.insert(client);
            if (client != null) {
                JOptionPane.showMessageDialog(null, "The client was successfully inserted !");
                view.setTable();
                view.setList();
            } else {
                JOptionPane.showMessageDialog(null, "The client was not inserted !");
            }
        } catch (Exception ex) {
            LOGGER.log(Level.INFO, ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    /**
     * This method is used for the control of the update button
     * We take the id of the client we want to update
     * We take the input from the TextFields, create a client object
     * And after that call the update method from ClientBLL class
     * If the operation was successfully realised we print a success message, else we print an error message.
     */
    private void controlUpdate() {

        try {
            int idSelected = view.getIdClientSelectedFromUpdate();
            String name = view.getClientNameFromUpdate();
            String address = view.getClientAddressFromUpdate();
            String email = view.getClientEmailFromUpdate();
            int age = view.getClientAgeFromUpdate();

            Client client = new Client(idSelected, name, address, email, age);

            client = clientBLL.update(client);
            if (client != null) {
                JOptionPane.showMessageDialog(null, "The client was successfully updated !");
                view.setList();
                view.setTable();
            } else {
                JOptionPane.showMessageDialog(null, "The client was not updated !");
            }

        } catch (Exception ex) {
            LOGGER.log(Level.INFO, ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    /**
     * This method is used for the control of the delete button
     * We take the id of the client we want to delete
     * And after that call the update method from ClientBLL class
     * If the operation was successfully realised we print a success message, else we print an error message.
     * Before performing any deleting we ask again if really is the wanted operation.
     */
    private void controlDelete() {
        try {

            int valid = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this client ?");

            if (valid == 0) {
                int idSelected = view.getIdClientSelectedFromDelete();

                Client client = new Client(idSelected);

                client = clientBLL.delete(client);
                if (client != null) {
                    JOptionPane.showMessageDialog(null, "The client was successfully deleted !");                view.setList();
                    view.setTable();
                    view.setList();
                } else {
                    JOptionPane.showMessageDialog(null, "The client was not deleted !");
                }
            }

        } catch (Exception ex) {
            LOGGER.log(Level.INFO, ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

}
