import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryManagementAppUI {
    private JFrame frame;
    private InventorySystem inventorySystem;
    //Jtable
    private JTable dataTable;
    private DefaultTableModel tableModel;

    //ScrollPane
    private JScrollPane scrollPane;

    //Jtable Base
    private JPanel tableBase;

    //add Button
    private JButton add, clear;

    public InventoryManagementAppUI(InventorySystem inventorySystem) {
        this.inventorySystem = inventorySystem;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Inventory Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        JPanel TablePanel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.NORTH);
        frame.getContentPane().add(TablePanel, BorderLayout.CENTER);
        placeComponents(panel, TablePanel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel, JPanel TablePanel) {

        JButton addButton = new JButton("Add Product");
        JButton viewButton = new JButton("View Products");
        JButton exitButton = new JButton("Exit Product");
        JButton viewExitButton = new JButton("View Exit Transactions");
        JButton editButton = new JButton("Edit Product Name");


         //Creating of Model
         tableModel = new DefaultTableModel(new Object[]{"ID", "Product Name", "Price", "Quantity"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        //Creation of Jtable
        dataTable = new JTable(tableModel);

        //Modifying permission Reordering
        dataTable.getTableHeader().setReorderingAllowed(false);

        //Making ScrollPane for Jtable able to scroll down
        scrollPane = new JScrollPane(dataTable);


        //Panel tableBase
        tableBase = new JPanel();
        tableBase.setBounds(90, 30, 900, 400);
        tableBase.setBackground(Color.white);
        tableBase.setLayout(new BorderLayout());
        tableBase.add(scrollPane, BorderLayout.CENTER);

        TablePanel.add(tableBase);

        panel.add(addButton);
        panel.add(viewButton);
        panel.add(exitButton);
        panel.add(viewExitButton);
        panel.add(editButton);
        panel.setLayout(new GridLayout(1,5,1,1));

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idStr = JOptionPane.showInputDialog("Enter product ID:");
                String name = JOptionPane.showInputDialog("Enter product name:");
                String stockStr = JOptionPane.showInputDialog("Enter initial stock quantity:");
                String priceStr = JOptionPane.showInputDialog("Enter product price:");
                int stock = Integer.parseInt(stockStr);
                int id = Integer.parseInt(idStr);
                int Price = Integer.parseInt(priceStr);
                inventorySystem.addProduct(id, name, Price, stock);
                tableModel.addRow(new Object[]{id, name, Price, stock}); 
                JOptionPane.showMessageDialog(null, "Product added successfully!");
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder productsInfo = new StringBuilder("Products List:\n");
                for (Product product : inventorySystem.getProducts()) {
                    productsInfo.append("PID: ").append(product.getID())
                    .append("\nProduct Name: ").append(product.getName())
                    .append("\n Price: ").append(product.getPrice())
                    .append("\nQuantity: ").append(product.getStock()).append("\n\n");
                }
                JOptionPane.showMessageDialog(null, productsInfo.toString());
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter product ID to exit:");
                Product product = inventorySystem.findProductByName(name);
                if (product != null) {
                    String quantityStr = JOptionPane.showInputDialog("Enter exit quantity:");
                    int quantity = Integer.parseInt(quantityStr);
                    String reason = JOptionPane.showInputDialog("Enter exit reason:");
                    product.addExitTransaction(reason, quantity);
                    JOptionPane.showMessageDialog(null, "Exit recorded successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Product not found!");
                }
            }
        });

        viewExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter product name to view exit transactions:");
                Product product = inventorySystem.findProductByName(name);
                if (product != null) {
                    StringBuilder exitTransactionsInfo = new StringBuilder("Exit Transactions:\n");
                    for (String transaction : product.getExitTransactions()) {
                        exitTransactionsInfo.append(transaction).append("\n");
                    }
                    exitTransactionsInfo.append("Remaining Stock: ").append(product.getStock());
                    JOptionPane.showMessageDialog(null, exitTransactionsInfo.toString());
                } else {
                    JOptionPane.showMessageDialog(null, "Product not found!");
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter product name to edit:");
                Product product = inventorySystem.findProductByName(name);
                if (product != null) {
                    String newName = JOptionPane.showInputDialog("Enter new product name:");
                    product.editProductName(newName);
                    JOptionPane.showMessageDialog(null, "Product name edited successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Product not found!");
                }
            }
        });
    }
}

