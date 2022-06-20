package ControllerPackage;

import ViewPackage.ClientOperationsView;
import ViewPackage.HomePage;
import ViewPackage.OrderView;
import ViewPackage.ProductOperationsView;

/**
 * Controller class
 * This class is used for controlling the homepage view.
 */
public class HomePageController {

    private HomePage view;

    /**
     * The class constructor.
     * @param view is the view we want to a controller for.
     */
    public HomePageController(HomePage view) {
        this.view = view;

        view.addOperateOnClientButtonListener(e -> controlClient());
        view.addOperateOnProductButtonListener(e -> controlProduct());
        view.addOrderButtonListener(e -> controlOrder());
    }

    /**
     * This method is used for the control of the button which opens the menu for operations on orders
     */
    private void controlOrder() {
        view.dispose();
        new OrderController(new OrderView());
    }

    /**
     * This method is used for the control of the button which opens the menu for operations on products
     */
    private void controlProduct() {
        view.dispose();
        new ProductOperationsController(new ProductOperationsView());
    }

    /**
     * This method is used for the control of the button which opens the menu for operations on clients
     */
    private void controlClient() {
        view.dispose();
        new ClientOperationsController(new ClientOperationsView());
    }

}
