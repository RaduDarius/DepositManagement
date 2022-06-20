package BusinessLogicPackage.validators;

import ModelPackage.Order;

/**
 * This class is a validator for the order amount
 */

public class AmountValidator implements Validator<Order>{

    /**
     * This method validates the order amount and the between the order amount and the product stock
     * It will throw an exception if the order amount is negative or if the order amount is greater than product stock.
     * @param order is the order which will be validated.
     */

    @Override
    public void validate(Order order) {


        if (order.getAmount() < 0) {
            throw new IllegalArgumentException("The Order amount is negative !");
        }

        if ((order.getProduct().getStock() - order.getAmount()) < 0) {
            throw new IllegalArgumentException("Not enough stock for the order !");
        }
    }
}
