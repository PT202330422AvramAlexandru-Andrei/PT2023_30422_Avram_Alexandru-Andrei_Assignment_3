package model;

import dao.AbstractDAO;

/**
 * This class is used to model the orders table, which contains the following fields:
 * id - the order id,
 * product_id - the product being ordered,
 * client_id - the client ordering the product,
 * quantity - how much of the product is being ordered,
 * total - total price of the order.
 */
public class Orders {
    int id;
    int product_id;
    int client_id;
    int quantity;
    float total;

    public Orders(int id, int product_id, int client_id, int quantity, float total) {
        this.id = id;
        this.product_id = product_id;
        this.client_id = client_id;
        this.quantity = quantity;
        this.total = total;
    }

    public Orders(){}

    public Orders(int product_id, int client_id, int quantity) {
        this.product_id = product_id;
        Product product = (Product) AbstractDAO.findById(this.product_id, Product.class);
        this.client_id = client_id;
        this.quantity = quantity;
        this.total = product.getPrice() * quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct() {
        return product_id;
    }

    public void setProduct(int products) {
        this.product_id = products;
    }

    public int getClientId() {
        return client_id;
    }

    public void setClientId(int client_id) {
        this.client_id = client_id;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
