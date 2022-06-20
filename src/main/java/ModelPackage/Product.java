package ModelPackage;

/**
 * The product model class.
 * This class contains id, name, stock, price.
 */
public class Product {

    private int id;
    private String name;
    private int stock;
    private float price;

    /**
     * The class constructor.
     */
    public Product() {
    }

    /**
     * The class constructor.
     * @param id is the id of the product we want to create.
     */
    public Product(int id) {
        this.id = id;
    }

    /**
     * The class constructor.
     * @param name is the name of the product we want to create.
     * @param stock is the stock of the product we want to create.
     * @param price is the price of the product we want to create.
     */
    public Product(String name, int stock, float price) {
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    /**
     * The class constructor.
     * @param id is the id of the product we want to create.
     * @param name is the name of the product we want to create.
     * @param stock is the stock of the product we want to create.
     * @param price is the price of the product we want to create.
     */
    public Product(int id, String name, int stock, float price) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    /**
     * This method creates a string with the product's attributes.
     * @return a string which contains the product's attributes.
     */
    @Override
    public String toString() {
        return "id: " + id + " " +
                "name: " + name + " " +
                "stock: " + stock + " " +
                "price: " + price;
    }

    /**
     * Getter for the product's id
     * @return the product's id
     */
    public int getId() {
        return id;
    }

    /**
     * setter for the product's id
     * @param id is the product's id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for the product's name
     * @return the product's name
     */
    public String getName() {
        return name;
    }

    /**
     * setter for the product's name
     * @param name is the product's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the product's stock
     * @return the product's stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * setter for the product's stock
     * @param stock is the product's stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Getter for the product's price
     * @return the product's price
     */
    public float getPrice() {
        return price;
    }

    /**
     * setter for the product's price
     * @param price is the product's price
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * This method decrements the stock with the amount received as parameter.
     * @param amount is the amount with whom we need to decrement the stock
     */
    public void decrementStock(int amount) {

        this.stock -= amount;

    }
}
