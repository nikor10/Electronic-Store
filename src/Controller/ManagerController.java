package Controller;

import FileHandler.StockFileManager;
import FileHandler.SupplierFileManager;
import FileHandler.UserFileHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.chart.XYChart;
import java.lang.Number;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.*;
import java.lang.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.lang.String;
import java.time.Month;
import java.util.*;
import javafx.scene.control.TableView;


public class ManagerController {

    //----------supplier list function-------------------------------------------------------------------------
    // Initialize supplier data (for reference)
    ArrayList<SupplierTable> suppliersregularList = SupplierFileManager.loadSuppliers();
    ObservableList<SupplierTable> supplierList = FXCollections.observableArrayList(suppliersregularList);
    public TableView<SupplierTable> supplierTable;

    public ObservableList<SupplierTable> getSupplierList() {
        return supplierList;
    }

    // Get the filtered list of suppliers (for reference)
    public FilteredList<SupplierTable> getFilteredData() {
        return filteredData;
    }

    // Filter supplier list based on search criteria
    public void filterSupplierList(String searchText) {
        filteredData.setPredicate(supplier -> {
            if (searchText == null || searchText.isEmpty()) {
                return true; // Show all suppliers if search is empty
            }
            String lowerCaseFilter = searchText.toLowerCase();

            // Perform filtering logic based on the supplier attributes
            boolean matchesNumber = String.valueOf(supplier.getNumber()).contains(lowerCaseFilter);
            boolean matchesName = supplier.getName().toLowerCase().contains(lowerCaseFilter);
            boolean matchesContact = supplier.getSupplierContact().toLowerCase().contains(lowerCaseFilter);
            boolean matchesProducts = supplier.getProducts().stream().anyMatch(product -> product.toLowerCase().contains(lowerCaseFilter));

            return matchesNumber || matchesName || matchesContact || matchesProducts;
        });
    }
//------------------prod table funct--------------------------------------------------------------

    private TableView<Item> productsTable;
    ArrayList<Item> productregularList = StockFileManager.loadStock();
    ObservableList<Item> productList = FXCollections.observableArrayList(productregularList);

    // get all table data items
    public ObservableList<Item> getProductList() {
        return productList;
    }

    public ManagerController() {
    }

    // Ensure products table is initialized with a mutable ObservableList
    public void setProductsTable(TableView<Item> table) {
        this.productsTable = table;

        // Ensure productsTable is using an ObservableList that can be modified
        if (this.productsTable.getItems() == null) {
            this.productsTable.setItems(getProductList());
        }
    }

    public void updateItemQuantity(Item item, int newQuantity) {
        ObservableList<Item> productList = getProductList();
        for (Item currentItem : productList) {
            if (currentItem.getItemid() == item.getItemid()) {
                currentItem.setStock(newQuantity);
                StockFileManager.updateStock(currentItem.getItemName(), newQuantity);
                productsTable.refresh();
                break;
            }
        }

        if (productsTable != null) {
            productsTable.refresh();
        }
    }
    //l
    public void deleteItem(Item item) {
        ObservableList<Item> productList = getProductList(); // Get the list
        ObservableList<SupplierTable> supplierList = getSupplierList();
        productList.remove(item);
        StockFileManager.deleteProduct(item.getItemName());

        // Create a temporary list to store suppliers to remove
        List<SupplierTable> suppliersToRemove = new ArrayList<>();

        // Iterate through the suppliers
        for (SupplierTable supplier : supplierList) {
            // If the supplier has the product, remove it
            if (supplier.getProducts().contains(item.getItemName())) {
                supplier.getProducts().remove(item.getItemName());
                SupplierFileManager.deleteProduct(item.getItemName());

                if (supplier.getProducts().isEmpty()) {
                    suppliersToRemove.add(supplier);
                    SupplierFileManager.deleteSupplier(supplier.getName());
                }
            }
        }

        for (SupplierTable supplier : suppliersToRemove) {
            supplierList.remove(supplier);
        }

        for (int i = 0; i < supplierList.size(); i++) {
            supplierList.get(i).setNumber(i + 1);
        }

        SupplierFileManager.saveSuppliers(supplierList);

        if (productsTable != null) {
            productsTable.refresh();
        }
        if (supplierTable != null) {
            supplierTable.setItems(supplierList);
            supplierTable.refresh();
        }
    }




