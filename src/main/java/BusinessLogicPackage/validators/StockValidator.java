package BusinessLogicPackage.validators;

import ModelPackage.Product;

/**
 * This class is a validator for the product stock.
 */

public class StockValidator implements Validator<Product>{

    /**
     * This method validates the product stock.
     * It will throw an exception if the product stock is negative.
     * @param product is the product which will be validated.
     */

    @Override
    public void validate(Product product) {
        if (product.getStock() < 0) {
            throw new IllegalArgumentException("The Product stock is negative !");
        }
    }
}
