package ViewPackage;

import BusinessLogicPackage.ClientBLL;
import ModelPackage.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * View class.
 * It contains a menu for choosing what operation we want to perform on the client entities.
 */
public class ClientOperationsView extends JFrame{
    private JPanel mainPanel;
    private JTextField txtClientEmailInsert;
    private JTextField txtClientAgeInsert;
    private JTextField txtClientNameInsert;
    private JTextField txtClientAddressInsert;
    private JButton insertButton;
    private JButton updateButton;
    private JList<Client> listEditClient;
    private JTextField txtClientNameUpdate;
    private JTextField txtClientAddressUpdate;
    private JTextField txtClientEmailUpdate;
    private JTextField txtClientAgeUpdate;
    private JButton deleteButton;
    private JList<Client> listDeleteClient;
    private JTable tableClients;
    private JButton backButton;

    private final String title = "Client operations frame";

    /**
     * The class constructor
     */
    public ClientOperationsView() {

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
     * After that sets the Table's header with the name of the client's attributes
     * In the end, it will populate the table with all the clients in the database.
     */
    public void setTable() {

        ClientBLL clientBLL = new ClientBLL();
        ArrayList<Client> clients = (ArrayList<Client>) clientBLL.findAll();

        ArrayList<Field> fields = getAttributes(new Client());

        TableModel tableModel = new DefaultTableModel(clients.size() + 1, fields.size());
        for (int i = 0; i < fields.size(); i++) {
            tableModel.setValueAt(fields.get(i).getName(), 0, i);
        }

        int cont = 1;
        for (Client c: clients) {
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

        tableClients.setModel(tableModel);
    }

    /**
     * This method will call the findAll method from ClientBLL
     * After that will set both lists with those clients.
     */
    public void setList() {

        ClientBLL clientBLL = new ClientBLL();
        ArrayList<Client> clients = (ArrayList<Client>) clientBLL.findAll();

        Client[] data = new Client[clients.size()];
        int cont = 0;
        for (Client c: clients) {
            data[cont++] = c;
        }
        listEditClient.setListData(data);
        listDeleteClient.setListData(data);
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
     * The method is used for choosing the client who we want to update and return his id.
     * @return the id of the client who has been selected.
     * @throws Exception with the message "No client selected !", because the selected client was null
     */
    public int getIdClientSelectedFromUpdate() throws Exception {
        if (listEditClient.getSelectedValue() != null) {
            return listEditClient.getSelectedValue().getId();
        } else {
            throw new Exception("No client selected !");
        }
    }

    /**
     * The method is used for choosing the client who we want to delete and return his id.
     * @return the id of the client who has been selected.
     * @throws Exception with the message "No client selected !", because the selected client was null
     */
    public int getIdClientSelectedFromDelete() throws Exception {
        if (listDeleteClient.getSelectedValue() != null) {
            return listDeleteClient.getSelectedValue().getId();
        } else {
            throw new Exception("No client selected !");
        }
    }

    /**
     * This method is used for returning what we inserted in the name TextField
     * for the insert operation
     * @return the product's name which we want to insert
     */
    public String getClientNameFromInsert() {
        return txtClientNameInsert.getText();
    }

    /**
     * This method is used for returning what we inserted in the address TextField
     * for the insert operation
     * @return the client's address which we want to insert
     */
    public String getClientAddressFromInsert() {
        return txtClientAddressInsert.getText();
    }

    /**
     * This method is used for returning what we inserted in the email TextField
     * for the insert operation
     * @return the client's email which we want to insert
     */
    public String getClientEmailFromInsert() {
        return txtClientEmailInsert.getText();
    }

    /**
     * This method is used for returning what we inserted in the age TextField
     * for the insert operation
     * @return the client's age which we want to insert
     * @throws Exception with the message "The age must be a number !", because the inserted age isn't a number
     */
    public int getClientAgeFromInsert() throws Exception {
        try {
            return Integer.parseInt(txtClientAgeInsert.getText());
        } catch (Exception e) {
            throw new Exception("The age must be a number !");
        }
    }

    /**
     * This method is used for returning what we inserted in the name TextField
     * for the update operation
     * @return the product's name which we want to update
     */
    public String getClientNameFromUpdate() {
        return txtClientNameUpdate.getText();
    }

    /**
     * This method is used for returning what we inserted in the address TextField
     * for the update operation
     * @return the client's address which we want to update
     */
    public String getClientAddressFromUpdate() {
        return txtClientAddressUpdate.getText();
    }

    /**
     * This method is used for returning what we inserted in the email TextField
     * for the update operation
     * @return the client's email which we want to update
     */
    public String getClientEmailFromUpdate() {
        return txtClientEmailUpdate.getText();
    }

    /**
     * This method is used for returning what we inserted in the age TextField
     * for the update operation
     * @return the client's age which we want to update
     * @throws Exception with the message "The age must be a number !", because the inserted age isn't a number
     */
    public int getClientAgeFromUpdate() throws Exception {
        try {
            return Integer.parseInt(txtClientAgeUpdate.getText());
        } catch (Exception e) {
            throw new Exception("The age must be a number !");
        }
    }
}
