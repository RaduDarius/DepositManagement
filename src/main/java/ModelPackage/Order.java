package ModelPackage;

/**
 * The order model class.
 * This class contains id, client, product, amount.
 */
public class Order {

    private int id;
    private Client client;
    private Product product;
    private int amount;

    /**
     * The class constructor.
     */
    public Order() {
    }

    /**
     * The class constructor.
     * @param id is the id of the order we want to create.
     * @param client is the client who made the order we want ot create.
     * @param product is the product who is ordered in the order we want to create.
     * @param amount is the quantity of the product in the order we want ot create.
     */
    public Order(int id, Client client, Product product, int amount) {
        this.id = id;
        this.client = client;
        this.product = product;
        this.amount = amount;
    }

    /**
     * The class constructor.
     * @param client is the client who made the order we want ot create.
     * @param product is the product who is ordered in the order we want to create.
     * @param amount is the quantity of the product in the order we want ot create.
     */
    public Order(Client client, Product product, int amount) {
        this.client = client;
        this.product = product;
        this.amount = amount;
    }

    /**
     * This method creates a string with the order's attributes.
     * @return a string which contains the order's attributes.
     */
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", client=" + client +
                ", product=" + product +
                ", amount=" + amount +
                '}';
    }

    /**
     * Getter for the order's id
     * @return the order's id
     */
    public int getId() {
        return id;
    }

    /**
     * setter for the order's id
     * @param id is the order's id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * getter for the order's client
     * @return the client who made the order.
     */
    public Client getClient() {
        return client;
    }

    /**
     * setter for the order's client
     * @param client is the new client
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * getter for the product who has been ordered
     * @return the product who has been ordered
     */
    public Product getProduct() {
        return product;
    }

    /**
     * setter for the product who was ordered.
     * @param product is the new product.
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * getter for the order's amount
     * @return the order's amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * setter for the order's amount
     * @param amount is the new amount for the order.
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
