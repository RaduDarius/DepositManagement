package BusinessLogicPackage.validators;

import ModelPackage.Product;

/**
 * This class is a validator for the product price.
 */

public class PriceValidator implements Validator<Product>{

    /**
     * This method validates the product price.
     * It will throw an exception if the price product is negative.
     * @param product is the product which will be validated.
     */

    @Override
    public void validate(Product product) {

        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("The Product price is negative !");
        }

    }
}
