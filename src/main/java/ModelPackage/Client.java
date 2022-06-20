package ModelPackage;

/**
 * The client model class.
 * This class contains id, name, address, email and age.
 */
public class Client {

    private int id;
    private String name;
    private String address;
    private String email;
    private int age;

    /**
     * The class constructor.
     */
    public Client() {}

    /**
     * The class constructor.
     * @param id is the id of the client we want to create.
     */
    public Client(int id) {
        this.id = id;
    }

    /**
     * The class constructor.
     * @param id is the id of the client we want to create.
     * @param name is the name of the client we want to create.
     * @param address is the address of the client we want to create.
     * @param email is the email of the client we want to create.
     * @param age is the age of the client we want to create.
     */
    public Client(int id, String name, String address, String email, int age) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.age = age;
    }

    /**
     * The class constructor.
     * @param name is the name of the client we want to create.
     * @param address is the address of the client we want to create.
     * @param email is the email of the client we want to create.
     * @param age is the age of the client we want to create.
     */
    public Client(String name, String address, String email, int age) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.age = age;
    }


    /**
     * This method creates a string with the client's attributes.
     * @return a string which contains the client's attributes.
     */
    @Override
    public String toString() {
        return "id: " + id + " " +
                "name: " + name + " " +
                "address: " + address + " " +
                "email: " + email + " " +
                "age: " + age;
    }

    /**
     * Getter for the client's id
     * @return the client's id
     */
    public int getId() {
        return id;
    }

    /**
     * setter for the client's id
     * @param id is the client's id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for the client's name
     * @return the client's name
     */
    public String getName() {
        return name;
    }

    /**
     * setter for the client's name
     * @param name is the client's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the client's address
     * @return the client's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * setter for the client's address
     * @param address is the client's address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter for the client's email
     * @return the client's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * setter for the client's email
     * @param email is the client's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for the client's age
     * @return the client's age
     */
    public int getAge() {
        return age;
    }

    /**
     * setter for the client's age
     * @param age is the client's age
     */
    public void setAge(int age) {
        this.age = age;
    }
}
