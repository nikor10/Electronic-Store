package FileHandler;

import models.SupplierTable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierFileManager {
    private static final File SUPPLIERS_FILE = new File("suppliers.dat");

    public static ArrayList<SupplierTable> loadSuppliers() {
        ArrayList<SupplierTable> suppliers = new ArrayList<>();

        if (!SUPPLIERS_FILE.exists() || SUPPLIERS_FILE.length() == 0) {
            System.out.println("Suppliers file is empty or does not exist.");
            return suppliers;
        }

        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(SUPPLIERS_FILE))) {
            while (true) {
                SupplierTable supplier = (SupplierTable) reader.readObject();
                suppliers.add(supplier);
            }
        } catch (EOFException e) {
            System.out.println("End of suppliers file reached.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading suppliers: " + e.getMessage());
        }

        return suppliers;
    }

    public static void saveSuppliers(List<SupplierTable> suppliers) {
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(SUPPLIERS_FILE))) {
            for (SupplierTable supplier : suppliers) {
                writer.writeObject(supplier);
            }
            System.out.println("Suppliers saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving suppliers: " + e.getMessage());
        }
    }

    /**
     * Adds a new supplier to the list and saves it.
     * @param supplier The supplier to be added.
     */
    public static void addSupplier(SupplierTable supplier) {
        List<SupplierTable> suppliers = loadSuppliers();
        suppliers.add(supplier);
        saveSuppliers(suppliers);
    }

    /**
     * Updates an existing supplier's information by ID.
     * @param updatedSupplier The supplier with updated information.
     */
    public static void updateSupplier(SupplierTable updatedSupplier) {
        List<SupplierTable> suppliers = loadSuppliers();
        boolean supplierFound = false;

        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getNumber() == updatedSupplier.getNumber()) {
                suppliers.set(i, updatedSupplier);
                supplierFound = true;
                break;
            }
        }

        if (supplierFound) {
            saveSuppliers(suppliers);
            System.out.println("Supplier updated successfully.");
        } else {
            System.out.println("Supplier not found.");
        }
    }

    /**
     * Deletes a supplier by ID.
     * @param supplierName The ID of the supplier to be deleted.
     */
    public static void deleteSupplier(String supplierName) {
        List<SupplierTable> suppliers = loadSuppliers();
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getName().equals(supplierName)) {
                suppliers.remove(i);
            }
        }
        saveSuppliers(suppliers);
    }
//l
    public static void deleteProduct(String productName) {
        List<SupplierTable> suppliers = loadSuppliers();
        for(int i=0; i<suppliers.size(); i++) {
            if(suppliers.get(i).getProducts().contains(productName)) {
                ArrayList products = suppliers.get(i).getProducts();
                products.remove(productName);
                suppliers.get(i).setProducts(products);
                saveSuppliers(suppliers);
            }
        }
    }


    /**
     * Displays all suppliers.
     */
    public static void displaySuppliers() {
        List<SupplierTable> suppliers = loadSuppliers();
        if (suppliers.isEmpty()) {
            System.out.println("No suppliers found.");
        } else {
            System.out.println("List of Suppliers:");
            for (SupplierTable supplier : suppliers) {
                System.out.println(supplier);
            }
        }
    }

    /**
     * Finds a supplier by ID.
     * @param supplierId The ID of the supplier to find.
     * @return The supplier if found, otherwise null.
     */
    public static SupplierTable findSupplierById(int supplierId) {
        List<SupplierTable> suppliers = loadSuppliers();
        for (SupplierTable supplier : suppliers) {
            if (supplier.getNumber() == supplierId) {
                return supplier;
            }
        }
        return null;
    }
}
