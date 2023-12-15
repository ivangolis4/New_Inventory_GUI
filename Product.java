import java.util.ArrayList;
import java.util.List;

public class Product {
    private String name;
    private int stock, ID, Price;
    private List<String> exitTransactions;

    public Product(int ID,String name, int Price, int stock) {
        this.name = name;
        this.stock = stock;
        this.ID =ID;
        this.Price = Price;
        this.exitTransactions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public int getID() {
        return ID;
    }

    public int getPrice() {
        return Price;
    }

    public List<String> getExitTransactions() {
        return exitTransactions;
    }

    public void addExitTransaction(String reason, int quantity) {
        exitTransactions.add("Exit Reason: " + reason + ", Quantity: " + quantity);
        stock -= quantity;
    }

    public void editProductName(String newName) {
        this.name = newName;
    }
}
