package model;

import java.util.List;

public class Order {
    int id;
    List<Product> products;
    int client_id;
    float total;

    public Order(int id, List<Product> products, int client_id, float total) {
        this.id = id;
        this.products = products;
        this.client_id = client_id;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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
