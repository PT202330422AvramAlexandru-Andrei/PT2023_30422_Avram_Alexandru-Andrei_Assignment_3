package bll;

import dao.ProductDAO;
import model.Product;

public class ProductBLL {

    public Product findProductById(int id) {
        return ProductDAO.findById(id);
    }
}
