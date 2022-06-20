package ViewPackage;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * View class.
 * This class is used as main frame or homepage.
 * It contains 3 buttons for choosing on what entity we want to perform operations.
 */
public class HomePage extends JFrame{
    private JPanel mainPanel;
    private JButton operateOnClientButton;
    private JButton operateOnProductButton;
    private JButton orderButton;

    private final String title = "Homepage";

    /**
     * The class constructor.
     */
    public HomePage() {

        setTitle(title);
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        setContentPane(mainPanel);
        setVisible(true);
    }

    /**
     * This method adds an ActionListener to the operateOnClientButton
     * @param a is an ActionListener who will be added to the operateOnClientButton
     */
    public void addOperateOnClientButtonListener(ActionListener a) {
        operateOnClientButton.addActionListener(a);
    }

    /**
     * This method adds an ActionListener to the operateOnProductButton
     * @param a is an ActionListener who will be added to the operateOnProductButton
     */
    public void addOperateOnProductButtonListener(ActionListener a) {
        operateOnProductButton.addActionListener(a);
    }

    /**
     * This method adds an ActionListener to the orderButton
     * @param a is an ActionListener who will be added to the orderButton
     */
    public void addOrderButtonListener(ActionListener a) {
        orderButton.addActionListener(a);
    }
}
