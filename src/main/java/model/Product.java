package model;

/**
 * The Product class is used to create objects that represent the products in the database.
 * It has the following attributes:
 * id - the id of the product,
 * name - the name of the product,
 * price - the price of the product,
 * quantity - the quantity of the product in stock.
 */
public class Product {
    public int id;
    private String name;
    private float price;
    private int quantity;

    //no argument constructor for using .newInstance() method
    public Product () {
    }

    public Product (int id, String name, float price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Product (String name, float price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