    // Method to add a new product item to the table
    public void addButton(String name, int itemid, int quantity, String section, String supplier, double purchasedPrice, double sellingPrice, String supplierContact) {
        // Get the actual list from getProductList (not the filtered version)
        ObservableList<Item> currentList = getProductList();
        ObservableList<SupplierTable> currentSupplierList = getSupplierList();
        Item newItem = new Item(name, itemid, section, supplier, purchasedPrice, sellingPrice, quantity);
        boolean found = false;
        for(int i=0; i<currentSupplierList.size(); i++) {
            if(currentSupplierList.get(i).getName().equalsIgnoreCase(supplier)) {
                currentList.add(newItem);// This will modify the ObservableList directly
                StockFileManager.addNewItem(newItem);
                found=true;
                ArrayList<String> products = currentSupplierList.get(i).getProducts();
                products.add(name);
                currentSupplierList.get(i).setProducts(products);
                SupplierFileManager.updateSupplier(currentSupplierList.get(i));
                if (supplierTable != null) {
                    supplierTable.refresh();
                }
                break;

            }
        }

        if(found == false) {
            int nr;
            if (!supplierList.isEmpty()) {
                nr = supplierList.getLast().getNumber() + 1;
            } else {
                nr = 1;
            }
            ArrayList <String> product1 = new ArrayList<>();
            product1.add(name);
            SupplierTable temp = new SupplierTable(nr, supplier, product1, supplierContact);
            supplierList.add(temp);
            currentList.add(newItem); // This will modify the ObservableList directly
            StockFileManager.addNewItem(newItem);
            SupplierFileManager.addSupplier(temp);
            if (productsTable != null) {
                productsTable.setItems(currentList);
                productsTable.refresh();
            }
        }
    }


    // Filtering logic for product data
    public FilteredList<Item> getFilteredProductData(String searchText) {
        ObservableList<Item> productList = getProductList();
        FilteredList<Item> filteredData = new FilteredList<>(productList, p -> true);

        filteredData.setPredicate(item -> {
            if (searchText == null || searchText.isEmpty()) {
                return true; // If search box is empty, show all products
            }
            String lowerCaseFilter = searchText.toLowerCase();

            // Check if search text matches any of the item's attributes
            boolean matchItemName = item.getItemName().toLowerCase().contains(lowerCaseFilter);
            boolean matchesItemId = String.valueOf(item.getItemid()).contains(lowerCaseFilter);
            boolean matchesStock = String.valueOf(item.getStock()).contains(lowerCaseFilter);
            boolean matchesSection = item.getSection().toLowerCase().contains(lowerCaseFilter);
            boolean matchedPurchasedPrice = String.valueOf(item.getPurchasedPrice()).contains(lowerCaseFilter);
            boolean matchedSellingPrice = String.valueOf(item.getSellingPrice()).contains(lowerCaseFilter);

            // Return true if any attribute matches
            return matchItemName || matchesItemId || matchesStock || matchesSection || matchedPurchasedPrice || matchedSellingPrice;
        });

        return filteredData;
    }


    //ll
//-------------user edit --------------------------------------------------------------------------------
    public void updateManagerProfile(Manager manager, String newUsername, String newPassword, LocalDate newDateOfBirth, String newEmail, String newPhoneNr, byte[] newImage) {
        manager.setUsername(newUsername);
        manager.setPassword(newPassword);
        manager.setBirthday(newDateOfBirth);
        manager.setEmail(newEmail);
        manager.setPhonenr(newPhoneNr);
        manager.setProfileImage(newImage);
        UserFileHandler.updateManager(manager);
    }


    //creating editIcon
    public ImageView createEditIcon(String path) {
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(path)));
        icon.setFitWidth(15);
        icon.setFitHeight(15);
        return icon;
    }

    // ----------creating button icons
    public ImageView createIcon(String path) {
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(path)));
        icon.setFitWidth(32);
        icon.setFitHeight(29);
        return icon;
    }

    FilteredList<SupplierTable> filteredData = new FilteredList<>(supplierList, p -> true);



