import javax.swing.*;

public class InventoryManagementApp {
    public static void main(String[] args) {
        InventorySystem inventorySystem = new InventorySystem();
        SwingUtilities.invokeLater(() -> new InventoryManagementAppUI(inventorySystem));
    }
}
