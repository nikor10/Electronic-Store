package FileHandler;

import models.Item;
import models.SupplierTable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StockFileManager {
    private static final File STOCK_FILE = new File("stock.dat");
    private static final int LOW_STOCK_THRESHOLD = 5;

    /**
     * Loads all items from the stock file.
     * @return A list of items in stock.
     */
    public static ArrayList<Item> loadStock() {
        ArrayList<Item> stock = new ArrayList<>();

        if (!STOCK_FILE.exists() || STOCK_FILE.length() == 0) {
            seedItemData();
            System.out.println("File sie: " + STOCK_FILE.length());
            return stock;
        }

        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(STOCK_FILE))) {
            while (true) {
                Item item = (Item) reader.readObject();
                stock.add(item);
            }
        } catch (EOFException e) {
            System.out.println("End of stock file reached.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading stock: " + e.getMessage());
        }

        return stock;
    }

    private static void seedItemData(){
        Item [] items = new Item[]{
                new Item("Battery", 1, "Electronics", "Alibaba", 10.0, 20.0, 100),
                new Item("Lamp", 2, "Appliances", "Temu", 5.5, 12.0, 200),
                new Item("iPhone", 3, "Electronics", "Apple", 50.0, 800.0, 50),
                new Item("iPad", 4, "Electronics", "Apple", 70.0, 700.0, 100),
                new Item("XBox", 5, "Electronics", "Microsoft", 30.0, 600.0, 50),
                new Item("SamsungTV", 6, "Electronics", "Samsung", 60.0, 800.0, 30),
                new Item("Laptop", 7, "Electronics", "DELL", 50.0, 1600.0, 50),
                new Item("TV", 8, "Electronics", "LG", 55.0, 900.0, 20),
        };
    }

    /**
     * Saves the current list of items to the stock file.
     * @param stock The list of items to be saved.
     */
    private static void saveStock(List<Item> stock) {
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(STOCK_FILE))) {
            for (Item item : stock) {
                writer.writeObject(item);
            }
            System.out.println("Stock updated successfully.");
        } catch (IOException e) {
            System.err.println("Error saving stock: " + e.getMessage());
        }
    }

    /**
     * Adds a new item to the stock.
     * @param newItem The item to be added.
     */
    public static void addNewItem(Item newItem) {
        List<Item> stock = loadStock();
        stock.add(newItem);
        saveStock(stock);
    }

    /**
     * Updates the quantity of an existing item in the stock.
     * @param itemName The name of the item to be updated.
     * @param quantity The new quantity to be added.
     */
    public static void updateStock(String itemName, int quantity) {
        List<Item> stock = loadStock();
        boolean itemFound = false;

        for (Item item : stock) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                item.setStock(item.getStock() + quantity);
                itemFound = true;
                System.out.println("Updated stock for item: " + itemName);
                break;
            }
        }

        if (itemFound) {
            saveStock(stock);
        } else {
            System.out.println("Item not found in stock.");
        }
    }

    /**
     * Alerts when the stock of any item falls below the threshold.
     */
    public static void alertLowStock() {
        List<Item> stock = loadStock();
        boolean lowStockFound = false;

        for (Item item : stock) {
            if (item.getStock() < LOW_STOCK_THRESHOLD) {
                System.out.println("Low stock alert! Item: " + item.getItemName() + ", Quantity: " + item.getStock());
                lowStockFound = true;
            }
        }

        if (!lowStockFound) {
            System.out.println("All items are sufficiently stocked.");
        }
    }

    /**
     * Retrieves the total value of the stock.
     * @return The total value of all items in stock.
     */
    public static double getTotalStockValue() {
        List<Item> stock = loadStock();
        double totalValue = 0.0;

        for (Item item : stock) {
            totalValue += item.getSellingPrice() * item.getStock();
        }

        return totalValue;
    }

    public static void deleteProduct(String productName) {
        List<Item> items = loadStock();
        for (Item item : items) {
            if (item.getItemName().equalsIgnoreCase(productName)) {
                items.remove(item);
            }
        }
        saveStock(items);
    }
//l

    /**
     * Displays all items in the stock.
     */
    public static void displayStock() {
        List<Item> stock = loadStock();
        if (stock.isEmpty()) {
            System.out.println("Stock is empty.");
        } else {
            System.out.println("Current Stock:");
            for (Item item : stock) {
                System.out.println(item);
            }
        }
    }
}
