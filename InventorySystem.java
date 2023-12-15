import java.util.ArrayList;
import java.util.List;

public class InventorySystem {
    private List<Product> products;

    public InventorySystem() {
        this.products = new ArrayList<>();
    }

    public void addProduct(int ID, String name, int Price, int stock) {
        products.add(new Product(ID, name, Price, stock));
    }

    public List<Product> getProducts() {
        return products;
    }

    public Product findProductByName(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }
}