//------------------------stat funct----------------------------------------------------------------


    static ObservableList<CustomData> data = FXCollections.observableArrayList(); // Initialize inside the method
    public ObservableList<CustomData> getStatisticsData() {
        ArrayList <Cashier> cashiers= UserFileHandler.loadCashiers();

        // Iterate through all cashiers
        for (Cashier cashier : cashiers) {
            // Iterate over each bill of the current cashier
            for (Bill bill : cashier.getAllCashierBills()) {
                // Add all items sold in the current bill to the data list
                for (Map.Entry<Item, Integer> entry : bill.getItemsSold().entrySet()) {
                    // Create a new custom entry with item, quantity, and cashier's name
                    Item item = entry.getKey();
                    Integer quantity = entry.getValue();
                    String cashierName = cashier.getUsername();
                    Integer billID = bill.getBillID(); // Get Bill ID
                    LocalDate billDate = bill.getDate(); // Get Bill Date
                    // Add the custom data to the list (you can create a new object here)
                    data.add(new CustomData(item, quantity, cashierName, billID, billDate));  // CustomData holds item, quantity, cashierName
                }
            }
        }

        return data;
    }


//--------------------------------------------------------------------------------------

    public static class CustomData {
        private Item item;
        private Integer quantity;
        private String cashierName;
        private LocalDate Billdate;
        private Integer billID; // Add bill ID
        private LocalDate billDate; // Add bill date

        public CustomData(Item item, Integer quantity, String cashierName, Integer billID, LocalDate billDate) {
            this.item = item;
            this.quantity = quantity;
            this.cashierName = cashierName;
            this.Billdate = LocalDate.now();
            this.billID = item.getItemid();
            this.billDate = LocalDate.now();
        }

        public Item getItem() {
            return item;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public String getCashierName() {
            return cashierName;
        }

        public Integer getBillID() { // Add getter for Bill ID
            return billID;
        }

        public LocalDate getDate() { // Add getter for Bill Date
            return billDate;
        }
    }

    // Helper methods to fetch metadata
    public  String getCashierNameForItem(Item item) {
        ArrayList <Cashier> cashiers= UserFileHandler.loadCashiers();

        for (Cashier cashier : cashiers) {
            for (Bill bill : cashier.getAllCashierBills()) {
                if (bill.getItemsSold().containsKey(item)) {
                    return cashier.getUsername(); // Return cashier username
                }
            }
        }
        return "Unknown";
    }

    public  int getBillIdForItem(Item item) {
        ArrayList <Cashier> cashiers= UserFileHandler.loadCashiers();

        for (Cashier cashier : cashiers) {

            for (Bill bill : cashier.getAllCashierBills()) {
                if (bill.getItemsSold().containsKey(item)) {
                    return bill.getBillID(); // Return the bill ID
                }
            }
        }
        return -1;
    }

    public  LocalDate getBillDateForItem(Item item) {
        ArrayList <Cashier> cashiers= UserFileHandler.loadCashiers();

        for (Cashier cashier : cashiers) {
            for (Bill bill : cashier.getAllCashierBills()) {
                if (bill.getItemsSold().containsKey(item)) {
                    return bill.getDate(); // Return bill date
                }
            }
        }
        return LocalDate.of(0000, 00, 00); // Return a default date if no match found
    }

    public FilteredList<CustomData> getFilteredData(String searchQuery) {
        // Wrap statisticsData in a FilteredList for search functionality
        FilteredList<CustomData> filteredData = new FilteredList<>(data, p -> true);

        // Apply search filtering
        filteredData.setPredicate(customData -> {
            if (searchQuery == null || searchQuery.isEmpty()) {
                return true; // Show all data if no query is provided
            }

            String lowerCaseFilter = searchQuery.toLowerCase();

            // Filter based on relevant fields
            return customData.getItem().getItemName().toLowerCase().contains(lowerCaseFilter) ||
                    String.valueOf(customData.getQuantity()).contains(lowerCaseFilter) ||
                    customData.getCashierName().toLowerCase().contains(lowerCaseFilter) ||
                    String.valueOf(customData.getItem().getSellingPrice() * customData.getQuantity()).contains(lowerCaseFilter) ||
                    String.valueOf(customData.getBillID()).contains(lowerCaseFilter) ||
                    (customData.getDate() != null && customData.getDate().toString().contains(lowerCaseFilter));
        });

        return filteredData;
    }
//--------------------------------------------weekly Revenue graph ----------------------

    private final ObservableList<XYChart.Data<String, Number>> weeklyRevenueData = FXCollections.observableArrayList();

    public Map<String, Double> getWeeklyRevenue() {
        Map<String, Double> weeklyRevenue = new LinkedHashMap<>();

        // Initialize all days of the week with 0.0 revenue
        DayOfWeek[] daysOfWeek = DayOfWeek.values();
        for (DayOfWeek day : daysOfWeek) {
            weeklyRevenue.put(day.name(), 0.0);
        }
        ArrayList <Cashier> cashiers= UserFileHandler.loadCashiers();

        // Aggregate revenue for each day
        for (Cashier cashier : cashiers) {
            for (Bill bill : cashier.getAllCashierBills()) {
                DayOfWeek billDay = bill.getDate().getDayOfWeek();
                String dayName = billDay.name();
                double totalRevenue = bill.getTotalAmount();
                weeklyRevenue.put(dayName, totalRevenue);
            }
        }

        return weeklyRevenue;
    }

    public double weeklyRevenueTotal(){
        ArrayList <Cashier> cashiers= UserFileHandler.loadCashiers();
        double weeklytotalRevenue = 0;
        for(Cashier cashier: cashiers) {
            for(int i=0; i<cashier.getAllCashierBills().size(); i++) {
                weeklytotalRevenue+=cashier.getAllCashierBills().get(i).getTotalAmount();
            }
        }
        return weeklytotalRevenue;
    }
    //---------------------monthly graph----------------------------------------------------

    private final ObservableList<XYChart.Data<String, Number>> monthlyRevenueData = FXCollections.observableArrayList();

    // Method to calculate monthly revenue
    public Map<String, Double> getMonthlyRevenue() {
        Map<String, Double> monthlyRevenue = new LinkedHashMap<>();

        // Initialize all months of the year with 0.0 revenue
        Month[] monthsOfYear = Month.values();
        for (Month month : monthsOfYear) {
            monthlyRevenue.put(month.name(), 0.0); // Initialize with 0.0
        }
        ArrayList <Cashier> cashiers= UserFileHandler.loadCashiers();

        // Aggregate revenue for each month
        for (Cashier cashier : cashiers) {
            for (Bill bill : cashier.getAllCashierBills()) {
                Month billMonth = bill.getDate().getMonth(); // Get the month of the bill
                String monthName = billMonth.name(); // Get the name of the month (e.g., JANUARY, FEBRUARY, etc.)
                double totalRevenue = bill.getTotalAmount();

                // Add the revenue to the correct month, accumulating if the month already exists
                monthlyRevenue.put(monthName, monthlyRevenue.getOrDefault(monthName, 0.0) + totalRevenue);
            }
        }

        return monthlyRevenue;
    }

    // Method to calculate total monthly revenue
    public double monthlyRevenueTotal() {
        ArrayList <Cashier> cashiers= UserFileHandler.loadCashiers();

        double totalMonthlyRevenue = 0;
        for (Cashier cashier : cashiers) {
            for (Bill bill : cashier.getAllCashierBills()) {
                totalMonthlyRevenue += bill.getTotalAmount(); // Sum up all the bill totals
            }
        }
        return totalMonthlyRevenue;
    }

    public double calculateTodayRevenue() {
        ArrayList <Cashier> cashiers= UserFileHandler.loadCashiers();

        double totalRevenue = 0;
        for (Cashier cashier : cashiers) {
            for (Bill bill : cashier.getAllCashierBills()) {
                totalRevenue += bill.getTotalAmount();
            }
        }
        return totalRevenue;
    }


}
