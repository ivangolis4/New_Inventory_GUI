import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InventorySystem {
    private List<Product> products;
    private List<ExitTransaction> exitTransactions;

    public InventorySystem() {
        this.products = new ArrayList<>();
        this.exitTransactions = new ArrayList<>();
    }

    public void addProduct(int ID, String name, int price, int stock) {
        products.add(new Product(ID, name, price, stock));
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

    public void addExitTransaction(String productName, String remarks, int quantity) {
        Product product = findProductByName(productName);

        if (product != null && product.getStock() >= quantity) {
            ExitTransaction exitTransaction = new ExitTransaction(productName, remarks, quantity);
            exitTransactions.add(exitTransaction);
        }
    }

    public List<ExitTransaction> getExitTransactions() {
        return exitTransactions;
    }

    public void saveProductsToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Product product : products) {
                writer.write(product.getID() + "," + product.getName() + "," + product.getPrice() + "," + product.getStock());
                writer.newLine();
            }
            System.out.println("Product information saved to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadProductsFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                int price = Integer.parseInt(data[2]);
                int stock = Integer.parseInt(data[3]);
                addProduct(id, name, price, stock);
            }
            System.out.println("Product information loaded from file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveExitTransactionsToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (ExitTransaction exitTransaction : exitTransactions) {
                writer.write(exitTransaction.getProductName() + "," + exitTransaction.getRemarks() + "," + exitTransaction.getQuantity());
                writer.newLine();
            }
            System.out.println("Exit transactions saved to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class ExitTransaction {
        private String productName;
        private String remarks;
        private int quantity;

        public ExitTransaction(String productName, String remarks, int quantity) {
            this.productName = productName;
            this.remarks = remarks;
            this.quantity = quantity;
        }

        public String getProductName() {
            return productName;
        }

        public String getRemarks() {
            return remarks;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}
