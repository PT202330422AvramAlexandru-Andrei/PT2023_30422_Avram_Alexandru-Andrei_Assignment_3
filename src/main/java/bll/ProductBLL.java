package bll;

import dao.AbstractDAO;
import model.Product;

public class ProductBLL {

    public static Product findProductById(int id) {
        return (Product) AbstractDAO.findById(id, Product.class);
    }

    public static int insertProduct(Product product) {
        return AbstractDAO.insert(product);
    }

    public static int deleteProduct(int id) {
        return AbstractDAO.delete(id, Product.class);
    }

    public static void updateProduct(int product, String name, int quantity, float price) {
        Product p = findProductById(product);
        p.setName(name);
        p.setQuantity(quantity);
        p.setPrice(price);
        AbstractDAO.update(p);
    }
}
