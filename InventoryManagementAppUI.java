import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public class InventoryManagementAppUI {
    private JFrame frame;
    private InventorySystem inventorySystem;
    private JTable jt;

    public InventoryManagementAppUI(InventorySystem inventorySystem) {
        this.inventorySystem = inventorySystem;
        createAndShowGUI();
        loadProductsFromTable(); // Load existing data when the UI is created

        // Add a shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Save product information when the program is closed
            inventorySystem.saveProductsToFile("products.txt");
        }));
    }

    private void createAndShowGUI() {
        frame = new JFrame("Inventory Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(630, 500);
        frame.setLocationRelativeTo(null);

        String[] column = {"ID", "NAME", "QUANTITY", "PRICE"};
        TableModel tableModel = new DefaultTableModel(column, 0);
        jt = new JTable(tableModel);
        jt.setPreferredSize(new Dimension(500, 400));
        jt.setDefaultEditor(Object.class, null);
        JScrollPane sp = new JScrollPane(jt);
        sp.setPreferredSize(new Dimension(465, 400));
        jt.setModel(tableModel);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.NORTH);
        frame.getContentPane().add(sp, BorderLayout.CENTER);
        placeComponents(panel);

        // Add a window listener to handle the close event
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Save product information when the program window is closed
                inventorySystem.saveProductsToFile("products.txt");
            }
        });

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JButton addButton = new JButton("Add Product");
        JButton addSupplyButton = new JButton("Add Supply"); // Updated button
        JButton exitButton = new JButton("Exit Product");
        JButton viewExitButton = new JButton("View Exit Transactions");

        panel.add(addButton);
        panel.add(addSupplyButton);
        panel.add(exitButton);
        panel.add(viewExitButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddProductFrame();
            }
        });

        addSupplyButton.addActionListener(new ActionListener() { // ActionListener for the "Add Supply" button
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddSupplyFrame();
            }
        });


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showExitProductFrame();
            }
        });

        viewExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showExitTransactions();
            }
        });

        
    }

    private void showAddProductFrame() {
        JFrame addProductFrame = new JFrame("Add Product");
        addProductFrame.setSize(300, 200);

        JPanel addProductPanel = new JPanel();
        addProductFrame.getContentPane().add(addProductPanel, BorderLayout.CENTER);
        addProductFrame.setLocationRelativeTo(null);

        JLabel nameLabel = new JLabel("Product Name:");
        JTextField nameTextField = new JTextField();
        JLabel stockLabel = new JLabel("Product Quantity:");
        JTextField stockTextField = new JTextField();
        JLabel priceLabel = new JLabel("Product Price");
        JTextField priceTextField = new JTextField();
        JButton addButton = new JButton("Add");

        addProductPanel.add(nameLabel);
        addProductPanel.add(nameTextField);
        addProductPanel.add(stockLabel);
        addProductPanel.add(stockTextField);
        addProductPanel.add(priceLabel);
        addProductPanel.add(priceTextField);
        addProductPanel.add(addButton);
        addProductPanel.setLayout(new GridLayout(4, 2, 1, 1));

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                String stockStr = stockTextField.getText();
                int id = new Random().nextInt(1000) + 1; // Randomized ID

                if (!name.isEmpty() && !stockStr.isEmpty()) {
                    int stock = Integer.parseInt(stockStr);
                    int price = Integer.parseInt(priceTextField.getText());
                    inventorySystem.addProduct(id, name, price, stock);
                    inventorySystem.saveProductsToFile("products.txt");
                    loadProductsToTable(); // Update the table after adding a product
                    JOptionPane.showMessageDialog(null, "Product added successfully!");
                    addProductFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter valid product details.");
                }
            }
        });

        addProductFrame.setVisible(true);
    }

    private void showAddSupplyFrame() {
        JFrame addSupplyFrame = new JFrame("Add Supply");
        addSupplyFrame.setSize(300, 200);

        JPanel addSupplyPanel = new JPanel();
        addSupplyFrame.getContentPane().add(addSupplyPanel, BorderLayout.CENTER);
        addSupplyFrame.setLocationRelativeTo(null);

        JLabel nameLabel = new JLabel("Product Name:");
        JTextField nameTextField = new JTextField();
        JLabel quantityLabel = new JLabel("Quantity to Add:");
        JTextField quantityTextField = new JTextField();
        JButton addSupplyButton = new JButton("Add Supply");

        addSupplyPanel.add(nameLabel);
        addSupplyPanel.add(nameTextField);
        addSupplyPanel.add(quantityLabel);
        addSupplyPanel.add(quantityTextField);
        addSupplyPanel.add(addSupplyButton);
        addSupplyPanel.setLayout(new GridLayout(3, 2, 1, 1));

        addSupplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                String quantityStr = quantityTextField.getText();

                if (!name.isEmpty() && !quantityStr.isEmpty()) {
                    int quantityToAdd = Integer.parseInt(quantityStr);
                    Product product = inventorySystem.findProductByName(name);

                    if (product != null) {
                        product.addSupply(quantityToAdd);
                        inventorySystem.saveProductsToFile("products.txt");
                        loadProductsToTable(); // Update the table after adding supply
                        JOptionPane.showMessageDialog(null, "Supply added successfully!");
                        addSupplyFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Product not found!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter valid details.");
                }
            }
        });

        addSupplyFrame.setVisible(true);
    }


    private void showExitProductFrame() {
        JFrame exitProductFrame = new JFrame("Exit Product");
        exitProductFrame.setSize(300, 200);

        JPanel exitProductPanel = new JPanel();
        exitProductFrame.getContentPane().add(exitProductPanel, BorderLayout.CENTER);
        exitProductFrame.setLocationRelativeTo(null);

        JLabel nameLabel = new JLabel("Product Name:");
        JTextField nameTextField = new JTextField();
        JLabel quantityLabel = new JLabel("Exit Quantity:");
        JTextField quantityTextField = new JTextField();
        JLabel remarksLabel = new JLabel("Remarks:");
        JTextField remarksTextField = new JTextField();
        JButton exitButton = new JButton("Exit");

        exitProductPanel.add(nameLabel);
        exitProductPanel.add(nameTextField);
        exitProductPanel.add(quantityLabel);
        exitProductPanel.add(quantityTextField);
        exitProductPanel.add(remarksLabel);
        exitProductPanel.add(remarksTextField);
        exitProductPanel.add(exitButton);
        exitProductPanel.setLayout(new GridLayout(4, 2, 1, 1));

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                String quantityStr = quantityTextField.getText();
                String remarks = remarksTextField.getText();

                if (!name.isEmpty() && !quantityStr.isEmpty() && !remarks.isEmpty()) {
                    int quantity = Integer.parseInt(quantityStr);
                    Product product = inventorySystem.findProductByName(name);

                    if (product != null && product.getStock() >= quantity) {
                        product.addExitTransaction(remarks, quantity);
                        inventorySystem.saveProductsToFile("products.txt");
                        loadProductsToTable(); // Update the table after exiting a product
                        JOptionPane.showMessageDialog(null, "Exit recorded successfully!");
                        exitProductFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid product or insufficient stock!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter valid exit details.");
                }
            }
        });

        exitProductFrame.setVisible(true);
    }

    private void showExitTransactions() {
        JFrame exitTransactionsFrame = new JFrame("Exit Transactions");
        exitTransactionsFrame.setSize(400, 300);

        JPanel exitTransactionsPanel = new JPanel();
        exitTransactionsFrame.getContentPane().add(exitTransactionsPanel, BorderLayout.CENTER);
        exitTransactionsFrame.setLocationRelativeTo(null);

        String[] column = {"Type", "Product Name", "Remarks", "Quantity"};
        TableModel tableModel = new DefaultTableModel(column, 0);
        JTable exitTransactionsTable = new JTable(tableModel);
        JScrollPane sp = new JScrollPane(exitTransactionsTable);
        sp.setPreferredSize(new Dimension(350, 200));
        exitTransactionsTable.setModel(tableModel);

        exitTransactionsPanel.add(sp);

        loadExitTransactionsToTable(exitTransactionsTable);

        exitTransactionsFrame.setVisible(true);
    }

    private void loadExitTransactionsToTable(JTable exitTransactionsTable) {
        DefaultTableModel tableModel = (DefaultTableModel) exitTransactionsTable.getModel();
        tableModel.setRowCount(0);
    
        for (InventorySystem.ExitTransaction exitTransaction : inventorySystem.getExitTransactions()) {
            Object[] rowData = {"Exit", exitTransaction.getProductName(), exitTransaction.getRemarks(), exitTransaction.getQuantity()};
            tableModel.addRow(rowData);
        }
    }

    private void loadProductsToTable() {
        DefaultTableModel tableModel = (DefaultTableModel) jt.getModel();
        tableModel.setRowCount(0);

        for (Product product : inventorySystem.getProducts()) {
            Object[] rowData = {product.getID(), product.getName(), product.getStock(), product.getPrice()};
            tableModel.addRow(rowData);
        }
        }

    private void loadProductsFromTable() {
        inventorySystem.saveExitTransactionsToFile("products.txt");
        inventorySystem.loadProductsFromFile("products.txt");
        loadProductsToTable();
    }
}
